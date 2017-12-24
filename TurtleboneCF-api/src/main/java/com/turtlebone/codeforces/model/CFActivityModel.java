package com.turtlebone.codeforces.model;
import java.io.Serializable;

public class CFActivityModel implements Serializable{
	
	private Integer idactivity;
	private String username;
	private String datetime;
	private String type;
	private String description;
	private Long result1;
	private Long result2;
	private Long result3;
	private String strresult1;
	private String strresult2;
	private String strresult3;
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
		
	public void setResult1(Long result1){
		this.result1 = result1;
	}
	
	public Long getResult1(){
		return this.result1;
	}
		
	public void setResult2(Long result2){
		this.result2 = result2;
	}
	
	public Long getResult2(){
		return this.result2;
	}
		
	public void setResult3(Long result3){
		this.result3 = result3;
	}
	
	public Long getResult3(){
		return this.result3;
	}
		
	public void setStrresult1(String strresult1){
		this.strresult1 = strresult1;
	}
	
	public String getStrresult1(){
		return this.strresult1;
	}
		
	public void setStrresult2(String strresult2){
		this.strresult2 = strresult2;
	}
	
	public String getStrresult2(){
		return this.strresult2;
	}
		
	public void setStrresult3(String strresult3){
		this.strresult3 = strresult3;
	}
	
	public String getStrresult3(){
		return this.strresult3;
	}
		
	public void setResult(String result){
		this.result = result;
	}
	
	public String getResult(){
		return this.result;
	}
		
		
}