package com.turtlebone.stock.util;


import org.springframework.util.StringUtils;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinHelper {
	public static String getPinyin(String ChineseLanguage) {
		if (StringUtils.isEmpty(ChineseLanguage)) {
			return "";
		}
		char[] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// (不)带声调
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			for (int i = 0; i < cl_chars.length; i++) {
				if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音
					hanyupinyin += net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
				} else {// 如果字符不是中文,则不转换
					hanyupinyin += cl_chars[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			
		}
		return hanyupinyin;
	}	
}
