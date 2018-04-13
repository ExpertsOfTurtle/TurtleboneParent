package com.turtlebone.codeforces.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.turtlebone.codeforces.bean.FilterSubmissionRequest;
import com.turtlebone.codeforces.bean.QueryStatusRequest;
import com.turtlebone.codeforces.bean.SyncSubmissionRequest;
import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.constants.ICFConstants;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.bean.ArrayResult;
import com.turtlebone.core.bean.ResultVO;
import com.turtlebone.core.util.StringUtil;

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
		logger.info("syncSubmission request:{}", JSON.toJSONString(request));

		List<CFSubmissionModel> list = fetchSubmissionsService.fetchResult(request.getUsername(), request.getFrom(),
				request.getCount());
		cfSubmissionService.insert(list);
		
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value = "/syncSubmission", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> syncSubmission() {
		logger.info("syncSubmission");

		Map<String, List> result = new HashMap<>();
		for (String username : ICFConstants.USERLIST) {
			logger.info("Start sync submissions for {}", username);
			List<CFSubmissionModel> list = fetchSubmissionsService.fetchResult(username, 1, 100);
			cfSubmissionService.insert(list);
			result.put(username, list);
		}
		
		return ResponseEntity.ok(result);
	}
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> filter(@RequestBody FilterSubmissionRequest request) {
		CFSubmissionModel filter = new CFSubmissionModel();
		if (!StringUtils.isEmpty(request.getUsername())) {
			filter.setUsername(request.getUsername());
		}
		if (!StringUtils.isEmpty(request.getContestId())) {
			filter.setContestid(request.getContestId());
		}
		if (!StringUtils.isEmpty(request.getResult())) {
			filter.setResult(request.getResult());
		}
		if (!StringUtils.isEmpty(request.getStatus())) {
			filter.setStatus(request.getStatus());
		}
		if (!StringUtils.isEmpty(request.getTags())) {
			filter.setTags(request.getTags());
		}
		
		int total = cfSubmissionService.selectCount(filter);
		List<CFSubmissionModel> list = null;
		if (total > 0) {
			list = cfSubmissionService.selectByCondition(filter, request.getPageNumber(), request.getPageSize());
		}
		ArrayResult ar = new ArrayResult();
		ar.setTotal(total);
		ar.setList(list);
		return ResponseEntity.ok(ar);
	}
	@RequestMapping(value = "/queryStatus", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> queryStatus(@RequestBody QueryStatusRequest request) {
		String from = request.getFrom();
		String to = request.getTo();
		WeeklySummary result = null;
		if (StringUtil.isEmpty(from) || StringUtil.isEmpty(to)) {
			result = weeklyTaskService.queryWeeklyStatus();
		} else {
			result = weeklyTaskService.queryStatus(from, to);
		}
		return ResponseEntity.ok(result);
	}
	
}
