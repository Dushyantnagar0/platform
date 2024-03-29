package com.org.platform.services;

import com.org.platform.beans.HeaderContext;
import com.org.platform.enums.UserAccessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.org.platform.utils.HeaderConstants.*;

@Slf4j
@Component
public class HeaderContextService {

    public static final ThreadLocal<HeaderContext> headerContext = new ThreadLocal<>();

    public static HeaderContext getContext() {
        return headerContext.get();
    }

    public static HeaderContext setContext(HeaderContext c) {
        final HeaderContext previousContext = getContext();
        unsetContext();
        headerContext.set(c);
        return previousContext;
    }

    public static void unsetContext() {
        headerContext.remove();
    }

    public static void createHeaderContextFromHttpHeadersForPublicApi(HttpServletRequest httpServletRequest) {
        HeaderContext headers = new HeaderContext();
        headers.setUserId(httpServletRequest.getHeader(USER_ID_KEY));
        headers.setClientId(httpServletRequest.getHeader(CLIENT_ID_KEY));
        headers.setUserType(UserAccessType.PUBLIC.name());
        headers.setRequestId(httpServletRequest.getHeader(REQUEST_ID_KEY));
        headerContext.set(headers);
    }

    public static void createHeaderContextFromHttpHeaders(HttpServletRequest httpServletRequest, Map<String, Object> headerMap) {
        HeaderContext headers = new HeaderContext();
        headers.setUserId(httpServletRequest.getHeader(USER_ID_KEY));
        headers.setClientId(httpServletRequest.getHeader(CLIENT_ID_KEY));
        headers.setRequestId(httpServletRequest.getHeader(REQUEST_ID_KEY));
        headers.setCustomerId((String) headerMap.get(CUSTOMER_ID_KEY));
        headers.setApiToken((String) headerMap.get(API_TOKEN_KEY));
        headers.setEmailId((String) headerMap.get(EMAIL_ID_KEY));
        headers.setUserType((String) headerMap.get(USER_TYPE_KEY));
        headerContext.set(headers);
    }

    public static String getCurrentCustomerId() {
        HeaderContext headerContext = getContext();
        return headerContext.getCustomerId();
    }

    public static String getCurrentUserEmailId() {
        HeaderContext headerContext = getContext();
        return headerContext.getEmailId();
    }

    public static String getCurrentUserClientId() {
        HeaderContext headerContext = getContext();
        return headerContext.getClientId();
    }

}
