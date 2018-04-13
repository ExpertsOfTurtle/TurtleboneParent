package com.turtlebone.codeforces.service;


public interface CFReportService {
		
	public String generateWeeklyReport(String from, String to);
	public String generateWeeklyReport(String from, String to, String email);
}
