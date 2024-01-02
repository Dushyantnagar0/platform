package com.org.platform.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.platform.errors.exceptions.PlatformCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;


@Slf4j
public class RestEntityBuilder {

    public static final String SUCCESS = "success";
    public static final String STATUS = "status";
    public static final String DATA = "data";
    public static final String FAILED = "failed";
    public static final String ERROR_DETAILS = "errorDetails";
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MESSAGE = "errorMessage";
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

    public static ResponseEntity<Map<String, Object>> createErrorResponseEntity(Throwable exception) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, FAILED);
        if(exception instanceof PlatformCoreException) {
            HttpHeaders headers = createHeadersForPlatformException((PlatformCoreException) exception);
            Map<String, Object> details = createPlatformExceptionErrorDetails((PlatformCoreException) exception);
            response.put(ERROR_DETAILS, details);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).body(response);
        }
        HttpHeaders headers = createHeadersAndResponseForJavaException(exception, response);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).body(response);
    }

    public static void handleExceptionResponse(HttpServletResponse httpResponse, Exception e) {
        try {
            httpResponse.getWriter().write(Objects.requireNonNull(createCustomErrorResponseBody(httpResponse, e)));
        } catch (IOException ignored) {
            log.error("error while writing http response : ", ignored);
        }
    }

    private static String createCustomErrorResponseBody(HttpServletResponse httpResponse, Exception e) {
        try {
            return new ObjectMapper().writeValueAsString(createServletFilterErrorResponse(httpResponse, e));
        } catch (JsonProcessingException ignored) {
            log.error("error while writing processing into json : ", ignored);
        }
        return null;
    }

    private static Map<String, Object> createServletFilterErrorResponse(HttpServletResponse httpResponse, Exception exception) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, FAILED);
        httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if(exception instanceof PlatformCoreException e) {
            createHttpResponseForPlatformException(httpResponse, e, response);
        } else {
            createHttpResponseForJavaException(exception, response);
        }
        return response;
    }

    private static void createHttpResponseForPlatformException(HttpServletResponse httpResponse, PlatformCoreException e, Map<String, Object> response) {
        httpResponse.setHeader(ERROR_CODE, e.getErrorCode());
        httpResponse.setHeader(ERROR_MESSAGE, e.getErrorMessage());
        httpResponse.setHeader(PARAMS, Arrays.toString(e.getParams()));
        response.put(ERROR_DETAILS, createPlatformExceptionErrorDetails(e));
    }

    private static void createHttpResponseForJavaException(Exception exception, Map<String, Object> response) {
        Map<String, Object> errorDetails = new HashMap<>();
        if (isNotBlank(exception.getLocalizedMessage())) {
            safePut(errorDetails, ERROR_MESSAGE, exception.getLocalizedMessage());
        } else {
            safePut(errorDetails, ERROR_MESSAGE, "Something went wrong");
        }
        response.put(ERROR_DETAILS, errorDetails);
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
        if (exception.getLocalizedMessage() != null) {
            addToHeader(headers, ERROR_MESSAGE, exception.getLocalizedMessage());
            safePut(errorDetails, ERROR_MESSAGE, exception.getLocalizedMessage());
        } else {
            addToHeader(headers, ERROR_MESSAGE, "Something went wrong");
            safePut(errorDetails, ERROR_MESSAGE, "Something went wrong");
        }
        response.put(ERROR_DETAILS, errorDetails);
        return headers;
    }

    private static HttpHeaders createHeadersForPlatformException(PlatformCoreException exception) {
        HttpHeaders headers = new HttpHeaders();
        if (isNotBlank(exception.getMessage())) {
            addToHeader(headers, ERROR_CODE, exception.getErrorCode());
        }
        if (isNotBlank(exception.getErrorMessage())) {
            addToHeader(headers, ERROR_KEY, exception.getErrorMessage());
        } else {
            addToHeader(headers, ERROR_KEY, "Something went wrong");
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
        if (isNull(value)) {
            return false;
        }
        map.put(key, value);
        return true;
    }

}
