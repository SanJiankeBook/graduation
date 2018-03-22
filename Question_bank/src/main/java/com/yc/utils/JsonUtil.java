package com.yc.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;




import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;



public class JsonUtil {
	
	/**
	 * 把json字符串传到页面中去
	 * @param jsonStr json字符串
	 * @throws IOException 先抛出,待处理
	 * @author fanhaohao
	 */
	public static void jsonOut(String jsonStr) throws IOException{
		PrintWriter out=null;;
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("application/json; charset=utf-8");
			out = response.getWriter();
			out.write(jsonStr);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	public static void writeJson(Object obj){
		PrintWriter out=null;
		try {
			String json=JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd",SerializerFeature.DisableCircularReferenceDetect);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("application/json; charset=utf-8");
			out=response.getWriter();
			out.write(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

}
