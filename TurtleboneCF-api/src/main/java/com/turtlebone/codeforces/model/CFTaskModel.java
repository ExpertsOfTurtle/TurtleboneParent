package com.turtlebone.codeforces.model;
import java.io.Serializable;
import java.util.Date;

public class CFTaskModel implements Serializable{
	
	private Integer id;
	private String username;
	private String deadline;
	private Integer amount;
	private String reason;
	private Integer finish;
	private Date createtime;
	private Date updatetime;
	private Integer status;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setDeadline(String deadline){
		this.deadline = deadline;
	}
	
	public String getDeadline(){
		return this.deadline;
	}
		
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	public Integer getAmount(){
		return this.amount;
	}
		
	public void setReason(String reason){
		this.reason = reason;
	}
	
	public String getReason(){
		return this.reason;
	}
		
	public void setFinish(Integer finish){
		this.finish = finish;
	}
	
	public Integer getFinish(){
		return this.finish;
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
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
}