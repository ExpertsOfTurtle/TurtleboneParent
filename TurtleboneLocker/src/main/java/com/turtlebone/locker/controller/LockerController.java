package com.turtlebone.locker.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turtlebone.locker.bean.AddLockerRequest;
import com.turtlebone.locker.model.LockerModel;
import com.turtlebone.locker.service.LockerService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/locker")
public class LockerController {
	private static Logger logger = LoggerFactory.getLogger(LockerController.class);
	
	@Autowired
	private LockerService lockerService;
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> add(HttpServletRequest httpServletRequest, @RequestBody AddLockerRequest request) {
		logger.info("Add locker");
		
		LockerModel lockerModel = new LockerModel();
		lockerModel.setName(request.getName());
		lockerModel.setCategory(request.getCategory());
		lockerModel.setLocation(request.getLocation());
		lockerService.create(lockerModel);
		
		return ResponseEntity.ok(lockerModel);
	}
}
