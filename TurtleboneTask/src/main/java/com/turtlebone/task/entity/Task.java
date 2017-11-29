package com.turtlebone.task.entity;

import lombok.Data;

@Data
public class Task{
	
	private Integer id;
	private String creator;
	private String owner;
	private String title;
	private String deadline;
	private Integer punishmentId;
	private Integer type;
	private Integer status;
	private String content;
			
}
















