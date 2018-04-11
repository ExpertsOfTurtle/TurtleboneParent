package com.turtlebone.codeforces.repository;

import com.turtlebone.codeforces.entity.CFTask;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CFTaskRepository{

  	int deleteByPrimaryKey(Integer id);
	
	CFTask selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(CFTask cFTask);

    int updateByPrimaryKeySelective(CFTask cFTask);

  	int insert(CFTask cFTask);
  	
	int insertSelective(CFTask cFTask);


    int selectCount(CFTask cFTask);

    List<CFTask> selectPage(@Param("cFTask") CFTask cFTask, @Param("pageable") Pageable pageable);
	
    List<CFTask> selectForCompleteTask(String username);
}