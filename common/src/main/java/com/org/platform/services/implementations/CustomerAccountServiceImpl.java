package com.org.platform.services.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.helpers.CustomerAccountHelper;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.services.interfaces.CustomerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INTERNAL_SERVER_ERROR;
import static com.org.platform.errors.errorCodes.PlatformErrorCodes.NO_CUSTOMER_FOUND;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerAccountHelper customerAccountHelper;

    public CustomerAccount getCustomerAccountByEmail(String emailId) {
        if(isNotBlank(emailId)) {
            return customerAccountRepository.getCustomerAccountByEmailId(emailId);
        }
        return null;
    }

    public CustomerAccount getCustomerAccountByCustomerId(String customerId) {
        if(isNotBlank(customerId)) {
            return customerAccountRepository.getCustomerAccountByCustomerId(customerId);
        }
        return null;
    }

    public CustomerAccount createOrUpdateCustomerAccount(CustomerAccountRequest customerAccountRequest) {
        if(isNull(customerAccountRequest)) return null;
        CustomerAccount customerAccount = null;
        if(isNotBlank(customerAccountRequest.getEmailId())) customerAccount = customerAccountRepository.getCustomerAccountByEmailId(customerAccountRequest.getEmailId());
        customerAccount = customerAccountHelper.createNewOrUpdateCustomerAccount(customerAccount, customerAccountRequest);
        return customerAccountRepository.saveCustomerAccount(customerAccount);
    }

    @Override
    public String getEmailIdFromRefId(String refId) {
        if(isNotBlank(refId)) {
            CustomerAccount customerAccount = customerAccountRepository.getCustomerAccountByCustomerId(refId);
            if(isNull(customerAccount)) throw new PlatformCoreException(NO_CUSTOMER_FOUND);
            return customerAccount.getEmailId();
        }
        throw new PlatformCoreException(INTERNAL_SERVER_ERROR);
    }

}
