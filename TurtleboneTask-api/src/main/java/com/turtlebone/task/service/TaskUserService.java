
package com.turtlebone.task.service;

import com.turtlebone.task.model.TaskUserModel;

public interface TaskUserService{
	
	public int create(TaskUserModel taskUserModel);
	
	public int createSelective(TaskUserModel taskUserModel);
	
	public TaskUserModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(TaskUserModel taskUserModel);
	
	public int updateByPrimaryKeySelective(TaskUserModel taskUserModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(TaskUserModel taskUserModel);
	
}