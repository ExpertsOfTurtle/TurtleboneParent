package com.turtlebone.auth.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.turtlebone.auth.bean.WechatLoginRequest;
import com.turtlebone.core.bean.UserDetails;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.RedisService;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.core.util.StringUtil;


@Controller
@EnableAutoConfiguration
@RequestMapping(value="/wechat")
public class WechatLoginController {
	private static Logger logger = LoggerFactory.getLogger(WechatLoginController.class);
	
	private final String APPID = "wxd18588d3eefb71e2";
	private final String SECRET = "750a90ba0151d3164185389a883f2f21";
	private final String WECHAT_URL = "https://api.weixin.qq.com/sns/jscode2session";
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getOpenId", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> getOpenId(@RequestBody WechatLoginRequest request) {
		logger.info("getOpenId:{}");
	
		String code = request.getCode();
		String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", 
				WECHAT_URL, APPID, SECRET, code);
		String result = "{}";
		
		UserDetails userDetails = new UserDetails();
		try {
			result = SendHTTPUtil.callApiServer(url, "GET", "", null);
			JSONObject rs = JSON.parseObject(result);
			String openId = rs.getString("openid");
			
			userDetails.setTokenId(openId);
			userDetails.setAvatarUrl(request.getAvatarUrl());
			userDetails.setNickName(request.getNickName());
			userDetails.setTokenType("WX");
			userDetails.setLoginName(getUsername(openId));
			
			//把openId存放到redis中，维持30分钟
			redisService.set(openId, JSON.toJSONString(userDetails));
			redisService.expire(openId, 60 * 30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(userDetails);
	}
		
	private String getUsername(String tokenId) {
		if (StringUtil.isEmpty(tokenId)) {
			return null;
		}
		UserModel user = userService.selectByCondition(null, tokenId);
		if (user != null) {
			return user.getLoginName();
		} else {
			return null;
		}
	}
	
}
