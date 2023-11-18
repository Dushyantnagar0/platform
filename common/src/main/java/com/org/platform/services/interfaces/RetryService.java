package com.org.platform.services.interfaces;

import com.org.platform.errors.exceptions.PlatformCoreException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface RetryService {

    @Retryable(value = {PlatformCoreException.class}, maxAttempts = 2, backoff = @Backoff(200))
    void retry() throws PlatformCoreException;

}
