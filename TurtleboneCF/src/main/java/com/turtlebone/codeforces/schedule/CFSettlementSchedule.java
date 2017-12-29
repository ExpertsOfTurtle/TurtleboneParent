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
import com.turtlebone.codeforces.service.CFSettlementService;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.core.util.DateUtil;

@Component
public class CFSettlementSchedule {
	private static Logger logger = LoggerFactory.getLogger(CFSettlementSchedule.class);

	@Autowired
	private CFSettlementService cfSettlementService;

	/**
	 * 每天早上八點，生成上周報告
	 */
	@Scheduled(cron = "0 0 0,1,2,8,10,12,18,20,22,23 * * ?")
	public void weeklyReport() {
		logger.info("開始结算罚题");
		cfSettlementService.calculate(DateUtil.getNDaysLater(-2, "yyyy-MM-dd"),
				DateUtil.getNDaysLater(0, "yyyy-MM-dd"));
	}
}
