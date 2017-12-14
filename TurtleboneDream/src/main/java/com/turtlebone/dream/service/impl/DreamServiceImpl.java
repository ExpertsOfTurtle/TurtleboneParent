
package com.turtlebone.dream.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


import com.turtlebone.dream.entity.Activity;
import com.turtlebone.dream.repository.DreamActivityRepository;
import com.turtlebone.dream.service.DreamService;
import com.turtlebone.core.enums.ActivityType;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.StringUtil;
import com.turtlebone.dream.model.ActivityModel;

@Service
public class DreamServiceImpl implements DreamService {

	@Autowired
	private DreamActivityRepository activityRepo;
	

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
	public List<ActivityModel> selectMyDream(String username, Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<>();
		if (!StringUtil.isEmpty(username)) {
			map.put("username", username);
		}
		map.put("type", ActivityType.DREAM.name());
		
		Sort sort = new Sort(Direction.DESC, "idactivity");
		
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);
		
		List<Activity> list = activityRepo.selectMyDream(map, pageable);
		return BeanCopyUtils.mapList(list, ActivityModel.class);
	}

}
