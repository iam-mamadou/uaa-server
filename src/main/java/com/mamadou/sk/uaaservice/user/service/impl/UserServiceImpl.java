package com.mamadou.sk.uaaservice.user.service.impl;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExists;
import com.mamadou.sk.uaaservice.user.repository.UserRepository;
import com.mamadou.sk.uaaservice.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User newUser) {
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if(existingUser.isPresent()) {
            throw new UsernameAlreadyExists("Username already exists");
        }
        userRepository.save(newUser);
    }
}
