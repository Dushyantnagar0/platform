package com.org.platform.errors.errorResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.platform.errors.errorCodes.PlatformError;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private ErrorDetails errorDetails;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorDetails implements Serializable, PlatformError {

        private String errorCode;
        private String errorMessage;

        @Override
        public String getErrorCode() {
            return this.errorCode;
        }

        @Override
        public String getErrorMessage() {
            return this.errorMessage;
        }
    }
}
