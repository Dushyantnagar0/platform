package com.org.platform.configurations.filter;

import com.org.platform.services.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.org.platform.utils.ServletFilterUtils.asHttp;
import static com.org.platform.utils.ServletFilterUtils.forwardTheApiCall;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformCustomerAdminApiFilter implements Filter {

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = asHttp(request);

        boolean isForAdminApi = (httpRequest.getRequestURI()).startsWith("/admin");

        String tokenId = httpRequest.getHeader("api-token");
        String customerId = httpRequest.getHeader("customerId");

        log.info("http request isForAdminApi : {}", isForAdminApi);
        if (tokenService.validateJwtTokenAndCreateHeaderMap(httpRequest, tokenId, customerId, isForAdminApi)) {
            forwardTheApiCall(request, response, chain);
        }
    }

}
