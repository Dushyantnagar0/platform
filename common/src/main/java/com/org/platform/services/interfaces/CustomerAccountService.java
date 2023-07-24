package com.org.platform.services.interfaces;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.requests.CustomerAccountRequest;

public interface CustomerAccountService {
    CustomerAccount getCustomerAccountByEmail(String emailId);
    CustomerAccount getCustomerAccountByCustomerId(String customerId);
    CustomerAccount createOrUpdateCustomerAccount(CustomerAccountRequest customerAccountRequest);
    String getEmailIdFromRefId(String refId);
}
