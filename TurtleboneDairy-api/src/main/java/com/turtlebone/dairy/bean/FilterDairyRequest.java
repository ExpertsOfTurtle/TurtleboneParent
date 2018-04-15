package com.turtlebone.dairy.bean;

import lombok.Data;

@Data
public class FilterDairyRequest {
	private String creator;
	private Integer type;
	private Integer subtype;
	private String title;
	private String checkExpire;
	
	private Integer pageNumber;
	private Integer pageSize;
}
