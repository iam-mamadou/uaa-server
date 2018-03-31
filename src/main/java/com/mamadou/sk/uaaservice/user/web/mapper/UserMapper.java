package com.mamadou.sk.uaaservice.user.web.mapper;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;

/**
 * This component is used for converting User entity to UserDTO, vice versa
 */
public interface UserMapper {

    /**
     * convert User entity to User DTO
     * @param userDTO - user DTO
     * @return User entity
     */
    User toEntity(UserDTO userDTO);

}
