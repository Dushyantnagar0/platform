package com.org.platform.services.interfaces;

import com.mongodb.client.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;

public interface PlatformMongoService {

    MongoClient getMongoClient();
    MongoTemplate getMongoTemplate(String dbName);

}
