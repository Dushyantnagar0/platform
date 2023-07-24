package com.org.platform.services.implementations;

import com.mongodb.client.MongoClient;
import com.org.platform.repos.interfaces.PlatformMongoRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlatformMongoServiceImpl implements PlatformMongoService {

    private final PlatformMongoRepository platformMongoRepository;

    public PlatformMongoServiceImpl(PlatformMongoRepository platformMongoRepository) {
        this.platformMongoRepository = platformMongoRepository;
    }

    public MongoClient getMongoClient() {
        return platformMongoRepository.getMongoClient();
    }

    public MongoTemplate getMongoTemplate(String dbName) {
        return platformMongoRepository.getMongoTemplate(dbName);
    }

}
