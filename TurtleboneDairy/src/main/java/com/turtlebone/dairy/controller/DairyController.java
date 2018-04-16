package com.turtlebone.dairy.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turtlebone.core.bean.ArrayResult;
import com.turtlebone.core.bean.ResultVO;
import com.turtlebone.core.exception.TurtleException;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.UserService;
import com.turtlebone.dairy.bean.CreateDairyRequest;
import com.turtlebone.dairy.bean.DairyVO;
import com.turtlebone.dairy.bean.FilterDairyRequest;
import com.turtlebone.dairy.common.DairyStatus;
import com.turtlebone.dairy.common.DairyType;
import com.turtlebone.dairy.common.SubDairyType;
import com.turtlebone.dairy.model.DairyModel;
import com.turtlebone.dairy.service.DairyService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/dairy")
public class DairyController {
	private static Logger logger = LoggerFactory.getLogger(DairyController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private DairyService dairyService;
	
	@RequestMapping(value="/insert")
	public @ResponseBody ResponseEntity<?> insert(@RequestBody CreateDairyRequest request) {
		DairyModel dairy = convert(request);
		try {
			validate(dairy);
		} catch (TurtleException e) {
			ResultVO<String> rs = new ResultVO<String>(e.getCode(), e.getMessage(), null);
			return ResponseEntity.ok(rs);
		}
		
		UserModel user = userService.selectByUsername(dairy.getCreator());
		if (user == null) {
			return ResponseEntity.ok(new ResultVO<String>("E8802", "Creator not exist", null));
		}
		
		int id = dairyService.create(dairy);
		
		return ResponseEntity.ok(new ResultVO<Integer>("0", "OK", id));
	}
	@RequestMapping(value="/modify")
	public @ResponseBody ResponseEntity<?> modify(@RequestBody CreateDairyRequest request) {
		DairyModel dairy = convert(request);
		if (dairy.getId() == null) {
			return ResponseEntity.ok(new ResultVO<String>("E8802", "DairyId is required", null));
		}		
		try {
			validate(dairy);
		} catch (TurtleException e) {
			ResultVO<String> rs = new ResultVO<String>(e.getCode(), e.getMessage(), null);
			return ResponseEntity.ok(rs);
		}
		
		UserModel user = userService.selectByUsername(dairy.getCreator());
		if (user == null) {
			return ResponseEntity.ok(new ResultVO<String>("E8802", "Creator not exist", null));
		}
		
		int id = dairyService.updateByPrimaryKey(dairy);
		
		return ResponseEntity.ok(new ResultVO<Integer>("0", "OK", id));
	}
	
	@RequestMapping(value="/filter")
	public @ResponseBody ResponseEntity<?> filter(@RequestBody FilterDairyRequest request) {
		ArrayResult result = new ArrayResult();
		DairyModel filter = new DairyModel();
		if (!StringUtils.isEmpty(request.getCreator()))
			filter.setCreator(request.getCreator());
		if (!StringUtils.isEmpty(request.getTitle()))
			filter.setTitle(request.getTitle());
		filter.setType(request.getType());
		filter.setSubtype(request.getSubtype());
		filter.setStatus(request.getStatus());
		if ("Y".equalsIgnoreCase(request.getCheckExpire())) {
			filter.setExpiretime(new Date());
		}
		int total = dairyService.selectCount(filter);
		List<DairyModel> list = null;
		List<DairyVO> dairyList = new ArrayList<>();
		if (total > 0) {
			list = dairyService.filter(filter, request.getPageNumber(), request.getPageSize());
			if (list != null) {
				for (DairyModel dm : list) {
					DairyVO vo = covert(dm);
					dairyList.add(vo);
				}
			}
		}
		result.setTotal(total);
		result.setList(dairyList);		
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value="/delete/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		//不删除，改为标记
		DairyModel dairy = dairyService.findByPrimaryKey(id);
		if (dairy == null) {
			return ResponseEntity.ok(new ResultVO<String>("E8802", "找不到记录, 你让我如何删除？", ""));	
		} else {
			dairy.setStatus(DairyStatus.Deleted.getVal());
			dairyService.updateByPrimaryKey(dairy);
			return ResponseEntity.ok(new ResultVO<String>("0", "OK", "成功"));	
		}
	//	int rt = dairyService.deleteByPrimaryKey(id);
	//	return ResponseEntity.ok(new ResultVO<Integer>("0", "OK", rt));
	}
	
	private DairyModel convert(CreateDairyRequest request) {
		DairyModel dairy = new DairyModel();
		dairy.setCreator(request.getCreator());
		dairy.setContent(request.getContent() != null ? request.getContent().getBytes() : null);
		dairy.setId(request.getId());
		dairy.setType(request.getType());
		dairy.setSubtype(request.getSubtype());
		dairy.setTitle(request.getTitle());
		dairy.setExpiretime(request.getExpiretime());
		dairy.setStatus(request.getStatus());
		return dairy;
	}
	private DairyVO covert(DairyModel dairy) {
		DairyVO rs = new DairyVO();
		rs.setContent(new String(dairy.getContent()));
		rs.setCreatetime(dairy.getCreatetime());
		rs.setCreator(dairy.getCreator());
		rs.setExpiretime(dairy.getExpiretime());
		rs.setId(dairy.getId());
		rs.setSubtype(dairy.getSubtype());
		rs.setTitle(dairy.getTitle());
		rs.setType(dairy.getType());
		rs.setUpdatetime(dairy.getUpdatetime());
		rs.setStatus(dairy.getStatus());
		return rs;
	}
	private boolean validate(DairyModel dairy) throws TurtleException {
		if (StringUtils.isEmpty(dairy.getTitle())) {
			throw new TurtleException("E8802", "Title is required");
		}
		if (StringUtils.isEmpty(dairy.getContent())) {
			throw new TurtleException("E8802", "Content is required");
		}
		if (StringUtils.isEmpty(dairy.getCreator())) {
			throw new TurtleException("E8802", "Creator is required");
		}
		if (dairy.getType() == null) {
			throw new TurtleException("E8802", "Type is required");
		}
		if (!validateType(dairy.getType())) {
			throw new TurtleException("E8802", "Type not exist");
		}
		if (dairy.getSubtype() != null && !validateSubType(dairy.getSubtype())) {
			throw new TurtleException("E8802", "Sub type not exist");
		}
		if (!validateStatus(dairy.getStatus())) {
			throw new TurtleException("E8802", "Status not exist");
		}
		return true;
	}
	private boolean validateType(int val) {
		for (DairyType dt : DairyType.values()) {
			if (dt.getVal() == val) {
				return true;
			}
		}
		return false;
	}
	private boolean validateSubType(int val) {
		for (SubDairyType dt : SubDairyType.values()) {
			if (dt.getVal() == val) {
				return true;
			}
		}
		return false;
	}
	private boolean validateStatus(int val) {
		for (DairyStatus ds : DairyStatus.values()) {
			if (ds.getVal() == val) {
				return true;
			}
		}
		return false;
	}
}
