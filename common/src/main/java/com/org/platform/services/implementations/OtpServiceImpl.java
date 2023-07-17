package com.org.platform.services.implementations;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.services.interfaces.OtpService;
import com.org.platform.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    public String sendAndSaveOtp(String emailId) {
        String otp = generateOtpValue(6);
        String hashedOtp = HashUtils.hash(otp);
//        emailService.sendEmail(emailId, otp);
        // for testing purpose
        return otpRepository.saveEmailOtpBean(emailId, hashedOtp) + " " + otp;
    }

    public String generateOtpValue(int length) {
        int otp = length == 4 ? current().nextInt(1000, 9999) : current().nextInt(100000, 999999);
        return String.valueOf(otp);
    }

    @Override
    public EmailOtpBean validateOtp(OtpValidationRequest otpValidationRequest) {
        String refId = otpValidationRequest.getRefId();
        String hashedOtpRequest = HashUtils.hash(otpValidationRequest.getOtp());
        EmailOtpBean existingEmailOtpBean = otpRepository.getEmailOtpBeanByRefId(refId);
        if(hashedOtpRequest.equalsIgnoreCase(existingEmailOtpBean.getHashedOtp())) return existingEmailOtpBean;
        return null;
    }

}
