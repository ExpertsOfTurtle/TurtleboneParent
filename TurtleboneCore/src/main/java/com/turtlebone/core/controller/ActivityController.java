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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.http.HttpResponse;
import com.turtlebone.core.bean.QueryActivityRequest;
import com.turtlebone.core.bean.ResultVO;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.ActivityService;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.StringUtil;

@Controller
//@EnableAutoConfiguration
@RequestMapping(value="/activity")
public class ActivityController {
	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/query")
	public @ResponseBody ResponseEntity<?> query(HttpServletRequest httpServletRequest, 
			@RequestBody QueryActivityRequest request) {
		logger.debug("Core. query Activity");
		String username = (String)httpServletRequest.getAttribute("username");
		UserModel user = userService.selectByUsername(username);
		
		if (!StringUtil.isEmpty(request.getUsername()) && 
			!username.equals(request.getUsername())) {
			if (user == null || user.getUsertype() != 1) {
				logger.warn("管理员才能查询其他人的信息");
				return ResponseEntity.ok(new ResultVO<String>("E1001", "Only admin can operate", ""));
			}
		} else if (StringUtil.isEmpty(request.getUsername())) {
			//不是管理员，只能查询自己的数据
			if (user.getUsertype() != 1) {
				request.setUsername(username);
			}
		}
		List<ActivityModel> list = activityService.selectByCondition(request.getUsername(),
				request.getType(), request.getResult1(), request.getResult2(), request.getResult3(),
				request.getFrom(), request.getTo(), 
				request.getPageSize(), request.getPageNumber());
		return ResponseEntity.ok(list);
	}
	
}
