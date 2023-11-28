package com.org.platform.configurations.launcher;

import com.org.platform.configurations.servlet.PlatformServlet;
import com.org.platform.configurations.servlet.PlatformServletListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletContextListener;

@EnableRetry
@EnableCaching
@ComponentScan(
        basePackages = {"com.org"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.org\\..*launcher\\..*")
        })
@Configuration
@EnableScheduling
@SpringBootApplication
public class PlatformLauncher extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PlatformLauncher.class);
        app.setLazyInitialization(true);
        app.run(args);
    }

    @Bean
    public ServletRegistrationBean customServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new PlatformServlet(), "/customer", "/public");
        return bean;
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> customListenerBean() {
        ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean();
        bean.setListener(new PlatformServletListener());
        return bean;
    }

    @Bean
    public AsyncTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

}
