package com.org.platform.errors.exceptions;


import com.org.platform.errors.errorCodes.PlatformError;

public class PlatformCoreException extends RuntimeException implements PlatformException{

    private String errorCode;
    private String errorMessage;

    PlatformCoreException() {
    }

    public PlatformCoreException(Throwable cause) {
        super(cause);
    }

    public PlatformCoreException(String errorMessage) {
        super(errorMessage);
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

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
