package com.turtlebone.codeforces.repository;

import com.turtlebone.codeforces.entity.CFActivity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CFActivityRepository{

  	int deleteByPrimaryKey(Integer id);
	
	CFActivity selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(CFActivity cFActivity);

    int updateByPrimaryKeySelective(CFActivity cFActivity);

  	int insert(CFActivity cFActivity);
  	
	int insertSelective(CFActivity cFActivity);


    int selectCount(CFActivity cFActivity);

    List<CFActivity> selectPage(@Param("cFActivity") CFActivity cFActivity, @Param("pageable") Pageable pageable);
	
}