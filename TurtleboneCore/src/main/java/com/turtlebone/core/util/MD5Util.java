package com.turtlebone.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String md5(String text) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(text.getBytes());

		byte[] data = md.digest();
		StringBuffer digestHexStr = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			digestHexStr.append(byteHEX(data[i]));
		}
//		String rs = new BigInteger(1, md.digest()).toString(16);
		return digestHexStr.toString().toLowerCase();
	}

	public static String byteHEX(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

}
