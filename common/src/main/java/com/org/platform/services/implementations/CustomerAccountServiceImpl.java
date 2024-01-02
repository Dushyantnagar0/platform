package com.org.platform.services.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.helpers.CustomerAccountHelper;
import com.org.platform.mappers.PlatformMapper;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.ProfileUpdateRequest;
import com.org.platform.responses.CustomerAccountResponse;
import com.org.platform.services.interfaces.CustomerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.org.platform.services.HeaderContextService.getCurrentCustomerId;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerAccountHelper customerAccountHelper;
    private final PlatformMapper platformMapper;

    public CustomerAccount getCustomerAccountByEmail(String emailId) {
        if(isNotBlank(emailId)) {
            return customerAccountRepository.getCustomerAccountByEmailIdCached(emailId);
        }
        return null;
    }

    public CustomerAccount getCustomerAccountByCustomerId(String customerId) {
        if(isNotBlank(customerId)) {
            return customerAccountRepository.getCustomerAccountByCustomerIdCached(customerId);
        }
        return null;
    }

    public CustomerAccount createOrUpdateCustomerAccount(CustomerAccountRequest customerAccountRequest) {
        if(isNull(customerAccountRequest)) return null;
        CustomerAccount customerAccount = null;
        if(isNotBlank(customerAccountRequest.getEmailId())) customerAccount = customerAccountRepository.getCustomerAccountByEmailIdCached(customerAccountRequest.getEmailId());
        customerAccount = customerAccountHelper.createNewOrUpdateCustomerAccount(customerAccount, customerAccountRequest);
        return saveCustomerAccount(customerAccount);
    }

    @Override
    public CustomerAccount saveCustomerAccount(CustomerAccount customerAccount) {
        if(isNull(customerAccount)) return null;
        return customerAccountRepository.saveCustomerAccount(customerAccount);
    }

    @Override
    @Async("asyncThreadPool")
    public void saveCustomerAccountAsync(CustomerAccount customerAccount) {
        if(isNull(customerAccount)) return;
        customerAccountRepository.saveCustomerAccount(customerAccount);
    }

    @Override
    public CustomerAccountResponse updateCustomerAccount(ProfileUpdateRequest profileUpdateRequest) {
        CustomerAccount customerAccount = customerAccountRepository.getCustomerAccountByCustomerIdCached(getCurrentCustomerId());
        customerAccountHelper.populateCustomerAccountToLatest(profileUpdateRequest, customerAccount);
        return platformMapper.convertToCustomerAccountResponse(customerAccountRepository.saveCustomerAccount(customerAccount));
    }

}
