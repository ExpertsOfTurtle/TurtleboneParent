package com.turtlebone.bean;

import lombok.Data;

@Data
public class FilterTaskRequest {
	private Integer type;
	private Integer status;
	private String deadlineFrom;
	private String deadlineTo;
}
