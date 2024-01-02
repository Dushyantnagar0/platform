package com.org.platform.api.publicApis;

import com.org.platform.annotations.PrintJson;
import com.org.platform.annotations.TrackRunTime;
import com.org.platform.requests.LogInRequest;
import com.org.platform.requests.OtpValidationRequest;
import com.org.platform.services.interfaces.EventSender;
import com.org.platform.services.interfaces.LogInService;
import com.org.platform.services.interfaces.RetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.org.platform.services.HeaderContextService.getContext;
import static com.org.platform.utils.RestEntityBuilder.okResponseEntity;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicApis {


    @Value("${service_name}")
    public String service_name;

    private final LogInService logInService;
    private final RetryService retryService;
    private final EventSender eventSender;

    @GetMapping("/service-name")
    public ResponseEntity<Map<String, Object>> getServiceName(){
        return okResponseEntity(service_name);
    }

    @GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getSampleHtmlResponse(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform.html");
        return modelAndView;
    }

    @GetMapping("/context")
    @PrintJson
    public ResponseEntity<Map<String, Object>> getPublicApiContext(){
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

    @GetMapping("/retry")
    public ResponseEntity<Map<String, Object>> retryApi(){
        retryService.retry();
        return okResponseEntity("Ok");
    }

    @PostMapping("/send/event/{topic}")
    public ResponseEntity<Map<String, Object>> sendEvent(@PathVariable("topic") String topic, @RequestBody Object event){
        eventSender.sendMessage(topic, event);
        return okResponseEntity("Ok");
    }

}
