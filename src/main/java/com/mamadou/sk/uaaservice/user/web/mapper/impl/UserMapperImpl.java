package com.mamadou.sk.uaaservice.user.web.mapper.impl;

import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.web.dto.AuthorityDTO;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.mapper.AuthorityMapper;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Component
public class UserMapperImpl implements UserMapper {

    private AuthorityMapper authorityMapper;

    @Override
    public User toEntity(UserDTO userDTO) {
        List<Authority> authorities = authorityMapper.toAuthorities(userDTO.getAuthorities());
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAuthorities(authorities);
        user.setEnabled(userDTO.isEnabled());
        user.setExpired(userDTO.isExpired());
        user.setLocked(userDTO.isLocked());
        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        List<AuthorityDTO> authorityDTOs = authorityMapper.toAuthorityDTOs(user.getAuthorities());
        return UserDTO.builder()
                      .userId(user.getUserId())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .username(user.getUsername())
                      .email(user.getEmail())
                      .password(user.getPassword())
                      .authorities(authorityDTOs)
                      .enabled(true).expired(true).locked(true)
                      .build();
    }

    @Override
    public Page<UserDTO> toDTOPage(Page<User> users) {
        return users.map(this::toDTO);
    }
}
