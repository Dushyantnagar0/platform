package com.org.platform.services.implementations;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.client.MongoClient;
import com.org.platform.repos.interfaces.PlatformMongoRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PlatformMongoServiceImpl implements PlatformMongoService {

    @Autowired
    private PlatformMongoRepository platformMongoRepository;
    private static LoadingCache<String, MongoTemplate> mongoTemplateLoadingCache;

    public PlatformMongoServiceImpl() {
        mongoTemplateLoadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
//                number of records
//                .maximumSize(10)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public MongoTemplate load(@NotNull String dbName) {
                                log.info("loading cache is null for mongo template so fetching form DB : {}", dbName);
                                return getMongoTemplate(dbName);
                            }
                        }
                );
    }

    public PlatformMongoServiceImpl(PlatformMongoRepository platformMongoRepository) {
        this.platformMongoRepository = platformMongoRepository;
    }

    public MongoClient getMongoClient() {
        return platformMongoRepository.getMongoClient();
    }

    public MongoTemplate getMongoTemplate(String dbName) {
        return platformMongoRepository.getMongoTemplate(dbName);
    }

    public MongoTemplate getMongoTemplateCached(String dbName) {
        log.info("from mongo template loading cache : {}", dbName);
        return mongoTemplateLoadingCache.getUnchecked(dbName);
    }

}
