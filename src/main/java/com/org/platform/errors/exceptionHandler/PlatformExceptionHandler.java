package com.org.platform.errors.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.org.platform.utils.RestEntityBuilder.createErrorResponseEntity;


@Slf4j
@RestControllerAdvice
@Order(100)
public class PlatformExceptionHandler {

    @ExceptionHandler({Throwable.class})
    public ResponseEntity handleException(Throwable exception) {
        log.error("Exception in request ", exception);
        return createErrorResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
