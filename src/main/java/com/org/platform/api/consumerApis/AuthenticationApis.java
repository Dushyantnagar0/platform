package com.org.platform.api.consumerApis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.org.platform.services.HeaderContextService.getContext;
import static com.org.platform.utils.RestEntityBuilder.okResponseEntity;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class AuthenticationApis {

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testConsumerApi(){
        return okResponseEntity(getContext());
    }

}
