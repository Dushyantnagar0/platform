package com.org.platform.services.interfaces;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.ProfileUpdateRequest;
import com.org.platform.responses.CustomerAccountResponse;

public interface CustomerAccountService {
    CustomerAccount getCustomerAccountByEmail(String emailId);
    CustomerAccount getCustomerAccountByCustomerId(String customerId);
    CustomerAccount createOrUpdateCustomerAccount(CustomerAccountRequest customerAccountRequest);
    CustomerAccount saveCustomerAccount(CustomerAccount customerAccount);
    CustomerAccountResponse updateCustomerAccount(ProfileUpdateRequest profileUpdateRequest);
}
