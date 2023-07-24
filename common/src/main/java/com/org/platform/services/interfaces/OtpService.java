package com.org.platform.services.interfaces;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.requests.OtpValidationRequest;

public interface OtpService {
    String sendAndSaveOtp(String emailId);
    String generateOtpValue(int length);
    EmailOtpBean validateOtp(OtpValidationRequest otpValidationRequest, String emailId);
}
