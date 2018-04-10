package com.turtlebone.stock.repository;

import com.turtlebone.stock.entity.AStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AStockRepository{

  	int deleteByPrimaryKey(Integer id);
	
	AStock selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(AStock aStock);

    int updateByPrimaryKeySelective(AStock aStock);

  	int insert(AStock aStock);
  	
	int insertSelective(AStock aStock);


    int selectCount(AStock aStock);

    List<AStock> selectPage(@Param("aStock") AStock aStock, @Param("pageable") Pageable pageable);
	
    int batchInsert(List<AStock> list);
}