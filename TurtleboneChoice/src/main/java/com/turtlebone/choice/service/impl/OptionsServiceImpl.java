
package com.turtlebone.choice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.turtlebone.choice.entity.Options;
import com.turtlebone.choice.repository.OptionsRepository;
import com.turtlebone.choice.model.OptionsModel;
import com.turtlebone.choice.service.OptionsService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class OptionsServiceImpl implements OptionsService {


	@Autowired
	private OptionsRepository optionsRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return optionsRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public OptionsModel findByPrimaryKey(Integer id) {
		Options options = optionsRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(options, OptionsModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(OptionsModel optionsModel) {
		return optionsRepo.updateByPrimaryKey(BeanCopyUtils.map(optionsModel, Options.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(OptionsModel optionsModel) {
		return optionsRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(optionsModel, Options.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(OptionsModel optionsModel) {
		return optionsRepo.insert(BeanCopyUtils.map(optionsModel, Options.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(OptionsModel optionsModel) {
		return optionsRepo.insertSelective(BeanCopyUtils.map(optionsModel, Options.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(OptionsModel optionsModel) {
		return optionsRepo.selectCount(BeanCopyUtils.map(optionsModel, Options.class));
	}


	@Override
	public int batchInsert(List<OptionsModel> list) {
		List<Options> li = BeanCopyUtils.mapList(list, Options.class);
		return optionsRepo.batchInsert(li);
	}


	@Override
	public List<OptionsModel> selectAll() {
		List<Options> list = optionsRepo.selectAll();
		return BeanCopyUtils.mapList(list, OptionsModel.class);
	}


	@Override
	public int deleteByGroupId(Integer groupId) {
		return optionsRepo.deleteByGroupId(groupId);
	}


	@Override
	public List<OptionsModel> selectByGroupId(Integer groupId) {
		Options options = new Options();
		options.setGroupid(groupId);
		List<Options> list = optionsRepo.selectPage(options, new PageRequest(0, 100));
		return BeanCopyUtils.mapList(list, OptionsModel.class);
	}


	@Override
	public List<OptionsModel> selectByGroupId(List<Integer> groupIdList) {
		List<Options> list = optionsRepo.selectByGroupId(groupIdList);
		return BeanCopyUtils.mapList(list, OptionsModel.class);
	}
}
