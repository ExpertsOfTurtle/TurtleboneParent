package com.turtlebone.contract.bean;

import java.util.List;

import lombok.Data;

@Data
public class CreateContractRequest {
	protected String title;
	protected String content;
	protected String creator;
	protected String type;
	protected String effectiveDate;
	protected String expiredDate;
	protected List<String> partyList;
}
