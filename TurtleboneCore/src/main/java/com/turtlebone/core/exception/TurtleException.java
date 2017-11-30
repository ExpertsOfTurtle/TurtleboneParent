package com.turtlebone.core.exception;

import lombok.Data;

@Data
public class TurtleException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -508504454841371597L;
	private String code;
	private String message;
	private String username;
	
	public TurtleException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public TurtleException(String code, String message, String username) {
		super();
		this.code = code;
		this.message = message;
		this.username = username;
	}
	
	
}
