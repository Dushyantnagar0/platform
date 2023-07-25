package com.org.platform.api.consumerApis;

import com.org.platform.services.interfaces.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.org.platform.services.HeaderContextService.getContext;
import static com.org.platform.utils.RestEntityBuilder.okResponseEntity;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class AuthenticationApis {

    private final LogInService logInService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testConsumerApi(){
        return okResponseEntity(getContext());
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(){
        return okResponseEntity(logInService.doLogout());
    }

}
