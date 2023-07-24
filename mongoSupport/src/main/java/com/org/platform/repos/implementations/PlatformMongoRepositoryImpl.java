package com.org.platform.repos.implementations;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.org.platform.repos.interfaces.PlatformMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@Component
@RequiredArgsConstructor
public class PlatformMongoRepositoryImpl implements PlatformMongoRepository {

    private final MongoClient mongoClient;
    public static final String PLATFORM_GLOBAL_DB = System.getenv("mongo_port");

    PlatformMongoRepositoryImpl() {
        ConnectionString connectionString = new ConnectionString(PLATFORM_GLOBAL_DB);
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