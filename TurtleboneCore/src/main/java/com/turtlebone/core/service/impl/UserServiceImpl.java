
package com.turtlebone.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.core.entity.User;
import com.turtlebone.core.repository.UserRepository;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public UserModel findByPrimaryKey(Integer id) {
		User user = userRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(user, UserModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(UserModel userModel) {
		return userRepo.updateByPrimaryKey(BeanCopyUtils.map(userModel, User.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(UserModel userModel) {
		return userRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(userModel, User.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(UserModel userModel) {
		return userRepo.insert(BeanCopyUtils.map(userModel, User.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(UserModel userModel) {
		return userRepo.insertSelective(BeanCopyUtils.map(userModel, User.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(UserModel userModel) {
		return userRepo.selectCount(BeanCopyUtils.map(userModel, User.class));
	}


	@Override
	public UserModel selectByUsername(String username) {
		User user = userRepo.selectByUsername(username);
		return BeanCopyUtils.map(user, UserModel.class);
	}



}
