package com.turtlebone.codeforces.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.digester.annotations.rules.PathCallParam;
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
import com.turtlebone.codeforces.bean.QueryStatusRequest;
import com.turtlebone.codeforces.bean.SyncSubmissionRequest;
import com.turtlebone.codeforces.bean.WeeklySummary;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.CFReportService;
import com.turtlebone.codeforces.service.CFSubmissionService;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.codeforces.service.ResolveHtmlService;
import com.turtlebone.codeforces.service.WeeklyTaskService;
import com.turtlebone.core.service.EmailService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.IOUtil;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.core.util.StringUtil;
import com.turtlebone.core.velocity.VelocityGenerator;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/html")
public class CodeforcesController {
	private static Logger logger = LoggerFactory.getLogger(CodeforcesController.class);

	@Autowired
	private ResolveHtmlService resolveHtmlService;
	
	@RequestMapping(value = "/problem/{id}/{idx}", method = RequestMethod.GET)
	public void problem(@PathVariable("id")Integer id,
			@PathVariable("idx")String idx,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		File file = new File(String.format("/data/web/codeforces/%d_%s.html", id, idx));
		if (file.exists()) {
			logger.info("文件存在，直接返回");
			String location = String.format("http://%s:12345/web/codeforces/%d_%s.html", request.getServerName(), id, idx);
			try {
				response.sendRedirect(location);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		String url = String.format("http://codeforces.com/problemset/problem/%d/%s", id, idx);
		String html = "", rs = "";
		try {
			html = SendHTTPUtil.callApiServer(url, "GET", "", null);
			rs = resolveHtmlService.resolve(html);
			
			String path = String.format("/data/web/codeforces/%d_%s.html", id, idx);
			IOUtil.writeStringToFile(path, rs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String location = String.format("http://%s:12345/web/codeforces/%d_%s.html", request.getServerName(), id, idx);
		try {
			response.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
