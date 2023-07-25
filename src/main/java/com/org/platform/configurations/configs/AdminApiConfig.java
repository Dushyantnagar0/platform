package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformAdminFilter;
import com.org.platform.configurations.filter.PlatformCustomerApiFilter;
import com.org.platform.services.interfaces.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminApiConfig {

    @Bean
    public FilterRegistrationBean<PlatformAdminFilter> filterRegistrationAdminBean(TokenService tokenService) {
        FilterRegistrationBean <PlatformAdminFilter> registrationBean = new FilterRegistrationBean();
        PlatformAdminFilter platformAdminFilter = new PlatformAdminFilter(tokenService);

        registrationBean.setFilter(platformAdminFilter);
        // TODO : it can be list of paths
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
