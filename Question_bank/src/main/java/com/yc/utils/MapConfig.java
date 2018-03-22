package com.yc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.ehcache.util.PropertyUtil;

public class MapConfig {

	protected static Map<String,Integer> map=new HashMap<String,Integer>();;

	public static Map<String,Integer> getInstance() throws Exception{
		Properties props = new Properties();
		String path=String.valueOf(PropertyUtil.class.getClassLoader().getResource("/"))+"map.txt";
		path=path.replace("file:/"," ").trim();
		File f = new File(path);   
		  if(f.isFile()&&f.exists()){ 
			InputStreamReader read = new InputStreamReader(new FileInputStream(f),"UTF-8");   
			BufferedReader  reader = new BufferedReader(new FileReader(new File(path))); 
			 String tempString = null;  
			while ((tempString = reader.readLine()) != null) { 
				String[] contents=tempString.split("=");
				map.put(contents[0], Integer.valueOf(contents[1]));
	        }  
		}
		return map;
	}




}
