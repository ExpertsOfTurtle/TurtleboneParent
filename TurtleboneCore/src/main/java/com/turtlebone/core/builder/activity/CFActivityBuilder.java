package com.turtlebone.core.builder.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.turtlebone.core.enums.ActivityType;
import com.turtlebone.core.enums.punishment.PunishType;
import com.turtlebone.core.enums.sudoku.SudokuLevel;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;
@Component
public class CFActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(CFActivityBuilder.class);
	
	public ActivityModel buildComplete(String username, String datetime, String type,
			String url, String deadline) {
		logger.debug("[CF]username={},type={},url={},deadline={}", 
				username, type, url, deadline);
		ActivityModel model = new ActivityModel();
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
}
