package com.turtlebone.task.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.core.constants.VMParam;
import com.turtlebone.core.exception.TurtleException;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.StringUtil;
import com.turtlebone.task.bean.FilterTaskRequest;
import com.turtlebone.task.model.TaskModel;
import com.turtlebone.task.model.TaskResultModel;
import com.turtlebone.task.model.TaskUserModel;
import com.turtlebone.task.service.TaskService;
import com.turtlebone.task.service.TaskUserService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/pages")
public class PageController {
	private static Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskUserService taskUserService;
	@Autowired
	private UserService userService;
		
	@RequestMapping(value="/listMain")
	public String listMain(ServletRequest request, Map<String, Object> model) {
		logger.info("goto listMain.vm");
		HttpServletRequest req = (HttpServletRequest) request;
		model.put("ROOT", req.getContextPath());
		return "task/listMain";
	}
	
	/**
	 * Create task
	 */
	@RequestMapping(value="/inputMain")
	public String inputMain(ServletRequest request, Map<String, Object> model) {
		logger.info("goto inputMain.vm");
		List<UserModel> userList = userService.listAllUser();
		model.put(VMParam.userList, userList);
		return "task/inputMain";
	}
	
	/**
	 * taskDetailDialog
	 */
	@RequestMapping(value="/taskDetailDialog")
	public String taskDetailDialog(ServletRequest request, Map<String, Object> model) {
		logger.info("goto taskDetailDialog.vm");
		HttpServletRequest req = (HttpServletRequest) request;
		model.put("ROOT", req.getContextPath());
		return "task/taskDetailDialog";
	}
	
	/**
	 * Query all my tasks
	 */
	@RequestMapping(value="/loadMyTask", method = RequestMethod.POST)
	public String loadMyTask(HttpServletRequest httpReq, @RequestBody FilterTaskRequest request,
			Map<String, Object> model) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		Integer type = request.getType();
		Integer status = request.getStatus();
		String deadlineFrom = request.getDeadlineFrom();
		String deadlineTo = request.getDeadlineTo();
		List<TaskResultModel> list = taskService.selectPage(null, type, username, status, deadlineFrom, deadlineTo, 0, 100);
		model.put("list", list);
		
		return "task/pages/list";
	}
	
	@RequestMapping(value="/detail/{taskId}", method = RequestMethod.GET)
	public String getDetailPage(HttpServletRequest httpReq, 
			Map<String, Object> model,
			@PathVariable("taskId") Integer taskId) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		TaskModel taskModel = taskService.findByPrimaryKey(taskId);
		model.put("taskModel", taskModel);
		
		List<TaskUserModel> list = taskUserService.selectByCondition(taskId, username, null, null, null);
		if (list != null && list.size() == 1) {
			model.put("myTask", list.get(0));
		} else {
			logger.warn("This is not my task!");
		}
		
		return "task/pages/details";
	}
	@RequestMapping(value="/edit/{taskId}", method = RequestMethod.GET)
	public String getEditPage(HttpServletRequest httpReq, 
			Map<String, Object> model,
			@PathVariable("taskId") Integer taskId) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		TaskModel taskModel = taskService.findByPrimaryKey(taskId);
		model.put("detail", taskModel);
		
		List<TaskUserModel> list = taskUserService.selectByCondition(taskId, username, null, null, null);
		if (list != null && list.size() == 1) {
			model.put("myTask", list.get(0));
		} else {
			logger.warn("This is not my task!");
		}
				
		return "task/pages/edit";
	}
}
