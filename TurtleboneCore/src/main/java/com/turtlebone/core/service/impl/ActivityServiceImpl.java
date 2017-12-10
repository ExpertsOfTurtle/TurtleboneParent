
package com.turtlebone.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.core.entity.Activity;
import com.turtlebone.core.repository.ActivityRepository;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.service.ActivityService;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.StringUtil;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository activityRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return activityRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public ActivityModel findByPrimaryKey(Integer id) {
		Activity activity = activityRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(activity, ActivityModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(ActivityModel activityModel) {
		return activityRepo.updateByPrimaryKey(BeanCopyUtils.map(activityModel, Activity.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(ActivityModel activityModel) {
		return activityRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(activityModel, Activity.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(ActivityModel activityModel) {
		Activity activity = BeanCopyUtils.map(activityModel, Activity.class);
		activityRepo.insert(activity);
		activityModel.setIdactivity(activity.getIdactivity());
		return activity.getIdactivity();
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(ActivityModel activityModel) {
		return activityRepo.insertSelective(BeanCopyUtils.map(activityModel, Activity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(ActivityModel activityModel) {
		return activityRepo.selectCount(BeanCopyUtils.map(activityModel, Activity.class));
	}


	@Override
	public List<ActivityModel> selectByCondition(String username, String type, String from, String to, Integer pageSize, Integer offset) {
		Map<String, Object> map = new HashMap<>();
		if (!StringUtil.isEmpty(username)) {
			map.put("username", username);
		}
		if (!StringUtil.isEmpty(type)) {
			map.put("type", type);
		}
		if (!StringUtil.isEmpty(from) && !StringUtil.isEmpty(to)) {
			map.put("from", from);
			map.put("to", to);
		}
		if (pageSize != null && offset != null) {
			map.put("offset", offset);
			map.put("pageSize", pageSize);
		} else {
			map.put("offset", 0);
			map.put("pageSize", 0);
		}
		List<Activity> list = activityRepo.selectByCondition(map);
		return BeanCopyUtils.mapList(list, ActivityModel.class);
	}

}
