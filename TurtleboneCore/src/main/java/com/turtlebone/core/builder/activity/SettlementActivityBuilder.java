package com.turtlebone.core.builder.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.turtlebone.core.enums.ActivityType;
import com.turtlebone.core.enums.sudoku.SudokuLevel;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;

@Component
public class SettlementActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(SettlementActivityBuilder.class);
	
	public ActivityModel build(String username, String datetime, Integer amount) {
		logger.debug("[SETTLEMENT]username={},amount={}", username, amount);
		ActivityModel model = new ActivityModel();
		model.setUsername(username);
		if (StringUtil.isEmpty(datetime)) {
			model.setDatetime(DateUtil.getDateTime());
		} else {
			model.setDatetime(datetime);
		}
		model.setType(ActivityType.SETTLEMENT.name());
		model.setResult1((long)amount);
		
		String description = String.format("[%s]被结算了,金额是[%d]", 
				username, amount);
		model.setDescription(description);
		logger.info(description);
		return model;
	}
}
