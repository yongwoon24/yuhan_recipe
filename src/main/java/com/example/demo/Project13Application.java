package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.example.comtroller"})
public class Project13Application {

	public static void main(String[] args) {
		SpringApplication.run(Project13Application.class, args);
	}

}
