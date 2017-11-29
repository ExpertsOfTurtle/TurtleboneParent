package com.turtlebone.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.turtlebone.home")
@EnableAutoConfiguration
public class HomeApp {
	public static void main(String[] args) {
		SpringApplication.run(HomeApp.class);
	}

}
