package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformCustomerAdminApiFilter;
import com.org.platform.services.interfaces.IpRateLimiterService;
import com.org.platform.services.interfaces.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.org.platform.adminApis", "com.org.platform.customerApis"})
public class CustomerAdminApiConfig {

    @Bean
    public FilterRegistrationBean<PlatformCustomerAdminApiFilter> filterRegistrationCustomerAdminBean(TokenService tokenService, IpRateLimiterService ipRateLimiterService) {
        FilterRegistrationBean <PlatformCustomerAdminApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformCustomerAdminApiFilter platformCustomerAdminApiFilter = new PlatformCustomerAdminApiFilter(tokenService, ipRateLimiterService);

        registrationBean.setFilter(platformCustomerAdminApiFilter);
        registrationBean.addUrlPatterns("/customer/*", "/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
