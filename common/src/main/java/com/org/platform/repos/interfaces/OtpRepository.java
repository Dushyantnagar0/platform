package com.org.platform.repos.interfaces;

import com.org.platform.beans.EmailOtpBean;

public interface OtpRepository {

    void saveEmailOtpBean(String refId, EmailOtpBean emailOtpBean);
    EmailOtpBean getEmailOtpBeanByRefId(String refId);

}
