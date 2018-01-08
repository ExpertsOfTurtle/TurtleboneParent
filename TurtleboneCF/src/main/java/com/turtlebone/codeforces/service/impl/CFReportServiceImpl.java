package com.turtlebone.codeforces.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.service.CFReportService;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.service.EmailService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;
import com.turtlebone.core.velocity.VelocityGenerator;

@Service
public class CFReportServiceImpl implements CFReportService {
	private static Logger logger = LoggerFactory.getLogger(CFReportService.class);
	@Autowired
	private WeeklyTaskService weeklyTaskService;
	@Autowired
	private EmailService emailService;
	@Override
	public String generateWeeklyReport(String from, String to) {
		WeeklySummary result = null;
		if (StringUtil.isEmpty(from) || StringUtil.isEmpty(to)) {
			from = DateUtil.getLastMonday();
			to = DateUtil.getLastSunday();
		}
		result = weeklyTaskService.queryStatus(from, to);
		
		Map<String, Object> model = new HashMap<>();
		model.put("weeklySummary", result);
		model.put("from", from);
		model.put("to", to);
		String html = VelocityGenerator.getVMResult("velocity/cf/email/weekly.vm", model);
		logger.debug("weekly.vm, length={}", html.length());
		List<String> addressList = new ArrayList<>();
		
		addressList.add("fengrenchang86@vip.qq.com");
//		addressList.add("danny01.feng@vipshop.com");
//		addressList.add("873847677@qq.com");
		
		String rs = emailService.sendEmail(addressList, "Codeforces周报", html, "Turtlebone");
		for (String addr : addressList) {
			logger.info("Send email to {}, result:{}", addr, rs);
		}
		
		return html;
	}
}
