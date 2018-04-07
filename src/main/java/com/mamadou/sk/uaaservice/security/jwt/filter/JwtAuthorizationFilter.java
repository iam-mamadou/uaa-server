package com.mamadou.sk.uaaservice.security.jwt.filter;

import com.mamadou.sk.uaaservice.security.jwt.service.JwtTokenService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    private JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        super(authenticationManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getBearerTokenFromHeader(request);
        if (Strings.isNotBlank(token) && jwtTokenService.isTokenValid(token)) {
            Authentication usernamePasswordToken = jwtTokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
        }
        chain.doFilter(request, response);
    }

    /**
     * Extract token from Authorization header:
     *  Authorization: Bearer <token>
     *
     * @param request - request
     * @return <token>
     */
    private String getBearerTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if(Strings.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }
        return null;
    }
}
