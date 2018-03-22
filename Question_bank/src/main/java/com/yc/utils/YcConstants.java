package com.yc.utils;

import org.apache.log4j.Logger;

/**
 * 常量类+日志
 * 整个项目中的字符串，整型等常量配置处
 * 请一定要加好文档注释
 * @author 张影
 *
 */
public class YcConstants {
	
	public static Logger logger=Logger.getLogger( YcConstants.class);
	
	/**
	 * 每日一讲 -> 状态  ->  等待开讲
	 */
	public static final String  ADILYTALK_ACCESSMENT_START="等待开讲";
	
	public  static  final int SeduceNum=2;//每一小节课的课时
	
	
}
