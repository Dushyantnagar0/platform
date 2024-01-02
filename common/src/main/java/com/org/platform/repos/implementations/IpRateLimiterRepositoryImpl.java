package com.org.platform.repos.implementations;

import com.org.platform.repos.interfaces.IpRateLimiterRepository;
import com.org.platform.services.interfaces.PlatformRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.Set;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@Repository
@RequiredArgsConstructor
public class IpRateLimiterRepositoryImpl implements IpRateLimiterRepository {

    private final PlatformRedisService platformRedisService;
    @Value("${ip.rate.limit.document.ttl}")
    private Long ipRateLimitTtl;


//    @Scheduled(fixedRate = 10000) // Run every 10 secs
    public void cleanupExpiredDocuments(String customerIp, long currentTimestamp) {
        long roundedTimestamp = currentTimestamp / ipRateLimitTtl * ipRateLimitTtl;
        String redisIpCacheKey = customerIp + ":" + roundedTimestamp;
        log.info("clearing expired documents : {}", redisIpCacheKey);
        long oldestValidTimestamp = currentTimestamp - ipRateLimitTtl;
        Set<String> keys = platformRedisService.getKeys(customerIp + ":*");
        if (nonNull(keys)) {
            for (String key : keys) {
                long ts = Long.parseLong(key.split(":")[1]);
                if (ts < oldestValidTimestamp) {
                    platformRedisService.remove(redisIpCacheKey);
                }
            }
        }
    }

    @Override
    public long countHitsFromIpInLastDefinedTime(String customerIp) {
        long totalRequests = 0;
        Set<String> keys = platformRedisService.getKeys(customerIp + ":*");
        if (nonNull(keys)) {
            for (String k : keys) {
                totalRequests += ((Integer) platformRedisService.get(k)).longValue();
            }
        }
        return totalRequests;
    }

    @Override
    @Async("asyncThreadPool")
    public void incrementHitCountForEndPoint(String customerIp, long currentTimestamp) {
        log.info("Running in thread inside : {}", Thread.currentThread().getName());
        long roundedTimestamp = currentTimestamp / ipRateLimitTtl * ipRateLimitTtl;
        String redisIpCacheKey = customerIp + ":" + roundedTimestamp;
        log.info("redisIpCacheKey in incrementHitCountForEndPoint : {}", redisIpCacheKey);
        platformRedisService.increment(redisIpCacheKey, 1);
        platformRedisService.setExpire(redisIpCacheKey, ipRateLimitTtl);
    }
}
