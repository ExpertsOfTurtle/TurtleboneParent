package com.turtlebone.stock.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
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

import com.turtlebone.core.service.UserService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/upload")
public class UploadController {
	private static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/normal", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> upload(@RequestParam(value="file")MultipartFile mpFile, 
			@RequestParam(value="actionType") String actionType,
			@RequestParam(value="data") String data,
			HttpServletRequest request, 
			HttpServletResponse response){
		
		logger.debug("File size={}, type={}", mpFile.getSize(), mpFile.getContentType());
		logger.debug("actionType={}, data={}", actionType, data);
		
		return ResponseEntity.ok("");
	}
		
	
}
