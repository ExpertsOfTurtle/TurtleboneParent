package com.turtlebone.dairy.entity;

import java.util.Date;

public class Dairy{
	
	private Integer id;
	private String title;
	private String creator;
	private Date createtime;
	private Date updatetime;
	private Integer type;
	private Integer subtype;
	private Date expiretime;
	private Integer status;
	private byte[] content;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
		
	public void setCreator(String creator){
		this.creator = creator;
	}
	
	public String getCreator(){
		return this.creator;
	}
		
	public void setCreatetime(Date createtime){
		this.createtime = createtime;
	}
	
	public Date getCreatetime(){
		return this.createtime;
	}
		
	public void setUpdatetime(Date updatetime){
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime(){
		return this.updatetime;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}
		
	public void setSubtype(Integer subtype){
		this.subtype = subtype;
	}
	
	public Integer getSubtype(){
		return this.subtype;
	}
		
	public void setExpiretime(Date expiretime){
		this.expiretime = expiretime;
	}
	
	public Date getExpiretime(){
		return this.expiretime;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setContent(byte[] content){
		this.content = content;
	}
	
	public byte[] getContent(){
		return this.content;
	}
		
		
}
















