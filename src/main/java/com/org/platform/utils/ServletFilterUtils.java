package com.org.platform.utils;

import com.org.platform.errors.exceptions.PlatformCoreException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.org.platform.errors.errorCodes.PlatformErrorCodes.INTERNAL_SERVER_ERROR;
import static com.org.platform.utils.CommonUtils.handleExceptionAndCreateResponse;
import static com.org.platform.utils.Constants.SOMETHING_WENT_WRONG;

@Slf4j
@UtilityClass
public class ServletFilterUtils {

    public static void forwardTheApiCall(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleExceptionAndCreateResponse(e, INTERNAL_SERVER_ERROR, SOMETHING_WENT_WRONG);
        }
    }

    public static HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

}
