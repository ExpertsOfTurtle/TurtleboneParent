package com.turtlebone.core.enums.punishment;

public enum PunishType {
	DAILY(1, "每日抽题"),
	NORMAL(2, "普通体"),
	SENIOR(3, "高级题"),
	PRETENDB(4,"装B题")
	;
	private Integer value;
	private String description;
	PunishType(Integer level, String description) {
		this.value = level;
		this.description = description;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
