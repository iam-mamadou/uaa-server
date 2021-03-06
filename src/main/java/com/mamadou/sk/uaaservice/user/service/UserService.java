package com.mamadou.sk.uaaservice.user.service;

import com.mamadou.sk.uaaservice.user.entitity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {


    /**
     * Create new User.
     * @throws com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExistsException
     *         if the given username already exists
     * @throws com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException
     *        if the given email already exists
     *
     * @param newUser - user to be created
     * @return created user
     */
    User createUser(User newUser);

    /**
     * Find existing user by userId
     * UserNotFoundException will be thrown if user does not exist
     * @param userId - user id
     * @return User
     */
    User getUserById(Long userId);

    /**
     * Get all users
     *
     * @param pageable - pagination info
     * @return a page of users
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Update an exist user
     *
     * @param userId - existing user if
     * @param user - user
     * @return updated user
     */
    User updateUser(Long userId, User user);
}
