package com.org.platform.services.interfaces;

import org.springframework.data.redis.core.RedisTemplate;

public interface PlatformRedisService {

    void save(String key, Object value);
    void remove(String key);
    Object get(String key);
    Long increment (String key, long delta);
    void setExpire (String key, long timeOut);
    RedisTemplate<String, Object> getRedisTemplate();

}
