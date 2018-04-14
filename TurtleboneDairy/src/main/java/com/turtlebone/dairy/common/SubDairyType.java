package com.turtlebone.dairy.common;

public enum SubDairyType {
	//Codeforces
	cfTranslate(0),	//题目翻译
	cfFav(1),		//收藏题目
	cfAnalysis(2),	//题解
	
	//Normal
	Idea(10),
	Study(11),
	
	;
	
	private int val;
	SubDairyType(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
	
}
