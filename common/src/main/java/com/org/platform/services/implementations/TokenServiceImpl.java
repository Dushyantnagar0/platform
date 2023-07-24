package com.org.platform.services.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.beans.EmailOtpBean;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.requests.TokenGenerationRequest;
import com.org.platform.services.interfaces.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.org.platform.utils.Constants.PLATFORM_LOGIN;
import static com.org.platform.utils.Constants.SECRET_KEY;
import static com.org.platform.utils.ValidationUtils.tokenRequestValidation;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final OtpRepository otpRepository;
    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public String generateJwtToken(TokenGenerationRequest tokenGenerationRequest) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
        tokenRequestValidation(tokenGenerationRequest);
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("emailId", tokenGenerationRequest.getEmailId())
                .claim("refId", tokenGenerationRequest.getRefId())
                .setSubject(PLATFORM_LOGIN)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(100l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
        return jwtToken;
    }

    @Override
    public boolean validateJwtToken(String token, String refId) {

        CustomerAccount customerAccount = customerAccountRepository.getCustomerAccountByCustomerId(refId);
        if(isNull(customerAccount)) return false;
        EmailOtpBean emailOtpBean = otpRepository.getEmailOtpBeanByEmailId(customerAccount.getEmailId());
          /*
          Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
          Jws<Claims> jwt = Jwts.parserBuilder()
                  .setSigningKey(hmacKey)
                  .build()
                  .parseClaimsJws(token);
          try {
              log.info("jwt : {}", new ObjectMapper().writeValueAsString(jwt));
          } catch (JsonProcessingException e) {
              throw new RuntimeException(e);
          }
          return jwt.getBody().get("refId").equals(refId);
           */
        return nonNull(emailOtpBean) && token.equals(emailOtpBean.getToken());
    }

}
