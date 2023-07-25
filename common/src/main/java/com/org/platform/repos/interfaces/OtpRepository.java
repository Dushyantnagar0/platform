package com.org.platform.repos.interfaces;

import com.org.platform.beans.EmailOtpBean;

public interface OtpRepository {

    void saveEmailOtpBean(String emailId, String hashedOtp);
    EmailOtpBean saveEmailOtpBean(EmailOtpBean emailOtpBean);
    EmailOtpBean getEmailOtpBeanByEmailId(String emailId);

}
