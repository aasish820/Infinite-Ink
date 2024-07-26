package com.infiniteink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.infiniteink.repositories.PostRepo")
public class InfiniteInkApplication {
	
	// test branch code
	public static void main(String[] args) {
		SpringApplication.run(InfiniteInkApplication.class, args);
	}

}