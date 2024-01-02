package com.org.platform.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String SECRET_KEY = System.getenv("secretKey");
    public static final String PLATFORM_GLOBAL_DB = System.getenv("dbName");
    public static final String APP_ROOT = "/platform";
    public static final String APPLICATION_JSON_TYPE = "application/json";
    public static final String SERVICE_NAME = System.getenv("service_name");
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String SOMETHING_WENT_WRONG = "Something Went Wrong";
    public static final String SOMETHING_WENT_WRONG_WHILE_SENDING_OTP = "Something Went Wrong While Sending OTP";
    public static final String FAILED_TO_VALIDATE_OTP_MESSAGE = "Failed to validate OTP";
    public static final String JSON_PARSING_ERROR = "Json Parsing Error";
    public static final String PLATFORM_LOGIN = "PLATFORM_LOGIN";
//    @Value("ip.rate.limit.threshold.limit")
    public static final long IP_RATE_LIMIT_THRESHOLD_LIMIT = 5;
    public static final long TOTAL_RATE_LIMIT_THRESHOLD_LIMIT = 8;
    public static final String SAMPLE_HTML_RESPONSE = "<html>\n" + "<header><title>Welcome</title></header>\n" + "<body>\n" + "Hello world\n" + "</body>\n" + "</html>";

}
