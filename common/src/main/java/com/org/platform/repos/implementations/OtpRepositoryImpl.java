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
    public String saveEmailOtpBean(String emailId, String hashedOtp) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();

//        Query query = new Query();
//        Update update = new Update().set("name", description.getName()).set("norwegian", description.getNorwegian()).set("english", description.getEnglish());
//        mongoTemplate.upsert(query, update, "descriptions");

        EmailOtpBean emailOtpBean = new EmailOtpBean(emailId, hashedOtp, null, null);
        EmailOtpBean existingEmailOtpBean = mongoTemplate.findOne(Query.query(new Criteria().and("emailId").is(emailId)), EmailOtpBean.class);
        if(nonNull(existingEmailOtpBean)) emailOtpBean.setRefId(existingEmailOtpBean.getRefId());
        else emailOtpBean.setRefId(UUID.randomUUID().toString());
        mongoTemplate.save(emailOtpBean);
        return emailOtpBean.getRefId();
    }

    @Override
    public EmailOtpBean saveEmailOtpBean(EmailOtpBean emailOtpBean, EmailOtpBean existingEmailOtpBean) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();
        existingEmailOtpBean.setToken(emailOtpBean.getToken());
        return mongoTemplate.save(existingEmailOtpBean);
    }

    @Override
    public EmailOtpBean getEmailOtpBeanByRefId(String refId) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();
        return mongoTemplate.findOne(Query.query(new Criteria().and("refId").is(refId)), EmailOtpBean.class);
    }
}
