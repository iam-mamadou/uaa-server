package com.mamadou.sk.uaaservice.security.jwt.mapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * This component is used for converting GrantedAuthority to Authority Strin, vice versa
 * @author Mamadou Barry
 * @since 1.0.0
 */
public interface GrantedAuthorityMapper {
    /**
     * convert List of GrantedAuthority to an array of String
     *
     * @param authorities - user granted authorities
     * @return array of authorities
     */
    String[] authorities(Collection<? extends GrantedAuthority> authorities);

    /**
     * convert list of string authorities to SimpleGrantedAuthorities
     * @param authorities - string of authority roles
     * @return list of Granted Authorities
     */
    List<SimpleGrantedAuthority> toSimpleGrantedAuthorities(List<String> authorities);
}
