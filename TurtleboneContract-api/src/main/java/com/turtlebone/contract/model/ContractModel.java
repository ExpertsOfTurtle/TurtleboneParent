package com.turtlebone.contract.model;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class ContractModel implements Serializable{
	
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
			
}