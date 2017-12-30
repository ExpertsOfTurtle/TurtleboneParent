package com.turtlebone.task.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.task.bean.CompleteProblemRequest;
import com.turtlebone.task.bean.InsertProblemRequest;
import com.turtlebone.task.bean.QueryProblemRequest;
import com.turtlebone.task.builder.CFActivityBuilder;
import com.turtlebone.task.model.TaskActivityModel;
import com.turtlebone.task.model.ProblemModel;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.task.service.TaskActivityService;
import com.turtlebone.task.service.ProblemService;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/CF")
public class CFController {
	private static Logger logger = LoggerFactory.getLogger(CFController.class);

	@Autowired
	private ProblemService problemService;
	@Autowired
	private TaskActivityService taskActivityService;
	@Autowired
	private UserService userService;
	@Autowired
	private CFActivityBuilder cfActivityBuilder;

	@RequestMapping(value = "/queryByDeadline", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> queryByDeadline(@RequestBody QueryProblemRequest request) {
		List<ProblemModel> list = problemService.selectByCondition(request.getUsername(), request.getType(),
				request.getStatus(), request.getDeadlineFrom(), request.getDeadlineTo(), "deadline");
		return ResponseEntity.ok(list);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> insert(@RequestBody InsertProblemRequest request) {
		if (request.getCount() == null || request.getCount() <= 0) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("count must greater than 0");
		}
		if (!"DFS".equals(request.getUsername()) && !"Could".equals(request.getUsername())) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("no such username!");
		}
		if (!"B".equals(request.getType())) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Only support type 'B'");
		}
		UserModel userModel = userService.selectByUsername(request.getUsername());
		if (userModel == null) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("no such username!");
		}
		int n = request.getCount();
		String date = DateUtil.getDateTime("yyyyMMdd");
		String deadline = request.getDeadline();
		if (StringUtil.isEmpty(deadline)) {
			deadline = DateUtil.getNDaysLater(365, "yyyyMMdd");
		}
		List<ProblemModel> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ProblemModel problem = new ProblemModel();
			problem.setDate(date);
			problem.setDeadline(deadline);
			problem.setRespondent(request.getUsername());
			problem.setType(request.getType());
			problem.setStatus("0");
			problem.setProblemNo("");
			// problemService.create(problem);
			list.add(problem);
		}
		problemService.batchInsert(list);

		TaskActivityModel activity = cfActivityBuilder.buildInsert(request.getUsername(), date, request.getType(), n,
				deadline, request.getRemark());
		taskActivityService.create(activity);
		return ResponseEntity.ok("OK");
	}

	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> complete(HttpServletRequest httpReq, @RequestBody CompleteProblemRequest request) {
		String remoteAddr = httpReq.getRemoteAddr();
		if (!"127.0.0.1".equals(remoteAddr) && !"111.230.249.171".equals(remoteAddr)) {
			return ResponseEntity.ok("非法访问");
		}
		ProblemModel problem = problemService.selectIdOfNextProblem(request.getUsername(), request.getType());
		if (problem == null) {
			logger.warn("根本就没有可以完成的题！");
			return ResponseEntity.ok("根本就没有可以完成的题！");
		}
		logger.info("即将更新problem[id={}, deadline={}]状态", problem.getId(), problem.getDeadline());
		problem.setStatus("1");
		problem.setProblemNo(request.getUrl());
		problemService.updateByPrimaryKey(problem);
		logger.info("更新完毕,即将写入日志");

		TaskActivityModel activity = cfActivityBuilder.buildComplete(request.getUsername(), "", request.getType(),
				request.getUrl(), problem.getDeadline());
		int rt = taskActivityService.create(activity);
		logger.info("写入日志完毕, id={}", rt);
		return ResponseEntity.ok(problem);
	}

}
