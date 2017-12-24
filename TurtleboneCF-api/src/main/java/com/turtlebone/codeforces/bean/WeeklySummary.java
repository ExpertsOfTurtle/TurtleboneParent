package com.turtlebone.codeforces.bean;

import java.util.List;

import lombok.Data;

@Data
public class WeeklySummary {
	private List<String> labels;
	private List<UserResult> list;
}
