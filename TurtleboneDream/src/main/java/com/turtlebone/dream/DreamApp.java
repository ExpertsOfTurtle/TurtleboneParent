package com.turtlebone.dream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.turtlebone")
@MapperScan("com.turtlebone.*.repository") 
@EnableScheduling
@EnableAutoConfiguration
public class DreamApp {
	public static void main(String[] args) {
		SpringApplication.run(DreamApp.class, args);
	}
}
