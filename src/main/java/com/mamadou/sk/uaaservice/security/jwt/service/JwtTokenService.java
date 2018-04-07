package com.mamadou.sk.uaaservice.security.jwt.service;

import org.springframework.security.core.Authentication;


/**
 *  Service for generating and validating jwts
 *  @author Mamadou Barry
 *  @since 1.0.0
 */
public interface JwtTokenService {

    /**
     * Generate token in the following format for authentication
     *          header.payload.signature
     * @param authentication - authenticated user
     * @return token signed with the secret using HS512
     */
    String generate(Authentication authentication);

    /**
     * Parse jwt and populate UsernamePasswordAuthenticationToken with username
     * and authorities from jwt. This Authentication token will be used for authentication
     *
     *  @param jwt - token
     * @return Authentication with username and password
     */
    Authentication getAuthentication(String jwt);

    /**
     * validate jwt
     *
     * @param jwtToken - token
     * @return true if token is valid
     */
    boolean isTokenValid(String jwtToken);
}
