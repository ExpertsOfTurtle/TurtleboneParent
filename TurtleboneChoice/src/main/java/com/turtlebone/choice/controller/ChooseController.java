package com.turtlebone.choice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.choice.bean.ChooseInfo;
import com.turtlebone.choice.model.OptionGroupModel;
import com.turtlebone.choice.model.OptionsModel;
import com.turtlebone.choice.service.OptionGroupService;
import com.turtlebone.choice.service.OptionsService;
import com.turtlebone.core.builder.activity.DeciderActivityBuilder;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.ActivityService;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.StringUtil;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/choose")
public class ChooseController {
	private static Logger logger = LoggerFactory.getLogger(ChooseController.class);

	@Autowired
	private OptionGroupService optionGroupService;
	@Autowired
	private OptionsService optionsService;	
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/random", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> random(HttpServletRequest request, @RequestBody ChooseInfo chooseInfo) {
		String username = (String)request.getAttribute("username");
		
		if (chooseInfo.getGroup() == null) {
			logger.error("group is null");
			return ResponseEntity.ok("Group is null");
		}
		Integer groupId = chooseInfo.getGroup().getGroupid();
		if (groupId == null) {
			logger.error("groupId is null");
			return ResponseEntity.ok("groupId is null");
		}
		OptionGroupModel group = optionGroupService.findByPrimaryKey(groupId);
		if (group == null) {
			logger.error("group is null");
			return ResponseEntity.ok("Group is null");
		}

		List<OptionsModel> optsList = optionsService.selectByGroupId(groupId);
		OptionsModel rs = runRandom(optsList);
		insertActivity(rs, group, username, optsList);
		return ResponseEntity.ok(rs);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> create(HttpServletRequest request, @RequestBody ChooseInfo chooseInfo) {
		logger.debug("Create group");
		String username = (String)request.getAttribute("username");
		
		if (chooseInfo.getGroup() == null) {
			logger.error("group is null");
			return ResponseEntity.ok("Group is null");
		} else if (chooseInfo.getOptions() == null || chooseInfo.getOptions().size() == 0) {
			logger.error("options is null");
			return ResponseEntity.ok("options is empty");
		}
		UserModel user = userService.selectByUsername(username);
		if (user == null) {
			logger.error("user is null");
			return ResponseEntity.ok("user is null");
		}
		OptionGroupModel group = chooseInfo.getGroup();
		group.setType(user.getUsertype() == null ? 2 :user.getUsertype());

		Integer groupId = optionGroupService.create(chooseInfo.getGroup());
		if (groupId == null || groupId == 0) {
			logger.error("fail to create group");
			return ResponseEntity.ok("Fail to create group");
		}
		for (OptionsModel om : chooseInfo.getOptions()) {
			om.setGroupid(groupId);
		}
		optionsService.batchInsert(chooseInfo.getOptions());

		return ResponseEntity.ok("OK");
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> modify(@RequestBody ChooseInfo chooseInfo) {
		logger.debug("Create group");
		
		if (chooseInfo.getGroup() == null) {
			logger.error("group is null");
			return ResponseEntity.ok("Group is null");
		} else if (chooseInfo.getOptions() == null || chooseInfo.getOptions().size() == 0) {
			logger.error("options is null");
			return ResponseEntity.ok("options is empty");
		}
		Integer gid = chooseInfo.getGroup().getGroupid();
		if (gid == null) {
			logger.error("groupId is required");
			return ResponseEntity.ok("groupdId is required");
		}
		OptionGroupModel group = optionGroupService.findByPrimaryKey(gid);
		if (group == null) {
			logger.error("no such group!");
			return ResponseEntity.ok("no such group!");
		}
		for (OptionsModel om : chooseInfo.getOptions()) {
			om.setGroupid(gid);
		}
		OptionGroupModel ogm = BeanCopyUtils.map(chooseInfo.getGroup(), OptionGroupModel.class);
		optionGroupService.updateByPrimaryKeySelective(ogm);

		optionsService.deleteByGroupId(gid);
		optionsService.batchInsert(chooseInfo.getOptions());

		return ResponseEntity.ok("OK");
	}

	@RequestMapping(value = "/delete/{groupId}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("groupId") Integer groupId) {
		logger.debug("Delete group {}", groupId);

		int rt = optionGroupService.deleteByPrimaryKey(groupId);
		rt += optionsService.deleteByGroupId(groupId);

		return ResponseEntity.ok(rt);
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> query(HttpServletRequest request) {
		String username = (String)request.getAttribute("username");
		UserModel user = userService.selectByUsername(username);
		
		List<ChooseInfo> result = selectAllOptions(user.getUsertype());
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> queryById(@PathVariable("id")Integer id) {
		OptionGroupModel group = optionGroupService.findByPrimaryKey(id);
		List<OptionsModel> options = optionsService.selectByGroupId(id);
		ChooseInfo ci = new ChooseInfo();
		ci.setGroup(group);
		ci.setOptions(options);
		return ResponseEntity.ok(ci);
	}

	@RequestMapping(value = "/selectpage")
	public String selectpage(HttpServletRequest request, Map<String, Object> model) {
		logger.debug("selectpage");
		String username = (String)request.getAttribute("username");
		UserModel user = userService.selectByUsername(username);
		
		List<ChooseInfo> result = selectAllOptions(user.getUsertype());

		model.put("chooseList", result);
		return "decide/ajax/list";
	}

	private List<ChooseInfo> selectAllOptions(Integer userType) {
		List<OptionGroupModel> groupList = null;
		List<OptionsModel> optionList = null;
		if (userType <= 1) {
			groupList = optionGroupService.selectAll();
			optionList = optionsService.selectAll();	
		} else {
			groupList = optionGroupService.selectByCondition(userType, null, null);
			List<Integer> groupIdList = getGroupId(groupList);
			optionList = optionsService.selectByGroupId(groupIdList);
		}
		
		Map<Integer, ChooseInfo> map = new HashMap<>();
		for (OptionGroupModel group : groupList) {
			ChooseInfo info = new ChooseInfo();
			info.setGroup(group);
			info.setOptions(new ArrayList<OptionsModel>());
			map.put(group.getGroupid(), info);
		}

		for (OptionsModel opt : optionList) {
			Integer gid = opt.getGroupid();
			ChooseInfo info = map.get(gid);
			if (info == null) {
				logger.warn("groupId {} not exist", gid);
				continue;
			}
			info.getOptions().add(opt);
		}

		List<ChooseInfo> result = new ArrayList<>();
		for (Entry<Integer, ChooseInfo> entry : map.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}

	protected OptionsModel runRandom(List<OptionsModel> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i).getProbability();
		}
		int r = (int) (Math.random() * sum * 1000);
		r %= sum;
		int x = 0;
		for (int i = 0; i < list.size(); i++) {
			x += list.get(i).getProbability();
			if (x >= r) {
				return list.get(i);
			}
		}
		return null;
	}

	protected void insertActivity(OptionsModel option, OptionGroupModel group, String username, List<OptionsModel> optsList) {
		try {
			int sum = 0;
			for (OptionsModel opt : optsList) {
				sum += opt.getProbability();
			}
			DeciderActivityBuilder builder = new DeciderActivityBuilder();
			ActivityModel activity = builder.build(username, null, group.getGroupid(), group.getGroupname(),
					option.getOptionname(), (double)option.getProbability() / (double)sum);
			activityService.create(activity);
		} catch (Exception e) {e.printStackTrace();}
		
	}
	private List<Integer> getGroupId(List<OptionGroupModel> groupList) {
		List<Integer> groupIdList = new ArrayList<>();
		for (OptionGroupModel group : groupList) {
			groupIdList.add(group.getGroupid());
		}
		return groupIdList;
	}
}
