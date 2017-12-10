package com.turtlebone.core.repository;

import com.turtlebone.core.entity.Activity;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Activity selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Activity activity);

    int updateByPrimaryKeySelective(Activity activity);

  	int insert(Activity activity);
  	
	int insertSelective(Activity activity);


    int selectCount(Activity activity);

    List<Activity> selectPage(@Param("activity") Activity activity, @Param("pageable") Pageable pageable);
	
    List<Activity> selectByCondition(Map map);
}