package com.turtlebone.contract.repository;

import com.turtlebone.contract.entity.ContractActivity;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractActivityRepository{

  	int deleteByPrimaryKey(Integer id);
	
	ContractActivity selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(ContractActivity contractActivity);

    int updateByPrimaryKeySelective(ContractActivity contractActivity);

  	int insert(ContractActivity contractActivity);
  	
	int insertSelective(ContractActivity contractActivity);


    int selectCount(ContractActivity contractActivity);

    List<ContractActivity> selectPage(@Param("contractActivity") ContractActivity contractActivity, @Param("pageable") Pageable pageable);

    ContractActivity selectSignActivity(Map<String, Object> map);
    
    List<ContractActivity> selectBulkSignActivity(Map<String, Object> map);
}