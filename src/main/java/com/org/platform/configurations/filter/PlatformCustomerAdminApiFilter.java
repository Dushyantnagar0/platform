package com.org.platform.configurations.filter;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.services.interfaces.IpRateLimiterService;
import com.org.platform.services.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_HEADERS;
import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_TOKEN;
import static com.org.platform.utils.HeaderConstants.*;
import static com.org.platform.utils.RestEntityBuilder.createCustomErrorResponseBody;
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
            validateHeaders(httpRequest);
            String endPoint = httpRequest.getRequestURI();
            String customerIp = httpRequest.getHeader(CLIENT_ID_KEY);

            ipRateLimiterService.checkHitsCountsAndUpdateThreshold(endPoint, customerIp);
            boolean isForAdminApi = (httpRequest.getRequestURI()).startsWith("/admin");

            log.info("http request isForAdminApi : {}", isForAdminApi);
            if (tokenService.validateJwtTokenAndCreateHeaderMap(httpRequest, isForAdminApi)) {
                forwardTheApiCall(request, response, chain);
            } else {
                log.info("token is expired or incorrect");
                throw new PlatformCoreException(INVALID_TOKEN, "token is expired or incorrect");
            }
        } catch (Exception e) {
            handleExceptionResponse(httpResponse, e);
        }
    }

    private void validateHeaders(HttpServletRequest httpRequest) {
        List<String> missingHeaders = new ArrayList<>();
        if (isBlank(httpRequest.getHeader(CLIENT_ID_KEY))) missingHeaders.add(CLIENT_ID_KEY);
        if (isBlank(httpRequest.getHeader(API_TOKEN_KEY))) missingHeaders.add(API_TOKEN_KEY);
        if (isBlank(httpRequest.getHeader(CUSTOMER_ID_KEY))) missingHeaders.add(CUSTOMER_ID_KEY);

        if (!missingHeaders.isEmpty()) {
            log.info(String.join(", ", missingHeaders) + " missing in headers");
            throw new PlatformCoreException(INVALID_HEADERS, String.join(", ", missingHeaders) + " missing in headers");
//            throw new RuntimeException(String.join(", ", missingHeaders) + " missing in headers");
        }
    }

    private void handleExceptionResponse(HttpServletResponse httpResponse, Exception e) {
        try {
            httpResponse.getWriter().write(createCustomErrorResponseBody(httpResponse, e));
        } catch (IOException ignored) {
            log.error("error while writing http response : ", ignored);
        }
    }

}
