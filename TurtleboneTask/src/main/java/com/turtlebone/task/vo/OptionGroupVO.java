package com.turtlebone.task.vo;

import java.io.Serializable;

public class OptionGroupVO implements Serializable{
	
	private Integer groupid;
	private String groupname;
		
	public void setGroupid(Integer groupid){
		this.groupid = groupid;
	}
	
	public Integer getGroupid(){
		return this.groupid;
	}
		
	public void setGroupname(String groupname){
		this.groupname = groupname;
	}
	
	public String getGroupname(){
		return this.groupname;
	}
		
		
}