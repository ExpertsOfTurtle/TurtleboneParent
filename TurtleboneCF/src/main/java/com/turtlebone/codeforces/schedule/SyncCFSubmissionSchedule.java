package com.turtlebone.codeforces.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.turtlebone.codeforces.constants.ICFConstants;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;

@Component
public class SyncCFSubmissionSchedule {
	private static Logger logger = LoggerFactory.getLogger(SyncCFSubmissionSchedule.class);
	
	@Autowired
	private FetchSubmissionsService fetchSubmissionsService;
	@Autowired
	private CFSubmissionService cfSubmissionService;
	
	/**
	 * 每天0:10 觸發
	 */
	@Scheduled(cron="0 10 0 * * ?") 
    public void syncSubmissions() {
		for (String username : ICFConstants.USERLIST) {
			logger.info("Start sync submissions for {}", username);
			List<CFSubmissionModel> list = fetchSubmissionsService.fetchResult(username, 0, 200);
			cfSubmissionService.insert(list);
		}
    }
}
