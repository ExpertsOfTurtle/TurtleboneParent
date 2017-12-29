package com.turtlebone.codeforces.mapper;

import java.util.HashMap;
import java.util.Map;

public class CFUserMapper {
	public static Map<String, String> toCFMapper;
	public static Map<String, String> fromCFMapper;
	static {
		toCFMapper = new HashMap<>();
		toCFMapper.put("DFS", "scorpiowf");
		toCFMapper.put("COULD", "Could1991");
		
		fromCFMapper = new HashMap<>();
		fromCFMapper.put("SCORPIOWF", "DFS");
		fromCFMapper.put("COULD1991", "Could");
	}
	public static String getCFName(String username) {
		return toCFMapper.get(username.toUpperCase());
	}
	public static String getTurtleName(String cfName) {
		return fromCFMapper.get(cfName.toUpperCase());
	}
}
