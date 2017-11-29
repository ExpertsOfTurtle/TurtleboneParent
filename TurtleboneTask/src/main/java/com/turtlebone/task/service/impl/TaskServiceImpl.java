
package com.turtlebone.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.task.entity.Task;
import com.turtlebone.task.model.TaskModel;
import com.turtlebone.task.repository.TaskRepository;
import com.turtlebone.task.service.TaskService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return taskRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public TaskModel findByPrimaryKey(Integer id) {
		Task task = taskRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(task, TaskModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(TaskModel taskModel) {
		return taskRepo.updateByPrimaryKey(BeanCopyUtils.map(taskModel, Task.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(TaskModel taskModel) {
		return taskRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(taskModel, Task.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(TaskModel taskModel) {
		Task task = BeanCopyUtils.map(taskModel, Task.class);
		taskRepo.insert(task);
		taskModel.setId(task.getId());
		return task.getId();
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(TaskModel taskModel) {
		return taskRepo.insertSelective(BeanCopyUtils.map(taskModel, Task.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(TaskModel taskModel) {
		return taskRepo.selectCount(BeanCopyUtils.map(taskModel, Task.class));
	}


	@Override
	public List<TaskModel> selectPage(String creator, String type, String owner, Integer status, String from, String to) {
		Map<String, Object> map = new HashMap<>();
		map.put("creator", creator);
		map.put("type", type);
		map.put("owner", owner);
		map.put("status", status);
		map.put("from", from);
		map.put("to", to);
		List<Task> list = taskRepo.selectPage(map);
		return BeanCopyUtils.mapList(list, TaskModel.class);
	}



}
