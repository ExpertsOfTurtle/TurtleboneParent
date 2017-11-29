package com.turtlebone.task.bean;

import java.util.List;

import lombok.Data;

@Data
public class CreateTaskRequest {
	private String creator;
	private String title;
	private String content;
	private List<String> owner;
	private String deadline;
	private Integer punishmentId;
	private Integer type;
}
