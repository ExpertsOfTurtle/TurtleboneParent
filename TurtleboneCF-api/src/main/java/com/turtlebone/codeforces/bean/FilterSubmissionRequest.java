package com.turtlebone.codeforces.bean;

import lombok.Data;

@Data
public class FilterSubmissionRequest {
	private Integer pageNumber;
	private Integer pageSize;
	private String username;
	private Integer status;
	private String result;
	private String tags;
	private Integer contestId;
}
