package com.turtlebone.contract.repository;

import com.turtlebone.contract.entity.Contract;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Contract selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Contract contract);

    int updateByPrimaryKeySelective(Contract contract);

  	int insert(Contract contract);
  	
	int insertSelective(Contract contract);


    int selectCount(Contract contract);

    List<Contract> selectPage(@Param("contract") Contract contract, @Param("pageable") Pageable pageable);
	
    List<Contract> selectAll();
}