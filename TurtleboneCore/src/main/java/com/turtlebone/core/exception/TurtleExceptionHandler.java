package com.turtlebone.core.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.turtlebone.core.bean.ResultVO;

@ControllerAdvice
public class TurtleExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(TurtleExceptionHandler.class);
	
	@ExceptionHandler(TurtleException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResultVO<String> handleException(HttpServletRequest request, TurtleException e) {
		ResultVO<String> rs = new ResultVO<String>(e.getCode(), e.getMessage(), "");
		logger.error("{}", e);
		return rs;
	}
}
