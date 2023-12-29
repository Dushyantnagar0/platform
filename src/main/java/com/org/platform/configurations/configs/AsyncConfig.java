package com.org.platform.configurations.configs;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
public class AsyncConfig {

    @Resource(name = "propertiesMap")
    private Map<String, String> propertiesMap;

    @Bean(name = "propertiesMap")
    public static PropertiesFactoryBean propertiesFactoryBean() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("application.properties"));
        return bean;
    }

    @Bean("asyncThreadPool")
    @Primary
    public AsyncTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.parseInt(propertiesMap.get("async.pool.size")));
        executor.setMaxPoolSize(Integer.parseInt(propertiesMap.get("async.max.pool.size")));
        executor.setQueueCapacity(Integer.parseInt(propertiesMap.get("async.queue.capacity")));
        executor.setThreadNamePrefix(propertiesMap.get("async.threadname.prefix"));
        return executor;
    }

}
