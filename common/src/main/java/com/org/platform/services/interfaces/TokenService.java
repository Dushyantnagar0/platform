package com.org.platform.services.interfaces;

import com.org.platform.requests.TokenGenerationRequest;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    String generateJwtToken(TokenGenerationRequest tokenGenerationRequest, String accessType);
    boolean validateJwtTokenAndCreateHeaderMap(HttpServletRequest httpRequest, String tokenId, String customerId, boolean isForAdmin);
}
