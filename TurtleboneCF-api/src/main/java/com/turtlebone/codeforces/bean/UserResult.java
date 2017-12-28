package com.turtlebone.codeforces.bean;

import java.util.List;

import com.turtlebone.codeforces.model.CFSubmissionModel;

import lombok.Data;

@Data
public class UserResult {
	private String username;
	private Integer problemSolved;
	private Integer failSubmission;
	private List<Integer> dailySolved;
	private List<Integer> dailyFailed;
	private Integer daySolved;//有做题的天数
	
	//额外的东西
	private String nickName;
	private String description;
	private List<CFSubmissionModel> submission;
}
