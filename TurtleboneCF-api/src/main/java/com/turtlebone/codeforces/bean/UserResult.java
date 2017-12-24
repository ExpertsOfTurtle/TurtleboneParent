package com.turtlebone.codeforces.bean;

import java.util.List;

import lombok.Data;

@Data
public class UserResult {
	private String username;
	private Integer problemSolved;
	private Integer failSubmission;
	private List<Integer> dailySolved;
	private List<Integer> dailyFailed;
}
