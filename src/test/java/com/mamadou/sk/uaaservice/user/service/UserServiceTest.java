package com.mamadou.sk.uaaservice.user.service;


import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.repository.UserRepository;
import com.mamadou.sk.uaaservice.user.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void createUser_shouldCreateNewUserSuccessfully() {
        // given
        User newUser = new User().setFirstName("first")
                                 .setPassword("last")
                                 .setUsername("username")
                                 .setEmail("email@test.com")
                                 .setPassword("password")
                                 .setAuthorities(Arrays.asList(new Authority("ROLE_USER")));

        // when
        userService.createUser(newUser);

        // then
        then(userRepository).should().save(newUser);
    }
}