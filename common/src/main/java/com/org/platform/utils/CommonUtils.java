package com.org.platform.utils;

import com.org.platform.errors.errorCodes.PlatformErrorCodes;
import com.org.platform.errors.exceptions.PlatformCoreException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class CommonUtils {

    public static void handleExceptionAndCreateResponse(Exception e, PlatformErrorCodes platformErrorCodes, String errorMessage) {
        if(e instanceof PlatformCoreException pe) {
            throw new PlatformCoreException(platformErrorCodes, pe.getErrorMessage(), Arrays.toString(pe.getParams()));
        } else {
            throw new PlatformCoreException(platformErrorCodes, errorMessage, e.getLocalizedMessage());
        }
    }

}
