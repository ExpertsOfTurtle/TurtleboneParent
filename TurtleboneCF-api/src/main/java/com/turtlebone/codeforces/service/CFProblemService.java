
package com.turtlebone.codeforces.service;

import java.util.List;

import com.turtlebone.codeforces.model.CFProblemModel;

public interface CFProblemService{
	
	public int create(CFProblemModel cFProblemModel);
	
	public int createSelective(CFProblemModel cFProblemModel);
	
	public CFProblemModel findByPrimaryKey(Long id);
	
	public int updateByPrimaryKey(CFProblemModel cFProblemModel);
	
	public int updateByPrimaryKeySelective(CFProblemModel cFProblemModel);
	
	public int deleteByPrimaryKey(Long id);
	

	public int selectCount(CFProblemModel cFProblemModel);
	
	public List<CFProblemModel> selectByCondition(String status, String type);
	
}