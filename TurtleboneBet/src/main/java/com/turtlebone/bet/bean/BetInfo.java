package com.turtlebone.bet.bean;

import java.util.Date;

import lombok.Data;

@Data
public class BetInfo {
	private String creator;
	private String title;
	private String id;
	private Date publicTime;
	private Date expireTime;
}
