package com.turtlebone.dream.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.turtlebone.core.bean.QueryActivityRequest;
import com.turtlebone.core.bean.Uploadpath;
import com.turtlebone.core.enums.ActivityType;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.ActivityService;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.dream.bean.DreamActivityRequest;
import com.turtlebone.dream.builder.DreamActivityBuilder;
import com.turtlebone.dream.constants.IDreamType;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/dream")
public class DreamController {
	private static Logger logger = LoggerFactory.getLogger(DreamController.class);

	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;
	@Autowired
	private DreamActivityBuilder dreamActivityBuilder;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> addDream(@RequestBody DreamActivityRequest request) {
		logger.debug("request:{}", JSON.toJSONString(request));

		UserModel user = userService.selectByUsername(request.getUsername());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("no such username!");
		}
		String username = request.getUsername();
		String datetime = request.getDatetime();
		Integer dreamType = request.getDreamType() == null ? IDreamType.NORMAL : request.getDreamType();
		ActivityModel activity = dreamActivityBuilder.build(username, datetime, request.getContent(),
				request.getDreampic(), dreamType);
		logger.debug("activity:{}", JSON.toJSONString(activity));
		activityService.create(activity);
		return ResponseEntity.ok(activity);
	}

	@RequestMapping(value = "/query")
	public String queryDream(Map<String, Object> model, @RequestBody QueryActivityRequest request) {
		List<ActivityModel> list = activityService.selectByCondition(request.getUsername(), ActivityType.DREAM.name(),
				null, null, request.getPageSize(), request.getOffset());
		model.put("list", list);
		return "dream/ajax/list";
	}
	
	@RequestMapping(value="/uploadImg", method=RequestMethod.POST)
	@ResponseBody
	public String uploadImg(@RequestParam(value="img")MultipartFile img,HttpServletResponse response){
		logger.debug("request:{}", img.toString());
		JSONObject result = new JSONObject();
	    boolean flag = true;
	    try {
	        flag = dreamActivityBuilder.upload(img, result);
	    } catch (Exception e) {
	        result.put("mess", "上传失败");
	        flag = false;
	        e.printStackTrace();
	    }
	    result.put("flag", flag);
	    
	    response.setContentType("text/html;charset=UTF-8");
	    //解决跨域名访问问题
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    logger.debug("response:{}", result.toString());
	    return result.toString();
	}
}
