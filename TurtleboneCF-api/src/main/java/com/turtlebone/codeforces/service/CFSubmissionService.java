
package com.turtlebone.codeforces.service;

import java.util.List;

import com.turtlebone.codeforces.model.CFSubmissionModel;

public interface CFSubmissionService {

	public int create(CFSubmissionModel cFSubmissionModel);

	public int createSelective(CFSubmissionModel cFSubmissionModel);

	public CFSubmissionModel findByPrimaryKey(Integer id);

	public int updateByPrimaryKey(CFSubmissionModel cFSubmissionModel);

	public int updateByPrimaryKeySelective(CFSubmissionModel cFSubmissionModel);

	public int deleteByPrimaryKey(Integer id);

	public int selectCount(CFSubmissionModel cFSubmissionModel);

	public int insert(List<CFSubmissionModel> list);

	public List<CFSubmissionModel> selectByCondition(List<Integer> idList, String username, String tag, String from,
			String to, Integer pageNumber, Integer pageSize);

	public List<CFSubmissionModel> selectByCondition(CFSubmissionModel filter, Integer pageNumber, Integer pageSize);

	public List<CFSubmissionModel> querySolved(String from, String to);
}