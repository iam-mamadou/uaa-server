package com.mamadou.sk.uaaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mamadou.sk.uaaservice")
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}
}
