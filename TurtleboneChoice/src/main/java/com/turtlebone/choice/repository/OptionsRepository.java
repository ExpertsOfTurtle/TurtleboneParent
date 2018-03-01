package com.turtlebone.choice.repository;

import com.turtlebone.choice.entity.Options;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Options selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Options options);

    int updateByPrimaryKeySelective(Options options);

  	int insert(Options options);
  	
	int insertSelective(Options options);


    int selectCount(Options options);

    List<Options> selectPage(@Param("options") Options options, @Param("pageable") Pageable pageable);
	
    int batchInsert(List<Options> list);
    
    List<Options> selectAll();
    
    int deleteByGroupId(Integer groupId);
}