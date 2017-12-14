package com.turtlebone.contract.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.core.constants.VMParam;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.UserService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/pages")
public class PageController {
	private static Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private UserService userService;
		
	@RequestMapping(value="/listMain")
	public String listMain(ServletRequest request, Map<String, Object> model) {
		logger.info("goto listMain.vm");
		HttpServletRequest req = (HttpServletRequest) request;
		model.put("ROOT", req.getContextPath());
		return "contract/listMain";
	}
	
	@RequestMapping(value="/inputMain")
	public String inputMain(ServletRequest request, Map<String, Object> model) {
		logger.info("goto inputMain.vm");
		HttpServletRequest req = (HttpServletRequest) request;
		
		List<UserModel> userList = userService.listAllUser();
		model.put("ROOT", req.getContextPath());
		model.put(VMParam.userList, userList);
		return "contract/inputMain";
	}
}
