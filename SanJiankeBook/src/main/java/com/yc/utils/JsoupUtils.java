package com.yc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.yc.bean.NovelChapter;
import com.yc.biz.NovelChapterbiz;
import com.yc.utils.Unicode;

@Component
public class JsoupUtils{
	
	private NovelChapterbiz novelChapterbiz;
	
	@Resource(name = "novelChapterbizImpl")
	public void setNovelchapterbiz(NovelChapterbiz novelChapterbiz){
		this.novelChapterbiz = novelChapterbiz;
	}
	
			
	
	public byte[] Chapter(int nid) throws IOException{
		Unicode uc=new Unicode();
		StringBuilder s = new StringBuilder("");
		List<NovelChapter> Chapter=novelChapterbiz.ShowAllChapter(nid);
		byte[] contentInBytes=null;
		String TextValue=null;
		for(int i=0;i<Chapter.size();i++){
			String con=null;
			String filePath=System.getProperty("catalina.home")+"\\webapps\\";
			String chapter=Chapter.get(i).getCaddress();
			String url=chapter.substring(0,22);
			String urll=filePath+chapter.substring(22); //服务器路径
			File in = new File(urll);
			Document doc = Jsoup.parse(in, "UTF-8", url); 
			//Document doc = Jsoup.connect(chapter).get(); //你要抽取的地址
			
			Elements divs = doc.select("html");
			
			for (Element div: divs) { 
		 		//System.out.println(div.select("div[class=Lyric_right]")); //你要抽取的标签<div class=Lyric_right></div>
		 	
		     	String content =div.select("body").toString();
		     	//String cte=uc.decodeUnicode(content);	  
	           // String regEx="[`~!@#$%^&*()+=|{}';',.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？/\"/gbody]";
//	            String regEx = "<[^>]*>";    //去除所有的标签
//	            Pattern p = Pattern.compile(regEx);
//	            Matcher m = p.matcher(uc.decodeUnicode(content));

		     	content=content.replace("<body>","");
		     	content=content.replace("</body>","");
		     	String title=content.replace("<h1>", "");
		     	String title1=title.replace("</h1>", "\n\r");
		     	String temp=title1.replace("<p>","");
		     	temp=temp.replace("&nbsp;", " ");
		     	String temp1=temp.replace("</p>","\n\r");
		     	temp1=temp1.replace("<br>","\n\r");
		     	temp1=temp1.replace(" <br>","\n\r");
		     	temp1=temp1.replace("<br> ","\n\r");
		     	temp1=temp1.replace("</span>","\n\r");
		     	temp1=temp1.replace("<span>","\n\r");
		     	String s1="<h1 style=\"text-align: center\">";
		     	temp1=temp1.replace(s1, "");
		     	s1="<span style=\"font-family: 宋体, 宋体, Verdana, Arial, sans-serif; font-size: 20px; background-color: rgb(229, 236, 243);\">";
		     	temp1=temp1.replace(s1, "");
		     	s1="<h1 style=\"text-align: center\">";
		     	con=temp1.replace(s1, "");
	            System.out.println(con);
	      
//	         	con =m.replaceAll("").trim();
//	         	con += "\n";
	            
	         	 			
			}
			//con += "\n";
			TextValue+=con;
            TextValue=TextValue+"\n\r";
		}
		contentInBytes=TextValue.getBytes();
		return contentInBytes;
 
	}
}
