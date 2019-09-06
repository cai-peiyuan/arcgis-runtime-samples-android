package com.bohaigaoke.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BHUtil {
	/**
	 * 返回缺省格式的当前日期时间字符串 默认格式:yyyy-mm-dd hh:mm:ss
	 * 
	 * @return String
	 */
	public static String getDateTimeStr() {
		return getDateTimeStr("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 返回指定格式的当前日期时间字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String getDateTimeStr(String format) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}
}
