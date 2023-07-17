package com.org.platform.utils;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.requests.TokenGenerationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.org.platform.errors.errorCodes.LoginError.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
public class ValidationUtils {

    private final OtpRepository otpRepository;

    public static void logInValidation(LogInRequest logInRequest) {
        if(isNull(logInRequest) || isEmpty(logInRequest.getEmailId())) throw new PlatformCoreException(INVALID_OTP_REQUEST);
    }

    public static void otpValidationInValidation(OtpValidationRequest otpValidationRequest) {
        if(isNull(otpValidationRequest) || isEmpty(otpValidationRequest.getOtp()) || isEmpty(otpValidationRequest.getRefId())) throw new PlatformCoreException(INVALID_OTP_VALIDATION_REQUEST);
    }

    public static void tokenRequestValidation(TokenGenerationRequest tokenGenerationRequest) {
        if(isNull(tokenGenerationRequest) || isEmpty(tokenGenerationRequest.getOtp()) || isEmpty(tokenGenerationRequest.getRefId()) || isEmpty(tokenGenerationRequest.getEmailId()))
            throw new PlatformCoreException(INTERNAL_SERVER_ERROR);
    }

}
