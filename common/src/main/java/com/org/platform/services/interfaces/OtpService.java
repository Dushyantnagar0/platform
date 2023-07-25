package com.org.platform.services.interfaces;

import com.org.platform.beans.EmailOtpBean;

public interface OtpService {
    String sendAndSaveOtp(String emailId);
    String generateOtpValue(int length);
    EmailOtpBean validateOtp(String hashedOtp, String emailId);
    EmailOtpBean inValidateToken(String emailId);
}
