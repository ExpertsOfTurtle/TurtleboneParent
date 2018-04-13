
package com.turtlebone.codeforces.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


import com.turtlebone.codeforces.entity.CFSubmission;
import com.turtlebone.codeforces.repository.CFSubmissionRepository;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.StringUtil;

@Service
public class CFSubmissionServiceImpl implements CFSubmissionService {

	@Autowired
	private CFSubmissionRepository cFSubmissionRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return cFSubmissionRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public CFSubmissionModel findByPrimaryKey(Integer id) {
		CFSubmission cFSubmission = cFSubmissionRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(cFSubmission, CFSubmissionModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(CFSubmissionModel cFSubmissionModel) {
		return cFSubmissionRepo.updateByPrimaryKey(BeanCopyUtils.map(cFSubmissionModel, CFSubmission.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(CFSubmissionModel cFSubmissionModel) {
		return cFSubmissionRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(cFSubmissionModel, CFSubmission.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(CFSubmissionModel cFSubmissionModel) {
		return cFSubmissionRepo.insert(BeanCopyUtils.map(cFSubmissionModel, CFSubmission.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(CFSubmissionModel cFSubmissionModel) {
		return cFSubmissionRepo.insertSelective(BeanCopyUtils.map(cFSubmissionModel, CFSubmission.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(CFSubmissionModel cFSubmissionModel) {
		return cFSubmissionRepo.selectCount(BeanCopyUtils.map(cFSubmissionModel, CFSubmission.class));
	}


	@Override
	public List<CFSubmissionModel> selectByCondition(List<Integer> idList, String username, String tag, String from,
			String to, Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<>();
		if (idList != null && idList.size() > 0) {
			map.put("idList", idList);
		}
		map.put("username", username);
		map.put("from", from);
		map.put("to", to);
		map.put("tag", tag);
		Sort sort = new Sort(Direction.DESC, "id");
		PageRequest pr = new PageRequest(pageNumber == null ? 0 : pageNumber, pageSize != null && pageSize > 0 ? pageSize : 20, sort);
		map.put("pageable", pr);
		List<CFSubmission> list = cFSubmissionRepo.selectByCondition(map);
		return BeanCopyUtils.mapList(list, CFSubmissionModel.class);
	}

	@Override
	public List<CFSubmissionModel> selectByCondition(CFSubmissionModel filter, Integer pageNumber, Integer pageSize) {
		CFSubmission sub = BeanCopyUtils.map(filter, CFSubmission.class);
		Sort sort = new Sort(Direction.DESC, "id");
		PageRequest pr = new PageRequest(pageNumber == null ? 0 : pageNumber, pageSize != null && pageSize > 0 ? pageSize : 20, sort);
		List<CFSubmission> list = cFSubmissionRepo.selectPage(sub, pr);
		return BeanCopyUtils.mapList(list, CFSubmissionModel.class);
	}


	@Override
	public int insert(List<CFSubmissionModel> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}
		Map<Integer, CFSubmissionModel> map = new HashMap<>();
		List<Integer> idList = new ArrayList<>();
		for (CFSubmissionModel cf : list) {
			idList.add(cf.getId());
			map.put(cf.getId(), cf);
		}
		List<CFSubmissionModel> existingList = selectByCondition(idList, null, null, null, null, null, 1000);
		for (CFSubmissionModel existing : existingList) {
			CFSubmissionModel cf = map.get(existing.getId());
			String result = existing.getResult();
			if (StringUtil.isEmpty(result) || "TESTING".equalsIgnoreCase(result)) {
				updateByPrimaryKey(cf);
			}
			map.remove(existing.getId());
		}
		List<CFSubmissionModel> insertList = new ArrayList<>();
		for (Entry<Integer, CFSubmissionModel> entry : map.entrySet()) {
			insertList.add(entry.getValue());
		}
		if (insertList.size() == 0) {
			return 0;
		}
		List<CFSubmission> li = BeanCopyUtils.mapList(insertList, CFSubmission.class);
		return cFSubmissionRepo.batchInsert(li);
	}


	@Override
	public List<CFSubmissionModel> querySolved(String from, String to) {
		Map<String, Object> map = new HashMap<>();
		map.put("from", from);
		map.put("to", to);
		List<CFSubmission> list = cFSubmissionRepo.querySolved(map);
		return BeanCopyUtils.mapList(list, CFSubmissionModel.class);
	}
}
