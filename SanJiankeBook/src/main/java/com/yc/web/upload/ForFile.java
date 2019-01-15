package com.yc.web.upload;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yc.bean.NovelChapter;
import com.yc.biz.NovelChapterbiz;

public class ForFile {
    //生成文件路径
    private static String picRootName = "Novel";
    
  
    
    //文件路径+名称
    private static String filenameTemp;
    /**
     * 创建文件
     * @param fileName  文件名称
     * @param filecontent   文件内容
     * @param novelchapter 
     * @param novelchapterbiz 
     * @return  是否创建成功，成功则返回true
     */
    public static String createFile(HttpServletRequest request,String filecontent,String title, NovelChapter novelchapter, NovelChapterbiz novelchapterbiz){
        
    	//E:\apache-tomcat-7.0.47\webapps
		File webappsfile=new File( request.getSession().getServletContext().getRealPath(  "/"     )).getParentFile();
		/*章节保存的服务器位置      E:\apache-tomcat-7.0.47\webapps \ uploadPdfs */
		File picFile=new File(  webappsfile, picRootName);
		//构建章节服务器的url地址      http://localhost:8080/uploadPdfs
		String picBasePath =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+picRootName ;
		
		// 生成新文件名,与时间相关     3c2d709a23444f9c98d2408b9d5f8246.zip
		String newfilename = getUniqueFileName()+".html";
		//得到保存的目录/*E:\apache-tomcat-7.0.47\webapps\ uploadPdfs\2017\2\26\*/
		String saveDir=picFile.getAbsolutePath()+getNowDateStr();
		//生成文件的目录  /*E:\apache-tomcat-7.0.47\webapps\ uploadPdfs\2017\2\26\3c2d709a23444f9c98d2408b9d5f8246.zip*/
		String newFilePath=saveDir+newfilename;
		//在浏览器上的路径 ,即等下存储进数据库中 /*http://localhost:8080/uploadPdfs\2017\2\26\3c2d709a23444f9c98d2408b9d5f8246.zip*/
		String newFileUrl= picBasePath+getNowDateStr()+newfilename;
		
		File saveDirFile=new File( saveDir);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		
        filenameTemp = newFilePath;
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                Double val=novelchapterbiz.getNovleChapterId(novelchapter);
                if(val==null ){
                	novelchapter.setStandby_2(1);
                }else{
                	val=val+1;
                	String str=""+val;
                	str=str.substring(0, 1);
                	novelchapter.setStandby_2( Integer.parseInt(str));
                }
                System.out.println("success create file,the file is "+filenameTemp);
                String startcontent="<!DOCTYPE html><html><head><meta charset='UTF-8'><title style=''>第"+novelchapter.getStandby_2()+"章              "+title+"</title></head><body><h1 style='text-align: center'>第"+novelchapter.getStandby_2()+"章  &nbsp;&nbsp;&nbsp;"+title+"</h1>";
                String endconten="</body></html>";	
                String content=startcontent+filecontent+endconten;
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileUrl;
        
    }
    
    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"<br/>";//新写入的行，换行
        String temp  = "";
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            
            //文件原有内容
//            for(int i=0;(temp =br.readLine())!=null;i++){
//                buffer.append(temp);
//                // 行与行之间的分隔符 相当于“\n”
//                buffer = buffer.append(System.getProperty("line.separator"));
//            }
            buffer.append(filein);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    
    /**
	 *  生成唯一的文件名
	 * @return
	 */
	private static String getUniqueFileName() {
		String str = UUID.randomUUID().toString();
		return str.replace("-", "");
	}
	/**
	 * 如果一个文件夹下面保存超过1000个文件，就会影响文件访问性能，所以上传的文件要分散存储, 这里用年月日作为目录层级 * 获取当前日期字符串
	 * * @param separator * @return "2017/2/20"
	 */
	private static String getNowDateStr() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		return File.separator+year + File.separator + month + File.separator + day+File.separator;
	}
}
