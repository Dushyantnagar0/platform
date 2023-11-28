package com.org.platform.services.implementations;

import com.org.platform.services.interfaces.PlatformRedisService;
import com.org.platform.services.interfaces.RedisTemplateAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformRedisServiceImpl implements PlatformRedisService {

    private final RedisTemplateAccessor redisTemplateAccessor;

    public void save(String key, Object value){
        getRedisTemplate().opsForValue().set(key, value);
    }

    public void remove(String key){
        getRedisTemplate().delete(key);
    }

    public Object get(String key){
        return getRedisTemplate().opsForValue().get(key);
    }

    public Long increment(String key, long delta){
        return getRedisTemplate().opsForValue().increment(key, delta);
    }

    public void setExpire(String key, long timeOut){
        getRedisTemplate().expire(key, timeOut, TimeUnit.SECONDS);
    }

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplateAccessor.getGlobalRedisTemplate();
    }
}
