package com.yc.test2;

import java.io.IOException;
import java.util.Scanner;

import com.yc.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class Test {
	public static void main(String[] args) throws IOException {
		/*String str="yc_1";
		int num=str.lastIndexOf("_");
		String str1=str.substring(0, num);
		System.out.println(str1);*/
		/*Integer paperId=23;
		String examineeName="YC_47";
		String answer="aaa";
		Jedis jedis=new Jedis(JedisUtils.REDIS_URL,JedisUtils.REDIS_PORT);
		String str=jedis.set(paperId+":"+examineeName, answer);
		 str=jedis.set(paperId+":"+examineeName, "bbb");
		if(str.equals("OK")){
			System.out.println("成功");
		}*/
		/*Runtime.getRuntime().exec("redis-server");*/
		/*String str="111";

		try {
			Integer sInteger=Integer.valueOf(str);
			System.out.println(sInteger);
		} catch (NumberFormatException e) {
			System.out.println("这是个字符串");
		}*/
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入年份：");
		int year = sc.nextInt();
		System.out.print("请输入月份：");
		int month = sc.nextInt();
		System.out.print("请输入日：");
		int day = sc.nextInt();
		int count = 0;
		int days = 0;
		if (year > 0 && month > 0 && month < 13 && day > 0 && day < 32) {
			for (int i = 1; i < month; i++) {
				switch (i) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					days = 31;
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					days = 30;
					break;
				case 2: {
					if ((year % 4 == 0 && year % 1 != 0) || (year % 400 == 0)) {
						days = 29;
					} else {
						days = 28;
					}
					break;
				}
				}
				count = count + days;
			}
			count = count + day;
			System.out.println(year + "年" + month + "月" + day + "日是" + year + "年的第" + count + "天");
		} else
			System.out.println("数据输入错误！");
	}




}
