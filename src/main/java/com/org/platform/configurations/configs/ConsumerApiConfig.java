package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformConsumerApiFilter;
import com.org.platform.services.interfaces.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerApiConfig {

    @Bean
    public FilterRegistrationBean<PlatformConsumerApiFilter> filterRegistrationConsumerBean(TokenService tokenService) {
        FilterRegistrationBean <PlatformConsumerApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformConsumerApiFilter platformConsumerApiFilter = new PlatformConsumerApiFilter(tokenService);

        registrationBean.setFilter(platformConsumerApiFilter);
        // TODO : it can be list of paths
        registrationBean.addUrlPatterns("/consumer/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
