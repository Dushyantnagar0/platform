package com.org.platform.repos.implementations;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.org.platform.repos.interfaces.PlatformMongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlatformMongoRepositoryImpl implements PlatformMongoRepository {

    private final MongoClient mongoClient;

    PlatformMongoRepositoryImpl() {
        ConnectionString connectionString = new ConnectionString(System.getenv("mongo_port"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        this.mongoClient = MongoClients.create(settings);
    }

    @Override
    public MongoClient getMongoClient() {
        return this.mongoClient;
    }

    @Override
    public MongoTemplate getMongoTemplate(String dbName) {
        MongoClient mongoClient = getMongoClient();
        return new MongoTemplate(mongoClient, dbName);
    }


}
