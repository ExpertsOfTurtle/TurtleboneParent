package com.turtlebone.task.model;
import java.io.Serializable;


import lombok.Data;
@Data
public class TaskModel implements Serializable{
	
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