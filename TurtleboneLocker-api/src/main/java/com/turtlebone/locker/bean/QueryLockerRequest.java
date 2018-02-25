package com.turtlebone.locker.bean;

import com.turtlebone.auth.bean.CoreRequest;

import lombok.Data;

@Data
public class QueryLockerRequest extends CoreRequest{
	private String category;
	private String name;
	private String location;
}
