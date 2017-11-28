package com.turtlebone.contract.entity;

import java.util.Date;

public class Contract{
	
	private Integer id;
	private String contractNo;
	private String contractName;
	private String contractType;
	private Date effectiveDate;
	private Date expiredDate;
	private Short contractStatus;
	private String contractParty;
	private String signedTime;
	private String createBy;
	private String updateBy;
	private Date createtime;
	private Date updatetime;
	private String contractContent;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setContractNo(String contractNo){
		this.contractNo = contractNo;
	}
	
	public String getContractNo(){
		return this.contractNo;
	}
		
	public void setContractName(String contractName){
		this.contractName = contractName;
	}
	
	public String getContractName(){
		return this.contractName;
	}
		
	public void setContractType(String contractType){
		this.contractType = contractType;
	}
	
	public String getContractType(){
		return this.contractType;
	}
		
	public void setEffectiveDate(Date effectiveDate){
		this.effectiveDate = effectiveDate;
	}
	
	public Date getEffectiveDate(){
		return this.effectiveDate;
	}
		
	public void setExpiredDate(Date expiredDate){
		this.expiredDate = expiredDate;
	}
	
	public Date getExpiredDate(){
		return this.expiredDate;
	}
		
	public void setContractStatus(Short contractStatus){
		this.contractStatus = contractStatus;
	}
	
	public Short getContractStatus(){
		return this.contractStatus;
	}
		
	public void setContractParty(String contractParty){
		this.contractParty = contractParty;
	}
	
	public String getContractParty(){
		return this.contractParty;
	}
		
	public void setSignedTime(String signedTime){
		this.signedTime = signedTime;
	}
	
	public String getSignedTime(){
		return this.signedTime;
	}
		
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	
	public String getCreateBy(){
		return this.createBy;
	}
		
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	
	public String getUpdateBy(){
		return this.updateBy;
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
		
	public void setContractContent(String contractContent){
		this.contractContent = contractContent;
	}
	
	public String getContractContent(){
		return this.contractContent;
	}
		
		
}
















