package com.mamadou.sk.uaaservice.security.jwt.mapper.impl;

import com.mamadou.sk.uaaservice.security.jwt.mapper.GrantedAuthorityMapper;
import io.jsonwebtoken.lang.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrantedAuthorityMapperImpl implements GrantedAuthorityMapper {

    @Override
    public String[] authorities(Collection<? extends GrantedAuthority> authorities) {
        return Strings.toStringArray(authorities.stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList()));
    }

    @Override
    public List<SimpleGrantedAuthority> toSimpleGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                          .map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());
    }
}
