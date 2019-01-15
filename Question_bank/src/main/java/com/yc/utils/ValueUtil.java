package com.yc.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 此类定义一此静态方法,用于返回处理过后的参数的结果
 *
 * <p>Title: 在线考试系统</p>
 */
public class ValueUtil {

  public ValueUtil() {
  }

 /**
   * 转字符集编码
   * @param s String 原字符串
   * @return String 返回结果
   * @throws UnsupportedEncodingException 发生转换自符异常
   */
  public static String toCharset(String s) throws UnsupportedEncodingException {
    return s = new String(s.getBytes("ISO-8859-1"), "UTF-8");
  }

  /**
   * 获得当前系统时间 列如:2007-01-01
   * @return String 返回时间字符串
   */
  public static String getNowDate() {
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    return df.format(date);
  }

  /**
   * 获取当前系统日期，格式自定义
   * @param f String 日期的格式
   * @return String 系统当前的日期
   */
  public static String getNowDate(String f) {
    Date now = new Date();
    SimpleDateFormat sf = new SimpleDateFormat(f);
    return sf.format(now);
  }

  /**
   * 生成随机数
   * @param max int 最大上限
   * @return int 随机数
   */
  public static int getRandom(int max) {
    Random rd = new Random();
    return rd.nextInt(max + 1);
  }

  /**
   * 转换HTML的特殊字符
   * @param s String 需要转换的字符串
   * @return String 转换后的字符串
   */
  public static String encodeHTML(String s) {
    StringBuffer stringbuffer = new StringBuffer();
    int j = s.length();
    for (int i = 0; i < j; i++) {
      char c = s.charAt(i); //折成每个字符
      //比较是否是特定的字符
      switch (c) {
        case 60:
          stringbuffer.append("&lt;");
          break;
        case 62:
          stringbuffer.append("&gt;");
          break;
        case 38:
          stringbuffer.append("&amp;");
          break;
        case 34:
          stringbuffer.append("&quot;");
          break;
        case 169:
          stringbuffer.append("&copy;");
          break;
        case 174:
          stringbuffer.append("&reg;");
          break;
        case 165:
          stringbuffer.append("&yen;");
          break;
        case 8364:
          stringbuffer.append("&euro;");
          break;
        case 8482:
          stringbuffer.append("&#153;");
          break;
        case 13:
          if (i < j - 1 && s.charAt(i + 1) == 10) {
            stringbuffer.append("<br>");
            i++;
          }
          break;
        case 32:
          if (i < j - 1 && s.charAt(i + 1) == ' ') {
            stringbuffer.append(" &nbsp;");
            i++;
            break;
          }
        default:
          stringbuffer.append(c);
          break;
      }
    }
    return new String(stringbuffer.toString());
  }

  /**
   * 格式化传入进来的字符串,为空时转为"",不为空时去除空格
   * @param str String
   * @return String
   */
  public static String formatRequestStr(String str) {
      str = str == null ? "" : str.trim();
    //  str = new String(str.trim().getBytes("ISO-8859-1"), "GBK");
    return str;
  }

  /**
   * 将传入进来的对象为空时转为“”，不为空时去除空格
   * @param str Object
   * @return String
   */
  public static String formatRequestStr(Object str) {
    str = str == null ? "" : str.toString().trim();
    //  str = new String(str.trim().getBytes("ISO-8859-1"), "GBK");
    return str.toString();
  }
  
  /**
	 * 制作指定日期
	 * 
	 * @param num :当前月减去几个月
	 * @param day:日期
	 * @return
	 */
	public static String getDate(int year,int month, int day) {
		// 制作日期 26-25 eg:3.26-4.25
		Calendar ca = Calendar.getInstance();
		//int year = ca.get(Calendar.YEAR);
		//int month = ca.get(Calendar.MONTH);
		ca.set(year, month, day);//制作日期
		Date resultDate = ca.getTime(); // 结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(resultDate);

	}

}
