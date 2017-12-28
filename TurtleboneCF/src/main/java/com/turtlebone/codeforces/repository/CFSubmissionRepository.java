package com.turtlebone.codeforces.repository;

import com.turtlebone.codeforces.entity.CFSubmission;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CFSubmissionRepository{

  	int deleteByPrimaryKey(Integer id);
	
	CFSubmission selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(CFSubmission cFSubmission);

    int updateByPrimaryKeySelective(CFSubmission cFSubmission);

  	int insert(CFSubmission cFSubmission);
  	
	int insertSelective(CFSubmission cFSubmission);


    int selectCount(CFSubmission cFSubmission);

    List<CFSubmission> selectPage(@Param("cFSubmission") CFSubmission cFSubmission, @Param("pageable") Pageable pageable);
	
    int batchInsert(List<CFSubmission> list);
    
    List<CFSubmission> selectByCondition(Map map);
    
    List<CFSubmission> querySolved(Map map);
}