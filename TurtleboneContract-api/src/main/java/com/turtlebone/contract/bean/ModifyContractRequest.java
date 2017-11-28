package com.turtlebone.contract.bean;

import java.util.List;

import lombok.Data;

@Data
public class ModifyContractRequest {
	protected Integer contractId;
	protected String title;
	protected String content;
	protected String type;
	protected String effectiveDate;
	protected String expiredDate;
	protected String username;
}
