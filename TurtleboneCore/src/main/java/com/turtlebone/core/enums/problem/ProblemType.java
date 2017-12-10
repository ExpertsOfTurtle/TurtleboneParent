package com.turtlebone.core.enums.problem;

public enum ProblemType {
	DAILY("A", ""),
	NORMAL("P", ""),
	SENIOR("H", ""),
	PRETENDB("B", "")
	;
	private String value;
	private String description;
	ProblemType(String level, String description) {
		this.value = level;
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
