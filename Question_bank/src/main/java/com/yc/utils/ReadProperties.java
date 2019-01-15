package com.yc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import net.sf.ehcache.util.PropertyUtil;

public class ReadProperties {

	public static   String readData(String filePath, String key) {    
		Properties props = new Properties();    
		try {    
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));    
			props.load(in);    
			in.close();    
			String value = props.getProperty(key);    
			return value;    
		} catch (Exception e) {    
			e.printStackTrace();    
			return null;    
		}    
	}    

	/**  
	 * 注意：把properties放到"src/main/resources/"下 
	 * 修改或添加键值对 如果key存在，修改, 反之，添加。  
	 * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties  
	 * @param key 键  
	 * @param value 键对应的值  
	 */    
	public static  void writeData(String filePath, String key, String value) {    
		String path = String.valueOf(PropertyUtil.class.getClassLoader().getResource(filePath));
		Properties prop = new Properties();    
		try {    
			int first=path.indexOf("/");
			path=path.substring(first,path.length());
			File file = new File(path);    
			if (!file.exists())    
				file.createNewFile();    
			InputStream fis = new FileInputStream(file);    
			prop.load(fis);    
			//一定要在修改值之前关闭fis    
			fis.close();    
			OutputStream fos = new FileOutputStream(path);    
			prop.setProperty(key, value);    
			//保存，并加入注释    
			prop.store(fos, "Update '" + key + "' value");    
			fos.close();    
		} catch (IOException e) {  
			e.printStackTrace();
			System.err.println("Visit " + filePath + " for updating " + value + " value error");    
		}    
    }    
}
