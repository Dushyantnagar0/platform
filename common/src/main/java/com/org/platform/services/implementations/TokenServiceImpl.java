package com.org.platform.services.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.beans.EmailOtpBean;
import com.org.platform.enums.UserAccessType;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.requests.TokenGenerationRequest;
import com.org.platform.services.interfaces.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.org.platform.errors.errorCodes.LoginError.AUTHENTICATION_FAILED;
import static com.org.platform.services.HeaderContextService.createHeaderContextFromHttpHeaders;
import static com.org.platform.utils.Constants.PLATFORM_LOGIN;
import static com.org.platform.utils.Constants.SECRET_KEY;
import static com.org.platform.utils.HeaderConstants.*;
import static com.org.platform.utils.ValidationUtils.initialTokenValidation;
import static com.org.platform.utils.ValidationUtils.tokenRequestValidation;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final OtpRepository otpRepository;
    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public String generateJwtToken(TokenGenerationRequest tokenGenerationRequest, String accessType) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
        tokenRequestValidation(tokenGenerationRequest);
        Instant now = Instant.now();
        return Jwts.builder()
                .claim(EMAIL_ID_KEY, tokenGenerationRequest.getEmailId())
                .claim(REF_ID_KEY, tokenGenerationRequest.getRefId())
                .claim(UNIQUE_ID_KEY, tokenGenerationRequest.getHashedOtp())
                .claim(VALIDATION_KEY, tokenGenerationRequest.isValid())
                .claim(USER_TYPE_KEY, accessType)
                .setSubject(PLATFORM_LOGIN)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(100l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
    }

    @Override
    public boolean validateJwtTokenAndCreateHeaderMap(HttpServletRequest httpRequest, String tokenId, String customerId, boolean isForAdmin) {
        initialTokenValidation(tokenId, customerId);
        CustomerAccount customerAccount = customerAccountRepository.getCustomerAccountByCustomerId(customerId);
        if(nonNull(customerAccount)) {
            Map<String, Object> headerMap = createHeaderMapFromToken(tokenId);
            EmailOtpBean emailOtpBean = otpRepository.getEmailOtpBeanByEmailId(customerAccount.getEmailId());
            boolean validation = nonNull(emailOtpBean) && tokenId.equals(emailOtpBean.getToken());
            if (isForAdmin) validation = validation && UserAccessType.ADMIN.name().equals(headerMap.get(USER_TYPE_KEY));
            if (validation) {
                createHeaderContextFromHttpHeaders(httpRequest, headerMap);
                return true;
            }
        }
        throw new PlatformCoreException(AUTHENTICATION_FAILED);
    }

    private Map<String, Object> createHeaderMapFromToken(String tokenId) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
        Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(tokenId);
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(VALIDATION_KEY, jwt.getBody().get(VALIDATION_KEY));
        headerMap.put(CUSTOMER_ID_KEY, jwt.getBody().get(REF_ID_KEY));
        headerMap.put(USER_TYPE_KEY, jwt.getBody().get(USER_TYPE_KEY));
        headerMap.put(EMAIL_ID_KEY, jwt.getBody().get(EMAIL_ID_KEY));
        return headerMap;
    }
}
