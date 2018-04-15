package com.turtlebone.bet.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBCursor;
import com.turtlebone.bet.bean.BetInfo;
import com.turtlebone.bet.bean.BetInput;
import com.turtlebone.core.util.StringUtil;

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
		request.setExpireTime(new Date(System.currentTimeMillis() + 3600 * 1000));
		mongoTemplate.insert(request);
		return ResponseEntity.ok(request);
	}
	
	@RequestMapping(value="/listBet", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> listBet() {
		
		List<BetInfo> list = mongoTemplate.findAll(BetInfo.class);
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value="/inputBet", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> inputBet(@RequestBody BetInput request) {
		String id = request.getBid();
		String data = request.getData();
		String username = request.getUsername();
		if (StringUtil.isEmpty(id) || StringUtil.isEmpty(data) || StringUtil.isEmpty(username)) {
			return ResponseEntity.ok("Fail");	
		}
		Query query = new Query(Criteria.where("bid").is(id).and("username").is(username));
		mongoTemplate.findAllAndRemove(query, BetInput.class);
		mongoTemplate.insert(request);
		return ResponseEntity.ok(request);
	}
	
	@RequestMapping(value="/queryBet/{bid}")
	public @ResponseBody ResponseEntity<?> inputBet(@PathVariable("bid") String id) {
		logger.debug("当前时间：{}", System.currentTimeMillis());
		Query query = new Query(Criteria.where("id").is(id));
		List<BetInfo> betInfoList = mongoTemplate.find(query, BetInfo.class);
		if (betInfoList == null || betInfoList.size() != 1) {
			return ResponseEntity.ok("找不到这个id~~");
		}
		BetInfo betInfo = betInfoList.get(0);
		boolean hasPublic = true;
		if (betInfo.getPublicTime() == null || betInfo.getPublicTime().getTime() > System.currentTimeMillis()) {
			hasPublic = false;
		}
		
		query = new Query(Criteria.where("bid").is(id));
		List<BetInput> list = mongoTemplate.find(query, BetInput.class);
		if (list != null && !hasPublic) {
			for (BetInput bi : list) {
				bi.setData("**** (未到公开时间)");
			}
		}
		
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value="/deleteBet/{bid}")
	public @ResponseBody ResponseEntity<?> deleteBet(@PathVariable("bid") String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.findAndRemove(query, BetInfo.class);
		
		return ResponseEntity.ok("OK");
	}
}
