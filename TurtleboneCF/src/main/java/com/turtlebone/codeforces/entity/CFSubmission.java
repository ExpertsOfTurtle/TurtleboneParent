package com.turtlebone.codeforces.entity;

public class CFSubmission{
	
	private Integer id;
	private Integer contestid;
	private String problemindex;
	private String tags;
	private String result;
	private String submittime;
	private String username;
	private Integer status;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setContestid(Integer contestid){
		this.contestid = contestid;
	}
	
	public Integer getContestid(){
		return this.contestid;
	}
		
	public void setProblemindex(String problemindex){
		this.problemindex = problemindex;
	}
	
	public String getProblemindex(){
		return this.problemindex;
	}
		
	public void setTags(String tags){
		this.tags = tags;
	}
	
	public String getTags(){
		return this.tags;
	}
		
	public void setResult(String result){
		this.result = result;
	}
	
	public String getResult(){
		return this.result;
	}
		
	public void setSubmittime(String submittime){
		this.submittime = submittime;
	}
	
	public String getSubmittime(){
		return this.submittime;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
}
















