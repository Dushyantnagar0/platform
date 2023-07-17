package com.org.platform.api.health;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/health")
public class DefaultHealthCheckAPI {

    private static final Date startedSince = new Date();
    private static final String STATUS = "status";
    private static final String OK = "ok";
    private static final String TIME = "TIME";
    private static final String SINCE = "since";


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> healthApi() {
        Map<String, Object> map = new HashMap<>();
        map.put(STATUS, OK);
        map.put(TIME, new Date().toString());
        map.put(SINCE, startedSince.toString());
        return map;
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
