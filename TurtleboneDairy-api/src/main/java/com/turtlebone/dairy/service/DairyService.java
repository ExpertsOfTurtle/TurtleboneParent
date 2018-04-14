
package com.turtlebone.dairy.service;

import com.turtlebone.dairy.model.DairyModel;
import java.util.Date;
import java.util.List;

public interface DairyService{
	
	public int create(DairyModel dairyModel);
	
	public int createSelective(DairyModel dairyModel);
	
	public DairyModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(DairyModel dairyModel);
	
	public int updateByPrimaryKeySelective(DairyModel dairyModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(DairyModel dairyModel);
	
	public List<DairyModel> filter(DairyModel dairyModel, Integer pageNumber, Integer pageSize);
	
}