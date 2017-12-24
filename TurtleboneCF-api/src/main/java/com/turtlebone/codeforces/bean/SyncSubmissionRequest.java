package com.turtlebone.codeforces.bean;

import lombok.Data;

@Data
public class SyncSubmissionRequest {
	private String username;
	private Integer from;
	private Integer count;
}
