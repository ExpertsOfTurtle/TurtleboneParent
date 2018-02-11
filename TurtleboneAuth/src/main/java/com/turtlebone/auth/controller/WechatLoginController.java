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
import com.turtlebone.core.service.RedisService;
import com.turtlebone.core.util.SendHTTPUtil;


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
	
	@RequestMapping(value="/getOpenId", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getOpenId(@RequestParam(value="code") String code) {
		logger.info("getOpenId:{}");
	
		String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", 
				WECHAT_URL, APPID, SECRET, code);
		String result = "{}";
		try {
			result = SendHTTPUtil.callApiServer(url, "GET", "", null);
			JSONObject rs = JSON.parseObject(result);
			String openId = rs.getString("openid");
			
			//把openId存放到redis中，维持30分钟
			redisService.set(openId, result);
			redisService.expire(openId, 60 * 30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
		
}
