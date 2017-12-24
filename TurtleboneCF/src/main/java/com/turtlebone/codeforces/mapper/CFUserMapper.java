package com.turtlebone.codeforces.mapper;

import java.util.HashMap;
import java.util.Map;

public class CFUserMapper {
	public static Map<String, String> userMapper;
	static {
		userMapper = new HashMap<>();
		userMapper.put("DFS", "scorpiowf");
		userMapper.put("COULD", "Could1991");
	}
	public static String getCFName(String username) {
		return userMapper.get(username.toUpperCase());
	}
}
