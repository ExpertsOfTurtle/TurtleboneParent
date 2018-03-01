package com.turtlebone.choice.bean;

import java.util.List;

import com.turtlebone.choice.model.OptionGroupModel;
import com.turtlebone.choice.model.OptionsModel;

import lombok.Data;

@Data
public class ChooseInfo {
	private OptionGroupModel group;
	private List<OptionsModel> options;
}
