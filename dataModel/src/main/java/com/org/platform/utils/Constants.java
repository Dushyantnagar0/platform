package com.org.platform.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String SECRET_KEY = System.getenv("secretKey");
    public static final String APP_ROOT = "/platform";
    public static final String APPLICATION_JSON_TYPE = "application/json";
    public static final String SERVICE_NAME = System.getenv("service_name");
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String JSON_PARSING_ERROR = "Json Parsing Error";
    public static final String PLATFORM_LOGIN = "PLATFORM_LOGIN";

}
