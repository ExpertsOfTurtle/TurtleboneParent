package com.turtlebone.contract.entity;


public class User{
	
	private Integer id;
	private String loginName;
	private String loginPwd;
	private String createtime;
	private Float balance;
	private Integer usertype;
	private String nickname;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}
	
	public String getLoginName(){
		return this.loginName;
	}
		
	public void setLoginPwd(String loginPwd){
		this.loginPwd = loginPwd;
	}
	
	public String getLoginPwd(){
		return this.loginPwd;
	}
		
	public void setCreatetime(String createtime){
		this.createtime = createtime;
	}
	
	public String getCreatetime(){
		return this.createtime;
	}
		
	public void setBalance(Float balance){
		this.balance = balance;
	}
	
	public Float getBalance(){
		return this.balance;
	}
		
	public void setUsertype(Integer usertype){
		this.usertype = usertype;
	}
	
	public Integer getUsertype(){
		return this.usertype;
	}
		
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	
	public String getNickname(){
		return this.nickname;
	}
		
		
}
















