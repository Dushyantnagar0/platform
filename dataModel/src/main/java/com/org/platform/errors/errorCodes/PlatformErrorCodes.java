package com.org.platform.errors.errorCodes;

public enum PlatformErrorCodes implements PlatformError {

    INTERNAL_SERVER_ERROR("PE100", "internal.server.error"),
    FAILED_TO_SEND_OTP("PE101", "otp.send.failed"),
    AUTHENTICATION_FAILED("PE102", "authentication.failed"),
    INVALID_OTP_REQUEST("PE103", "invalid.otp.request"),
    INVALID_OTP_VALIDATION_REQUEST("PE104", "invalid.otp.validation.request"),
    FAILED_TO_VALIDATE_OTP("PE106", "otp.validation.failed"),
    INVALID_TOKEN("PE107", "invalid.token"),
    NO_CUSTOMER_FOUND("PE108", "no.customer.found"),
    TEST_EXCEPTION("PE109", "test.exception"),
    IP_RATE_LIMIT_EXCEEDED("PE110", "ip.rate.limit.exceeded"),
    RATE_LIMIT_EXCEEDED("PE111", "rate.limit.exceeded"),
    INVALID_HEADERS("PE112", "invalid.headers");

    private final String errorCode;
    private final String errorMessage;

    PlatformErrorCodes(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
