package com.org.platform.repos.implementations;

import com.org.platform.beans.IpRateLimit;
import com.org.platform.repos.interfaces.IpRateLimiterRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static com.org.platform.utils.Constants.PLATFORM_GLOBAL_DB;

@Slf4j
@Component
@Repository
@RequiredArgsConstructor
public class IpRateLimiterRepositoryImpl implements IpRateLimiterRepository {

    private final PlatformMongoService platformMongoService;
    @Value("${ip.rate.limit.document.ttl}")
    private Long ipRateLimitTtl;


    public MongoTemplate getMongoTemplateForIpRateLimiter() {
        return platformMongoService.getMongoTemplate(PLATFORM_GLOBAL_DB);
    }

//    @Scheduled(fixedRate = 10000) // Run every 10 secs
    public void cleanupExpiredDocuments(String customerIp) {
        log.info("clearing expired documents : ");
        MongoTemplate mongoTemplate = getMongoTemplateForIpRateLimiter();
        Query query = new Query();
        query.addCriteria(Criteria.where("createdTime").lt(System.currentTimeMillis() - ipRateLimitTtl));
//        query.addCriteria(Criteria.where("customerIp").is(customerIp));
        mongoTemplate.remove(query, IpRateLimit.class);
    }

//    use aggregator
    @Override
    public long countTotalHitsInLastDefinedTime(String endPoint) {
        MongoTemplate mongoTemplate = getMongoTemplateForIpRateLimiter();
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is(false));
//        query.addCriteria(Criteria.where("createdTime").lte(System.currentTimeMillis() - ipRateLimitTtl));
        query.addCriteria(Criteria.where("endPoint").gte(endPoint));
        return mongoTemplate.count(query, IpRateLimit.class);
    }

    @Override
    public long countHitsFromIpInLastDefinedTime(String customerIp, String endPoint) {
        MongoTemplate mongoTemplate = getMongoTemplateForIpRateLimiter();
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is(false));
        query.addCriteria(Criteria.where("customerIp").is(customerIp));
        query.addCriteria(Criteria.where("endPoint").gte(endPoint));
        return mongoTemplate.count(query, IpRateLimit.class);
    }

    @Override
    public void incrementHitCountForEndPoint(IpRateLimit ipRateLimit) {
        MongoTemplate mongoTemplate = getMongoTemplateForIpRateLimiter();
        mongoTemplate.save(ipRateLimit);
    }
}
