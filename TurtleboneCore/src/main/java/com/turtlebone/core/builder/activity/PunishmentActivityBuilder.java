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
public class PunishmentActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(PunishmentActivityBuilder.class);
	
	public ActivityModel build(String username, String datetime, PunishType type,
			Integer number, String deadline) {
		logger.debug("[PUNISH]username={},type={},count={},deadline={}", 
				username, type.getDescription(), number, deadline);
		ActivityModel model = new ActivityModel();
		model.setUsername(username);
		if (StringUtil.isEmpty(datetime)) {
			model.setDatetime(DateUtil.getDateTime());
		} else {
			model.setDatetime(datetime);
		}
		model.setType(ActivityType.SUDOKU.name());
		model.setResult1((long)number);
		model.setStrresult1(type.getDescription());
		model.setStrresult2(deadline);
		
		String description = String.format("[%s]被惩罚[%s],数量=%d,deadline=%s", 
				username, type.getDescription(), number, deadline);
		model.setDescription(description);
		logger.info(description);
		return model;
	}
}
