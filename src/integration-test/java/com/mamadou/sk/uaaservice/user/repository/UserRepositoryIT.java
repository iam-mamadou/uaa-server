package com.mamadou.sk.uaaservice.user.repository;


import com.mamadou.sk.uaaservice.user.entitity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest()
public class UserRepositoryIT {
    private static final String NON_EXISTING_USER = "NON_EXISTING_USER";
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_shouldReturnUserAssociatedWithUsername_whenFound() {
        // given
        User user = new User().setFirstName("First Name")
                              .setLastName("Last Name")
                              .setUsername("username")
                              .setEmail("email@test.com")
                              .setPassword("password")
                              .setEnabled(true)
                              .setLocked(false)
                              .setExpired(false);

        entityManager.persistAndFlush(user);


        // when
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        // then
        assertThat(existingUser).contains(user);
    }

    @Test
    public void findByUsername_shouldReturnEmpty_whenUserWithUsernameIsNotFound() {
        // when
        Optional<User> user = userRepository.findByUsername(NON_EXISTING_USER);

        // then
        assertThat(user).isEmpty();
    }
}