package com.mamadou.sk.uaaservice.user.service;


import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.exception.UserNotFoundException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    private User user;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());

        // setup user
        user = new User();
        user.setFirstName("first");
        user.setPassword("last");
        user.setUsername(ADMIN_USERNAME);
        user.setEmail(ADMIN_EMAIL);
        user.setPassword("password");
        user.setAuthorities(Arrays.asList(new Authority("ROLE_USER")));
    }

    @Test
    public void createUser_shouldCreateNewUserSuccessfully() {
        // when
        userService.createUser(user);

        // then
        then(userRepository).should().save(user);
    }

    @Test
    public void createUser_shouldThrowUsernameAlreadyExists_whenUsernameAlreadyUsed() {
        // given
        User existingUser = new User();
        existingUser.setUsername(ADMIN_USERNAME);
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(existingUser));

        User newUser = new User();
        newUser.setUsername(ADMIN_USERNAME);
        // throw
        thrown.expect(UsernameAlreadyExistsException.class);
        thrown.expectMessage("Username already exists");

        // when
        userService.createUser(newUser);
    }

    @Test
    public void createUser_shouldThrowEmailAlreadyExists_whenEmailAddressAlreadyUsed() {
        // given
        User existingUser = new User();
        existingUser.setEmail(ADMIN_EMAIL);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(existingUser));

        User newUser = new User();
        newUser.setEmail(ADMIN_EMAIL);
        // throw
        thrown.expect(EmailAlreadyExistsException.class);
        thrown.expectMessage("Email already exists");

        // when
        userService.createUser(newUser);
    }

    @Test
    public void getUserById_shouldReturnExistingUser_whenFound() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when
        User existingUser = userService.getUserById(1L);

        // then
        assertThat(existingUser).extracting("username").contains(ADMIN_USERNAME);
    }

    @Test
    public void getUserById_shouldThrowUserIdNotFoundException_whenUserDoesNotExists() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        // throw
        thrown.expect(UserNotFoundException.class);
        thrown.expectMessage("User with id 1 is not found");

        // when
        userService.getUserById(1L);
    }
}