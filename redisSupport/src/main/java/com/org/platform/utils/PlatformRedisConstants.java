package com.org.platform.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@UtilityClass
public class PlatformRedisConstants {

    public static final RedisSerializer<Object> json = RedisSerializer.json();
    public static final long CONNECT_TIMEOUT_IN_SECONDS = 60;
    public static final long READ_TIMEOUT_IN_SECONDS = 60;
    public static final int MIN_IDLE_CONNECTIONS = 4;
    public static final int MAX_IDLE_CONNECTIONS = 40;
    // defaults are the same as old values, threads are blocked for waiting for infinity
    public static final boolean BLOCK_THREADS_WHEN_POOL_EXHAUSTED = true;
    // default is -1 and it would wait forever
    public static final Duration THREAD_WAIT_TIME_WHILE_BORROWING_MILLIS = Duration.ofMillis(-1);
    public static final int REDIS_DB_INDEX = 0;
    public static final String REDIS_PASSWORD = "";
    public static final String REDIS_HOST = "localhost";
    public static final int REDIS_PORT = Integer.parseInt(System.getenv("redis.port"));

}
