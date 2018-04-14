package com.turtlebone.dairy.repository;

import com.turtlebone.dairy.entity.Dairy;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DairyRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Dairy selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Dairy dairy);

    int updateByPrimaryKeySelective(Dairy dairy);

  	int insert(Dairy dairy);
  	
	int insertSelective(Dairy dairy);


    int selectCount(Dairy dairy);

    List<Dairy> selectPage(@Param("dairy") Dairy dairy, @Param("pageable") Pageable pageable);
	
}