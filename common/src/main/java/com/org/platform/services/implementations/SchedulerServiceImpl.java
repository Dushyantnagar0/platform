package com.org.platform.services.implementations;

import com.org.platform.services.interfaces.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {


    @Override
    @Scheduled(fixedRate = 60000) // Runs every 60 secs  // Disadvantages : this will be instance level
    public void scheduledPrintMessage() {
        log.info("Scheduled current time in millis : {}", System.currentTimeMillis());
    }

}
