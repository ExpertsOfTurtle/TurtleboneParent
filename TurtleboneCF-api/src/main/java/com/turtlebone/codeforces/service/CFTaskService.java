
package com.turtlebone.codeforces.service;

import com.turtlebone.codeforces.model.CFTaskModel;
import java.util.Date;

public interface CFTaskService{
	
	public int create(CFTaskModel cFTaskModel);
	
	public int createSelective(CFTaskModel cFTaskModel);
	
	public CFTaskModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(CFTaskModel cFTaskModel);
	
	public int updateByPrimaryKeySelective(CFTaskModel cFTaskModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(CFTaskModel cFTaskModel);
	
	public void completeTask(String username, int count);
	
}