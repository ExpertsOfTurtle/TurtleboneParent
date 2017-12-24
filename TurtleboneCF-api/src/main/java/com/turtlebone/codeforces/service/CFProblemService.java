
package com.turtlebone.codeforces.service;

import com.turtlebone.codeforces.model.CFProblemModel;

public interface CFProblemService{
	
	public int create(CFProblemModel cFProblemModel);
	
	public int createSelective(CFProblemModel cFProblemModel);
	
	public CFProblemModel findByPrimaryKey(Long id);
	
	public int updateByPrimaryKey(CFProblemModel cFProblemModel);
	
	public int updateByPrimaryKeySelective(CFProblemModel cFProblemModel);
	
	public int deleteByPrimaryKey(Long id);
	

	public int selectCount(CFProblemModel cFProblemModel);
	
}