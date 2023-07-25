package com.org.platform.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeaderContext {
    private String userId;
    private String emailId;
    private String userType;
    private String customerId;
    private String apiToken;
    private String clientId;
    private String requestId;
    private Map<String, String> extraHeaderMap;
 }
