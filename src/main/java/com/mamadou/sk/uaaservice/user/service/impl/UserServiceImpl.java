package com.mamadou.sk.uaaservice.user.service.impl;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.exception.UserNotFoundException;
import com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.repository.UserRepository;
import com.mamadou.sk.uaaservice.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(User newUser) {
        throwExceptionIfUsernameExists(newUser);

        throwExceptionIfEmailExists(newUser);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new UserNotFoundException(format("User with id {0} is not found", userId)));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, User user) {
        // validate username is not used by users
        userRepository.findByUsername(user.getUsername())
                      .ifPresent(u -> {
                          if (!u.getUserId().equals(userId)) {
                              throw new UsernameAlreadyExistsException("Username already exists");
                          }
                      });

        // validate email is not used by users
        userRepository.findByUsername(user.getUsername())
                      .ifPresent(u -> {
                          if (!u.getUserId().equals(userId)) {
                              throw new EmailAlreadyExistsException("Email already exists");
                          }
                      });

        return userRepository.findById(userId)
                             .map(u -> {
                                 u.setFirstName(user.getFirstName());
                                 u.setLastName(user.getLastName());
                                 u.setUsername(user.getUsername());
                                 u.setPassword(passwordEncoder.encode(user.getPassword()));
                                 u.setAuthorities(user.getAuthorities());
                                 u.setEmail(user.getEmail());
                                 u.setLocked(user.isLocked());
                                 u.setEnabled(user.isEnabled());
                                 u.setExpired(user.isExpired());
                                 return u; })
                             .orElseThrow(() -> new UserNotFoundException(format("User with id {0} is not found", user.getUserId())));
    }

    private void throwExceptionIfUsernameExists(User newUser) {
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if(existingUser.isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
    }

    private void throwExceptionIfEmailExists(User newUser) {
        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
}
