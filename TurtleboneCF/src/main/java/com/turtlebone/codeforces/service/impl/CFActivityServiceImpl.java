
package com.turtlebone.codeforces.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.codeforces.entity.CFActivity;
import com.turtlebone.codeforces.repository.CFActivityRepository;
import com.turtlebone.codeforces.model.CFActivityModel;
import com.turtlebone.codeforces.service.CFActivityService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class CFActivityServiceImpl implements CFActivityService {


	@Autowired
	private CFActivityRepository cFActivityRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return cFActivityRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public CFActivityModel findByPrimaryKey(Integer id) {
		CFActivity cFActivity = cFActivityRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(cFActivity, CFActivityModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(CFActivityModel cFActivityModel) {
		return cFActivityRepo.updateByPrimaryKey(BeanCopyUtils.map(cFActivityModel, CFActivity.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(CFActivityModel cFActivityModel) {
		return cFActivityRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(cFActivityModel, CFActivity.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(CFActivityModel cFActivityModel) {
		return cFActivityRepo.insert(BeanCopyUtils.map(cFActivityModel, CFActivity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(CFActivityModel cFActivityModel) {
		return cFActivityRepo.insertSelective(BeanCopyUtils.map(cFActivityModel, CFActivity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(CFActivityModel cFActivityModel) {
		return cFActivityRepo.selectCount(BeanCopyUtils.map(cFActivityModel, CFActivity.class));
	}



}
