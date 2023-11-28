package com.org.platform.errors.exceptions;


import com.org.platform.errors.errorCodes.PlatformError;

import static com.org.platform.utils.Constants.SOMETHING_WRONG_HAPPENED;

public class PlatformCoreException extends RuntimeException implements PlatformException {

    private String errorCode;
    private String errorMessage;
    private String[] params;

    PlatformCoreException() {
    }

    public PlatformCoreException(Throwable cause) {
        super(cause);
    }

    public PlatformCoreException(String errorMessage) {
        super(errorMessage);
        this.errorCode = SOMETHING_WRONG_HAPPENED;
        this.errorMessage = errorMessage;
    }

    public PlatformCoreException(int errorCode) {
        super(String.valueOf(errorCode));
    }

    public PlatformCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformCoreException(PlatformError platformError) {
        this.errorCode = platformError.getErrorCode();
        this.errorMessage = platformError.getErrorMessage();
    }

    public PlatformCoreException(PlatformError platformError, String... params) {
        this(platformError);
        this.params = params;
    }

    public PlatformCoreException(String... params) {
        this.errorCode = SOMETHING_WRONG_HAPPENED;
        this.params = params;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String[] getParams() {
        return this.params;
    }
}
