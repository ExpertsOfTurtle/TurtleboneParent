package com.turtlebone.stock.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.util.NumberUtils;

import com.turtlebone.core.util.IOUtil;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.stock.bean.SInfo;
import com.turtlebone.stock.common.IConstants;
import com.turtlebone.stock.util.PinyinHelper;

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
//				IOUtil.writeStringToFile("/dfs/info.st", str, true);
				System.out.println(str.split(",").length);
				resolve(str);
			}
		}
	}
	
	private void resolve(String str) {
		SInfo sinfo = new SInfo();
		String arr[] = str.split(",");
		if (arr.length == 14) {
			sinfo.setStart(NumberUtils.parseNumber(arr[0], Double.class));
			sinfo.setPre(NumberUtils.parseNumber(arr[1], Double.class));
			sinfo.setPrice(NumberUtils.parseNumber(arr[2], Double.class));
			sinfo.setMax(NumberUtils.parseNumber(arr[3], Double.class));
			sinfo.setMin(NumberUtils.parseNumber(arr[4], Double.class));
			sinfo.setName(arr[13]);
		} else if (arr.length == 33) {
			//sinfo.setStart(NumberUtils.parseNumber(arr[0], Double.class));
			//sinfo.setPre(NumberUtils.parseNumber(arr[1], Double.class));
			sinfo.setPrice(NumberUtils.parseNumber(arr[1], Double.class));
			sinfo.setMax(NumberUtils.parseNumber(arr[4], Double.class));
			sinfo.setMin(NumberUtils.parseNumber(arr[5], Double.class));
			sinfo.setName(arr[0]);
		}
		sinfo.setPinyin(PinyinHelper.getPinyin(sinfo.getName()));
		System.out.println(sinfo.getPinyin() + " " + sinfo.getPrice());
	}
	
	
}
