
package com.turtlebone.locker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.locker.entity.Locker;
import com.turtlebone.locker.repository.LockerRepository;
import com.turtlebone.locker.model.LockerModel;
import com.turtlebone.locker.service.LockerService;

@Service
public class LockerServiceImpl implements LockerService {

	@Autowired
	private LockerRepository lockerRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return lockerRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public LockerModel findByPrimaryKey(Integer id) {
		Locker locker = lockerRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(locker, LockerModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(LockerModel lockerModel) {
		return lockerRepo.updateByPrimaryKey(BeanCopyUtils.map(lockerModel, Locker.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(LockerModel lockerModel) {
		return lockerRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(lockerModel, Locker.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(LockerModel lockerModel) {
		Locker locker = BeanCopyUtils.map(lockerModel, Locker.class);
		lockerRepo.insert(locker);
		lockerModel.setId(locker.getId());
		return locker.getId();
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(LockerModel lockerModel) {
		return lockerRepo.insertSelective(BeanCopyUtils.map(lockerModel, Locker.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(LockerModel lockerModel) {
		return lockerRepo.selectCount(BeanCopyUtils.map(lockerModel, Locker.class));
	}



}
