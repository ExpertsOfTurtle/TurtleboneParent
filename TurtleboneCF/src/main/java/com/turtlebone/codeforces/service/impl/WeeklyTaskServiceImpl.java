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
import com.turtlebone.codeforces.repository.CFSubmissionRepository;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.statistics.bean.StatisticsObject;
import com.turtlebone.core.statistics.bean.StatisticsResult;
import com.turtlebone.core.statistics.service.DataStatisticsUtil;
import com.turtlebone.core.statistics.service.FilterConfig;
import com.turtlebone.core.statistics.service.FilterCriteria;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;

@Service
public class WeeklyTaskServiceImpl implements WeeklyTaskService {
	private static Logger logger = LoggerFactory.getLogger(WeeklyTaskServiceImpl.class);
	
	@Autowired
	private CFSubmissionRepository cFSubmissionRepo;
	
	@Override
	public WeeklySummary queryWeeklyStatus() {
		WeeklySummary weeklySummary = null;
		StatisticsResult result = null;
		String lastMonday = DateUtil.getLastMonday();
		String thisMonday = DateUtil.getThisMonday();
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("from", lastMonday);
		queryMap.put("to", thisMonday);
		List<CFSubmission> list = cFSubmissionRepo.selectByCondition(queryMap);
		
		FilterConfig filterConfig = new FilterConfig();
		filterConfig.setFromDate(lastMonday);
		filterConfig.setToDate(thisMonday);
		filterConfig.setSumBy("submittime");
		filterConfig.setSumByType("DAY");
		filterConfig.setSeparateBy("username");
		List<FilterCriteria> filterCriteria = new ArrayList<>();
		filterCriteria.add(new FilterCriteria("=", "result", "OK"));
		filterConfig.setFilters(filterCriteria);
		
		try {
			result = DataStatisticsUtil.groupData(list, filterConfig);
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
			parseResult(weeklySummary, result);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weeklySummary;
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
	private void parseResult(WeeklySummary weeklySummary, StatisticsResult input) {
		for (StatisticsObject so : input.getList()) {
			for (UserResult userResult : weeklySummary.getList()) {
				if (userResult.getUsername().equals(so.getLabel())) {
					userResult.setDailyFailed(so.getData());
					userResult.setFailSubmission(so.getTotal());
				}
			}
		}
	}
}