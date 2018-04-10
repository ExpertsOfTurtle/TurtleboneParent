
package com.turtlebone.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.stock.entity.AStock;
import com.turtlebone.stock.repository.AStockRepository;
import com.turtlebone.stock.model.AStockModel;
import com.turtlebone.stock.service.AStockService;

@Service
public class AStockServiceImpl implements AStockService {

	@Autowired
	private AStockRepository aStockRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return aStockRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public AStockModel findByPrimaryKey(Integer id) {
		AStock aStock = aStockRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(aStock, AStockModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(AStockModel aStockModel) {
		return aStockRepo.updateByPrimaryKey(BeanCopyUtils.map(aStockModel, AStock.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(AStockModel aStockModel) {
		return aStockRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(aStockModel, AStock.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(AStockModel aStockModel) {
		return aStockRepo.insert(BeanCopyUtils.map(aStockModel, AStock.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(AStockModel aStockModel) {
		return aStockRepo.insertSelective(BeanCopyUtils.map(aStockModel, AStock.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(AStockModel aStockModel) {
		return aStockRepo.selectCount(BeanCopyUtils.map(aStockModel, AStock.class));
	}


	@Override
	public int batchInsert(List<AStockModel> list) {
		List<AStock> stockList = BeanCopyUtils.mapList(list, AStock.class);
		return aStockRepo.batchInsert(stockList);
	}



}
