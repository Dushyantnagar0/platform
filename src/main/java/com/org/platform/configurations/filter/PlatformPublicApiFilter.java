package com.org.platform.configurations.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.org.platform.services.HeaderContextService.createHeaderContextFromHttpHeadersForPublicApi;
import static com.org.platform.utils.ServletFilterUtils.asHttp;
import static com.org.platform.utils.ServletFilterUtils.forwardTheApiCall;

@Slf4j
public class PlatformPublicApiFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        createHeaderContextFromHttpHeadersForPublicApi(httpRequest);
        forwardTheApiCall(request, response, chain);
    }

}
