package com.turtlebone.codeforces.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.turtlebone.codeforces.constants.ICFConstants;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFReportService;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;

@Component
public class CFReportSchedule {
	private static Logger logger = LoggerFactory.getLogger(CFReportSchedule.class);
	
	@Autowired
	private FetchSubmissionsService fetchSubmissionsService;
	@Autowired
	private CFSubmissionService cfSubmissionService;
	@Autowired
	private CFReportService cfReportService;
		
	/**
	 * 每周一早上八點，生成上周報告
	 */
	@Scheduled(cron="0 0 8 * * MON") 
    public void weeklyReport() {
		logger.info("開始生成每周CF報告");
		cfReportService.generateWeeklyReport(null, null);
    }
}
