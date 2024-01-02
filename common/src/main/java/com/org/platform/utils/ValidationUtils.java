package com.org.platform.utils;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.requests.TokenGenerationRequest;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ValidationUtils {

    public static void logInValidation(LogInRequest logInRequest) {
        if(isNull(logInRequest) || isEmpty(logInRequest.getEmailId())) throw new PlatformCoreException(INVALID_OTP_REQUEST);
    }

    public static void otpValidationInValidation(OtpValidationRequest otpValidationRequest) {
        if(isNull(otpValidationRequest) || isEmpty(otpValidationRequest.getOtp()) || isEmpty(otpValidationRequest.getRefId())) throw new PlatformCoreException(INVALID_OTP_VALIDATION_REQUEST);
    }

    public static void tokenRequestValidation(TokenGenerationRequest tokenGenerationRequest) {
        if(isNull(tokenGenerationRequest) || isEmpty(tokenGenerationRequest.getHashedOtp()) || isEmpty(tokenGenerationRequest.getCustomerId()) || isEmpty(tokenGenerationRequest.getEmailId()))
            throw new PlatformCoreException(INTERNAL_SERVER_ERROR);
    }

    public static void initialTokenValidation(String tokenId, String customerId) {
        if(isEmpty(tokenId) || isEmpty(customerId))
            throw new PlatformCoreException(AUTHENTICATION_FAILED);
    }

}
