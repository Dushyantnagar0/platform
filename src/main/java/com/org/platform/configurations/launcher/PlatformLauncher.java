package com.org.platform.configurations.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@ComponentScan(
        basePackages = {"com.org"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.org\\..*launcher\\..*")
        })
@Configuration
@SpringBootApplication
public class PlatformLauncher extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PlatformLauncher.class, args);
    }


}
