package com.turtlebone.choice.model;
import java.io.Serializable;

public class OptionGroupModel implements Serializable{
	
	private Integer groupid;
	private String groupname;
	private Integer type;
		
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
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
		
}