package com.turtlebone.codeforces;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.turtlebone")
@MapperScan("com.turtlebone.*.repository") 
@EnableScheduling
@EnableAutoConfiguration
public class CFApplication {
	public static void main(String[] args) {
		SpringApplication.run(CFApplication.class, args);
	}
}
