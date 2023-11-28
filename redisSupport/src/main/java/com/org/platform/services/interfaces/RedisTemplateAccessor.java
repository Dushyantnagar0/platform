package com.org.platform.services.interfaces;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisTemplateAccessor {

    RedisTemplate<String, Object> getGlobalRedisTemplate();
}
