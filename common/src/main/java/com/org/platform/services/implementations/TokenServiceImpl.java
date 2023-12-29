package com.org.platform.services.implementations;

import com.org.platform.annotations.TrackRunTime;
import com.org.platform.beans.CustomerAccount;
import com.org.platform.enums.UserAccessType;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
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

import static com.org.platform.enums.UserAccessType.ADMIN;
import static com.org.platform.services.HeaderContextService.createHeaderContextFromHttpHeaders;
import static com.org.platform.services.HeaderContextService.getCurrentCustomerId;
import static com.org.platform.utils.Constants.PLATFORM_LOGIN;
import static com.org.platform.utils.Constants.SECRET_KEY;
import static com.org.platform.utils.HeaderConstants.*;
import static com.org.platform.utils.ValidationUtils.initialTokenValidation;
import static com.org.platform.utils.ValidationUtils.tokenRequestValidation;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public String generateJwtToken(TokenGenerationRequest tokenGenerationRequest, String accessType) {
        tokenRequestValidation(tokenGenerationRequest);
        Instant now = Instant.now();
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .claim(EMAIL_ID_KEY, tokenGenerationRequest.getEmailId())
                .claim(CUSTOMER_ID_KEY, tokenGenerationRequest.getCustomerId())
                .claim(UNIQUE_ID_KEY, tokenGenerationRequest.getHashedOtp())
                .claim(VALIDATION_KEY, tokenGenerationRequest.isValid())
                .claim(USER_TYPE_KEY, accessType)
                .setSubject(PLATFORM_LOGIN)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1440l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
    }

    @Override
    @TrackRunTime
    public boolean validateJwtTokenAndCreateHeaderMap(HttpServletRequest httpRequest, boolean isForAdminApi) {
        try {
            String tokenId = httpRequest.getHeader(API_TOKEN_KEY);
            Map<String, Object> headerMap = createHeaderMapFromToken(tokenId);
            createHeaderContextFromHttpHeaders(httpRequest, headerMap);
            String customerId = getCurrentCustomerId();
            initialTokenValidation(tokenId, customerId);
            CustomerAccount customerAccount = customerAccountRepository.getCustomerAccountByCustomerIdCached(customerId);
            if (nonNull(customerAccount)) {
                return tokenId.equals(customerAccount.getToken()) && (!isForAdminApi || ADMIN.name().equals(headerMap.get(USER_TYPE_KEY)));
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Jws<Claims> decodeJwtToken(String tokenId) {
        try {
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
            return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(tokenId);
        }  catch (Exception e) {
            log.info("Exception occurred while token computation : ", e);
        }
        return null;
    }

    private Map<String, Object> createHeaderMapFromToken(String tokenId) {
        Map<String, Object> headerMap = new HashMap<>();
        try {
            Jws<Claims> jwt = decodeJwtToken(tokenId);
            Object accessType = jwt.getBody().get(USER_TYPE_KEY);
            headerMap.put(VALIDATION_KEY, jwt.getBody().get(VALIDATION_KEY));
            headerMap.put(USER_TYPE_KEY, isNull(accessType) ? UserAccessType.CUSTOMER.name() : accessType);
            headerMap.put(EMAIL_ID_KEY, jwt.getBody().get(EMAIL_ID_KEY));
            headerMap.put(CUSTOMER_ID_KEY, jwt.getBody().get(CUSTOMER_ID_KEY));
            headerMap.put(API_TOKEN_KEY, tokenId);
        } catch (Exception e) {
            log.info("Exception occurred while header map creation from token : ", e);
        }
        return headerMap;
    }
}
