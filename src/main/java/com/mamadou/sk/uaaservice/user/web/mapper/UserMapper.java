package com.mamadou.sk.uaaservice.user.web.mapper;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;

/**
 * This component is used for converting User entity to UserDTO, vice versa
 */
public interface UserMapper {

    /**
     * convert UserDTO entity to User
     * @param userDTO - user DTO
     * @return User entity
     */
    User toEntity(UserDTO userDTO);

    /**
     * convert User entity to UserDTO
     * @param user - user entity
     * @return UserDTO
     */
    UserDTO toDTO(User user);

}
