package com.turtlebone.task.repository;

import com.turtlebone.task.entity.TaskActivity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskActivityRepository{

  	int deleteByPrimaryKey(Integer id);
	
	TaskActivity selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(TaskActivity taskActivity);

    int updateByPrimaryKeySelective(TaskActivity taskActivity);

  	int insert(TaskActivity taskActivity);
  	
	int insertSelective(TaskActivity taskActivity);


    int selectCount(TaskActivity taskActivity);

    List<TaskActivity> selectPage(@Param("taskActivity") TaskActivity taskActivity, @Param("pageable") Pageable pageable);
	
}