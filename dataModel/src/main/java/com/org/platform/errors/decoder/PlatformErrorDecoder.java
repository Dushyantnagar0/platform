package com.org.platform.errors.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.platform.errors.errorCodes.PlatformErrorCodes;
import com.org.platform.errors.errorResponses.ErrorResponse;
import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.utils.ErrorUtils;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.org.platform.utils.Constants.INTERNAL_SERVER_ERROR;
import static com.org.platform.utils.Constants.JSON_PARSING_ERROR;
import static java.util.Objects.nonNull;

@Slf4j
public class PlatformErrorDecoder implements ErrorDecoder {

//    Not being used, use in feign client
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        log.info("exception in decoder : ", exception);
        final ErrorResponse.ErrorDetails errorDetails;
        try {
            errorDetails = new ObjectMapper().readValue(exception.content(), ErrorResponse.class).getErrorDetails();
        } catch (IOException e) {
            throw new RuntimeException(JSON_PARSING_ERROR);
        }
        if(nonNull(errorDetails)) {
            PlatformErrorCodes platformErrorCode = ErrorUtils.fromErrorCodeOrNull(PlatformErrorCodes.class, errorDetails.getErrorCode());
            if(nonNull(platformErrorCode)) {
                throw new PlatformCoreException(platformErrorCode);
            }
        }
        throw new RuntimeException(INTERNAL_SERVER_ERROR);
    }
}
