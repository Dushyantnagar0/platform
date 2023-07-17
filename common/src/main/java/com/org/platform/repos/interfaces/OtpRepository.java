package com.org.platform.repos.interfaces;

import com.org.platform.beans.EmailOtpBean;

public interface OtpRepository {

    String saveEmailOtpBean(String emailId, String hashedOtp);
    EmailOtpBean saveEmailOtpBean(EmailOtpBean emailOtpBean, EmailOtpBean existingEmailOtpBean);
    EmailOtpBean getEmailOtpBeanByRefId(String refId);

}
