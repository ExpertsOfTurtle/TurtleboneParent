package com.turtlebone.core.service;

import java.util.List;

public interface EmailService {
	public String sendEmail(List<String> addressList, String title, String template, String alias);
}
