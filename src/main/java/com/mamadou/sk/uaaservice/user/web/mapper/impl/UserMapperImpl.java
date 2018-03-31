package com.mamadou.sk.uaaservice.user.web.mapper.impl;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapperImpl implements UserMapper {

    private ModelMapper modelMapper;

    @Override
    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
