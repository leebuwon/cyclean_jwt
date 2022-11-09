package com.example.cyclean_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 11월 8일 완성본
//토큰 refresh 추가해줘야된다.

@SpringBootApplication
public class CycleanJwtApplication {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(CycleanJwtApplication.class, args);
	}

}
