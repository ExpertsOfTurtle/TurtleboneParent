
package com.turtlebone.dream.service;

import java.util.List;

import com.turtlebone.dream.model.ActivityModel;

public interface DreamService{
	
	public int create(ActivityModel activityModel);
	
	public int createSelective(ActivityModel activityModel);
	
	public ActivityModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(ActivityModel activityModel);
	
	public int updateByPrimaryKeySelective(ActivityModel activityModel);
	
	public int deleteByPrimaryKey(Integer id);
	
	public int selectCount(ActivityModel activityModel);
	
	public List<ActivityModel> selectMyDream(String username, Integer size, Integer pageNumber);
}