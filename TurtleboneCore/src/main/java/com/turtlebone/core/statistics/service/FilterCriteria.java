package com.turtlebone.core.statistics.service;

public class FilterCriteria {
	protected String type;
	protected String filedName;
	protected String filedValue;
	
	public FilterCriteria(String type, String filedName, String filedValue) {
		this.type = type;
		this.filedName = filedName;
		this.filedValue = filedValue;
	}
	public FilterCriteria() {
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getFiledValue() {
		return filedValue;
	}
	public void setFiledValue(String filedValue) {
		this.filedValue = filedValue;
	}
}
