package com.turtlebone.locker.bean;

import com.turtlebone.core.bean.CoreRequest;

import lombok.Data;

@Data
public class AddLockerRequest extends CoreRequest{
	private String category;
	private String name;
	private String location;
}
