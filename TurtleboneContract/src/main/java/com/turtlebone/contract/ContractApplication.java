package com.turtlebone.contract;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = "com.turtlebone")
@MapperScan("com.turtlebone.*.repository")
@EnableScheduling
@EnableAutoConfiguration
public class ContractApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContractApplication.class);
	}
}
