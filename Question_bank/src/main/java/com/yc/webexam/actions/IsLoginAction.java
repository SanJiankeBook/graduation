package com.yc.webexam.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;

import com.yc.utils.JsonUtil;
import com.yc.utils.ValueUtil;

@SuppressWarnings("serial")
@Scope("prototype")
public class IsLoginAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
	private static final Logger logger = Logger.getLogger(IsLoginAction.class);

	// private Map<String, Object> session;
	//
	//
	// public void setSession(Map<String, Object> session) {
	// this.session = session;
	// }

	ArrayList arr = new ArrayList();

	private HttpSession mysession;

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
			logger.error(e);
		}
	}

	// /**
	// * 将对象转换成JSON字符串，并响应回前台
	// * @param object
	// * @throws IOException
	// */
	// public void writeJson(Object object) {
	// try {
	// // SerializeConfig serializeConfig = new SerializeConfig();
	// // serializeConfig.setAsmEnable(false);
	// // String json = JSON.toJSONString(object);
	// // String json = JSON.toJSONString(object, serializeConfig,
	// // SerializerFeature.PrettyFormat);
	// String json = JSON.toJSONStringWithDateFormat(object,
	// "yyyy-MM-dd HH:mm:ss");
	// // String json = JSON.toJSONStringWithDateFormat(object,
	// // "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
	// ServletActionContext.getResponse().setContentType(
	// "text/html;charset=utf-8");
	// ServletActionContext.getResponse().getWriter().write(json);
	// ServletActionContext.getResponse().getWriter().flush();
	// } catch (IOException e) {
	// logger.debug(e);
	// }
	// }

	private String userName; // 考生姓名
	private String examClass; // 考生班级

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExamClass() {
		return examClass;
	}

	public void setExamClass(String examClass) {
		this.examClass = examClass;
	}

	/**
	 * 是否登录
	 * 
	 * @throws IOException
	 */
	public void isL() throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = "true";
		String userName = ValueUtil.formatRequestStr(mysession.getAttribute("userName"));
		String examClass = ValueUtil.formatRequestStr(mysession.getAttribute("examClass"));
		if ((userName == "" || userName == null) && (examClass == "" || examClass == null)) {
			flag = "false";
		}
		map.put("flag", flag);
		map.put("username", userName);
		list.add(map);
		JsonUtil.jsonOut(writeJson(0, list));
		// String json = this.writeJson(0, list);
		// out.print(json);
		// out.flush();
		// out.close();
	}

}
