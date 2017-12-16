package com.turtlebone.task.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.turtlebone.core.enums.ActivityType;
import com.turtlebone.task.model.TaskActivityModel;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;
@Component
public class CFActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(CFActivityBuilder.class);
	
	public TaskActivityModel buildComplete(String username, String datetime, String type,
			String url, String deadline) {
		logger.debug("[CF]username={},type={},url={},deadline={}", 
				username, type, url, deadline);
		TaskActivityModel model = new TaskActivityModel();
		model.setUsername(username);
		if (StringUtil.isEmpty(datetime)) {
			model.setDatetime(DateUtil.getDateTime());
		} else {
			model.setDatetime(datetime);
		}
		model.setType(ActivityType.COMPLETECF.name());
		
		model.setStrresult1(type);
		model.setStrresult2(deadline);
		model.setStrresult3(url);
		
		String description = String.format("[%s]完成了类型是[%s]的题目[%s],deadline=%s", 
				username, type, url, deadline);
		model.setDescription(description);
		logger.info(description);
		return model;
	}
	
	public TaskActivityModel buildInsert(String username, String datetime, String type, 
			Integer count, String deadline, String remark) {
		logger.debug("[CF]username={},type={},count={},deadline={}, remark={}", 
				username, type, count, deadline, remark);
		TaskActivityModel model = new TaskActivityModel();
		model.setUsername(username);
		if (StringUtil.isEmpty(datetime)) {
			model.setDatetime(DateUtil.getDateTime());
		} else {
			model.setDatetime(datetime);
		}
		model.setType(ActivityType.PUNISH.name());
		
		model.setResult1((long)count);
		model.setStrresult1(remark);
		model.setStrresult2(deadline);
		model.setStrresult3(type);
		
		String description = String.format("[%s]被插入了[類型=%s]的题目[數量=%s],deadline=%s", 
				username, type, count, deadline);
		model.setDescription(description);
		logger.info(description);
		return model;
	}
}
