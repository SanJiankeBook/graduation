package com.yc.utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class RedisUtils {
	private YcConstant YcConstant;
	private Double numl=null;
	
	Jedis jedis=new Jedis(YcConstant.REDI_URL,YcConstant.REDIS_PORT);
	
	//统计点击量
	public Double Ranking(String novel){
		numl=jedis.zincrby("rank", 1, novel);
		//System.out.println("输出"+numl);	
		return numl;
	}
	
	//显示点击量
	public Double ShowRank(String novel){
		Double numll;
		numll=jedis.zscore("rank",novel);
		//System.out.println(numll);
		return numll;
	}
	
	//排序，从高到低
	public Set<String> ShowRankNum(){
		Set<String> list=jedis.zrevrange("rank", 0, -1);
		//System.out.println("排行榜"+list);
		return list;
	}
	
//	//转换Set<String>为String[]
//	public String[] ShowRankNum1(){
//		Set<String> set =ShowRankNum();
//		String[] st=new String[set.size()];
//		int j=0;
//		for(Iterator  iter=ShowRankNum().iterator();;iter.hasNext()){
//			st[j]=(String) iter.next();
//			System.out.println(st[j]+" --- String数组是什么");
//			return st;
//		}	
//	}
	
		//转换Set<String>为List
		public List ShowRankNum1(){
			Set<String> set =ShowRankNum();
			List<String> list=new ArrayList<String>(set);
			//System.out.println(list+" --- List是什么");
			return list;		
		}
	
}
