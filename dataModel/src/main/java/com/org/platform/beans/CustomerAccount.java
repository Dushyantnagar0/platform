package com.org.platform.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAccount {
    @Id
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
    private String token;
}
