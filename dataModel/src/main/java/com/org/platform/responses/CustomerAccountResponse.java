package com.org.platform.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAccountResponse {
    private String emailId;
    private String customerId;
    private String phoneNumber;
    private String userAccessType;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String zipCode;
}
