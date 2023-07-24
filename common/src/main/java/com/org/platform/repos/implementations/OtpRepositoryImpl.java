package com.org.platform.repos.implementations;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Slf4j
@Repository
@Component
@RequiredArgsConstructor
public class OtpRepositoryImpl implements OtpRepository {

    private final PlatformMongoService platformMongoService;


    public MongoTemplate getMongoTemplateForOtp() {
        return platformMongoService.getMongoTemplate(System.getenv("dbName"));
    }

    @Override
    public void saveEmailOtpBean(String emailId, String hashedOtp) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();

//        EmailOtpBean existingEmailOtpBean = mongoTemplate.findOne(Query.query(new Criteria().and("emailId").is(emailId)), EmailOtpBean.class);
//        Query query = Query.query(new Criteria().and("emailId").is(emailId));
//        Update update = new Update().set("hashedOtp", hashedOtp);
//        UpdateResult updateResult = mongoTemplate.upsert(query, update, EmailOtpBean.class);

        EmailOtpBean emailOtpBean = new EmailOtpBean(emailId, hashedOtp, null);
        mongoTemplate.save(emailOtpBean);
    }

    @Override
    public EmailOtpBean saveEmailOtpBean(EmailOtpBean emailOtpBean) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();
        return mongoTemplate.save(emailOtpBean);
    }

    @Override
    public EmailOtpBean getEmailOtpBeanByEmailId(String emailId) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();
        return mongoTemplate.findOne(Query.query(new Criteria().and("emailId").is(emailId)), EmailOtpBean.class);
    }
}
