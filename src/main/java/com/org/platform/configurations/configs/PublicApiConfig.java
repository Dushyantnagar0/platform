package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformPublicApiFilter;
import com.org.platform.services.interfaces.IpRateLimiterService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.org.platform.publicApis"})
public class PublicApiConfig {

    @Bean
    public FilterRegistrationBean <PlatformPublicApiFilter> filterRegistrationPublicBean(IpRateLimiterService ipRateLimiterService) {
        FilterRegistrationBean <PlatformPublicApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformPublicApiFilter platformUserApiFilter = new PlatformPublicApiFilter(ipRateLimiterService);

        registrationBean.setFilter(platformUserApiFilter);
        // TODO : it can be list of paths
        registrationBean.addUrlPatterns("/public/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
