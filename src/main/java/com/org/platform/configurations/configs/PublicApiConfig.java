package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformPublicApiFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicApiConfig {

    @Bean
    public FilterRegistrationBean <PlatformPublicApiFilter> filterRegistrationPublicBean() {
        FilterRegistrationBean <PlatformPublicApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformPublicApiFilter platformUserApiFilter = new PlatformPublicApiFilter();

        registrationBean.setFilter(platformUserApiFilter);
        // TODO : it can be list of paths
        registrationBean.addUrlPatterns("/public/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
