package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformTokenApiFilter;
import com.org.platform.services.interfaces.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenApiConfig {

    @Bean
    public FilterRegistrationBean<PlatformTokenApiFilter> filterRegistrationConsumerBean(TokenService tokenService) {
        FilterRegistrationBean <PlatformTokenApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformTokenApiFilter platformTokenApiFilter = new PlatformTokenApiFilter(tokenService);

        registrationBean.setFilter(platformTokenApiFilter);
        // TODO : it can be list of paths
        registrationBean.addUrlPatterns("/consumer/*", "/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
