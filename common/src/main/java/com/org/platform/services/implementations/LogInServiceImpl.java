package com.org.platform.services.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.beans.EmailOtpBean;
import com.org.platform.helpers.CustomerAccountHelper;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.requests.TokenGenerationRequest;
import com.org.platform.responses.LogInResponse;
import com.org.platform.responses.OtpValidationResponse;
import com.org.platform.services.interfaces.*;
import com.org.platform.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.FAILED_TO_SEND_OTP;
import static com.org.platform.errors.errorCodes.PlatformErrorCodes.FAILED_TO_VALIDATE_OTP;
import static com.org.platform.services.HeaderContextService.getCurrentCustomerId;
import static com.org.platform.services.HeaderContextService.getCurrentUserEmailId;
import static com.org.platform.utils.CommonUtils.handleExceptionAndCreateResponse;
import static com.org.platform.utils.Constants.FAILED_TO_VALIDATE_OTP_MESSAGE;
import static com.org.platform.utils.Constants.SOMETHING_WENT_WRONG_WHILE_SENDING_OTP;
import static com.org.platform.utils.ValidationUtils.logInValidation;
import static com.org.platform.utils.ValidationUtils.otpValidationInValidation;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInServiceImpl implements LogInService {

    private final OtpService otpService;
    private final TokenService tokenService;
    private final CustomerAccountService customerAccountService;
    private final CustomerAccountHelper customerAccountHelper;
    private final AdminMetaDataService adminMetaDataService;


    @Override
    public LogInResponse doLogin(LogInRequest logInRequest) {
        logInValidation(logInRequest);
        try {
            CustomerAccountRequest customerAccountRequest = customerAccountHelper.createCustomerAccountCreateRequest(logInRequest);
            customerAccountService.createOrUpdateCustomerAccount(customerAccountRequest);
            return otpService.sendAndSaveOtp(logInRequest.getEmailId());
        } catch (Exception e) {
            handleExceptionAndCreateResponse(e, FAILED_TO_SEND_OTP, SOMETHING_WENT_WRONG_WHILE_SENDING_OTP);
        }
        return null;
    }

    @Override
    public OtpValidationResponse validateOtp(OtpValidationRequest otpValidationRequest) {
        otpValidationInValidation(otpValidationRequest);
        try {
            String hashedOtp = HashUtils.hash(otpValidationRequest.getOtp());
            EmailOtpBean emailOtpBean = otpService.validateOtp(hashedOtp, otpValidationRequest);
            String accessType = adminMetaDataService.fetchAccessLevelForAnEmailId(emailOtpBean.getEmailId());
            CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(emailOtpBean.getEmailId());
            String token = tokenService.generateJwtToken(new TokenGenerationRequest(emailOtpBean.getEmailId(), customerAccount.getCustomerId(), hashedOtp ,true), accessType);
            customerAccount.setToken(token);
            customerAccount.setUserAccessType(accessType);
            customerAccountService.saveCustomerAccount(customerAccount);
            return new OtpValidationResponse(token);
        } catch (Exception e) {
            handleExceptionAndCreateResponse(e, FAILED_TO_VALIDATE_OTP, FAILED_TO_VALIDATE_OTP_MESSAGE);
        }
        return null;
    }

    @Override
    public void doLogout() {
        String emailId = getCurrentUserEmailId();
        log.info("emailId in logout api : {}", emailId);
        CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(emailId);
        customerAccount.setToken(null);
        customerAccountService.saveCustomerAccount(customerAccount);
    }

    @Override
    public CustomerAccount getLoginData() {
        String customerId = getCurrentCustomerId();
        log.info("customerId in login data api : {}", customerId);
        return customerAccountService.getCustomerAccountByCustomerId(customerId);
    }

}
