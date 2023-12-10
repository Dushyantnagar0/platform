package com.org.platform.services.interfaces;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.responses.LogInResponse;

public interface OtpService {
    LogInResponse sendAndSaveOtp(String emailId);
    String generateOtpValue(int length);
    EmailOtpBean validateOtp(String hashedOtp, OtpValidationRequest otpValidationRequest);
}
