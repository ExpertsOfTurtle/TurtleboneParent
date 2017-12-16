package com.turtlebone.task.bean;


import lombok.Data;

@Data
public class QueryProblemRequest {
	private String username;
	private String type;
	private String status;
	private String deadlineFrom;
	private String deadlineTo;
}
