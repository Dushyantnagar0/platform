package com.org.platform.errors.exceptionResolver;

import com.org.platform.errors.exceptions.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class PlatformExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof PlatformCoreException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // Set custom response body or headers
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        // Log the exception
        log.error("Error occurred in filter: ", ex);
        return new ModelAndView();
    }
}
