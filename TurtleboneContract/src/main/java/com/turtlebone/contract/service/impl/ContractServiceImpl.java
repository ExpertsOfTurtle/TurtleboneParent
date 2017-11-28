
package com.turtlebone.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.contract.entity.Contract;
import com.turtlebone.contract.repository.ContractRepository;
import com.turtlebone.contract.model.ContractModel;
import com.turtlebone.contract.service.ContractService;
import com.turtlebone.contract.util.BeanCopyUtils;

@Service
public class ContractServiceImpl implements ContractService {


	@Autowired
	private ContractRepository contractRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return contractRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public ContractModel findByPrimaryKey(Integer id) {
		Contract contract = contractRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(contract, ContractModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(ContractModel contractModel) {
		return contractRepo.updateByPrimaryKey(BeanCopyUtils.map(contractModel, Contract.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(ContractModel contractModel) {
		return contractRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(contractModel, Contract.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(ContractModel contractModel) {
		Contract contract = BeanCopyUtils.map(contractModel, Contract.class);
		contractRepo.insert(contract);
		contractModel.setId(contract.getId());
		return contract.getId();
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(ContractModel contractModel) {
		return contractRepo.insertSelective(BeanCopyUtils.map(contractModel, Contract.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(ContractModel contractModel) {
		return contractRepo.selectCount(BeanCopyUtils.map(contractModel, Contract.class));
	}


	@Override
	public List<ContractModel> selectAll() {
		List<Contract> list = contractRepo.selectAll();
		return BeanCopyUtils.mapList(list, ContractModel.class);
	}



}
