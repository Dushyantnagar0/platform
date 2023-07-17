package com.org.platform.errors.errorCodes;

public enum LoginError implements PlatformError {

    INTERNAL_SERVER_ERROR("PE100", "internal.server.error"),
    FAILED_TO_SEND_OTP("PE101", "otp.send.failed"),
    AUTHENTICATION_FAILED("PE102", "authentication.failed"),
    INVALID_OTP_REQUEST("PE103", "invalid.otp.request"),
    INVALID_OTP_VALIDATION_REQUEST("PE104", "invalid.otp.validation.request"),
    FAILED_TO_VALIDATE_OTP("PE106", "otp.validation.failed"),
    INVALID_TOKEN("PE107", "invalid.token");

    private final String errorCode;
    private final String errorMessage;

    LoginError(String errorCode, String errorMessage) {
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
