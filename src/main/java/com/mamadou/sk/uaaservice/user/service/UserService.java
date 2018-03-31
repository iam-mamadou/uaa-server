package com.mamadou.sk.uaaservice.user.service;

import com.mamadou.sk.uaaservice.user.entitity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    /**
     * Create new User
     *
     * @param newUser - user to be created
     */
    void createUser(User newUser);
}
