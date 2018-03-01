
package com.turtlebone.choice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.choice.entity.OptionGroup;
import com.turtlebone.choice.repository.OptionGroupRepository;
import com.turtlebone.choice.model.OptionGroupModel;
import com.turtlebone.choice.service.OptionGroupService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class OptionGroupServiceImpl implements OptionGroupService {


	@Autowired
	private OptionGroupRepository optionGroupRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return optionGroupRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public OptionGroupModel findByPrimaryKey(Integer id) {
		OptionGroup optionGroup = optionGroupRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(optionGroup, OptionGroupModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(OptionGroupModel optionGroupModel) {
		return optionGroupRepo.updateByPrimaryKey(BeanCopyUtils.map(optionGroupModel, OptionGroup.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(OptionGroupModel optionGroupModel) {
		return optionGroupRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(optionGroupModel, OptionGroup.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(OptionGroupModel optionGroupModel) {
		OptionGroup group = BeanCopyUtils.map(optionGroupModel, OptionGroup.class);
		optionGroupRepo.insert(group);
		return group.getGroupid();
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(OptionGroupModel optionGroupModel) {
		return optionGroupRepo.insertSelective(BeanCopyUtils.map(optionGroupModel, OptionGroup.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(OptionGroupModel optionGroupModel) {
		return optionGroupRepo.selectCount(BeanCopyUtils.map(optionGroupModel, OptionGroup.class));
	}


	@Override
	public OptionGroupModel selectByName(String groupname) {
		OptionGroup group = optionGroupRepo.selectByName(groupname);
		return BeanCopyUtils.map(group, OptionGroupModel.class);
	}


	@Override
	public List<OptionGroupModel> selectAll() {
		List<OptionGroup> list = optionGroupRepo.selectAll();
		return BeanCopyUtils.mapList(list, OptionGroupModel.class);
	}



}
