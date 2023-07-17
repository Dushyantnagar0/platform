package com.org.platform.configurations.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        forwardTheApiCall(request, response, chain);
    }

    @Override
    public void destroy() {

    }

    private void forwardTheApiCall(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("error in updating user context: ", e);
        }
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}
