
package com.turtlebone.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.task.entity.Problem;
import com.turtlebone.task.repository.ProblemRepository;
import com.turtlebone.task.model.ProblemModel;
import com.turtlebone.task.service.ProblemService;

@Service
public class ProblemServiceImpl implements ProblemService {


	@Autowired
	private ProblemRepository problemRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		return problemRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public ProblemModel findByPrimaryKey(Long id) {
		Problem problem = problemRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(problem, ProblemModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(ProblemModel problemModel) {
		return problemRepo.updateByPrimaryKey(BeanCopyUtils.map(problemModel, Problem.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(ProblemModel problemModel) {
		return problemRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(problemModel, Problem.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(ProblemModel problemModel) {
		return problemRepo.insert(BeanCopyUtils.map(problemModel, Problem.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(ProblemModel problemModel) {
		return problemRepo.insertSelective(BeanCopyUtils.map(problemModel, Problem.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(ProblemModel problemModel) {
		return problemRepo.selectCount(BeanCopyUtils.map(problemModel, Problem.class));
	}


	@Override
	public List<ProblemModel> selectByCondition(String username, String type, 
			String status, String deadlineFrom, String deadlineTo, String order) {
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("type", type);
		map.put("status", status);
		map.put("from", deadlineFrom);
		map.put("to", deadlineTo);
		map.put("order", order);
		List<Problem> list = problemRepo.selectByCondition(map);
		return BeanCopyUtils.mapList(list, ProblemModel.class);
	}


	@Override
	public ProblemModel selectIdOfNextProblem(String username, String type) {
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("type", type);
		Problem problem = problemRepo.selectIdOfNextProblem(map);
		return BeanCopyUtils.map(problem, ProblemModel.class);
	}


	@Override
	public int batchInsert(List<ProblemModel> list) {
		List<Problem> pList = BeanCopyUtils.mapList(list, Problem.class);
		return problemRepo.batchInsert(pList);
	}
}
