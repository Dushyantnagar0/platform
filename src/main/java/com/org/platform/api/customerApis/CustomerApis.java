package com.org.platform.api.customerApis;

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
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApis {

    private final LogInService logInService;

    @GetMapping("/context")
    public ResponseEntity<Map<String, Object>> getCustomerApiContext(){
        return okResponseEntity(getContext());
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(){
        return okResponseEntity(logInService.doLogout());
    }

}
