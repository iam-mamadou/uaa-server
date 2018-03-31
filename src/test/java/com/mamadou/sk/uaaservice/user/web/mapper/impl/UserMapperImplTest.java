package com.mamadou.sk.uaaservice.user.web.mapper.impl;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.service.impl.AuthorityMapper;
import com.mamadou.sk.uaaservice.user.web.dto.AuthorityDTO;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;


public class UserMapperImplTest {

    private UserMapper userMapper;
    private AuthorityMapper authorityMapper;

    @Before
    public void setUp() {
        userMapper = new UserMapperImpl(new ModelMapper());
        authorityMapper = new AuthorityMapperImpl();
    }

    @Test
    public void toEntity_shouldConvertUserDTOToUserEntity() {
        // given
        List<AuthorityDTO> authorities = authorityMapper.toAuthorityDTOs("ROLE_ADMIN", "ROLE_USER");

        UserDTO userDTO = UserDTO.builder()
                             .firstName("first").lastName("last")
                             .username("username").email("email@test.com")
                             .password("password")
                             .authorities(authorities)
                             .enabled(true).expired(true).locked(true)
                             .build();

        // when
        User user = userMapper.toEntity(userDTO);

        // then
        then(user).extracting("firstName", "lastName", "username", "email", "password", "authorities", "enabled", "expired", "expired", "locked")
                  .contains("first", "last", "email@test.com", "password", authorityMapper.toAuthorities(authorities), true, true, true);

    }
}