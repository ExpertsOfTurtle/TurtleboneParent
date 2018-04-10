package com.turtlebone.bet.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBCursor;
import com.turtlebone.bet.bean.BetInfo;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/bet")
public class BetController {
	private static Logger logger = LoggerFactory.getLogger(BetController.class);
	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	@RequestMapping(value="/insertObject", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> insertObject(@RequestBody JSONObject obj) {
		obj.put("Date", new Date());
		mongoTemplate.insert(obj, "wf");
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(value="/createBet", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createBet(@RequestBody BetInfo request) {
		String id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
		request.setId(id);
		request.setExpireTime(new Date(System.currentTimeMillis() + 10 * 1000));
		mongoTemplate.insert(request);
		return ResponseEntity.ok(request);
	}
	
	@RequestMapping(value="/listBet", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> listBet() {
		
		List<BetInfo> list = mongoTemplate.findAll(BetInfo.class);
		return ResponseEntity.ok(list);
	}
}
