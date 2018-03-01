
package com.turtlebone.choice.service;

import java.util.List;
import com.turtlebone.choice.model.OptionsModel;

public interface OptionsService{
	
	public int create(OptionsModel optionsModel);
	
	public int createSelective(OptionsModel optionsModel);
	
	public OptionsModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(OptionsModel optionsModel);
	
	public int updateByPrimaryKeySelective(OptionsModel optionsModel);
	
	public int deleteByPrimaryKey(Integer id);
	
	public List<OptionsModel> selectAll();

	public int selectCount(OptionsModel optionsModel);

	public int batchInsert(List<OptionsModel> list);
	
	public int deleteByGroupId(Integer groupId);
	
	public List<OptionsModel> selectByGroupId(Integer groupId);
}