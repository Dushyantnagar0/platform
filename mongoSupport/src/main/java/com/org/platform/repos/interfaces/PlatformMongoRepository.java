package com.org.platform.repos.interfaces;

import com.mongodb.client.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;

public interface PlatformMongoRepository {

    MongoClient getMongoClient();
    MongoTemplate getMongoTemplate(String dbName);

}
