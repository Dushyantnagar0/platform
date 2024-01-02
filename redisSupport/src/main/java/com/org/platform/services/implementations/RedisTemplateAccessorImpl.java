package com.org.platform.services.implementations;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.org.platform.services.interfaces.RedisTemplateAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.org.platform.utils.PlatformRedisConstants.*;
import static com.org.platform.utils.PlatformRedisConstants.THREAD_WAIT_TIME_WHILE_BORROWING_MILLIS;

@Slf4j
@Component
public class RedisTemplateAccessorImpl implements RedisTemplateAccessor {

    RedisTemplate<String, Object> globalRedisTemplate;
    private static LoadingCache<String, RedisTemplate<String, Object>> redisTemplateLoadingCache;

    @PostConstruct
    private void postConstruct(){
        globalRedisTemplate  = redisTemplateLoadingCache.getUnchecked("REDIS");
        globalRedisTemplate.setValueSerializer(RedisSerializer.json());
    }

    public RedisTemplateAccessorImpl() {

        redisTemplateLoadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
//                number of records
                .maximumSize(10)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public RedisTemplate<String, Object> load(@NotNull String key) {
                                log.info("loading cache is null for redis template so fetching form DB : ");
                                return getDefaultRedisTemplate();
                            }
                        }
                );

    }

    public RedisTemplate<String, Object> getGlobalRedisTemplate() {
        return this.globalRedisTemplate;
    }

    public RedisTemplate<String, Object> getDefaultRedisTemplate() {
        JedisConnectionFactory connectionFactory = getConnectionFactory();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(RedisSerializer.string());
        // using cached instance because this will create its own object mapper everytime which will
        // have its own serialization and deserialization cache
        template.setValueSerializer(json);
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    private JedisConnectionFactory getConnectionFactory() {
        return createConnectionFactory();
    }

    private JedisConnectionFactory createConnectionFactory() {
        JedisConnectionFactory connectionFactory;
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);
        redisStandaloneConfiguration.setDatabase(REDIS_DB_INDEX);
        redisStandaloneConfiguration.setPassword(REDIS_PASSWORD);
        connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    private JedisClientConfiguration getJedisClientConfiguration() {
        return JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(getRedisPoolConfig())
                .and()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_IN_SECONDS))
                .readTimeout(Duration.ofSeconds(READ_TIMEOUT_IN_SECONDS))
                .build();
    }

    private GenericObjectPoolConfig<Jedis> getRedisPoolConfig() {
        GenericObjectPoolConfig<Jedis> poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(MAX_IDLE_CONNECTIONS);
        poolConfig.setMinIdle(MIN_IDLE_CONNECTIONS);
        poolConfig.setMaxTotal(MAX_IDLE_CONNECTIONS);
        poolConfig.setBlockWhenExhausted(BLOCK_THREADS_WHEN_POOL_EXHAUSTED);
        poolConfig.setMaxWaitMillis(THREAD_WAIT_TIME_WHILE_BORROWING_MILLIS.toMillis());
        return poolConfig;
    }

}
