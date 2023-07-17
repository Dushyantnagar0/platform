package com.org.platform.configurations.filter;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.services.interfaces.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.org.platform.errors.errorCodes.LoginError.INTERNAL_SERVER_ERROR;
import static com.org.platform.errors.errorCodes.LoginError.INVALID_TOKEN;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformConsumerApiFilter implements Filter {

    private final TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        String tokenId = httpRequest.getHeader("api-token");
        String customerId = httpRequest.getHeader("customerId");

        log.info("http request tokenId : {}", tokenId);
        try {
            if (tokenService.validateJwtToken(tokenId, customerId)) {
                forwardTheApiCall(request, response, chain);
            } else {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new PlatformCoreException(INVALID_TOKEN);
            }
        } catch (Exception e) {
            throw new PlatformCoreException(INVALID_TOKEN);
        }
    }

    @Override
    public void destroy() {

    }

    private void forwardTheApiCall(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new PlatformCoreException(INTERNAL_SERVER_ERROR);
        }
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}
