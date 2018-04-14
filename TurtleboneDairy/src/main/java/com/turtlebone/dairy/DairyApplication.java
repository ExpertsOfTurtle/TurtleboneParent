package com.turtlebone.dairy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.turtlebone")
@MapperScan("com.turtlebone.*.repository") 
@EnableScheduling
@EnableAutoConfiguration
public class DairyApplication /* extends SpringBootServletInitializer */{
//	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(DairyApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(DairyApplication.class, args);
	}
}
