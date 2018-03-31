package com.mamadou.sk.uaaservice.security.jwt;

import com.mamadou.sk.uaaservice.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Service for generating and validating jwts
 *  @author Mamadou Barry
 *  @since 1.0.0
 */
@Component
@Slf4j
public class JwtTokenService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    private ZonedDateTime now = ZonedDateTime.now();


    /**
     * Generate token in the following format for authentication
     *          header.payload.signature
     * @param authentication - authenticated user
     * @return token signed with the secret using HS512
     */
    public String generate(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
        return Jwts.builder()
                   .setIssuer(applicationName)
                   .setIssuedAt(createdDate())
                   .setSubject(user.getUsername())
                   .claim("auth", authorities(user.getAuthorities()))
                   .setExpiration(expirationDate())
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }

    /**
     * Parse jwt and populate UsernamePasswordAuthenticationToken with username
     * and authorities from jwt. This Authentication token will be used for authentication
     *
     *  @param jwt - token
     * @return Authentication with username and password
     */
    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String jwt) {
        Claims body =(Claims) Jwts.parser()
                                  .setSigningKey(secret)
                                  .parse(jwt)
                                  .getBody();
        List<String> authorityClaim = ((List<String>) body.get("auth"));
        List<SimpleGrantedAuthority> authorities = authorityClaim.stream()
                                                                 .map(SimpleGrantedAuthority::new)
                                                                 .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(body.getSubject(), jwt, authorities);
    }

    /**
     * validate jwt
     *
     * @param jwtToken - token
     * @return true if token is valid
     */
    public boolean isTokenValid(String jwtToken) {
        // TODO further validation
        try {
            Jwts.parser().setSigningKey(secret).parse(jwtToken);
            return true;
        } catch (Exception e) {
            log.info("Invalid jwt", e);
        }
        return false;
    }

    /**
     * convert List of GrantedAuthority to an array of String
     *
     * @param authorities - user granted authorities
     * @return array of authorities
     */
    private String[] authorities(Collection<? extends GrantedAuthority> authorities) {
        return Strings.toStringArray(authorities.stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList()));
    }

    /**
     * set token expiration date
     *
     * @return expiration date
     */
    private Date expirationDate() {
        ZonedDateTime expirationDate = now.plusHours(expiration);
        return Date.from(expirationDate.toInstant());
    }

    /**
     * get token issued date
     *
     * @return issued date
     */
    private Date createdDate() {
        return Date.from(now.toInstant());
    }

}
