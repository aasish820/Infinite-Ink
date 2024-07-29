package com.infiniteink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.infiniteink")
//@EnableJpaRepositories(basePackages = "com.infiniteink.repositories.PostRepo")
public class InfiniteInkApplication {
	
	// test branch code
	public static void main(String[] args) {
		SpringApplication.run(InfiniteInkApplication.class, args);
	}

}