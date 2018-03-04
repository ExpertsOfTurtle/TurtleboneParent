package com.turtlebone.core.bean;


import lombok.Data;

@Data
public class QueryActivityRequest {
	private String username;
	private String type;
	private Integer result1;
	private Integer result2;
	private Integer result3;
	private String from;
	private String to;
	private Integer pageSize;
	private Integer pageNumber;
}
