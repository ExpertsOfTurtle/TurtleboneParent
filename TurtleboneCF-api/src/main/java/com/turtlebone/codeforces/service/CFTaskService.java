
package com.turtlebone.codeforces.service;

import com.turtlebone.codeforces.bean.FilterCFTaskRequest;
import com.turtlebone.codeforces.model.CFTaskModel;
import java.util.Date;
import java.util.List;

public interface CFTaskService{
	
	public int create(CFTaskModel cFTaskModel);
	
	public int createSelective(CFTaskModel cFTaskModel);
	
	public CFTaskModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(CFTaskModel cFTaskModel);
	
	public int updateByPrimaryKeySelective(CFTaskModel cFTaskModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(CFTaskModel cFTaskModel);
	
	public void completeTask(String username, int count);
	
	public List<CFTaskModel> filter(FilterCFTaskRequest filter);
	public int filterCount(FilterCFTaskRequest filter);
}