package com.turtlebone.contract.exception;

import lombok.Data;

@Data
public class ContractException extends Exception {
	protected String errorMesage;
	
	public ContractException(String errorMessage){
		this.errorMesage = errorMessage;
	}
	
}
