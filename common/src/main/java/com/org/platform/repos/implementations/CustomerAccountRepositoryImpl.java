package com.org.platform.repos.implementations;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.org.platform.beans.CustomerAccount;
import com.org.platform.repos.interfaces.CustomerAccountRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

import static com.org.platform.utils.Constants.PLATFORM_GLOBAL_DB;

@Slf4j
@Component
@Repository
public class CustomerAccountRepositoryImpl implements CustomerAccountRepository {

    @Autowired
    private PlatformMongoService platformMongoService;
    private static LoadingCache<String, CustomerAccount> customerAccountCustomerIdLoadingCache;
    private static LoadingCache<String, CustomerAccount> customerAccountEmailIdLoadingCache;

    public CustomerAccountRepositoryImpl() {

        customerAccountCustomerIdLoadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public CustomerAccount load(@NotNull String customerId) {
                                log.info("loading cache by customer Id is null for customer account so fetching form DB : {}", customerId);
                                return getCustomerAccountByCustomerId(customerId);
                            }
                        }
                );

        customerAccountEmailIdLoadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public CustomerAccount load(@NotNull String emailId) {
                                log.info("loading cache by email Id is null for customer account so fetching form DB : {}", emailId);
                                return getCustomerAccountByEmailId(emailId);
                            }
                        }
                );
    }

    public MongoTemplate getMongoTemplateForCustomerAccount() {
        return platformMongoService.getMongoTemplateCached(PLATFORM_GLOBAL_DB);
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
    public CustomerAccount getCustomerAccountByEmailIdCached(String emailId) {
        log.info("from customer account by emailId loading cache : {}", emailId);
        return customerAccountEmailIdLoadingCache.getUnchecked(emailId);
    }

    @Override
    public CustomerAccount getCustomerAccountByCustomerId(String customerId) {
        MongoTemplate mongoTemplate = getMongoTemplateForCustomerAccount();
        return mongoTemplate.findOne(Query.query(new Criteria().and("customerId").is(customerId)), CustomerAccount.class);
    }

    @Override
    public CustomerAccount getCustomerAccountByCustomerIdCached(String customerId) {
        log.info("from customer account by customerId loading cache : {}", customerId);
        return customerAccountCustomerIdLoadingCache.getUnchecked(customerId);
    }

}
