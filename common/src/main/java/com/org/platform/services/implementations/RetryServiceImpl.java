package com.org.platform.services.implementations;

import com.org.platform.errors.exceptions.PlatformCoreException;
import com.org.platform.services.interfaces.RetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryServiceImpl implements RetryService {

    @Override
    public void retry() throws PlatformCoreException {
        log.info("Throwing PlatformCoreException in method retry");
        throw new PlatformCoreException("Exception In Retry");
    }

    @Recover
    public void recover(PlatformCoreException exception) {
        log.info("Default Recover method");
        throw new PlatformCoreException(exception.getErrorMessage(), "Recover Also Failed");
//        return "Error Class :: " + throwable.getClass().getName();
    }
}
