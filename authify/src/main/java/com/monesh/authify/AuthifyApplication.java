package com.monesh.authify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthifyApplication {

	public static void main(String[] args) {
		System.setProperty("logging.level.org.springframework.security", "DEBUG");
		SpringApplication.run(AuthifyApplication.class, args);
	}

}
