package com.org.platform.services.interfaces;

public interface IpRateLimiterService {

    void checkHitsCountsAndUpdateThreshold(String endPoint, String customerIp);
}
