package com.turtlebone.contract.model;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ContractActivityModel implements Serializable{
	
	private Integer id;
	private Integer contactid;
	private String username;
	private Date datetime;
	private Integer action;

}