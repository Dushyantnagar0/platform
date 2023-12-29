package com.org.platform.services.implementations;

import com.org.platform.services.interfaces.PlatformRedisService;
import com.org.platform.services.interfaces.RedisTemplateAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformRedisServiceImpl implements PlatformRedisService {

    private final RedisTemplateAccessor redisTemplateAccessor;

    @Override
    public void save(String key, Object value){
        getRedisTemplate().opsForValue().set(key, value);
    }

    @Override
    public void remove(String key){
        getRedisTemplate().delete(key);
    }

    @Override
    public Object get(String key){
        return getRedisTemplate().opsForValue().get(key);
    }

    @Override
    public Set<String> getKeys(String key) {
        return getRedisTemplate().keys(key);
    }

    @Override
    public Long increment(String key, long delta){
        return getRedisTemplate().opsForValue().increment(key, delta);
    }

    @Override
    public void setExpire(String key, long timeOut){
        getRedisTemplate().expire(key, timeOut, TimeUnit.MILLISECONDS);
    }

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplateAccessor.getGlobalRedisTemplate();
    }
}
