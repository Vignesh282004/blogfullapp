package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogAppFullApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppFullApplication.class, args);
		System.out.println("tomcat started");
	}

}
