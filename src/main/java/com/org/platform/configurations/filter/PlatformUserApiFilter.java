package com.org.platform.configurations.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.org.platform.services.HeaderContextService.createHeaderContextFromHttpHeaders;
import static com.org.platform.utils.ServletFilterUtils.asHttp;
import static com.org.platform.utils.ServletFilterUtils.forwardTheApiCall;

@Slf4j
public class PlatformUserApiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        String userId = httpRequest.getHeader("userId");
        log.info("http response userId : {}", userId);

        createHeaderContextFromHttpHeaders(httpRequest);
        forwardTheApiCall(request, response, chain);
    }

}
