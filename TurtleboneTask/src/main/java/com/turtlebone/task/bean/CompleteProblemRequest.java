package com.turtlebone.task.bean;


import lombok.Data;

@Data
public class CompleteProblemRequest {
	private String username;
	private String type;
	private String url;
}
