package com.mamadou.sk.uaaservice.user.service;

import com.mamadou.sk.uaaservice.user.entitity.User;

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
}
