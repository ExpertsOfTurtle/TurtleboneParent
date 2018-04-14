package com.turtlebone.dairy.controller;

import java.util.ArrayList;
import java.util.Base64;
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
import com.turtlebone.dairy.common.DairyType;
import com.turtlebone.dairy.common.SubDairyType;
import com.turtlebone.dairy.model.DairyModel;
import com.turtlebone.dairy.service.DairyService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private DairyService dairyService;
	
	
	
	@RequestMapping(value="/all")
	public @ResponseBody ResponseEntity<?> filter() {
		List<UserModel> userList = userService.listAllUser();
		List<String> usernameList = new ArrayList<>();
		for (UserModel user : userList) {
			usernameList.add(user.getLoginName());
		}
		return ResponseEntity.ok(usernameList);
	}
	
	@RequestMapping(value="/delete/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		int rt = dairyService.deleteByPrimaryKey(id);
		return ResponseEntity.ok(new ResultVO<Integer>("0", "OK", rt));
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
}
