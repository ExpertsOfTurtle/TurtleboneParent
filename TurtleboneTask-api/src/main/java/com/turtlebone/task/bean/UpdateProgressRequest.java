package com.turtlebone.task.bean;


import lombok.Data;

@Data
public class UpdateProgressRequest {
	private Integer id;
	private String username;
	private Integer percentage;
	private Integer status;
	private String actionType;
}
