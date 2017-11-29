package com.turtlebone.task.model;
import java.io.Serializable;

import lombok.Data;

@Data
public class TaskUserModel implements Serializable{
	
	private Integer id;
	private Integer taskid;
	private String username;
	private String assigndatetime;
	private String deadline;
	private Integer status;
		
}