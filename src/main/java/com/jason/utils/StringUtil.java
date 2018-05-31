package com.jason.utils;

/**
 * 字符工具
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月31日 上午10:45:52
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str.trim())) {
			return false;
		}
		return true;
	}
}
