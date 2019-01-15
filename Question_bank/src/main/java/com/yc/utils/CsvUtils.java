package com.yc.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import net.sf.ehcache.util.PropertyUtil;

public class CsvUtils {

	/** 
	 * 读取CSV文件 
	 */  
	public List<String[]>  readeCsv(String path){  
		try {      

			ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据  
			String csvFilePath = path;  
			CsvReader reader = new CsvReader(csvFilePath,',',Charset.forName("GBK"));    //一般用这编码读就可以了      

			//reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。  

			while(reader.readRecord()){ //逐行读入除表头的数据      
				csvList.add(reader.getValues());  
			}              
			reader.close();  
			return csvList;

		}catch(Exception ex){  
			System.out.println(ex);  
		}  

		return null;
	}  

	/** 
	 * 写入CSV文件 
	 * @throws IOException 
	 */  
	public void writeCsv(String path,List<Object> t) throws IOException{  
		String csvFilePath = path;
		String filepath=path.substring(0,path.lastIndexOf("/"));
		File f=new File(filepath);
		if(!f.exists()){
			f.mkdirs();
		}
		File file=new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		CsvWriter wr =new CsvWriter(csvFilePath,',',Charset.forName("GBK"));
		try { 
			for(Object obj:t){
				String[] contents=obj.toString().split(",");
				wr.writeRecord(contents);  
			}
			wr.close();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}  

}
