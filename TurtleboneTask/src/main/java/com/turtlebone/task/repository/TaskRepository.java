package com.turtlebone.task.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.turtlebone.task.entity.Task;
import com.turtlebone.task.entity.TaskResult;

@Repository
public interface TaskRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Task selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Task task);

    int updateByPrimaryKeySelective(Task task);

  	int insert(Task task);
  	
	int insertSelective(Task task);


    int selectCount(Task task);

    List<TaskResult> selectPage(@Param("task")Map map, @Param("pageable") Pageable pageable);
	
}