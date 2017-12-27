package com.turtlebone.codeforces.service;

import com.turtlebone.codeforces.bean.WeeklySummary;

public interface WeeklyTaskService {
	public WeeklySummary queryStatus(String from, String to);
	public WeeklySummary queryWeeklyStatus();
}
