package com.mamadou.sk.uaaservice.user.service;


import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.repository.UserRepository;
import com.mamadou.sk.uaaservice.user.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin@test.com";

    @Rule
    public ExpectedException thrown = ExpectedException.none();
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
                                 .setUsername(ADMIN_USERNAME)
                                 .setEmail(ADMIN_EMAIL)
                                 .setPassword("password")
                                 .setAuthorities(Arrays.asList(new Authority("ROLE_USER")));

        // when
        userService.createUser(newUser);

        // then
        then(userRepository).should().save(newUser);
    }

    @Test
    public void createUser_shouldThrowUsernameAlreadyExists_whenUsernameAlreadyUsed() {
        // given
        User existingUser = new User().setUsername(ADMIN_USERNAME);
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(existingUser));

        User newUser = new User().setUsername(ADMIN_USERNAME);
        // throw
        thrown.expect(UsernameAlreadyExistsException.class);
        thrown.expectMessage("Username already exists");

        // when
        userService.createUser(newUser);
    }

    @Test
    public void createUser_shouldThrowEmailAlreadyExists_whenEmailAddressAlreadyUsed() {
        // given
        User existingUser = new User().setEmail(ADMIN_EMAIL);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(existingUser));

        User newUser = new User().setEmail(ADMIN_EMAIL);
        // throw
        thrown.expect(EmailAlreadyExistsException.class);
        thrown.expectMessage("Email already exists");

        // when
        userService.createUser(newUser);
    }

}