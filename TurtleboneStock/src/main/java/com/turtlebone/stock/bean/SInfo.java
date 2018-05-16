package com.turtlebone.stock.bean;

import lombok.Data;

@Data
public class SInfo {
	private Double start;
	private Double pre;
	private Double price;
	private Double max;
	private Double min;
	private String name;
	private String pinyin;
}
