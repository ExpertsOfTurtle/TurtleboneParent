package com.turtlebone.task.vo;

import java.io.Serializable;

public class ActivityVO implements Serializable{
	
	private Integer idactivity;
	private String username;
	private String datetime;
	private String type;
	private String description;
	private String result;
		
	public void setIdactivity(Integer idactivity){
		this.idactivity = idactivity;
	}
	
	public Integer getIdactivity(){
		return this.idactivity;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setDatetime(String datetime){
		this.datetime = datetime;
	}
	
	public String getDatetime(){
		return this.datetime;
	}
		
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
		
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
		
	public void setResult(String result){
		this.result = result;
	}
	
	public String getResult(){
		return this.result;
	}
		
		
}