
package com.turtlebone.codeforces.service;

import com.turtlebone.codeforces.model.CFActivityModel;

public interface CFActivityService{
	
	public int create(CFActivityModel cFActivityModel);
	
	public int createSelective(CFActivityModel cFActivityModel);
	
	public CFActivityModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(CFActivityModel cFActivityModel);
	
	public int updateByPrimaryKeySelective(CFActivityModel cFActivityModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(CFActivityModel cFActivityModel);
	
}