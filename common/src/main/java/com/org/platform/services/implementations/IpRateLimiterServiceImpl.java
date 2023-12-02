package com.org.platform.services.implementations;

import com.org.platform.beans.IpRateLimit;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.repos.interfaces.IpRateLimiterRepository;
import com.org.platform.services.interfaces.IpRateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.RATE_LIMIT_EXCEEDED;
import static com.org.platform.utils.Constants.IP_RATE_LIMIT_THRESHOLD_LIMIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class IpRateLimiterServiceImpl implements IpRateLimiterService {

    @Value("${ip.rate.limit.document.ttl}")
    private Long ipRateLimitTtl;
    private final IpRateLimiterRepository ipRateLimiterRepository;

    public void checkHitsCountsAndUpdateThreshold(String endPoint, String customerIp) {
        ipRateLimiterRepository.cleanupExpiredDocuments();
        long count = ipRateLimiterRepository.countHitsInLastDefinedTime(endPoint);
        log.info("count for this api end point : {} {}", endPoint, count);
        if(count >= IP_RATE_LIMIT_THRESHOLD_LIMIT) {
            throw new PlatformCoreException(RATE_LIMIT_EXCEEDED, "Getting more than expected : " + count + " hits on this",
                    "endpoint : ", endPoint, " in last " + ipRateLimitTtl/1000 + " seconds interval , from Ip", customerIp);
        } else {
            IpRateLimit ipRateLimit = new IpRateLimit().builder()
                    .createdTime(System.currentTimeMillis())
                    .customerIp(customerIp)
                    .deleted(false)
                    .endPoint(endPoint)
                    .build();
            ipRateLimiterRepository.incrementHitCountForEndPoint(ipRateLimit);
        }
    }

}
