package com.turtlebone.contract.bean;

import lombok.Data;

@Data
public class ResultVO<T> {
	public final static String PARAMERROR = "ParamError";
	
	
	private String code;
	private String message;
	private T result;

	public ResultVO(String code, String message, T t) {
		this.code = code;
		this.message = message;
		this.result = t;
	}
}
