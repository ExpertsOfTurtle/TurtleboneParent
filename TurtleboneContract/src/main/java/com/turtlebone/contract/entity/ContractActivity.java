package com.turtlebone.contract.entity;

import java.util.Date;

public class ContractActivity{
	
	private Integer id;
	private Integer contactid;
	private String username;
	private Date datetime;
	private Integer action;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setContactid(Integer contactid){
		this.contactid = contactid;
	}
	
	public Integer getContactid(){
		return this.contactid;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setDatetime(Date datetime){
		this.datetime = datetime;
	}
	
	public Date getDatetime(){
		return this.datetime;
	}
		
	public void setAction(Integer action){
		this.action = action;
	}
	
	public Integer getAction(){
		return this.action;
	}
		
		
}
















