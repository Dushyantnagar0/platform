package com.org.platform.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.platform.enums.UserAccessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMetaDataRequest {
    private String emailId;
    private UserAccessType userAccessType;
}
