package com.turtlebone.contract.bean;

import java.util.List;

import lombok.Data;

@Data
public class SignContractRequest {
	protected Integer contractId;
	protected String username;
	protected String actionType;
}
