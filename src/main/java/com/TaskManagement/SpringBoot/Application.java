package com.TaskManagement.SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity  // لتمكين استخدام @PreAuthorize في الـ Controllers
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}