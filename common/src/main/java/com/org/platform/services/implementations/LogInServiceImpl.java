package com.org.platform.services.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.beans.EmailOtpBean;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.helpers.CustomerAccountHelper;
import com.org.platform.repos.interfaces.OtpRepository;
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
import static com.org.platform.services.HeaderContextService.getCurrentUserEmailId;
import static com.org.platform.utils.ValidationUtils.logInValidation;
import static com.org.platform.utils.ValidationUtils.otpValidationInValidation;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInServiceImpl implements LogInService {

    private final OtpService otpService;
    private final OtpRepository otpRepository;
    private final TokenService tokenService;
    private final CustomerAccountService customerAccountService;
    private final CustomerAccountHelper customerAccountHelper;
    private final AdminMetaDataService adminMetaDataService;


    @Override
    public LogInResponse doLogin(LogInRequest logInRequest) {
        logInValidation(logInRequest);
        try {
            CustomerAccountRequest customerAccountRequest = customerAccountHelper.createCustomerAccountCreateRequest(logInRequest);
            CustomerAccount customerAccount = customerAccountService.createOrUpdateCustomerAccount(customerAccountRequest);
            // TODO : save hashed OTP with refId in cache
            String otp = otpService.sendAndSaveOtp(logInRequest.getEmailId());
            // TODO : for testing
            return new LogInResponse(customerAccount.getCustomerId() + " " + otp);
        } catch (Exception e) {
            throw new PlatformCoreException(FAILED_TO_SEND_OTP, "something wrong occurred while sending OTP");
        }
    }

    @Override
    public OtpValidationResponse validateOtp(OtpValidationRequest otpValidationRequest) {
        otpValidationInValidation(otpValidationRequest);
        try {
            String emailId = customerAccountService.getEmailIdFromRefId(otpValidationRequest.getRefId());
            String hashedOtp = HashUtils.hash(otpValidationRequest.getOtp());
            EmailOtpBean emailOtpBean = otpService.validateOtp(hashedOtp, emailId);
            String accessType = adminMetaDataService.fetchAccessLevelForAnEmailId(emailId);
            String token = tokenService.generateJwtToken(new TokenGenerationRequest(emailOtpBean.getEmailId(), otpValidationRequest.getRefId(), hashedOtp ,true), accessType);
            emailOtpBean.setToken(token);
            otpRepository.saveEmailOtpBean(emailOtpBean);
            return new OtpValidationResponse(token);
        } catch (Exception e) {
            throw new PlatformCoreException(FAILED_TO_VALIDATE_OTP, "failed to validate OTP");
        }
    }

    @Override
    public boolean doLogout() {
        String emailId = getCurrentUserEmailId();
        log.info("emailId in logout api : {}", emailId);
        return nonNull(otpService.inValidateToken(emailId));
    }

}
