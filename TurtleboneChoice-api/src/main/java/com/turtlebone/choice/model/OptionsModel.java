package com.turtlebone.choice.model;
import java.io.Serializable;

public class OptionsModel implements Serializable{
	
	private Integer optionid;
	private Integer groupid;
	private String optionname;
	private Integer probability;
		
	public void setOptionid(Integer optionid){
		this.optionid = optionid;
	}
	
	public Integer getOptionid(){
		return this.optionid;
	}
		
	public void setGroupid(Integer groupid){
		this.groupid = groupid;
	}
	
	public Integer getGroupid(){
		return this.groupid;
	}
		
	public void setOptionname(String optionname){
		this.optionname = optionname;
	}
	
	public String getOptionname(){
		return this.optionname;
	}
		
	public void setProbability(Integer probability){
		this.probability = probability;
	}
	
	public Integer getProbability(){
		return this.probability;
	}
		
		
}