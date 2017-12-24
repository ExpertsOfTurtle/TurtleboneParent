package com.turtlebone.codeforces.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.turtlebone.codeforces.bean.SyncSubmissionRequest;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.codeforces.service.WeeklyTaskService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/cf")
public class SubmissionController {
	private static Logger logger = LoggerFactory.getLogger(SubmissionController.class);

	@Autowired
	private FetchSubmissionsService fetchSubmissionsService;
	@Autowired
	private CFSubmissionService cfSubmissionService;
	@Autowired
	private WeeklyTaskService weeklyTaskService;

	@RequestMapping(value = "/syncSubmission", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> syncSubmission(@RequestBody SyncSubmissionRequest request) {
		logger.info("syncSubmission token:{}", JSON.toJSONString(request));

		List<CFSubmissionModel> list = fetchSubmissionsService.fetchResult(request.getUsername(), request.getFrom(),
				request.getCount());
		cfSubmissionService.insert(list);
		
		return ResponseEntity.ok(list);
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> test() {
		weeklyTaskService.queryWeeklyStatus("DFS");
		return ResponseEntity.ok("OK");
	}
	
}
