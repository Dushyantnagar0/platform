package com.org.platform.services.implementations;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.requests.TokenGenerationRequest;
import com.org.platform.responses.LogInResponse;
import com.org.platform.responses.OtpValidationResponse;
import com.org.platform.services.interfaces.LogInService;
import com.org.platform.services.interfaces.OtpService;
import com.org.platform.services.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.org.platform.errors.errorCodes.LoginError.*;
import static com.org.platform.utils.ValidationUtils.logInValidation;
import static com.org.platform.utils.ValidationUtils.otpValidationInValidation;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class LogInServiceImpl implements LogInService {

    private final OtpService otpService;
    private final OtpRepository otpRepository;
    private final TokenService tokenService;


    @Override
    public LogInResponse doLogin(LogInRequest logInRequest) {
        logInValidation(logInRequest);
        try {
            String refId = otpService.sendAndSaveOtp(logInRequest.getEmailId());
            return new LogInResponse(refId);
        } catch (Exception e) {
            throw new PlatformCoreException(FAILED_TO_SEND_OTP);
        }
    }

    @Override
    public OtpValidationResponse validateOtp(OtpValidationRequest otpValidationRequest) {
        otpValidationInValidation(otpValidationRequest);
        try {
            EmailOtpBean emailOtpBean = otpService.validateOtp(otpValidationRequest);
            if(isNull(emailOtpBean))
                throw new PlatformCoreException(AUTHENTICATION_FAILED);
            String token = tokenService.generateJwtToken(new TokenGenerationRequest(emailOtpBean.getEmailId(), emailOtpBean.getRefId(), otpValidationRequest.getOtp()));
            emailOtpBean.setToken(token);
            EmailOtpBean existingEmailOtpBean = otpRepository.getEmailOtpBeanByRefId(emailOtpBean.getRefId());
            // TODO : invalidate token

            otpRepository.saveEmailOtpBean(emailOtpBean, existingEmailOtpBean);
            return new OtpValidationResponse(token);
        } catch (Exception e) {
            throw new PlatformCoreException(FAILED_TO_VALIDATE_OTP);
        }
    }

}
