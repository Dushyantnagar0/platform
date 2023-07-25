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

import static com.org.platform.utils.Constants.PLATFORM_GLOBAL_DB;

@Slf4j
@Repository
@Component
@RequiredArgsConstructor
public class OtpRepositoryImpl implements OtpRepository {

    private final PlatformMongoService platformMongoService;


    public MongoTemplate getMongoTemplateForOtp() {
        return platformMongoService.getMongoTemplate(PLATFORM_GLOBAL_DB);
    }

    @Override
    public void saveEmailOtpBean(String emailId, String hashedOtp) {
        MongoTemplate mongoTemplate = getMongoTemplateForOtp();
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
