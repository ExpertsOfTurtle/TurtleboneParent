package com.turtlebone.core.model;
import java.io.Serializable;
import java.util.Date;

public class LockerModel implements Serializable{
	
	private Integer id;
	private String category;
	private String name;
	private String location;
	private Date updatetime;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setCategory(String category){
		this.category = category;
	}
	
	public String getCategory(){
		return this.category;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
		
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation(){
		return this.location;
	}
		
	public void setUpdatetime(Date updatetime){
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime(){
		return this.updatetime;
	}
		
		
}