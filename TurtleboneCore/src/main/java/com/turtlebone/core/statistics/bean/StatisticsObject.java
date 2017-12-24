package com.turtlebone.core.statistics.bean;

import java.util.List;

import lombok.Data;

@Data
public class StatisticsObject {
	private String label;
	private List<Integer> data;
	private Integer total;
}
