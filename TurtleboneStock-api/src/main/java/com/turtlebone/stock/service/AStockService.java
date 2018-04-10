
package com.turtlebone.stock.service;

import com.turtlebone.stock.model.AStockModel;
import java.util.Date;
import java.util.List;

public interface AStockService{
	
	public int create(AStockModel aStockModel);
	
	public int createSelective(AStockModel aStockModel);
	
	public AStockModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(AStockModel aStockModel);
	
	public int updateByPrimaryKeySelective(AStockModel aStockModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(AStockModel aStockModel);
	
	public int batchInsert(List<AStockModel> list);
	
}