package com.mamadou.sk.uaaservice;

import com.mamadou.sk.uaaservice.user.entitity.Authority;
import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.repository.AuthorityRepository;
import com.mamadou.sk.uaaservice.user.repository.UserRepository;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.mamadou.sk.uaaservice")
public class UaaServiceApplication {
	@Autowired
	private UserMapper userMapper;

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}


	/**
	 * populate users for development for purposes
	 */
	@Bean
	public CommandLineRunner runner(UserRepository userRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder passwordEncoder) {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(new Authority("ROLE_ADMIN"));
		authorities.add(new Authority("ROLE_USER"));

		User admin = new User().setFirstName("first")
							   .setLastName("last")
							   .setUsername("admin")
							   .setPassword(passwordEncoder.encode("admin"))
							   .setAuthorities(authorities)
							   .setEnabled(true);
		return args -> userRepository.save(admin);
	}
}
