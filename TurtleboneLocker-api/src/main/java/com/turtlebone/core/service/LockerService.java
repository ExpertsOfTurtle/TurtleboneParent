
package com.turtlebone.core.service;

import com.turtlebone.core.model.LockerModel;
import java.util.Date;

public interface LockerService{
	
	public int create(LockerModel lockerModel);
	
	public int createSelective(LockerModel lockerModel);
	
	public LockerModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(LockerModel lockerModel);
	
	public int updateByPrimaryKeySelective(LockerModel lockerModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(LockerModel lockerModel);
	
}