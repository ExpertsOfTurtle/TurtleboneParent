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
import com.turtlebone.codeforces.bean.FilterCFTaskRequest;
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
@RequestMapping(value = "/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value="/all")
	public @ResponseBody ResponseEntity<?> filter() {
		List<UserModel> userList = userService.listAllUser();
		List<String> usernameList = new ArrayList<>();
		for (UserModel user : userList) {
			usernameList.add(user.getLoginName());
		}
		return ResponseEntity.ok(usernameList);
	}
	
}
