package com.org.platform.services.implementations;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.services.interfaces.CustomerAccountService;
import com.org.platform.services.interfaces.OtpService;
import com.org.platform.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.org.platform.errors.errorCodes.LoginError.AUTHENTICATION_FAILED;
import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    public String sendAndSaveOtp(String emailId) {
        String otp = generateOtpValue(6);
        String hashedOtp = HashUtils.hash(otp);
//        TODO : send OTP email
//        emailService.sendEmail(emailId, otp);
        otpRepository.saveEmailOtpBean(emailId, hashedOtp);
        return otp;
    }

    public String generateOtpValue(int length) {
        int otp = length == 4 ? current().nextInt(1000, 9999) : current().nextInt(100000, 999999);
        return String.valueOf(otp);
    }

    @Override
    public EmailOtpBean validateOtp(String hashedOtpRequest, String emailId) {
        EmailOtpBean existingEmailOtpBean = otpRepository.getEmailOtpBeanByEmailId(emailId);
        if(hashedOtpRequest.equals(existingEmailOtpBean.getHashedOtp())) return existingEmailOtpBean;
        throw new PlatformCoreException(AUTHENTICATION_FAILED);
    }

}
