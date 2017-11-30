package com.turtlebone.core.filter;

import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ServletFilterRegister {

	@Autowired
	private Environment env;

	@Value("${constants.excludeRegex}")
	private String excludeRegex;
	
	@Bean
	public FilterRegistrationBean registrationAuthenticationFilter() throws ServletException {
		SecurityFilter authenticationFilter = new SecurityFilter();
		authenticationFilter.setEnv(env);
		
		FilterRegistrationBean registration = new FilterRegistrationBean(authenticationFilter);
		registration.addUrlPatterns("/*");
		registration.addInitParameter("excludeRegex", excludeRegex);
		registration.setOrder(0);

		return registration;
	}

	
}
