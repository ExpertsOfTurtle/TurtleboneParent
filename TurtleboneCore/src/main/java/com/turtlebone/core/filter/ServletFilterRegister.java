package com.turtlebone.core.filter;

import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.turtlebone.core.service.RedisService;
import com.turtlebone.core.service.UserService;

@Component
public class ServletFilterRegister {

	@Autowired
	private Environment env;

	@Value("${constants.excludeRegex}")
	private String excludeRegex;
	@Value("${core.basehost}")
	private String BASEHOST;
	
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	
	@Bean
	public FilterRegistrationBean registrationAuthenticationFilter() throws ServletException {
		
		SecurityFilter authenticationFilter = new SecurityFilter();
//		MockSecurityFilter authenticationFilter = new MockSecurityFilter();
		
		authenticationFilter.setEnv(env);
		authenticationFilter.setUserService(userService);
		authenticationFilter.setRedisService(redisService);
		authenticationFilter.setBASEHOST(BASEHOST);
		
		FilterRegistrationBean registration = new FilterRegistrationBean(authenticationFilter);
		registration.addUrlPatterns("/*");
		registration.addInitParameter("excludeRegex", excludeRegex);
		registration.setOrder(0);

		return registration;
	}

	
}
