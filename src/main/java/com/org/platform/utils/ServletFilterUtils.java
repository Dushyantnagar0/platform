package com.org.platform.utils;

import com.org.platform.errors.exceptions.PlatformCoreException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.org.platform.errors.errorCodes.LoginError.INTERNAL_SERVER_ERROR;

@Slf4j
@UtilityClass
public class ServletFilterUtils {

    public static void forwardTheApiCall(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new PlatformCoreException(INTERNAL_SERVER_ERROR);
        }
    }

    public static HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

}
