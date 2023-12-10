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
import static com.org.platform.errors.errorCodes.PlatformErrorCodes.IP_RATE_LIMIT_EXCEEDED;
import static com.org.platform.utils.Constants.IP_RATE_LIMIT_THRESHOLD_LIMIT;
import static com.org.platform.utils.Constants.TOTAL_RATE_LIMIT_THRESHOLD_LIMIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class IpRateLimiterServiceImpl implements IpRateLimiterService {

    @Value("${ip.rate.limit.document.ttl}")
    private Long ipRateLimitTtl;
    private final IpRateLimiterRepository ipRateLimiterRepository;

//    TODO : don't access DB instead use cache
    public void checkHitsCountsAndUpdateThreshold(String endPoint, String customerIp) {
        ipRateLimiterRepository.cleanupExpiredDocuments(customerIp);
//        TODO : Optimise this
        long totalCount = ipRateLimiterRepository.countTotalHitsInLastDefinedTime(endPoint);
        long totalCountsFromIp = ipRateLimiterRepository.countHitsFromIpInLastDefinedTime(customerIp, endPoint);
        if(totalCountsFromIp >= IP_RATE_LIMIT_THRESHOLD_LIMIT) {
            log.info("More than expected : {} hits on this endpoint : {} in last : {} seconds interval , from Ip : {}", totalCountsFromIp , endPoint,  ipRateLimitTtl/1000, customerIp);
            throw new PlatformCoreException(IP_RATE_LIMIT_EXCEEDED, "Got more than expected : " + totalCountsFromIp + " hits on this " +
                    "endpoint : " + endPoint + " in last " + ipRateLimitTtl/1000 + " seconds interval , from Ip " + customerIp);
        } else if(totalCount >= TOTAL_RATE_LIMIT_THRESHOLD_LIMIT) {
            log.info("More than limit : {} got : {} hits on this endpoint : {} in last : {} seconds interval", TOTAL_RATE_LIMIT_THRESHOLD_LIMIT, totalCountsFromIp , endPoint,  ipRateLimitTtl/1000);
            throw new PlatformCoreException(RATE_LIMIT_EXCEEDED, "Got more than expected : " + totalCountsFromIp + " hits on this " +
                    "endpoint : " + endPoint + " in last " + ipRateLimitTtl/1000 + " seconds interval");
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
