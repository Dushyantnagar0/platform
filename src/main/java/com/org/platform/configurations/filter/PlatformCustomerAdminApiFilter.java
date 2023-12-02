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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_HEADERS;
import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_TOKEN;
import static com.org.platform.utils.HeaderConstants.*;
import static com.org.platform.utils.RestEntityBuilder.*;
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
                throw new PlatformCoreException(INVALID_TOKEN, "token is expired or incorrect");
            }
        } catch (PlatformCoreException e) {
            handleExceptionResponse(httpResponse, e);
        }
    }

    private void validateHeaders(HttpServletRequest httpRequest) {
        List<String> missingHeaders = new ArrayList<>();
        if (isBlank(httpRequest.getHeader(CLIENT_ID_KEY))) missingHeaders.add(CLIENT_ID_KEY);
        if (isBlank(httpRequest.getHeader(API_TOKEN_KEY))) missingHeaders.add(API_TOKEN_KEY);
        if (isBlank(httpRequest.getHeader(CUSTOMER_ID_KEY))) missingHeaders.add(CUSTOMER_ID_KEY);

        if (!missingHeaders.isEmpty()) {
            throw new PlatformCoreException(INVALID_HEADERS, String.join(", ", missingHeaders) + " missing");
        }
    }

    private void handleExceptionResponse(HttpServletResponse httpResponse, PlatformCoreException e) {
        try (PrintWriter printWriter = httpResponse.getWriter()) {
            httpResponse.setHeader(ERROR_CODE, e.getErrorCode());
            httpResponse.setHeader(ERROR_MESSAGE, e.getErrorMessage());
            httpResponse.setHeader(PARAMS, Arrays.toString(e.getParams()));
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            printWriter.flush();
            httpResponse.flushBuffer();
        } catch (Exception exception) {
            log.error("error in writing actionResponse to servlet");
        }
    }

}
