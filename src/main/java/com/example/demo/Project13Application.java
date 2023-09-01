package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.example.demo")
@SpringBootApplication
@EnableScheduling
public class Project13Application {
	
	public static void main(String[] args) {
			SpringApplication.run(Project13Application.class, args);
	}

}
