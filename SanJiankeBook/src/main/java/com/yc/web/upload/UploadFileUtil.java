package com.yc.web.upload;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileUtil {

	/**
	 * 
	 * @param request
	 * @param files
	 * @param picProjectFile : 图片服务器的位置   tomcat/webapps/uploadBookImages/
	 * @param  picProjectUrl  : 图片服务器的url   http://localhost:8080/uploadBookImages/
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, UploadFile> uploadFile(HttpServletRequest request, List<MultipartFile> files, String picRootName ) throws IOException {
		Map<String, UploadFile> map = new HashMap<String, UploadFile>();
		List<MultipartFile> listMultipart=new ArrayList<MultipartFile>();
		if(files.size()>1){
			List<File> listFile=new ArrayList<File>();
			for(MultipartFile mf:files){
		        listFile.add(multipartToFile(mf));
			}
			String bt=getFileMD5(listFile.get(0));
			boolean flag=true;
			for(int i=2;i<=listFile.size();i++){
				String bbt=getFileMD5(listFile.get(i-1));
				if(bbt.equals(bt)){
					flag=false;
					break;
				}
			}
			if(!flag){
				
				listMultipart.add(files.get(0));
				files=listMultipart;
			}
			
		}
		
		if (files != null && files.size() > 0) {
				//E:\apache-tomcat-7.0.47\webapps
			File webappsfile=new File( request.getSession().getServletContext().getRealPath(  "/"     )).getParentFile();
			/*图片保存的服务器位置      E:\apache-tomcat-7.0.47\webapps \ uploadPdfs */
			File picFile=new File(  webappsfile, picRootName);
			
			//构建图片服务器的url地址      http://localhost:8080/uploadPdfs
			String picBasePath =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+picRootName ;
			
			for (MultipartFile multipartFile : files) {
				try {
					//得到原文件的名字
					String originalFilename = multipartFile.getOriginalFilename();
					
					if(  multipartFile.isEmpty() ){
						continue;
					}
					// 生成新文件名,与时间相关     3c2d709a23444f9c98d2408b9d5f8246.zip
					String newfilename = getUniqueFileName()+ originalFilename.substring(originalFilename.lastIndexOf("."));
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

					File imageFile = new File(newFilePath);

					UploadFile uploadFile = new UploadFile();
					uploadFile.contentType = multipartFile.getContentType();
					uploadFile.size = multipartFile.getSize();
					uploadFile.originalFileName = originalFilename;
					uploadFile.newFileName = newfilename;
					uploadFile.newFilePath=newFilePath;
					uploadFile.newFileUrl=newFileUrl;

					map.put(originalFilename, uploadFile);

					multipartFile.transferTo(imageFile);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return map;
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

	/**
	 *  生成唯一的文件名
	 * @return
	 */
	private static String getUniqueFileName() {
		String str = UUID.randomUUID().toString();
		return str.replace("-", "");
	}
	
	/** 
     * MultipartFile 转换成File 
     *  
     * @param multfile 原文件类型 
     * @return File 
     * @throws IOException 
     */  
    private static File multipartToFile(MultipartFile multfile) throws IOException {  
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
        //这个myfile是MultipartFile的  
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
        File file = fi.getStoreLocation();  
        //手动创建临时文件  
        if(file.length() < 2048){  
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +   
                    file.getName());  
            multfile.transferTo(tmpFile);  
            return tmpFile;  
        }  
        return file;  
    }  
	
	// 计算文件的 MD5 值
	public static String getFileMD5(File file) {
	    if (!file.isFile()) {
	        return null;
	    }
	    //MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值
	    MessageDigest digest = null;
	    FileInputStream in = null;
	    byte buffer[] = new byte[8192];
	    int len;
	    try {
	        digest =MessageDigest.getInstance("MD5");
	        in = new FileInputStream(file);
	        while ((len = in.read(buffer)) != -1) {
	            digest.update(buffer, 0, len);
	        }
	        BigInteger bigInt = new BigInteger(1, digest.digest());
	        return bigInt.toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        try {
	            in.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	}

	public static class UploadFile {
		String originalFileName;
		String newFileName;
		String newFilePath;
		String newFileUrl;
		long size;
		String contentType;
		
		

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

