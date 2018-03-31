package com.mamadou.sk.uaaservice.user.repository;

import com.mamadou.sk.uaaservice.user.entitity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String > {

    Optional<AuthorityRepository> findByRole(String role);

}
