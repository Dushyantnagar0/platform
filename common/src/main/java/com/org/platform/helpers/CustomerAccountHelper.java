package com.org.platform.helpers;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.mappers.PlatformEnhancedMapper;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerAccountHelper {

    private final PlatformEnhancedMapper platformEnhancedMapper;

    public CustomerAccount createNewOrUpdateCustomerAccount(CustomerAccount customerAccount, CustomerAccountRequest customerAccountRequest) {
        if(isNull(customerAccount)) {
            customerAccount = new CustomerAccount();
        }
        if(isBlank(customerAccount.getCustomerId())) customerAccount.setCustomerId(UUID.randomUUID().toString());
        if(isBlank(customerAccount.getEmailId())) customerAccount.setEmailId(customerAccountRequest.getEmailId());
        platformEnhancedMapper.populateCustomerAccountFromCustomerAccountRequest(customerAccountRequest, customerAccount);
        return customerAccount;
    }

    public CustomerAccountRequest createCustomerAccountCreateRequest(LogInRequest logInRequest) {
        if(isNull(logInRequest)) return null;
        CustomerAccountRequest customerAccountRequest = new CustomerAccountRequest();
        customerAccountRequest.setEmailId(logInRequest.getEmailId());
        customerAccountRequest.setPhoneNumber(logInRequest.getPhoneNumber());
        return customerAccountRequest;
    }

    public void populateCustomerAccountToLatest(ProfileUpdateRequest profileUpdateRequest, CustomerAccount customerAccount) {
        if(isNull(customerAccount) || isNull(profileUpdateRequest)) return;
        platformEnhancedMapper.populateCustomerAccountFromProfileUpdate(profileUpdateRequest, customerAccount);
    }
}
