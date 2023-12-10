package com.org.platform.repos.interfaces;

import com.org.platform.beans.IpRateLimit;

public interface IpRateLimiterRepository {

    void cleanupExpiredDocuments(String customerIp);
    long countTotalHitsInLastDefinedTime(String endPoint);
    long countHitsFromIpInLastDefinedTime(String customerIp, String endPoint);
    void incrementHitCountForEndPoint(IpRateLimit ipRateLimit);
}
