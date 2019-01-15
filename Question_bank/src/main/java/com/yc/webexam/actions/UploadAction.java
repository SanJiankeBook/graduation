package com.yc.webexam.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ActionSupport;
import com.yc.biz.ADailyTalkBiz;

@SuppressWarnings("serial")
@Scope("prototype")
public class UploadAction extends ActionSupport {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	private static final Logger logger = Logger.getLogger(SubjectAction.class);
	@Resource(name = "aDailyTalkBiz")
	private ADailyTalkBiz aDailyTalkBiz;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	private HttpSession mysession = request.getSession();

	public void setServletResponse(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			this.request = request;
			this.mysession = this.request.getSession();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}

	// 封装上传文件域的属性
	private File knowledgeinfo;
	// 封装上传文件类型的属性
	private String knowledgeinfoContentType;
	// 封装上传文件名的属性
	private String knowledgeinfoFileName;
	// 接受依赖注入的属性
	private String savePath;
	
	/*public void uploadfile_hwh() throws FileUploadException{
		 DiskFileItemFactory factory = new DiskFileItemFactory();  
         factory.setSizeThreshold(1024*1024*80);  
         factory.setRepository(new File(request.getServletContext().getRealPath("WEB-INF/temp")));  
         //1.2获取上传文件的核心类  
         ServletFileUpload fileload = new ServletFileUpload(factory);  
         fileload.setHeaderEncoding("UTF-8");  
         fileload.setFileSizeMax(1024*1024*100);  
         fileload.setSizeMax(1024*1024*200);
         List<FileItem> list = fileload.parseRequest((RequestContext) request);
         for(FileItem item :list){ 
        	 if(!item.isFormField()){//普通字段  
        		 String realname = item.getName();  
        	 }
         }
	}*/

	public void uploadfile() {
		String id = null; // 新技术编号
		byte[] bt = null;
		String fileName = null;
		int result = 0;
		
		id = request.getParameter("id");
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			// 建立文件输出流
			String filetype = getKnowledgeinfoFileName().substring(getKnowledgeinfoFileName().lastIndexOf("."));
			String filename = getKnowledgeinfoFileName().substring(getKnowledgeinfoFileName().lastIndexOf("\\") + 1,
					getKnowledgeinfoFileName().lastIndexOf(".")) + sdf.format(new Date()) + filetype;
			String path=getSavePath() ;
			//获取Tomact根路劲   C:\Users\Administrator\Desktop\apache-tomcat-8.0.44\webapps
			for(int i=0;i<2;i++){
				path=path.substring(0,path.lastIndexOf("\\"));
			}
			
			String sepa = java.io.File.separator;
			path=path+sepa+"upload";
			String file = path + sepa + filename;
			
			File f=new File(path);
			if(!f.exists()){
				f.mkdir();
			}
			fos = new FileOutputStream(file);
			// 建立文件上传流
			fis = new FileInputStream(getKnowledgeinfo());
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fis = new FileInputStream(file);
			bt = new byte[fis.available()];
			fis.read(bt);
			fis.close();
			result = aDailyTalkBiz.updateUploadKnowledgeInfos(bt, Integer.parseInt(id), filename);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			close(fos, fis);
		}

	}

	/**
	 * 返回上传文件的保存位置
	 * 
	 * @return
	 */
	public String getSavePath() throws Exception {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public File getKnowledgeinfo() {
		return knowledgeinfo;
	}

	public void setKnowledgeinfo(File knowledgeinfo) {
		this.knowledgeinfo = knowledgeinfo;
	}

	public String getKnowledgeinfoContentType() {
		return knowledgeinfoContentType;
	}

	public void setKnowledgeinfoContentType(String knowledgeinfoContentType) {
		this.knowledgeinfoContentType = knowledgeinfoContentType;
	}

	public String getKnowledgeinfoFileName() {
		return knowledgeinfoFileName;
	}

	public void setKnowledgeinfoFileName(String knowledgeinfoFileName) {
		this.knowledgeinfoFileName = knowledgeinfoFileName;
	}

	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}

	public void setaDailyTalkBiz(ADailyTalkBiz aDailyTalkBiz) {
		this.aDailyTalkBiz = aDailyTalkBiz;
	}

}