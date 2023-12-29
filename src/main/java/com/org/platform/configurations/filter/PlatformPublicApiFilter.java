package com.org.platform.configurations.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.org.platform.services.HeaderContextService.createHeaderContextFromHttpHeadersForPublicApi;
import static com.org.platform.utils.RestEntityBuilder.handleExceptionResponse;
import static com.org.platform.utils.ServletFilterUtils.asHttp;
import static com.org.platform.utils.ServletFilterUtils.forwardTheApiCall;

@Slf4j
public class PlatformPublicApiFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        try {
            createHeaderContextFromHttpHeadersForPublicApi(httpRequest);
            forwardTheApiCall(request, response, chain);
        } catch (Exception e) {
            handleExceptionResponse(httpResponse, e);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("total time taken : {}ms by this api : {}", endTime - startTime, httpRequest.getRequestURI());
        }
    }

}
