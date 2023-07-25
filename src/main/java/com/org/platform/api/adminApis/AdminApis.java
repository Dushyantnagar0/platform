package com.org.platform.api.adminApis;

import com.org.platform.enums.UserAccessType;
import com.org.platform.requests.UserMetaDataRequest;
import com.org.platform.services.interfaces.AdminMetaDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.org.platform.services.HeaderContextService.getContext;
import static com.org.platform.utils.RestEntityBuilder.okResponseEntity;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminApis {

    private final AdminMetaDataService adminMetaDataService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testConsumerApi(){
        return okResponseEntity(getContext());
    }

    @PostMapping("/add/user")
    public ResponseEntity<Map<String, Object>> addOrUpdateUserInMetaData(@RequestBody UserMetaDataRequest userMetaDataRequest){
        return okResponseEntity(adminMetaDataService.upsertEmailIdAndAccessLevelInMetaData(userMetaDataRequest));
    }

}
