package com.yc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


public class UploadFileUtil {

	/**
	 * 
	 * @param request http请求,用来获取服务器的路径
	 * @param file 要保存的临时文件
	 * @param fileName 要上传文件的原来名字
	 * @param picRootName 要上传文件保存在webapps下面那个文件夹下
	 * @author Administrator 
	 * @return
	 */
	public static String uploadFile(HttpServletRequest request,File file, String fileName,String picRootName) {
		String newFileUrl=null;
		Map<String, UploadFile> map = new HashMap<String, UploadFile>();
			
			//  request.getSession().getServletContext().getRealPath("/") =>   C:\tomcat\apache-tomcat-7.0.47\webapps\douban
			// getParentFile() =>   E:\apache-tomcat-8.0.44-windows-x64\apache-tomcat-8.0.44\webapps  
			File webappsfile = new File(request.getSession().getServletContext().getRealPath("/")).getParentFile();
			

			File picFile = new File(webappsfile, picRootName);
			
			
			String picBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/" + picRootName;
			String originalFilename=fileName;
			
					// 生成新文件名,与时间相关
					String newfilename = getUniqueFileName()
							+ originalFilename.substring(originalFilename.lastIndexOf("."));
					String saveDir = picFile.getAbsolutePath() + getNowDateStr();
					String newFilePath = saveDir + newfilename;
					
					// http://localhost:8080/updloadBook/2017/8/22/**.txt
					newFileUrl = picBasePath + getNowDateStr() + newfilename;
					
					File saveDirFile = new File(saveDir);

					if (!saveDirFile.exists()) {
						saveDirFile.mkdirs();
					}
					String path=saveDir+ java.io.File.separator+newFileUrl;
				
			        try {
						//输出流  http://localhost:8080/updloadBook/2017/8/22/3413dba067d74a37b7e7e11060dcab03.tmp
						OutputStream os = new FileOutputStream(newFilePath);  
						//输入流  
						InputStream is = new FileInputStream(file);  
						  
						byte[] buf = new byte[1024];  
						int length = 0 ;  
						  
						while(-1 != (length = is.read(buf) ) )  
						{  
						    os.write(buf, 0, length) ;  
						}  
						is.close();  
						os.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}  
			          

				

		
		return newFileUrl;
	}

	/**
	 * 如果一个文件夹下面保存超过1000个文件，就会影响文件访问性能，所以上传的文件要分散存储, 这里用年月日作为目录层级 * 获取当前日期字符串
	 * * @param separator * @return "/2017/2/20/"
	 */
	private static String getNowDateStr() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		
		//          /                        /                       /
		//return File.separator + year + File.separator + month + File.separator + day + File.separator;
		return File.separator+year+File.separator+month+File.separator+day+File.separator;
	}

	/**
	 * 生成唯一的文件名
	 * 
	 * @return
	 */
	private static String getUniqueFileName() {
		String str = UUID.randomUUID().toString();   //444ffdd-333dd -666-666
		return str.replace("-", "");
	}

	public static class UploadFile {

		String originalFileName;   //原始文件名
		String newFileName;         //新文件名
		String newFilePath;         //新文件在服务器的保存路径
		String newFileUrl;          //新文件的访问路径
		long size;                  //文件大小
		String contentType;         //文件类型

		public String getNewFilePath() {
			return newFilePath;
		}

		public void setNewFilePath(String newFilePath) {
			this.newFilePath = newFilePath;
		}

		public String getNewFileUrl() {
			return newFileUrl;
		}

		public void setNewFileUrl(String newFileUrl) {
			this.newFileUrl = newFileUrl;
		}

		public String getOriginalFileName() {
			return originalFileName;
		}

		public void setOriginalFileName(String originalFileName) {
			this.originalFileName = originalFileName;
		}

		public String getNewFileName() {
			return newFileName;
		}

		public void setNewFileName(String newFileName) {
			this.newFileName = newFileName;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

	}
}
