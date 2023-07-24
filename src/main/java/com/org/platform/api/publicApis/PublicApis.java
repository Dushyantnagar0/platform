package com.org.platform.api.publicApis;

import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.services.HeaderContextService;
import com.org.platform.services.interfaces.LogInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.org.platform.services.HeaderContextService.getContext;
import static com.org.platform.utils.RestEntityBuilder.okResponseEntity;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicApis {

    private final LogInService logInService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testPublicApi(){
        return okResponseEntity(getContext());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LogInRequest logInRequest) {
        return okResponseEntity(logInService.doLogin(logInRequest));
    }

    @PostMapping("/validate/otp")
    public ResponseEntity<Map<String, Object>> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        return okResponseEntity(logInService.validateOtp(otpValidationRequest));
    }

}
