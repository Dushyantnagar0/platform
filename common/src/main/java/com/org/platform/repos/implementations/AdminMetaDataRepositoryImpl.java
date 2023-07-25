package com.org.platform.repos.implementations;

import com.org.platform.beans.UserMetaData;
import com.org.platform.repos.interfaces.AdminMetaDataRepository;
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
public class AdminMetaDataRepositoryImpl implements AdminMetaDataRepository {

    private final PlatformMongoService platformMongoService;


    public MongoTemplate getMongoTemplateForUserMetaData() {
        return platformMongoService.getMongoTemplate(PLATFORM_GLOBAL_DB);
    }

    @Override
    public UserMetaData upsertUserMetaData(UserMetaData userMetaData) {
        MongoTemplate mongoTemplate = getMongoTemplateForUserMetaData();
        return mongoTemplate.save(userMetaData);
    }

    @Override
    public UserMetaData fetchUserMetaDataFromEmailId(String emailId) {
        MongoTemplate mongoTemplate = getMongoTemplateForUserMetaData();
        return mongoTemplate.findOne(Query.query(new Criteria().and("emailId").is(emailId)) , UserMetaData.class);
    }
}
