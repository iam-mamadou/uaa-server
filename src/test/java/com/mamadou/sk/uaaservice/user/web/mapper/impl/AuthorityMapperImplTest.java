package com.mamadou.sk.uaaservice.user.web.mapper.impl;


import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.web.mapper.AuthorityMapper;
import com.mamadou.sk.uaaservice.user.web.dto.AuthorityDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorityMapperImplTest {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String[] ADMIN_USER_ROLES = {ROLE_ADMIN, ROLE_USER };

    private AuthorityMapper authorityMapper;

    @Before
    public void setUp() {
        authorityMapper = new AuthorityMapperImpl();
    }

    @Test
    public void toAuthorities_shouldConvertAuthorityArrayToAuthorityList() {
        // when
        List<Authority> authorities = authorityMapper.toAuthorities(ADMIN_USER_ROLES);

        // then
        assertThat(authorities).containsExactly(new Authority(ROLE_ADMIN), new Authority(ROLE_USER));
    }

    @Test
    public void toAuthorities_shouldConvertAuthorityStringToAuthorityList() {
        // when
        List<Authority> authorities = authorityMapper.toAuthorities(ROLE_ADMIN);

        // then
        assertThat(authorities).containsExactly(new Authority(ROLE_ADMIN));
    }

    @Test
    public void toAuthorities_shouldConvertAuthorityDTOListToAuthorityList() {
        // given
        List<AuthorityDTO> authorityDTOs = authorityMapper.toAuthorityDTOs(ADMIN_USER_ROLES);

        // when
        List<Authority> authorities = authorityMapper.toAuthorities(authorityDTOs);

        // then
        assertThat(authorities).containsExactly(new Authority(ROLE_ADMIN), new Authority(ROLE_USER));
    }

}