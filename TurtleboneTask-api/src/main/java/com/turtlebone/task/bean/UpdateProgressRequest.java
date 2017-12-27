package com.turtlebone.task.bean;


import lombok.Data;

@Data
public class UpdateProgressRequest {
	private Integer id;
	private String username;
	private Integer progress;
	private Integer status;
	private String actionType;
}
