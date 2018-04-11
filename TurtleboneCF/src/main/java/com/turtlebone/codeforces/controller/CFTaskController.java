package com.turtlebone.codeforces.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.alibaba.fastjson.JSONObject;
import com.turtlebone.codeforces.bean.InsertCFTaskRequest;
import com.turtlebone.codeforces.bean.QueryStatusRequest;
import com.turtlebone.codeforces.bean.SyncSubmissionRequest;
import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.entity.CFTask;
import com.turtlebone.codeforces.model.CFProblemModel;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.model.CFTaskModel;
import com.turtlebone.codeforces.service.CFProblemService;
import com.turtlebone.codeforces.service.CFReportService;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.CFTaskService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.EmailService;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;
import com.turtlebone.core.velocity.VelocityGenerator;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/task")
public class CFTaskController {
	private static Logger logger = LoggerFactory.getLogger(CFTaskController.class);

	@Autowired
	private CFTaskService cfTaskService;
	@Autowired
	private UserService userService;
	@Autowired
	private CFProblemService cfProblemService;

	@RequestMapping(value = "/insert")
	public @ResponseBody ResponseEntity<?> insert(@RequestBody InsertCFTaskRequest request) {
		String validateResult = validate(request);
		if (!"".equals(validateResult)) {
			return ResponseEntity.ok(validateResult);
		}
		UserModel user = userService.selectByUsername(request.getUsername());
		if (user == null) {
			return ResponseEntity.ok("Username not exist");
		}
		
		CFTaskModel task = new CFTaskModel();
		task.setUsername(request.getUsername());
		task.setAmount(request.getAmount());
		if (StringUtils.isEmpty(request.getDeadline())) {
			task.setDeadline(DateUtil.getNDaysLater(365, "yyyyMMdd"));
		} else {
			task.setDeadline(request.getDeadline());	
		}	
		task.setFinish(0);
		task.setReason(request.getReason());
		cfTaskService.create(task);
		
		return ResponseEntity.ok("OK");
	}
	
	@RequestMapping(value = "/migrate")
	public @ResponseBody ResponseEntity<?> migrate() {
		List<CFProblemModel> list = cfProblemService.selectByCondition("0", "B");
		if (list == null) {
			return null;
		}
		Collections.sort(list, new Comparator<CFProblemModel>() {
			@Override
			public int compare(CFProblemModel arg0, CFProblemModel arg1) {
				int x = arg0.getDeadline().compareTo(arg1.getDeadline());
				if (x == 0) {
					return arg0.getRespondent().compareTo(arg1.getRespondent());
				} else {
					return x;
				}
			}			
		});
		int c = 1;
		List<CFTaskModel> taskList = new ArrayList<>();
		CFProblemModel pre = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			CFProblemModel cur = list.get(i);
			if (cur.getRespondent().equals(pre.getRespondent()) && cur.getDeadline().equals(pre.getDeadline())) {
				c++;
			} else {
				CFTaskModel task = new CFTaskModel();
				task.setAmount(c);
				task.setDeadline(pre.getDeadline());
				task.setUsername(pre.getRespondent());
				task.setFinish(0);
				task.setReason("系统录入历史记录");
				taskList.add(task);
				c = 1;
			}
			pre = cur;
		}
		CFTaskModel task = new CFTaskModel();
		task.setAmount(c);
		task.setDeadline(pre.getDeadline());
		task.setUsername(pre.getRespondent());
		task.setFinish(0);
		task.setReason("系统录入历史记录");
		taskList.add(task);
		
		for (CFTaskModel t : taskList) {
			cfTaskService.create(t);
		}
		
		JSONObject result = new JSONObject();
		result.put("origin", list);
		result.put("new", taskList);
		return ResponseEntity.ok(result);
	}
	
	private String validate(InsertCFTaskRequest request) {
		if (StringUtils.isEmpty(request.getUsername())) {
			return "Username is required";
		}
		if (StringUtils.isEmpty(request.getAmount()) ) {
			return "Amount is required";
		}
		if (request.getAmount() <= 0) {
			return "Amount should be greater than zero";
		}
		if (StringUtils.isEmpty(request.getReason())) {
			return "Reason is required";
		}
		return "";
	}
	
}
