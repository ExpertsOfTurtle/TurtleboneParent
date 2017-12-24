package com.turtlebone.codeforces.service;

import com.turtlebone.codeforces.bean.WeeklyResult;

public interface WeeklyTaskService {
	public WeeklyResult queryWeeklyStatus(String username);
}
