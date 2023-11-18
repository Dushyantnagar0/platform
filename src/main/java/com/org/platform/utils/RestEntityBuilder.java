package com.org.platform.utils;

import com.org.platform.errors.exceptions.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isBlank;


@Slf4j
public class RestEntityBuilder {

    public static final String SUCCESS = "success";
    public static final String STATUS = "status";
    public static final String DATA = "data";
    public static final String FAILED = "failed";
    public static final String ERROR_DETAILS = "errorDetails";
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_KEY = "errorKey";
    public static final String DISPLAY_MESSAGE = "displayMessage";
    public static final String PARAMS = "params";



    public static ResponseEntity<Map<String, Object>> okResponseEntity(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, SUCCESS);
        response.put(DATA, data);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> errorResponseEntity(Object errorDetails, HttpStatus httpStatus, HttpHeaders headers) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, FAILED);
        response.put(ERROR_DETAILS, errorDetails);
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON_UTF8).headers(headers).body(response);
    }

    public static ResponseEntity<Map<String, Object>> createErrorResponseEntity(Throwable exception, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, FAILED);
        if(exception instanceof PlatformCoreException) {
            HttpHeaders headers = createHeadersForPlatformException((PlatformCoreException) exception);
            Map<String, Object> details = createPlatformExceptionErrorDetails((PlatformCoreException) exception);
            response.put(ERROR_DETAILS, details);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).body(response);
        }
        HttpHeaders headers = createHeadersAndResponseForJavaException(exception, response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).body(response);
    }

    private static Map<String, Object> createPlatformExceptionErrorDetails(PlatformCoreException exception) {
        Map<String, Object> details = new HashMap<>();
        safePut(details, ERROR_CODE, exception.getErrorCode());
        safePut(details, DISPLAY_MESSAGE, exception.getErrorMessage());
        safePut(details, PARAMS, exception.getParams());
        return details;
    }

    private static HttpHeaders createHeadersAndResponseForJavaException(Throwable exception, Map<String, Object> response) {
        Map<String, Object> errorDetails = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        if (exception.getMessage() != null) {
//            addToHeader(headers, ERROR_CODE, exception.getMessage());
            safePut(errorDetails, ERROR_CODE, exception.getMessage());
        }
        if (exception.getLocalizedMessage() != null) {
//            addToHeader(headers, ERROR_KEY, exception.getLocalizedMessage());
            safePut(errorDetails, ERROR_KEY, exception.getLocalizedMessage());
        } else {
//            addToHeader(headers, ERROR_KEY, "error");
            safePut(errorDetails, ERROR_KEY, "error");
        }
        response.put(ERROR_DETAILS, errorDetails);
        return headers;
    }

    private static HttpHeaders createHeadersForPlatformException(PlatformCoreException exception) {
        HttpHeaders headers = new HttpHeaders();
        if (exception.getMessage() != null) {
            addToHeader(headers, ERROR_CODE, exception.getErrorCode());
        }
        if (exception.getErrorMessage() != null) {
            addToHeader(headers, ERROR_KEY, exception.getErrorMessage());
        } else {
            addToHeader(headers, ERROR_KEY, "error");
        }
        return headers;
    }

    private static void addToHeader(HttpHeaders headers, String key, String value) {
        headers.put(key, Collections.singletonList(value));
    }

    private static boolean safePut(Map<String, Object> map, String key, Object value) {
        if (isBlank(key)) {
            return false;
        }
        if (value == null) {
            return false;
        }
        map.put(key, value);
        return true;
    }

}
