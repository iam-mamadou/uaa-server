package com.mamadou.sk.uaaservice.user.web.mapper.impl;

import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.web.dto.AuthorityDTO;
import com.mamadou.sk.uaaservice.user.web.mapper.AuthorityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public List<Authority> toAuthorities(List<AuthorityDTO> authorityDTOs) {
        return authorityDTOs.stream()
                            .map(authorityDTO -> new Authority(authorityDTO.getRole()))
                            .collect(Collectors.toList());
    }

    @Override
    public List<Authority> toAuthorities(String... authorities) {
        return Stream.of(authorities)
                     .map(Authority::new)
                     .collect(Collectors.toList());
    }

    @Override
    public List<AuthorityDTO> toAuthorityDTOs(List<Authority> authorities) {
        return authorities.stream()
                            .map(authority -> AuthorityDTO.builder()
                                                          .role(authority.getRole())
                                                          .build())
                            .collect(Collectors.toList());
    }

    @Override
    public List<AuthorityDTO> toAuthorityDTOs(String... authorities) {
        return Stream.of(authorities)
                     .map(auth -> AuthorityDTO.builder().role(auth).build())
                     .collect(Collectors.toList());
    }
}
