package com.turtlebone.core.bean;


import lombok.Data;

@Data
public class QueryActivityRequest {
	private String username;
	private String type;
	private Integer pageSize;
	private Integer pageNumber;
}
