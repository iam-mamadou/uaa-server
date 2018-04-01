package com.mamadou.sk.uaaservice.security.jwt;

import com.mamadou.sk.uaaservice.security.user.CustomUserDetails;
import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenServiceImplTest {
    private static final String INVALID_TOKEN_SECRET = "INVALID_SECRET";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ADMIN_USERNAME = "admin";
    private static final String TOKEN_ISSUER = "uaa-service";
    private static final String TOKEN_SECRET = "secret";
    private static final long TOKEN_EXPIRATION = 5L;
    @Mock
    private Authentication authentication;
    private JwtTokenService jwtTokenService;

    @Before
    public void setUp() {
        jwtTokenService = new JwtTokenServiceImpl();

        // setup environment variable that are required by the JwtService
        ReflectionTestUtils.setField(jwtTokenService, "applicationName", TOKEN_ISSUER);
        ReflectionTestUtils.setField(jwtTokenService, "expiration", TOKEN_EXPIRATION);
        ReflectionTestUtils.setField(jwtTokenService, "secret", TOKEN_SECRET);

        // set up new authenticated user
        User admin = new User().setUsername(ADMIN_USERNAME)
                               .setAuthorities(Lists.newArrayList(new Authority(ROLE_ADMIN)));
        given(authentication.getPrincipal()).willReturn(new CustomUserDetails(admin));
    }

    @Test
    public void generate_shouldAddApplicationNameAsIssuedBy() {
        // when
        String generateToken = jwtTokenService.generate(authentication);
        String issuedBy = getParsedTokenBody(generateToken).getIssuer();

        // then
        assertThat(issuedBy).isEqualTo(TOKEN_ISSUER);
    }

    @Test
    public void generate_shouldAddTokenCreationDate() {
        // given
        Instant instant = ZonedDateTime.now().toInstant();
        // when
        String generateToken = jwtTokenService.generate(authentication);
        Date issuedAt = getParsedTokenBody(generateToken).getIssuedAt();

        // then
        assertThat(issuedAt).isEqualToIgnoringMillis(Date.from(instant));
    }

    @Test
    public void generate_shouldAddUsernameAsSubject_whenAuthenticationIsSetup() {
        // when
        String generateToken = jwtTokenService.generate(authentication);
        String username = getParsedTokenBody(generateToken).getSubject();

        // then
        assertThat(username).isEqualTo(ADMIN_USERNAME);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void generate_shouldAddUserAuthorities_whenAuthenticationIsSetup() {
        // when
        String generateToken = jwtTokenService.generate(authentication);
        List<String> authorities = (List<String>) getParsedTokenBody(generateToken).get("auth");

        // then
        assertThat(authorities).containsExactly(ROLE_ADMIN);
    }

    @Test
    public void generate_shouldAddTokenExpirationDate() {
        // given
        Instant instant = ZonedDateTime.now()
                                       .plusMinutes(TOKEN_EXPIRATION)
                                       .toInstant();
        // when
        String generateToken = jwtTokenService.generate(authentication);
        Date issuedAt = getParsedTokenBody(generateToken).getExpiration();

        // then
        assertThat(issuedAt).isEqualToIgnoringMillis(Date.from(instant));
    }

    @Test
    public void getAuthentication_shouldPopulateAuthenticationWithUsernameFromToken() {
        // given
        String token = jwtTokenService.generate(authentication);

        // when
        Authentication usernamePasswordAuthenticationToken = jwtTokenService.getAuthentication(token);
        String username = (String) usernamePasswordAuthenticationToken.getPrincipal();

        // then
        assertThat(username).isEqualTo(ADMIN_USERNAME);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAuthentication_shouldPopulateAuthenticationWithAuthoritiesFromToken() {
        // given
        String token = jwtTokenService.generate(authentication);

        // when
        Authentication usernamePasswordAuthenticationToken = jwtTokenService.getAuthentication(token);
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) usernamePasswordAuthenticationToken.getAuthorities();

        // then
        assertThat(authorities).isEqualTo(AuthorityUtils.createAuthorityList(ROLE_ADMIN));
    }


    @Test
    public void isTokenValid_shouldReturnTrue_whenTokenIsValid() {
        // given
        String validToken = jwtTokenService.generate(authentication);

        // when
        boolean tokenValid = jwtTokenService.isTokenValid(validToken);

        // then
        assertThat(tokenValid).isTrue();
    }

    @Test
    public void isTokenValid_shouldReturnFalse_whenTokenAlreadyExpired() {
        // given
        // set up token to 0 so that it will expire by the time we
        ReflectionTestUtils.setField(jwtTokenService, "expiration", 0L);

        String expiredToken = jwtTokenService.generate(authentication);

        // when
        boolean tokenValid = jwtTokenService.isTokenValid(expiredToken);

        // then
        assertThat(tokenValid).isFalse();
    }

    @Test
    public void isTokenValid_shouldReturnFalse_whenTokenIsMalFormed() {
        // given
        String malformedToken = "eyJhbGciOiJIUzUxMiJ9..-bA";

        // when
        boolean tokenValid = jwtTokenService.isTokenValid(malformedToken);

        // then
        assertThat(tokenValid).isFalse();
    }

    @Test
    public void isTokenValid_shouldReturnFalse_whenTokenHaveDifferentSecretKey() {
        // given
        String validToken = jwtTokenService.generate(authentication);

        // change token secret key
        ReflectionTestUtils.setField(jwtTokenService, "secret", INVALID_TOKEN_SECRET);

        // when
        boolean tokenValid = jwtTokenService.isTokenValid(validToken);

        // then
        assertThat(tokenValid).isFalse();

    }

    @Test
    public void isTokenValid_shouldReturnFalse_whenTokenIsNull() {
        // when
        boolean tokenValid = jwtTokenService.isTokenValid(null);

        // then
        assertThat(tokenValid).isFalse();
    }

    @Test
    public void isTokenValid_shouldReturnFalse_whenTokenIsEmpty() {
        // when
        boolean tokenValid = jwtTokenService.isTokenValid("");

        // then
        assertThat(tokenValid).isFalse();
    }

    private Claims getParsedTokenBody(String generateToken) {
        return (Claims) Jwts.parser()
                            .setSigningKey(TOKEN_SECRET)
                            .parse(generateToken)
                            .getBody();
    }
}