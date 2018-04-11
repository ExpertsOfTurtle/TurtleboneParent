
package com.turtlebone.codeforces.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.codeforces.entity.CFTask;
import com.turtlebone.codeforces.repository.CFTaskRepository;
import com.turtlebone.codeforces.model.CFTaskModel;
import com.turtlebone.codeforces.service.CFTaskService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class CFTaskServiceImpl implements CFTaskService {


	@Autowired
	private CFTaskRepository cFTaskRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return cFTaskRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public CFTaskModel findByPrimaryKey(Integer id) {
		CFTask cFTask = cFTaskRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(cFTask, CFTaskModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(CFTaskModel cFTaskModel) {
		return cFTaskRepo.updateByPrimaryKey(BeanCopyUtils.map(cFTaskModel, CFTask.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(CFTaskModel cFTaskModel) {
		return cFTaskRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(cFTaskModel, CFTask.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(CFTaskModel cFTaskModel) {
		return cFTaskRepo.insert(BeanCopyUtils.map(cFTaskModel, CFTask.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(CFTaskModel cFTaskModel) {
		return cFTaskRepo.insertSelective(BeanCopyUtils.map(cFTaskModel, CFTask.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(CFTaskModel cFTaskModel) {
		return cFTaskRepo.selectCount(BeanCopyUtils.map(cFTaskModel, CFTask.class));
	}


	@Override
	public void completeTask(String username, int count) {
		List<CFTask> list = cFTaskRepo.selectForCompleteTask(username);
		for (CFTask task : list) {
			int finish = task.getFinish();
			int amount = task.getAmount();
			int rest = amount - finish;
			if (rest >= count) {
				task.setFinish(finish + count);
				task.setUpdatetime(null);
				cFTaskRepo.updateByPrimaryKeySelective(task);
				break;
			} else {
				task.setFinish(amount);
				task.setUpdatetime(null);
				cFTaskRepo.updateByPrimaryKeySelective(task);
				count -= rest;
			}
		}
	}



}
