
package com.turtlebone.locker.service;

import com.turtlebone.locker.model.LockerModel;
import java.util.Date;
import java.util.List;

public interface LockerService{
	
	public int create(LockerModel lockerModel);
	
	public int createSelective(LockerModel lockerModel);
	
	public LockerModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(LockerModel lockerModel);
	
	public int updateByPrimaryKeySelective(LockerModel lockerModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(LockerModel lockerModel);
	
	public List<LockerModel> selectByCondition(String name, String category, String location);
}