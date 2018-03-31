package com.mamadou.sk.uaaservice.user.repository;

import com.mamadou.sk.uaaservice.user.entitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * find user by username
     * @param username - username
     * @return user if present, else return empty
     */
    Optional<User> findByUsername(String username);

}
