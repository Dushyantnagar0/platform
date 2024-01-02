package com.org.platform.api.customerApis;

import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.ProfileUpdateRequest;
import com.org.platform.services.interfaces.CustomerAccountService;
import com.org.platform.services.interfaces.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.org.platform.services.HeaderContextService.getContext;
import static com.org.platform.utils.RestEntityBuilder.okResponseEntity;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApis {

    private final LogInService logInService;
    private final CustomerAccountService customerAccountService;

    @GetMapping("/context")
    public ResponseEntity<Map<String, Object>> getCustomerApiContext(){
        return okResponseEntity(getContext());
    }

    @GetMapping("/login/data")
    public ResponseEntity<Map<String, Object>> getLoginData() {
        return okResponseEntity(logInService.getLoginData());
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(){
        logInService.doLogout();
        return okResponseEntity("Logging out");
    }

    @PostMapping("/update/profile")
    public ResponseEntity<Map<String, Object>> logout(@RequestBody ProfileUpdateRequest profileUpdateRequest){
        return okResponseEntity(customerAccountService.updateCustomerAccount(profileUpdateRequest));
    }

}
