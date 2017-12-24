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
import com.turtlebone.codeforces.bean.WeeklyResult;
import com.turtlebone.codeforces.entity.CFSubmission;
import com.turtlebone.codeforces.mapper.CFUserMapper;
import com.turtlebone.codeforces.repository.CFSubmissionRepository;
import com.turtlebone.codeforces.service.WeeklyTaskService;
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
	public WeeklyResult queryWeeklyStatus(String username) {
		String cfUsername = CFUserMapper.getCFName(username);
		if (StringUtil.isEmpty(cfUsername)) {
			logger.warn("{} is empty!", username);
		}
		String lastMonday = DateUtil.getLastMonday();
		String thisMonday = DateUtil.getThisMonday();
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("username", cfUsername);
		queryMap.put("from", lastMonday);
		queryMap.put("to", thisMonday);
		List<CFSubmission> list = cFSubmissionRepo.selectByCondition(queryMap);
		
		FilterConfig filterConfig = new FilterConfig();
		filterConfig.setFromDate(lastMonday);
		filterConfig.setToDate(thisMonday);
		filterConfig.setSumBy("submittime");
		filterConfig.setSumByType("DAY");
		List<FilterCriteria> filterCriteria = new ArrayList<>();
		filterCriteria.add(new FilterCriteria("=", "result", "OK"));
		filterConfig.setFilters(filterCriteria);
		
		try {
			StatisticsResult result = DataStatisticsUtil.groupData(list, filterConfig);
			System.out.println(JSON.toJSONString(result));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
