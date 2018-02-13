package com.turtlebone.locker.repository;

import com.turtlebone.locker.entity.Locker;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Locker selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Locker locker);

    int updateByPrimaryKeySelective(Locker locker);

  	int insert(Locker locker);
  	
	int insertSelective(Locker locker);


    int selectCount(Locker locker);

    List<Locker> selectPage(@Param("locker") Locker locker, @Param("pageable") Pageable pageable);
	
    List<Locker> selectByCondition(Map map);
}