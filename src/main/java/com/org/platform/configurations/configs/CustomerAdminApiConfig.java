package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformCustomerAdminApiFilter;
import com.org.platform.services.interfaces.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerAdminApiConfig {

    @Bean
    public FilterRegistrationBean<PlatformCustomerAdminApiFilter> filterRegistrationCustomerAdminBean(TokenService tokenService) {
        FilterRegistrationBean <PlatformCustomerAdminApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformCustomerAdminApiFilter platformCustomerAdminApiFilter = new PlatformCustomerAdminApiFilter(tokenService);

        registrationBean.setFilter(platformCustomerAdminApiFilter);
        registrationBean.addUrlPatterns("/customer/*", "/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
