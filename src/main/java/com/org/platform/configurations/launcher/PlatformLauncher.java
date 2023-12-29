package com.org.platform.configurations.launcher;

import com.org.platform.configurations.servlet.PlatformServlet;
import com.org.platform.configurations.servlet.PlatformServletListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextListener;

@Slf4j
@EnableRetry
@EnableKafka
@EnableAsync
@EnableCaching
@ComponentScan(
        basePackages = {"com.org"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.org\\..*launcher\\..*")
        })
@Configuration
@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
public class PlatformLauncher extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
        String basePackage = "com.org";
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            log.info("Found component: " + beanDefinition.getBeanClassName());
        }
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

}
