package com.org.platform.helpers;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.LogInRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
public class CustomerAccountHelper {

    public CustomerAccount createNewOrUpdateCustomerAccount(CustomerAccount customerAccount, CustomerAccountRequest customerAccountRequest) {
        if(isNull(customerAccount)) {
            customerAccount = new CustomerAccount();
        }
        if(isBlank(customerAccount.getCustomerId())) customerAccount.setCustomerId(UUID.randomUUID().toString());
        if(isBlank(customerAccount.getEmailId())) customerAccount.setEmailId(customerAccountRequest.getEmailId());
        customerAccount.setEmailId(customerAccountRequest.getEmailId());
        customerAccount.setPhoneNumber(customerAccount.getPhoneNumber());
        customerAccount.setFirstName(customerAccountRequest.getFirstName());
        customerAccount.setLastName(customerAccountRequest.getLastName());
        customerAccount.setAddress1(customerAccountRequest.getAddress1());
        customerAccount.setAddress2(customerAccountRequest.getAddress2());
        customerAccount.setCity(customerAccountRequest.getCity());
        customerAccount.setZipCode(customerAccountRequest.getZipCode());
        return customerAccount;
    }

    public CustomerAccountRequest createCustomerAccountCreateRequest(LogInRequest logInRequest) {
        if(isNull(logInRequest)) return null;
        CustomerAccountRequest customerAccountRequest = new CustomerAccountRequest();
        customerAccountRequest.setEmailId(logInRequest.getEmailId());
        customerAccountRequest.setPhoneNumber(logInRequest.getPhoneNumber());
        return customerAccountRequest;
    }
}
