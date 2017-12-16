package com.turtlebone.task.bean;


import lombok.Data;

@Data
public class InsertProblemRequest {
	private String username;
	private String type;
	private String deadline;
	private Integer count;
	private String remark;
}
