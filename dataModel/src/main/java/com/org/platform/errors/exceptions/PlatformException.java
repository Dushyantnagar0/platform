package com.org.platform.errors.exceptions;

public interface PlatformException {

    String getErrorCode();

    String getErrorMessage();

    String[] getParams();
}
