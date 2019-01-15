package com.yc.utils;

public class HtmlUtil {
	public static String toHtml(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 换成相应的ASCII码值，半角的逗句，分号，引号换成了全角
			switch (c) {
			case '<':
				sb.append("&#60;");
				break;
			case '>':
				sb.append("&#62;");
				break;
			case '\'':
				sb.append("&#39;");
				break;
			case '\"':
				sb.append("&#34;");
				break;
			case '&':
				sb.append("&#38;");
				break;
			case '?':
				sb.append("&#63;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/*
	 * 将章节名中的,;:在程序中用到的特殊符号转成全角，还要考虑&和？在URL上的传输
	 */
	public static String convertSpecialName(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 换成相应的ASCII码值，半角的逗句，分号，引号换成了全角
			switch (c) {
			case ',':
				sb.append("，");
				break;
			case ';':
				sb.append("；");
				break;
			case ':':
				sb.append("：");
				break;
			case '?':
				sb.append("？");
				break;
			//章节名和科目名中包含&会与URL有冲突
			case '&':
				sb.append("＆");
				break;
			case 'M':
				//用的三个M分隔的，所以如果M在第一个或是最后一个则转成全角的
				if (i==0 || i==str.length()-1) {
					sb.append("Ｍ");
				}
				else {
					sb.append("M");
				}
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	
	/*
	 * 写入数据库时再转回去
	 */
	public static String convertBackSpecialName(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 换成相应的ASCII码值，半角的逗句，分号，引号换成了全角
			switch (c) {
			case '＆':
				sb.append("&");
				break;
			case '．':
				sb.append(".");
				break;
			case '？':
				sb.append("?");
				break;
			case '＋':
				sb.append("+");
				break;
			default:
				sb.append(c);
			break;
			}
		}
		return sb.toString();
	}
}
