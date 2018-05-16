package com.turtlebone.stock.schedule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.IOUtil;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.stock.common.IConstants;

@Component
public class ScheduleTask {
	private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
	
	@Scheduled(cron = "*/30 * 9-11 * * 1-5")
	public void v1() throws Exception {
		test();
	}
	
	@Scheduled(cron = "*/30 * 13-15 * * 1-5")
	public void v2() throws Exception {
		test();
	}
	
	private void test()  throws Exception{
		String url = IConstants.STOCKURL + "?_list=";
		for (String s : IConstants.STOCKQUERY_LIST) {
			url += s + ",";
		}
		String rs = SendHTTPUtil.callApiServer(url, "GET", "", null, "GBK");
		String arr[] = rs.split(";");
		String p = ".*\"(.*)\".*";
		Pattern pattern = Pattern.compile(p);
		for (String s : arr) {
			s = s.trim();
			Matcher matcher = pattern.matcher(s);
			if (matcher.matches()) {
				String str = matcher.group(1) + "\n";
				IOUtil.writeStringToFile("/dfs/info.st", str, true);
			}
		}
		logger.debug("{}", arr.length);
	}
}
