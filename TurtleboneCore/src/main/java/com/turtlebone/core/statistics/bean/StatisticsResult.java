package com.turtlebone.core.statistics.bean;

import java.util.List;

import lombok.Data;

@Data
public class StatisticsResult {
	private List<String> labels;
	private List<StatisticsObject> list;
}
