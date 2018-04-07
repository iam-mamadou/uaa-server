package com.mamadou.sk.uaaservice.user.web.mapper.impl;

import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.web.mapper.AuthorityMapper;
import com.mamadou.sk.uaaservice.user.web.dto.AuthorityDTO;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;


public class UserMapperImplTest {

    private static final String[] ADMIN_USER_ROLES = {"ROLE_ADMIN", "ROLE_USER"};
    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email@test.com";
    private static final String PASSWORD = "password";
    private UserMapper userMapper;
    private AuthorityMapper authorityMapper;

    @Before
    public void setUp() {
        authorityMapper = new AuthorityMapperImpl();
        userMapper = new UserMapperImpl(authorityMapper);
    }

    @Test
    public void toEntity_shouldConvertUserDTOToUserEntity() {
        // given
        List<AuthorityDTO> authorities = authorityMapper.toAuthorityDTOs(ADMIN_USER_ROLES);

        UserDTO userDTO = UserDTO.builder()
                                 .firstName(FIRST_NAME).lastName(LAST_NAME)
                                 .username(USERNAME)
                                 .email(EMAIL)
                                 .password(PASSWORD)
                                 .authorities(authorities)
                                 .enabled(true).expired(true).locked(true)
                                 .build();

        // when
        User user = userMapper.toEntity(userDTO);

        // then
        then(user).extracting("firstName", "lastName", "username", "email", "password", "authorities", "enabled", "expired", "expired", "locked")
                  .contains(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD, authorityMapper.toAuthorities(authorities), true, true, true);

    }

    @Test
    public void toDTO_shouldConvertUserEntityToUserDTO() {
        // given
        List<Authority> authorities = authorityMapper.toAuthorities(ADMIN_USER_ROLES);

        User user = new User();
        user.setUserId(1L);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setAuthorities(authorities);
        user.setEnabled(true);
        user.setExpired(true);
        user.setLocked(true);

        // when
        UserDTO userDTO = userMapper.toDTO(user);

        // then
        then(userDTO).extracting("userId", "firstName", "lastName", "username", "email", "password", "authorities", "enabled", "expired", "expired", "locked")
                     .contains(1L, FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD, authorityMapper.toAuthorityDTOs(authorities), true, true, true);

    }
}