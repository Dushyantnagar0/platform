package com.org.platform.repos.interfaces;

public interface IpRateLimiterRepository {

    void cleanupExpiredDocuments(String customerIp, long currentTimestamp);
    long countHitsFromIpInLastDefinedTime(String customerIp);
    void incrementHitCountForEndPoint(String customerIp, long currentTimestamp);
}
