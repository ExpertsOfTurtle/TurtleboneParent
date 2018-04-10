
package com.turtlebone.task.service;

import java.util.List;
import java.util.Map;

import com.turtlebone.task.model.TaskModel;
import com.turtlebone.task.model.TaskResultModel;

public interface TaskService{
	
	public int create(TaskModel taskModel);
	
	public int createSelective(TaskModel taskModel);
	
	public TaskModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(TaskModel taskModel);
	
	public int updateByPrimaryKeySelective(TaskModel taskModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(TaskModel taskModel);
	
	public List<TaskResultModel> selectPage(String creator, Integer type, String owner, Integer status, String from, String to,
			Integer pageNumber, Integer pageSize);
	
}