package com.turtlebone.stock.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.turtlebone.core.util.IOUtil;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.stock.common.IConstants;

public class SendReq {
	@Test
	public void test() throws Exception {
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
				String str = matcher.group(1);
				IOUtil.writeStringToFile("/dfs/info.st", str, true);
			}
		}
	}
}
