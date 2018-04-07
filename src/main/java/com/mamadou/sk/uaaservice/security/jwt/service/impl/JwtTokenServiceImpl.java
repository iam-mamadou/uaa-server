package com.mamadou.sk.uaaservice.security.jwt.service.impl;

import com.mamadou.sk.uaaservice.security.jwt.mapper.GrantedAuthorityMapper;
import com.mamadou.sk.uaaservice.security.jwt.service.JwtTokenService;
import com.mamadou.sk.uaaservice.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    private GrantedAuthorityMapper authorityMapper;

    public JwtTokenServiceImpl(GrantedAuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }

    @Override
    public String generate(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
        return Jwts.builder()
                   .setId(UUID.randomUUID().toString())
                   .setIssuer(applicationName)
                   .setIssuedAt(createdDate())
                   .setSubject(user.getUsername())
                   .claim("auth", authorityMapper.authorities(user.getAuthorities()))
                   .setExpiration(expirationDate())
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String jwt) {
        Claims body =(Claims) Jwts.parser()
                                  .setSigningKey(secret)
                                  .parse(jwt)
                                  .getBody();
        List<String> authorityClaim = ((List<String>) body.get("auth"));
        List<SimpleGrantedAuthority> authorities = authorityMapper.toSimpleGrantedAuthorities(authorityClaim);
        return new UsernamePasswordAuthenticationToken(body.getSubject(), jwt, authorities);
    }

    @Override
    public boolean isTokenValid(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secret).parse(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("Token have expired", e);
        } catch (MalformedJwtException e) {
            log.info("Token is malformed");
        } catch (SignatureException e) {
            log.info("Token Signature could be verified");
        } catch (IllegalArgumentException e) {
            log.info("token is missing or empty");
        }
        return false;
    }

    /**
     * set token expiration date
     * The expiration date is calculate by taking current time plus the given
     * {jwt.expiration} value from the application properties
     * @return expiration date
     */
    private Date expirationDate() {
        ZonedDateTime expirationDate = ZonedDateTime.now().plusMinutes(expiration);
        return Date.from(expirationDate.toInstant());
    }

    /**
     * get token issued date
     *
     * @return issued date
     */
    private Date createdDate() {
        return new Date();
    }

}
