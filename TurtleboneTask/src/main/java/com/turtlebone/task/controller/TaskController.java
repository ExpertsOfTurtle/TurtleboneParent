package com.turtlebone.task.controller;

import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.turtlebone.task.bean.CreateTaskRequest;
import com.turtlebone.task.bean.QueryTaskRequest;
import com.turtlebone.task.bean.UpdateProgressRequest;
import com.turtlebone.task.constants.ITaskStatus;
import com.turtlebone.task.constants.ITaskType;
import com.turtlebone.task.model.TaskModel;
import com.turtlebone.task.model.TaskResultModel;
import com.turtlebone.task.model.TaskUserModel;
import com.turtlebone.task.service.TaskService;
import com.turtlebone.task.service.TaskUserService;
import com.alibaba.fastjson.JSON;
import com.turtlebone.core.exception.TurtleException;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/task")
public class TaskController {
	private static Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskUserService taskUserService;
	@Autowired
	private UserService userService;
		
	@RequestMapping(value="/queryMyTask", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> queryMyTask(HttpServletRequest request) throws TurtleException {
		String username = (String)request.getAttribute("username");
		
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		List<TaskResultModel> list = taskService.selectPage(null, null, username, null, null, null, 0, 100);
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value="/query", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> query(HttpServletRequest httpReq, @RequestBody QueryTaskRequest request) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		
		Integer userType = 2;
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		UserModel userModel = userService.selectByUsername(username);
		
		if (userModel != null) {
			userType = userModel.getUsertype();
		}
		String owner = request.getOwner();
		if (userType > 1) {
			//不是管理员，只能查询自己
			owner = username;
		}
		
		List<TaskResultModel> list = taskService.selectPage(null, request.getType(), owner, request.getStatus(), 
				request.getDeadlineFrom(), request.getDeadlineTo(), 
				request.getPageNumber(), request.getPageSize());
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value="/create", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request, 
			HttpServletRequest httpReq) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		//Create Task
		TaskModel taskModel = new TaskModel();
		taskModel.setTitle(request.getTitle());
		taskModel.setContent(request.getContent());
		taskModel.setCreator(username);
		taskModel.setDeadline(request.getDeadline());
		taskModel.setPunishmentId(request.getPunishmentId());
		taskModel.setType(request.getType());
		taskModel.setDifficulty(request.getDifficulty());
		Integer total = request.getTotal();
		if (total == null || total == 0) {
			total = 100;
		}
		Integer progress = request.getProgress();
		if (progress == null) {
			progress = 0;
		}
		taskModel.setTotal(total);
		taskModel.setProgress(progress);
		taskModel.setCreatetime(DateUtil.getDateTime());
		taskModel.setStatus(ITaskStatus.INPROGRESS);
		int id = taskService.create(taskModel);
		
		//Asign task to user
		for (String user : request.getOwner()) {
			TaskUserModel taskUserModel = new TaskUserModel();
			taskUserModel.setTaskid(id);
			taskUserModel.setUsername(user);
			taskUserModel.setDeadline(taskModel.getDeadline());
			taskUserModel.setAssigndatetime(DateUtil.getDateTime());
			taskUserModel.setProgress(progress);
			taskUserModel.setStatus(ITaskStatus.INPROGRESS);
			taskUserService.create(taskUserModel);
		}
		
		return ResponseEntity.ok(taskModel);
	}	
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> modifyTask(@RequestBody CreateTaskRequest request, 
			HttpServletRequest httpReq) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		logger.debug("modify task, request={}", JSON.toJSONString(request));
		
		if (request.getId() == null) {
			throw new TurtleException("", "taskId is null", "");
		}
		logger.debug("Modify task, id={}", request.getId());
		
		TaskModel taskModel = taskService.findByPrimaryKey(request.getId());
		if (taskModel == null) {
			throw new TurtleException("", "No such task", "");
		} else if (!username.equalsIgnoreCase(taskModel.getCreator())) {
			throw new TurtleException("", "You are not the owner of the task", "");
		}
		
		//Update task
		taskModel.setTitle(request.getTitle());
		taskModel.setContent(request.getContent());
		taskModel.setDeadline(request.getDeadline());
		taskModel.setPunishmentId(request.getPunishmentId());
		taskModel.setDifficulty(request.getDifficulty());
		taskModel.setProgress(request.getProgress());
		taskService.updateByPrimaryKey(taskModel);
		
		//Update task for task owner
		List<TaskUserModel> taskuserList = taskUserService.selectByCondition(request.getId(), null, null, null, null);
		for (TaskUserModel taskUserModel : taskuserList) {
			taskUserModel.setDeadline(taskModel.getDeadline());
			taskUserService.updateByPrimaryKey(taskUserModel);
		}
		
		return ResponseEntity.ok(taskModel);
	}	
	
	@RequestMapping(value="/delete/{taskId}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(HttpServletRequest httpReq, 
			@PathVariable(value="taskId") Integer taskId) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		TaskModel taskModel = taskService.findByPrimaryKey(taskId);
		if (taskModel == null) {
			throw new TurtleException("", "No such task", "");
		} else if (!username.equalsIgnoreCase(taskModel.getCreator())) {
			throw new TurtleException("", "You are not the owner of the task", "");
		}
		
		taskService.deleteByPrimaryKey(taskId);
		taskUserService.deleteByTaskId(taskId);
		
		return ResponseEntity.ok("OK");
	}
	
	@RequestMapping(value="/updateProgress", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> updateProgress(HttpServletRequest httpReq,
			@RequestBody UpdateProgressRequest request) throws TurtleException {
		String username = (String)httpReq.getAttribute("username");
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		Integer taskId = request.getId();
		TaskModel task = taskService.findByPrimaryKey(taskId);
		if (task == null) {
			throw new TurtleException("", "No such Task", "");
		}
		
		Integer progress = request.getProgress();
		if (progress == null || progress < 0 || progress > task.getTotal()) {
			throw new TurtleException("", "Incorrect percentage!", "");
		}
		
		List<TaskUserModel> list = taskUserService.selectByCondition(request.getId(), username, null, null, null);
		if (list == null) {
			throw new TurtleException("", "No such record", "");
		} else if (list.size() != 1) {
			throw new TurtleException("", "Why you have 2 same tasks?", "");
		}
		TaskUserModel taskUserModel = list.get(0);
		switch (request.getActionType()) {
			case "STATUS":
				taskUserModel.setStatus(request.getStatus());
				if (ITaskStatus.DONE == request.getStatus()) {
					taskUserModel.setProgress(task.getTotal());
				}
				break;
			case "PERCENTAGE":
				taskUserModel.setProgress(request.getProgress());
				taskUserModel.setStatus(ITaskStatus.INPROGRESS);
				break;
			default:
				break;
		}
		taskUserService.updateByPrimaryKey(taskUserModel);
		
		return ResponseEntity.ok(taskUserModel);
	}
	
}
