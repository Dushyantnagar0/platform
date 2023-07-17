package com.org.platform.utils;

import com.org.platform.errors.errorCodes.PlatformError;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import static org.apache.commons.lang3.StringUtils.isBlank;


@UtilityClass
public class ErrorUtils {
    @Nullable
    public static <T extends PlatformError> T fromErrorCodeOrNull(
            Class<T> enumClass, String errorCode) {
        if (isBlank(errorCode)) {
            return null;
        }
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (errorCode.equalsIgnoreCase(enumConstant.getErrorCode())) {
                return enumConstant;
            }
        }
        return null;
    }
}
