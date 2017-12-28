package com.turtlebone.codeforces.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.turtlebone.codeforces.bean.QueryStatusRequest;
import com.turtlebone.codeforces.bean.SyncSubmissionRequest;
import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.service.EmailService;
import com.turtlebone.core.util.StringUtil;
import com.turtlebone.core.velocity.VelocityGenerator;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/report")
public class CFReportController {
	private static Logger logger = LoggerFactory.getLogger(CFReportController.class);

	@Autowired
	private FetchSubmissionsService fetchSubmissionsService;
	@Autowired
	private CFSubmissionService cfSubmissionService;
	@Autowired
	private WeeklyTaskService weeklyTaskService;
	@Autowired
	private EmailService emailService;


	@RequestMapping(value = "/weekly", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> weekly() {
		WeeklySummary result = null;
		result = weeklyTaskService.queryWeeklyStatus();
		
		Map<String, Object> model = new HashMap<>();
		model.put("weeklySummary", result);
		String html = VelocityGenerator.getVMResult("velocity/email/weekly.vm", model);
		List<String> addressList = new ArrayList<>();
		addressList.add("133344251@qq.com");
		emailService.sendEmail(addressList, "CF weekly report", html, "CF_SYS");
		return ResponseEntity.ok(html);
	}
	
}
