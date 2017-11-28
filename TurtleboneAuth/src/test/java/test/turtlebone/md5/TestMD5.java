package test.turtlebone.md5;

import org.junit.Test;

import com.turtlebone.core.util.MD5Util;

public class TestMD5 {
	@Test
	public void test() {
		String rs = MD5Util.md5("DF场");
		System.out.println(MD5Util.md5("DF场"));
		System.out.println(MD5Util.md5("dfsDFS123"));
		System.out.println(MD5Util.md5("dfsDFS12"));
	}
}
