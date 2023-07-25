package com.org.platform.configurations.configs;

import com.org.platform.configurations.filter.PlatformCustomerApiFilter;
import com.org.platform.services.interfaces.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerApiConfig {

    @Bean
    public FilterRegistrationBean<PlatformCustomerApiFilter> filterRegistrationCustomerBean(TokenService tokenService) {
        FilterRegistrationBean <PlatformCustomerApiFilter> registrationBean = new FilterRegistrationBean();
        PlatformCustomerApiFilter platformCustomerApiFilter = new PlatformCustomerApiFilter(tokenService);

        registrationBean.setFilter(platformCustomerApiFilter);
        registrationBean.addUrlPatterns("/consumer/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
