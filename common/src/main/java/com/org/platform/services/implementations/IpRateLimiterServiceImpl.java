package com.org.platform.services.implementations;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.repos.interfaces.IpRateLimiterRepository;
import com.org.platform.services.interfaces.IpRateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.IP_RATE_LIMIT_EXCEEDED;
import static com.org.platform.utils.Constants.IP_RATE_LIMIT_THRESHOLD_LIMIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class IpRateLimiterServiceImpl implements IpRateLimiterService {

    @Value("${ip.rate.limit.document.ttl}")
    private Long ipRateLimitTtl;
    private final IpRateLimiterRepository ipRateLimiterRepository;

    public void checkHitsCountsAndUpdateThreshold(String customerIp) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        ipRateLimiterRepository.cleanupExpiredDocuments(customerIp, currentTimestamp);
        long totalCountsFromIp = ipRateLimiterRepository.countHitsFromIpInLastDefinedTime(customerIp);
        if(totalCountsFromIp >= IP_RATE_LIMIT_THRESHOLD_LIMIT) {
            log.info("More than expected hits in last : {} seconds interval , from Ip : {}",  ipRateLimitTtl/1000, customerIp);
            throw new PlatformCoreException(IP_RATE_LIMIT_EXCEEDED, "Got more than expected hits in last " + ipRateLimitTtl/1000 + " seconds interval , from Ip " + customerIp);
        } else {
            log.info("Running in thread before : {}", Thread.currentThread().getName());
            ipRateLimiterRepository.incrementHitCountForEndPoint(customerIp, currentTimestamp);
        }
    }

}
