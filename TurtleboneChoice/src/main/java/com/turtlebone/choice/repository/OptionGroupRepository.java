package com.turtlebone.choice.repository;

import com.turtlebone.choice.entity.OptionGroup;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionGroupRepository{

  	int deleteByPrimaryKey(Integer id);
	
	OptionGroup selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(OptionGroup optionGroup);

    int updateByPrimaryKeySelective(OptionGroup optionGroup);

  	int insert(OptionGroup optionGroup);
  	
	int insertSelective(OptionGroup optionGroup);


    int selectCount(OptionGroup optionGroup);

    List<OptionGroup> selectPage(@Param("optionGroup") OptionGroup optionGroup, @Param("pageable") Pageable pageable);
	
    OptionGroup selectByName(String groupname);
    
    List<OptionGroup> selectAll();
    List<OptionGroup> selectPageLimit(Integer offset, Integer pageSize);
    
}