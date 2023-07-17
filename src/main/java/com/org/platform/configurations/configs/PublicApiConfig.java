package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformUserApiFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicApiConfig {

    @Bean
    public FilterRegistrationBean <PlatformUserApiFilter> filterRegistrationPublicBean() {
        FilterRegistrationBean <PlatformUserApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformUserApiFilter platformUserApiFilter = new PlatformUserApiFilter();

        registrationBean.setFilter(platformUserApiFilter);
        // TODO : it can be list of paths
        registrationBean.addUrlPatterns("/public/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
