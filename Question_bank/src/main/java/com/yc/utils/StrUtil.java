package com.yc.utils;

public class StrUtil {
	/**
	 * 格式化传入进来的字符串,为空时转为"",不为空时去除空格(防止页面上传过来的为空造成格式转化错误)
	 * @author fanhaohao
	 */
	public static String formatStr(String str) {
		str = str == null ? "" : str.trim();
		return str;
	}
}
