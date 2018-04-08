package com.mamadou.sk.uaaservice.user.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import com.mamadou.sk.uaaservice.AbstractIntegrationTest;
import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.exception.UserIdNotFoundException;
import com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.repository.UserRepository;
import com.mamadou.sk.uaaservice.user.web.mapper.AuthorityMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplIT extends AbstractIntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<Authority> authorities = authorityMapper.toAuthorities("ROLE_USER");

        user = new User();
        user.setFirstName("first");
        user.setLastName("last");
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setAuthorities(authorities);
        user.setEnabled(true);
        user.setLocked(false);
        user.setExpired(false);
    }

    @Test
    public void createUser_shouldCreateNewUserSuccessfully_whenSetupCorrectly() {
        // when
        userService.createUser(user);

        // then
        Optional<User> user = userRepository.findByUsername("user");
        assertThat(user).get()
                        .extracting("username", "email")
                        .contains("user", "user@email.com");
    }


    @Test
    public void createUser_shouldThrowUsernameAlreadyExistsException_whenUsernameIsAlreadyUsed() {
        // given
        userRepository.save(user);

        User newUser = new User();
        newUser.setUsername("user");

        // throw
        thrown.expect(UsernameAlreadyExistsException.class);
        thrown.expectMessage("Username already exists");

        // when
        userService.createUser(newUser);
    }

    @Test
    public void createUser_shouldThrowEmailAlreadyExistsException_whenEmailIsAlreadyUsed() {
        // given
        userRepository.save(user);

        User newUser = new User();
        newUser.setEmail("user@email.com");

        // throw
        thrown.expect(EmailAlreadyExistsException.class);
        thrown.expectMessage("Email already exists");

        // when
        userService.createUser(newUser);
    }

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/datasets/user/admin_user_roles_setup.xml"),
            @DatabaseSetup("/datasets/user/user_admin_setup.xml")
    })
    public void getUserById_shouldReturnUserAssociatedWithId_whenFound() {

        // when
        User existingUser = userService.getUserById(1L);
        // then
        assertThat(existingUser).extracting("username", "email")
                                .contains("admin", "admin@email.com");
    }

    @Test
    public void getUserById_shouldThrowUserIdNotFoundException_whenUserDoesNotExists() {
        // throw
        thrown.expect(UserIdNotFoundException.class);
        thrown.expectMessage("User with id 999 is not found");

        // when
        userService.getUserById(999L);
    }
}
