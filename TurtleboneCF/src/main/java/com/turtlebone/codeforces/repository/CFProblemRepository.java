package com.turtlebone.codeforces.repository;

import com.turtlebone.codeforces.entity.CFProblem;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CFProblemRepository{

  	int deleteByPrimaryKey(Long id);
	
	CFProblem selectByPrimaryKey(Long id);
	
	    
    int updateByPrimaryKey(CFProblem cFProblem);

    int updateByPrimaryKeySelective(CFProblem cFProblem);

  	int insert(CFProblem cFProblem);
  	
	int insertSelective(CFProblem cFProblem);


    int selectCount(CFProblem cFProblem);

    List<CFProblem> selectPage(@Param("cFProblem") CFProblem cFProblem, @Param("pageable") Pageable pageable);
	
}