package com.mamadou.sk.uaaservice.user.service.impl;

import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.web.dto.AuthorityDTO;

import java.util.List;

/**
 * This component is used for converting Authority entity to AuthorityDTO, vice versa
 * @author Mamadou Barry
 * @since 1.0.0
 */
public interface AuthorityMapper {

    /**
     * Convert Authority DTOs to Authorities
     * @param authorityDTOs - list of authority DTOs
     * @return list Authorities
     */
    List<Authority> toAuthorities(List<AuthorityDTO> authorityDTOs);

    /**
     * convert one or more authorities of type String to a List of Authorities
     *
     * @param authorities - array of authorities
     * @return list of Authorities
     */
    List<Authority> toAuthorities(String... authorities);


    /**
     * convert one or more authorities of type String to a List of Authority DTOs
     *
     * @param authorities - array of authorities
     * @return list of AuthorityDTOs
     */
    List<AuthorityDTO> toAuthorityDTOs(String... authorities);
}
