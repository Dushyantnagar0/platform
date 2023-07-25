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
public class CustomerAccountRequest {
    private String customerId;
    private String emailId;
    private String phoneNumber;
    private UserAccessType userAccessType;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String zipCode;
}
