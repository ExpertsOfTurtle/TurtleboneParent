package com.turtlebone.core.service;

import java.util.List;

import com.turtlebone.core.statistics.bean.StatisticsResult;

public interface StatisticsService {
	public StatisticsResult calculate(List list);
}
