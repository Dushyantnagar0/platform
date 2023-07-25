package com.org.platform.repos.implementations;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static com.org.platform.utils.Constants.PLATFORM_GLOBAL_DB;

@Slf4j
@Repository
@Component
@RequiredArgsConstructor
public class CustomerAccountRepositoryImpl implements CustomerAccountRepository {

    private final PlatformMongoService platformMongoService;


    public MongoTemplate getMongoTemplateForCustomerAccount() {
        return platformMongoService.getMongoTemplate(PLATFORM_GLOBAL_DB);
    }

    @Override
    public CustomerAccount saveCustomerAccount(CustomerAccount customerAccount) {
        MongoTemplate mongoTemplate = getMongoTemplateForCustomerAccount();
        return mongoTemplate.save(customerAccount);
    }

    @Override
    public CustomerAccount getCustomerAccountByEmailId(String emailId) {
        MongoTemplate mongoTemplate = getMongoTemplateForCustomerAccount();
        return mongoTemplate.findOne(Query.query(new Criteria().and("emailId").is(emailId)), CustomerAccount.class);
    }

    @Override
    public CustomerAccount getCustomerAccountByCustomerId(String customerId) {
        MongoTemplate mongoTemplate = getMongoTemplateForCustomerAccount();
        return mongoTemplate.findOne(Query.query(new Criteria().and("customerId").is(customerId)), CustomerAccount.class);
    }


}
