package com.turtlebone.codeforces.bean;

import lombok.Data;

@Data
public class InsertCFTaskRequest {
	private String username;
	private String deadline;
	private Integer amount;
	private String reason;
}
