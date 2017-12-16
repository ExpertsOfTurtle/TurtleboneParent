
package com.turtlebone.task.service;

import com.turtlebone.task.model.TaskActivityModel;

public interface TaskActivityService{
	
	public int create(TaskActivityModel taskActivityModel);
	
	public int createSelective(TaskActivityModel taskActivityModel);
	
	public TaskActivityModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(TaskActivityModel taskActivityModel);
	
	public int updateByPrimaryKeySelective(TaskActivityModel taskActivityModel);
	
	public int deleteByPrimaryKey(Integer id);
	
	public int selectCount(TaskActivityModel taskActivityModel);
	
}