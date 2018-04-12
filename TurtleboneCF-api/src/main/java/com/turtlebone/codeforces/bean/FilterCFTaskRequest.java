package com.turtlebone.codeforces.bean;

import lombok.Data;

@Data
public class FilterCFTaskRequest {
	private Integer pageNumber;
	private Integer pageSize;
	private String username;
	private Integer status;
}
