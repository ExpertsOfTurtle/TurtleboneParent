package com.turtlebone.dairy.common;

public enum DairyType {
	Codeforces(0),
	Normal(1)
	;
	
	private int val;
	DairyType(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
	
}
