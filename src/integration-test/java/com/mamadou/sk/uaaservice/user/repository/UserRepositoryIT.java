package com.mamadou.sk.uaaservice.user.repository;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import com.mamadou.sk.uaaservice.AbstractIntegrationTest;
import com.mamadou.sk.uaaservice.user.entitity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class UserRepositoryIT extends AbstractIntegrationTest {
    private static final String NON_EXISTING_USER = "NON_EXISTING_USER";
    private static final String NON_EXISTING_EMAIL = "non.existin.email@email.com";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin@email.com";
    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/datasets/user/admin_user_roles_setup.xml"),
            @DatabaseSetup("/datasets/user/user_admin_setup.xml")
    })
    public void findByUsername_shouldReturnUserAssociatedWithUsername_whenFound() {
        // when
        Optional<User> existingUser = userRepository.findByUsername(ADMIN_USERNAME);

        // then
        assertThat(existingUser).isNotEmpty();
    }

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/datasets/user/admin_user_roles_setup.xml"),
            @DatabaseSetup("/datasets/user/user_admin_setup.xml")
    })
    public void findByUsername_shouldReturnEmpty_whenUserWithUsernameIsNotFound() {
        // when
        Optional<User> user = userRepository.findByUsername(NON_EXISTING_USER);

        // then
        assertThat(user).isEmpty();
    }

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/datasets/user/admin_user_roles_setup.xml"),
            @DatabaseSetup("/datasets/user/user_admin_setup.xml")
    })
    public void findByEmail_shouldReturnUser_whenUserWithEmailIsFound() {
        // when
        Optional<User> existingUser = userRepository.findByEmail(ADMIN_EMAIL);

        // then
        assertThat(existingUser).isNotEmpty();
    }

    @Test
    @DatabaseSetups({
            @DatabaseSetup("/datasets/user/admin_user_roles_setup.xml"),
            @DatabaseSetup("/datasets/user/user_admin_setup.xml")
    })
    public void findByEmail_shouldReturnEmpty_whenUserWithEmailIsNoyFound() {
        // when
        Optional<User> user = userRepository.findByEmail(NON_EXISTING_EMAIL);

        // then
        assertThat(user).isEmpty();
    }
}