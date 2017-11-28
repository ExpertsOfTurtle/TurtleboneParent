
package com.turtlebone.contract.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.contract.entity.ContractActivity;
import com.turtlebone.contract.repository.ContractActivityRepository;
import com.turtlebone.contract.model.ContractActivityModel;
import com.turtlebone.contract.service.ContractActivityService;
import com.turtlebone.contract.util.BeanCopyUtils;

@Service
public class ContractActivityServiceImpl implements ContractActivityService {

	@Autowired
	private ContractActivityRepository contractActivityRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return contractActivityRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public ContractActivityModel findByPrimaryKey(Integer id) {
		ContractActivity contractActivity = contractActivityRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(contractActivity, ContractActivityModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(ContractActivityModel contractActivityModel) {
		return contractActivityRepo.updateByPrimaryKey(BeanCopyUtils.map(contractActivityModel, ContractActivity.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(ContractActivityModel contractActivityModel) {
		return contractActivityRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(contractActivityModel, ContractActivity.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(ContractActivityModel contractActivityModel) {
		return contractActivityRepo.insert(BeanCopyUtils.map(contractActivityModel, ContractActivity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(ContractActivityModel contractActivityModel) {
		return contractActivityRepo.insertSelective(BeanCopyUtils.map(contractActivityModel, ContractActivity.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(ContractActivityModel contractActivityModel) {
		return contractActivityRepo.selectCount(BeanCopyUtils.map(contractActivityModel, ContractActivity.class));
	}


	@Override
	public ContractActivityModel selectSignActivity(Integer contrarctId, String username) {
		Map<String, Object> map = new HashMap<>();
		map.put("contractid", contrarctId);
		map.put("username", username);
		return BeanCopyUtils.map(contractActivityRepo.selectSignActivity(map), ContractActivityModel.class);
	}


	@Override
	public List<ContractActivityModel> selectBulkSignActivity(Integer contrarctId, String username) {
		Map<String, Object> map = new HashMap<>();
		map.put("contractid", contrarctId);
		map.put("username", username);
		List<ContractActivity> list = contractActivityRepo.selectBulkSignActivity(map);
		return BeanCopyUtils.mapList(list, ContractActivityModel.class);
	}



}
