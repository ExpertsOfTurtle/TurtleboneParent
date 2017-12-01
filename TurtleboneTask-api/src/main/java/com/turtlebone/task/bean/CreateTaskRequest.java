package com.turtlebone.task.bean;

import java.util.List;

import lombok.Data;

@Data
public class CreateTaskRequest {
	private Integer id;
	private String creator;
	private String title;
	private String content;
	private List<String> owner;
	private String deadline;
	private Integer punishmentId;
	private Integer type;
	private Integer difficulty;
	private Integer percentage;
}
