
package com.turtlebone.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.task.entity.TaskActivity;
import com.turtlebone.task.repository.TaskActivityRepository;
import com.turtlebone.task.model.TaskActivityModel;
import com.turtlebone.task.service.TaskActivityService;

@Service
public class TaskActivityServiceImpl implements TaskActivityService {

	@Autowired
	private TaskActivityRepository taskActivityRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return taskActivityRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public TaskActivityModel findByPrimaryKey(Integer id) {
		TaskActivity taskActivity = taskActivityRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(taskActivity, TaskActivityModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(TaskActivityModel taskActivityModel) {
		return taskActivityRepo.updateByPrimaryKey(BeanCopyUtils.map(taskActivityModel, TaskActivity.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(TaskActivityModel taskActivityModel) {
		return taskActivityRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(taskActivityModel, TaskActivity.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(TaskActivityModel taskActivityModel) {
		return taskActivityRepo.insert(BeanCopyUtils.map(taskActivityModel, TaskActivity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(TaskActivityModel taskActivityModel) {
		return taskActivityRepo.insertSelective(BeanCopyUtils.map(taskActivityModel, TaskActivity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(TaskActivityModel taskActivityModel) {
		return taskActivityRepo.selectCount(BeanCopyUtils.map(taskActivityModel, TaskActivity.class));
	}



}
