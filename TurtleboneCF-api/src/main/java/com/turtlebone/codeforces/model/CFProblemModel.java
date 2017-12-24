package com.turtlebone.codeforces.model;
import java.io.Serializable;

public class CFProblemModel implements Serializable{
	
	private Long id;
	private String problemNo;
	private String date;
	private String respondent;
	private String status;
	private String type;
	private String deadline;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setProblemNo(String problemNo){
		this.problemNo = problemNo;
	}
	
	public String getProblemNo(){
		return this.problemNo;
	}
		
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return this.date;
	}
		
	public void setRespondent(String respondent){
		this.respondent = respondent;
	}
	
	public String getRespondent(){
		return this.respondent;
	}
		
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
		
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
		
	public void setDeadline(String deadline){
		this.deadline = deadline;
	}
	
	public String getDeadline(){
		return this.deadline;
	}
		
		
}