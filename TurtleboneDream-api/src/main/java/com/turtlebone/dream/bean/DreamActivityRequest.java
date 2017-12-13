package com.turtlebone.dream.bean;


import lombok.Data;

@Data
public class DreamActivityRequest {
	private String username;
	private String datetime;
	private String content;
	private String dreampic;
	private Integer dreamId;
	private Integer dreamType;
}
