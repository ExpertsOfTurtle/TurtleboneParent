
package com.turtlebone.codeforces.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.codeforces.entity.CFProblem;
import com.turtlebone.codeforces.repository.CFProblemRepository;
import com.turtlebone.codeforces.model.CFProblemModel;
import com.turtlebone.codeforces.service.CFProblemService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class CFProblemServiceImpl implements CFProblemService {

	@Autowired
	private CFProblemRepository cFProblemRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cFProblemRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public CFProblemModel findByPrimaryKey(Long id) {
		CFProblem cFProblem = cFProblemRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(cFProblem, CFProblemModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(CFProblemModel cFProblemModel) {
		return cFProblemRepo.updateByPrimaryKey(BeanCopyUtils.map(cFProblemModel, CFProblem.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(CFProblemModel cFProblemModel) {
		return cFProblemRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(cFProblemModel, CFProblem.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(CFProblemModel cFProblemModel) {
		return cFProblemRepo.insert(BeanCopyUtils.map(cFProblemModel, CFProblem.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(CFProblemModel cFProblemModel) {
		return cFProblemRepo.insertSelective(BeanCopyUtils.map(cFProblemModel, CFProblem.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(CFProblemModel cFProblemModel) {
		return cFProblemRepo.selectCount(BeanCopyUtils.map(cFProblemModel, CFProblem.class));
	}



}
