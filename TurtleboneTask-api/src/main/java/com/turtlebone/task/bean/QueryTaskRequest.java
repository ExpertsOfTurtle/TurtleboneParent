package com.turtlebone.task.bean;

import lombok.Data;

@Data
public class QueryTaskRequest {
	private String creator;
	private String owner;
	private String deadlineFrom;
	private String deadlineTo;
	private String orderBy;
	private String orderDirection;
	private Integer status;
	private Integer type;
	private Integer pageNumber;
	private Integer pageSize;
}
