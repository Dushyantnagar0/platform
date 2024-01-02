package com.org.platform.configurations.filter;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.services.interfaces.IpRateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INVALID_HEADERS;
import static com.org.platform.services.HeaderContextService.createHeaderContextFromHttpHeadersForPublicApi;
import static com.org.platform.utils.HeaderConstants.API_TOKEN_KEY;
import static com.org.platform.utils.HeaderConstants.CLIENT_ID_KEY;
import static com.org.platform.utils.RestEntityBuilder.handleExceptionResponse;
import static com.org.platform.utils.ServletFilterUtils.asHttp;
import static com.org.platform.utils.ServletFilterUtils.forwardTheApiCall;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformPublicApiFilter implements Filter {


    private final IpRateLimiterService ipRateLimiterService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        try {
            validateHeaders(httpRequest);
            String customerIp = httpRequest.getHeader(CLIENT_ID_KEY);
            ipRateLimiterService.checkHitsCountsAndUpdateThreshold(customerIp);
            createHeaderContextFromHttpHeadersForPublicApi(httpRequest);
            forwardTheApiCall(request, response, chain);
        } catch (Exception e) {
            handleExceptionResponse(httpResponse, e);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("total time taken : {}ms by this public api : {}", endTime - startTime, httpRequest.getRequestURI());
        }
    }

    private void validateHeaders(HttpServletRequest httpRequest) {
        List<String> missingHeaders = new ArrayList<>();
        if (isBlank(httpRequest.getHeader(CLIENT_ID_KEY))) missingHeaders.add(CLIENT_ID_KEY);

        if (!missingHeaders.isEmpty()) {
            log.info(String.join(", ", missingHeaders) + " missing in headers");
            throw new PlatformCoreException(INVALID_HEADERS, String.join(", ", missingHeaders) + " missing in headers");
//            throw new RuntimeException(String.join(", ", missingHeaders) + " missing in headers");
        }
    }

}
