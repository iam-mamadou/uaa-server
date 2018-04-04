package com.mamadou.sk.uaaservice.user.web.controller;

import com.mamadou.sk.uaaservice.user.service.UserService;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserResource {
    private UserService userService;
    private UserMapper userMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDTO newUserDTO) {
        userService.createUser(userMapper.toEntity(newUserDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
