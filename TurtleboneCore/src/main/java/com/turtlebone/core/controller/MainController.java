package com.turtlebone.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.http.HttpResponse;

//@Controller
//@EnableAutoConfiguration
//@RequestMapping(value="/main")
public class MainController {
	private static Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/*
	 * 进入主页
	 * */
	@RequestMapping(value="/")
	public String getMainPage(Map<String, Object> model) {
		logger.debug("go to index.vm");
		model.put("wf", "DFSDFS");
		return "index";
	}

	@RequestMapping(value="/tb")
	public String turtlebone(ServletRequest request, Map<String, Object> model) {
		logger.debug("go to turtlebone.vm");
		HttpServletRequest req = (HttpServletRequest) request;
		logger.info("ContextPath:{}", req.getContextPath());
		logger.info("getPathInfo:{}", req.getPathInfo());
		model.put("ROOT", req.getContextPath());
		return "turtlebone";
	}
	/*
	@RequestMapping(value="/login")
	public String login(Map<String, Object> model) {
		logger.debug("go to login.vm");
		return "login/login";
	}
	
	@RequestMapping(value="/dream/{page}")
	public String dreamPage(Map<String, Object> model, @PathVariable("page") String page) {
		logger.debug("go to /dream/{}.vm", page);
		return "dream/" + page;
	}
	@RequestMapping(value="/decide/{page}")
	public String decidePage(Map<String, Object> model, @PathVariable("page") String page) {
		logger.debug("go to /decide/{}.vm", page);
		return "decide/" + page;
	}
	@RequestMapping(value="/contract/{page}")
	public String contractPage(Map<String, Object> model, @PathVariable("page") String page,
			HttpServletResponse resp) {
		logger.debug("go to /contract/{}.vm", page);
		return "contract/" + page;
	}*/
}
