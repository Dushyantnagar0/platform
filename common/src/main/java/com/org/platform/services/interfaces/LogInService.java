package com.org.platform.services.interfaces;


import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.responses.LogInResponse;
import com.org.platform.responses.OtpValidationResponse;

public interface LogInService {
    LogInResponse doLogin(LogInRequest logInRequest);
    OtpValidationResponse validateOtp(OtpValidationRequest otpValidationRequest);
    boolean doLogout();

}
