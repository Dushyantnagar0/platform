package com.org.platform.configurations.filter;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.services.interfaces.IpRateLimiterService;
import com.org.platform.services.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_HEADERS;
import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_TOKEN;
import static com.org.platform.utils.HeaderConstants.CLIENT_ID_KEY;
import static com.org.platform.utils.ServletFilterUtils.asHttp;
import static com.org.platform.utils.ServletFilterUtils.forwardTheApiCall;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformCustomerAdminApiFilter implements Filter {

    private final TokenService tokenService;
    private final IpRateLimiterService ipRateLimiterService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        try {
            String endPoint = httpRequest.getRequestURI();
            String customerIp = httpRequest.getHeader(CLIENT_ID_KEY);
            if (isBlank(customerIp)) {
                throw new PlatformCoreException(INVALID_HEADERS);
            }
            ipRateLimiterService.checkHitsCountsAndUpdateThreshold(endPoint, customerIp);
            boolean isForAdminApi = (httpRequest.getRequestURI()).startsWith("/admin");

            String tokenId = httpRequest.getHeader("api-token");
            String customerId = httpRequest.getHeader("customerId");

            log.info("http request isForAdminApi : {}", isForAdminApi);
            if (tokenService.validateJwtTokenAndCreateHeaderMap(httpRequest, tokenId, customerId, isForAdminApi)) {
                forwardTheApiCall(request, response, chain);
            } else {
                throw new PlatformCoreException(INVALID_TOKEN);
            }
        } catch (PlatformCoreException e) {
            httpResponse.setHeader("error code", e.getErrorCode());
            httpResponse.setHeader("error message", e.getErrorMessage());
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw e;
        }
    }

}
