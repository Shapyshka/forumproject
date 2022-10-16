package com.example.restik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestikApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RestikApplication.class, args);
	}

}
