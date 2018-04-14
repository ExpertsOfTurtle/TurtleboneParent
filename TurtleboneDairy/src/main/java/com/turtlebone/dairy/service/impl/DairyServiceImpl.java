
package com.turtlebone.dairy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.dairy.entity.Dairy;
import com.turtlebone.dairy.repository.DairyRepository;
import com.turtlebone.dairy.model.DairyModel;
import com.turtlebone.dairy.service.DairyService;

@Service
public class DairyServiceImpl implements DairyService {

	@Autowired
	private DairyRepository dairyRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return dairyRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public DairyModel findByPrimaryKey(Integer id) {
		Dairy dairy = dairyRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(dairy, DairyModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(DairyModel dairyModel) {
		return dairyRepo.updateByPrimaryKey(BeanCopyUtils.map(dairyModel, Dairy.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(DairyModel dairyModel) {
		return dairyRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(dairyModel, Dairy.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(DairyModel dairyModel) {
		Dairy dairy = BeanCopyUtils.map(dairyModel, Dairy.class);
		dairyRepo.insert(dairy);
		dairyModel.setId(dairy.getId());
		return dairy.getId();
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(DairyModel dairyModel) {
		return dairyRepo.insertSelective(BeanCopyUtils.map(dairyModel, Dairy.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(DairyModel dairyModel) {
		return dairyRepo.selectCount(BeanCopyUtils.map(dairyModel, Dairy.class));
	}


	@Override
	public List<DairyModel> filter(DairyModel dairyModel, Integer pageNumber, Integer pageSize) {
		PageRequest pr = new PageRequest(
				pageNumber != null ? pageNumber : 0, 
				pageSize != null && pageSize > 0 ? pageSize : 20, 
				new Sort(Direction.DESC, "id"));
		List<Dairy> list = dairyRepo.selectPage(BeanCopyUtils.map(dairyModel, Dairy.class), pr);
		return BeanCopyUtils.mapList(list, DairyModel.class);
	}



}
