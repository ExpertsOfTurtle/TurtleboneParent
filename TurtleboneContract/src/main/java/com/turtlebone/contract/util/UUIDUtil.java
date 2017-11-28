package com.turtlebone.contract.util;

import java.util.UUID;

public class UUIDUtil {
	public static String generateId(int length) {
		String str = UUID.randomUUID().toString().replaceAll("-", "");
		if (length < 10 && str.length() >= length) {
			str = str.substring(0, length);
		}
		return str;
	}
}
