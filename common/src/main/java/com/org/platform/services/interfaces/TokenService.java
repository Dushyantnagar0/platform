package com.org.platform.services.interfaces;

import com.org.platform.requests.TokenGenerationRequest;

public interface TokenService {

    String generateJwtToken(TokenGenerationRequest tokenGenerationRequest);
    boolean validateJwtToken(String tokenId, String customerId);
}
