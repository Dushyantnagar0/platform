package com.org.platform.repos.interfaces;

import com.org.platform.beans.IpRateLimit;

public interface IpRateLimiterRepository {

    void cleanupExpiredDocuments();
    long countHitsInLastDefinedTime(String endPoint);
    void incrementHitCountForEndPoint(IpRateLimit ipRateLimit);
}
