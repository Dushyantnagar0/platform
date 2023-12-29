package com.org.platform.repos.interfaces;

import com.org.platform.beans.CustomerAccount;

public interface CustomerAccountRepository {

    CustomerAccount saveCustomerAccount(CustomerAccount customerAccount);
    CustomerAccount getCustomerAccountByEmailId(String emailId);
    CustomerAccount getCustomerAccountByEmailIdCached(String emailId);
    CustomerAccount getCustomerAccountByCustomerId(String customerId);
    CustomerAccount getCustomerAccountByCustomerIdCached(String customerId);
}
