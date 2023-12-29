package com.org.platform.services.interfaces;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

public interface PlatformRedisService {

    void save(String key, Object value);
    void remove(String key);
    Object get(String key);
    Set<String> getKeys(String key);
    Long increment (String key, long delta);
    void setExpire (String key, long timeOut);
    RedisTemplate<String, Object> getRedisTemplate();

}
