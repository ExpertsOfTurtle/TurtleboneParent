
package com.turtlebone.contract.service;

import com.turtlebone.contract.model.ContractModel;
import java.util.Date;
import java.util.List;

public interface ContractService{
	
	public int create(ContractModel contractModel);
	
	public int createSelective(ContractModel contractModel);
	
	public ContractModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(ContractModel contractModel);
	
	public int updateByPrimaryKeySelective(ContractModel contractModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(ContractModel contractModel);

	public List<ContractModel> selectAll();
}