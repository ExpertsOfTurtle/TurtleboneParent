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
public class DeciderActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(DeciderActivityBuilder.class);
	
	public ActivityModel build(String username, String datetime, Integer groupId,
			String groupName, String optionName, Double probability) {
		logger.debug("[CHOOSE]username={},groupName={},optionName={}", username, groupName, optionName);
		ActivityModel model = new ActivityModel();
		model.setUsername(username);
		if (StringUtil.isEmpty(datetime)) {
			model.setDatetime(DateUtil.getDateTime());
		} else {
			model.setDatetime(datetime);
		}
		model.setType(ActivityType.CHOOSE.name());
		model.setResult1((long)groupId);
		model.setStrresult1(groupName);
		model.setStrresult2(optionName);
		model.setStrresult3(probability.toString());
		
		String description = String.format("[%s]参与命运轮盘[%s],命运选中了[%s],概率是[%.2lf]%%", 
				username, groupName, optionName, probability*100);
		model.setDescription(description);
		logger.info(description);
		return model;
	}
}
