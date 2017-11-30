package com.turtlebone.task.model;
import java.io.Serializable;


import lombok.Data;
@Data
public class TaskModel implements Serializable{
	
	private Integer id;
	private String creator;
	private String title;
	private String createtime;
	private String deadline;
	private Integer difficulty;
	private Integer percentage;
	private Integer type;
	private Integer status;
	private Integer punishmentId;
	private String content;
		
}