package com.turtlebone.codeforces.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.turtlebone.codeforces.bean.UserResult;
import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.entity.CFSubmission;
import com.turtlebone.codeforces.mapper.CFUserMapper;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.repository.CFSubmissionRepository;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.statistics.bean.StatisticsObject;
import com.turtlebone.core.statistics.bean.StatisticsResult;
import com.turtlebone.core.statistics.service.DataStatisticsUtil;
import com.turtlebone.core.statistics.service.FilterConfig;
import com.turtlebone.core.statistics.service.FilterCriteria;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;

@Service
public class WeeklyTaskServiceImpl implements WeeklyTaskService {
	private static Logger logger = LoggerFactory.getLogger(WeeklyTaskServiceImpl.class);
	
	@Autowired
	private CFSubmissionRepository cFSubmissionRepo;
	
	@Override
	public WeeklySummary queryStatus(String from, String to) {
		WeeklySummary weeklySummary = null;
		StatisticsResult result = null;
		to  += " 23:59:59";
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("from", from);
		queryMap.put("to", to);
		List<CFSubmission> solvedList = cFSubmissionRepo.querySolved(queryMap);
		List<CFSubmission> list = cFSubmissionRepo.selectByCondition(queryMap);
		
		FilterConfig filterConfig = new FilterConfig();
		filterConfig.setFromDate(from);
		filterConfig.setToDate(to);
		filterConfig.setSumBy("submittime");
		filterConfig.setSumByType("DAY");
		filterConfig.setSeparateBy("username");
		List<FilterCriteria> filterCriteria = new ArrayList<>();
		filterCriteria.add(new FilterCriteria("=", "result", "OK"));
		filterConfig.setFilters(filterCriteria);
		
		try {
			result = DataStatisticsUtil.groupData(solvedList, filterConfig);
			logger.debug(JSON.toJSONString(result));
			weeklySummary = parseResult(result);			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		filterCriteria = new ArrayList<>();
		filterCriteria.add(new FilterCriteria("!=", "result", "OK"));
		filterConfig.setFilters(filterCriteria);
		try {
			result = DataStatisticsUtil.groupData(list, filterConfig);
			logger.debug(JSON.toJSONString(result));
			parseFailResult(weeklySummary, result);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		parseDaySolve(weeklySummary);
		
		//每個用戶添加額外信息，如稱號，提交記錄列表
		parseUser(weeklySummary, from, to);
		
		return weeklySummary;
	}
	
	@Override
	public WeeklySummary queryWeeklyStatus() {
		String lastMonday = DateUtil.getLastMonday();
		String lastSunday = DateUtil.getLastSunday();
		return queryStatus(lastMonday, lastSunday);
	}

	private WeeklySummary parseResult(StatisticsResult input) {
		WeeklySummary result = new WeeklySummary();
		result.setLabels(input.getLabels());
		List<UserResult> list = new ArrayList<>();
		for (StatisticsObject so : input.getList()) {
			UserResult userResult = new UserResult();
			userResult.setUsername(so.getLabel());
			userResult.setProblemSolved(so.getTotal());
			userResult.setDailySolved(so.getData());
			list.add(userResult);
		}
		result.setList(list);
		return result;
	}
	private void parseDaySolve(WeeklySummary weeklySummary) {
		if (weeklySummary == null) {
			logger.warn("weeklySummary is null");
			return;
		}
		for (UserResult userResult : weeklySummary.getList()) {
			int cnt = 0;
			List<Integer> list = userResult.getDailySolved();
			for (int i = 0; i < list.size(); i++) {
				int v = list.get(i);
				if (v > 0) {
					cnt++;
				}
			}
			userResult.setDaySolved(cnt);
		}
	}
	private void parseFailResult(WeeklySummary weeklySummary, StatisticsResult input) {
		int n = input.getLabels().size();
		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			data.add(0);
		}
		for (UserResult userResult : weeklySummary.getList()) {
			userResult.setFailSubmission(0);
			userResult.setDailyFailed(data);
		}
		
		for (StatisticsObject so : input.getList()) {
			for (UserResult userResult : weeklySummary.getList()) {
				if (userResult.getUsername().equals(so.getLabel())) {
					userResult.setDailyFailed(so.getData());
					userResult.setFailSubmission(so.getTotal());
				}
			}
		}
	}
	private void parseUser(WeeklySummary weeklySummary, String from, String to) {
		for (UserResult userResult : weeklySummary.getList()) {
			parseUser(userResult, from, to);
		}
	}
	private void parseUser(UserResult userResult, String from, String to) {
		String username = userResult.getUsername();
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("from", from);
		map.put("to", to);
		List<CFSubmission> list = cFSubmissionRepo.selectByCondition(map);
		List<CFSubmissionModel> submissionList = BeanCopyUtils.mapList(list, CFSubmissionModel.class);
		userResult.setSubmission(submissionList);
		
		int problemSolve = userResult.getProblemSolved();
		int failed = userResult.getFailSubmission();
		String nickName = "";
		boolean solveGood = problemSolve >= getNeedSolve(userResult.getUsername());
		if (solveGood && failed <= problemSolve) {
			nickName = "乖巧机智";
		} else if (solveGood) {
			nickName = "勤奋努力";
		} else if (problemSolve == 0 && failed > 0) {
			nickName = "笨笨笨";
		} else if (problemSolve == 0 && failed == 0) {
			nickName = "懒懒懒";
		} else {
			nickName = "差少少乖巧";
		}
		
		if ("scorpiowf".equalsIgnoreCase(userResult.getUsername())) {
			nickName += "小王子";
		} else {
			nickName += "小公举";
		}
		userResult.setNickName(nickName);
	}
	private int getNeedSolve(String name) {
		if ("scorpiowf".equalsIgnoreCase(name)) {
			return 5;
		} else {
			return 4;
		}
	}
}
