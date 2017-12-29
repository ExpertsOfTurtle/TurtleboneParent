package com.turtlebone.codeforces.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.turtlebone.codeforces.bean.UserResult;
import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.entity.CFSubmission;
import com.turtlebone.codeforces.mapper.CFUserMapper;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.repository.CFSubmissionRepository;
import com.turtlebone.codeforces.service.CFSettlementService;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.core.util.StringUtil;

@Service
public class CFSettlementServiceImpl implements CFSettlementService {
	private static Logger logger = LoggerFactory.getLogger(CFSettlementServiceImpl.class);
	
	@Autowired
	private WeeklyTaskService weeklyTaskService;
	@Autowired
	private CFSubmissionRepository cFSubmissionRepo;
	
	@Override
	public String calculate(String from, String to) {
		if (StringUtil.isEmpty(from) || StringUtil.isEmpty(to)) {
			from = DateUtil.getLastMonday();
			to = DateUtil.getLastSunday();
		}
		WeeklySummary weeklySummary = weeklyTaskService.queryStatus(from, to);
		for (UserResult userResult : weeklySummary.getList()) {
			List<CFSubmissionModel> list = userResult.getSubmission();
			for (CFSubmissionModel submission : list) {
				if ("OK".equals(submission.getResult()) && submission.getStatus() == 0) {
					String username = CFUserMapper.getTurtleName(submission.getUsername());
					if (doCompleteProblem(submission.getContestid(), submission.getProblemindex(), username)) {
						submission.setStatus(1);	//标记为已经处理过
						CFSubmission sub = BeanCopyUtils.map(submission, CFSubmission.class);
						cFSubmissionRepo.updateByPrimaryKeySelective(sub);
					}
				}
			}
		}
		return null;
	}
	
	private boolean doCompleteProblem(Integer contestId, String problemIndex, String username) {
		String url = "http://111.230.249.171:8080/task/CF/complete";
		String paramUrl = String.format("http://codeforces.com/problemset/problem/%d/%s", 
				contestId, problemIndex);
		JSONObject req = new JSONObject();
		req.put("username", username);
		req.put("type", "B");
		req.put("url", paramUrl);
		try {
			SendHTTPUtil.callApiServer(url, "POST", req.toJSONString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
