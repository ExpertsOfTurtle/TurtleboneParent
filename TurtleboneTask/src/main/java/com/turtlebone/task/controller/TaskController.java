package com.turtlebone.task.controller;

import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.turtlebone.task.bean.CreateTaskRequest;
import com.turtlebone.task.bean.UpdateProgressRequest;
import com.turtlebone.task.constants.ITaskStatus;
import com.turtlebone.task.constants.ITaskType;
import com.turtlebone.task.model.TaskModel;
import com.turtlebone.task.model.TaskUserModel;
import com.turtlebone.task.service.TaskService;
import com.turtlebone.task.service.TaskUserService;
import com.alibaba.fastjson.JSON;
import com.turtlebone.core.exception.TurtleException;
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
		
	@RequestMapping(value="/queryMyTask", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> queryMyTask(HttpServletRequest request) throws TurtleException {
		String username = (String)request.getAttribute("username");
		
		if (StringUtil.isEmpty(username)) {
			throw new TurtleException("", "Please login first", "");
		}
		
		List<TaskModel> list = taskService.selectPage(null, null, username, null, null, null);
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
		taskModel.setStatus(ITaskStatus.NEW);
		taskModel.setType(ITaskType.NORMAL);
		taskModel.setDifficulty(request.getDifficulty());
		taskModel.setPercentage(request.getPercentage() == null ? 0 : request.getPercentage());
		taskModel.setCreatetime(DateUtil.getDateTime());		
		int id = taskService.create(taskModel);
		
		//Asign task to user
		for (String user : request.getOwner()) {
			TaskUserModel taskUserModel = new TaskUserModel();
			taskUserModel.setTaskid(id);
			taskUserModel.setUsername(user);
			taskUserModel.setStatus(ITaskStatus.NEW);
			taskUserModel.setDeadline(taskModel.getDeadline());
			taskUserModel.setAssigndatetime(DateUtil.getDateTime());
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
		taskModel.setDeadline(taskModel.getDeadline());
		taskModel.setPunishmentId(taskModel.getPunishmentId());
		taskModel.setDifficulty(request.getDifficulty());
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
			@PathParam(value="taskId") Integer taskId) throws TurtleException {
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
		
		Integer percentage = request.getPercentage();
		if (percentage == null || percentage < 0 || percentage > 100) {
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
					taskUserModel.setPercentage(100);
				}
				break;
			case "PERCENTAGE":
				taskUserModel.setPercentage(request.getPercentage());
				break;
			default:
				break;
		}
		taskUserService.updateByPrimaryKey(taskUserModel);
		
		return ResponseEntity.ok(taskUserModel);
	}
	
}
