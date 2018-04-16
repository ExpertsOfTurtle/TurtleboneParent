package com.turtlebone.dairy.common;

public enum DairyStatus {
	Normal(0),
	Expired(1),
	Deleted(2),
	;
	
	private int val;
	DairyStatus(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
	
}
