package com.turtlebone.task.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.turtlebone.task.entity.TaskUser;

@Repository
public interface TaskUserRepository{

  	int deleteByPrimaryKey(Integer id);
  	
  	int deleteByTaskId(Integer taskId);
	
	TaskUser selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(TaskUser taskUser);

    int updateByPrimaryKeySelective(TaskUser taskUser);

  	int insert(TaskUser taskUser);
  	
	int insertSelective(TaskUser taskUser);


    int selectCount(TaskUser taskUser);

    List<TaskUser> selectPage(@Param("taskUser") TaskUser taskUser, @Param("pageable") Pageable pageable);
	
    List<TaskUser> selectByCondition(Map map);
}