package com.turtlebone.task.entity;

import lombok.Data;

@Data
public class TaskResult{
	
	private Integer id;
	private String creator;
	private String title;
	private String createtime;
	private String deadline;
	private Integer difficulty;
	private Integer total;
	private Integer progress;
	private Integer type;
	private Integer status;
	private Integer punishmentId;
	private String username;
	private String content;
			
}
