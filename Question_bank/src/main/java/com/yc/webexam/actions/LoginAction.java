package com.yc.webexam.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.po.Examinee;
import com.yc.po.SystemUser;
import com.yc.utils.Encrypt;
import com.yc.utils.JsonUtil;
import com.yc.utils.UTFUtil;
import com.yc.utils.ValueUtil;

@SuppressWarnings("serial")
public class LoginAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

	private static final Logger logger = Logger.getLogger(LoginAction.class);

	@Resource(name = "systemUserBiz")
	private SystemUserBiz systemUserBiz;
	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;

	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
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
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 查询班级
	 */
	public void findClass() {
		// 查询出所有的班级名称
	    logger.info("进入查询班级");
		List<String> allClass = examineeClassBiz.searchAllExamineeClassName();
		Collections.sort(allClass,new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1.replaceAll("[^0-9]*", "")) - Integer.parseInt(o2.replaceAll("[^0-9]*", ""));
			}
		});
		// 将list集合转换成String的字符串
		String dataInfo = writeJson(0, allClass);
		try {
			JsonUtil.jsonOut(dataInfo);
		} catch (IOException e1) {
			logger.error(e1);
			e1.printStackTrace();
		}
	}

	/**
	 * 登录
	 */
	public void login() {
		try {
			this.request.setCharacterEncoding("UTF-8");

			String identity = this.request.getParameter("identity");
			String examClass = this.request.getParameter("examClass");
			
			// UTFUtil.Utf8Util(this.request.getParameter("uname"));
			String uname = this.request.getParameter("uname");
			String password = Encrypt.md5AndSha(this.request.getParameter("password"));
			String validateCode = this.request.getParameter("validateCode").trim();
			// 取出imageCode.jsp中存的验证码
			String rand = (String) mysession.getAttribute("rand");
			if (!validateCode.equals(rand)) {
				// 验证码错误,返回0
				try {
					JsonUtil.jsonOut("0");
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			} else {
				// 标识列
				boolean flag = false;
				// 学员登录
				if (identity.equals("1")) {
					Examinee e = examineeBiz.getExaminee(uname, password, examClass);
					if (e != null) {
						flag = true;
					}
					if (flag) {
						
						mysession.setAttribute("userid", e.getId());
						mysession.setAttribute("userName", uname);
						mysession.setAttribute("examClass", examClass);
						mysession.setAttribute("identity", identity); // 标识列，用来区别教员和学生登录
						mysession.setMaxInactiveInterval(45*60);
						//mysession.setMaxInactiveInterval(20);
						// session.put("userName", uname);
						// session.put("examClass", examClass);
						// 学生登录成功时，返回1
						try {
							JsonUtil.jsonOut("1");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						HttpServletRequest request = ServletActionContext.getRequest();
						request.setAttribute("message", "用户的密码输入有误!");
						// 用于登录失败时回复页面
						request.setAttribute("role", 1);
						// 密码错误，返回2
						try {
							JsonUtil.jsonOut("2");
						} catch (IOException e1) {
							logger.error(e1);
							e1.printStackTrace();
						}
					}
					// 教师登陆
				} else {
					flag = systemUserBiz.isExist(uname, password);
					if (flag) {
						mysession.setAttribute("userName", uname);
						mysession.setAttribute("identity", identity);
						mysession.setMaxInactiveInterval(45*60);
						//	mysession.setMaxInactiveInterval(10);
						// session.put("userName", uname);
						// 教师登录成功时，返回3
						try {
							JsonUtil.jsonOut("3");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						request.setAttribute("message", "用户的密码输入有误!");
						request.setAttribute("role", 2);
						// 密码错误，返回2
						try {
							JsonUtil.jsonOut("2");
						} catch (IOException e1) {
							logger.error(e1);
							e1.printStackTrace();
							
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e2) {
			logger.error(e2);
			e2.printStackTrace();
		}
	}

	/**
	 * 是否登录
	 * 
	 * @author tane
	 * @throws IOException
	 */
	public void isLogin() throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = "true";
		String userid = ValueUtil.formatRequestStr(mysession.getAttribute("userid"));
		
		String userName = ValueUtil.formatRequestStr(mysession.getAttribute("userName"));
		String examClass = ValueUtil.formatRequestStr(mysession.getAttribute("examClass"));
		if ((userName == "" || userName == null) && (examClass == "" || examClass == null)) {
			flag = "false";
		}
		map.put("flag", flag);
		map.put("userid", userid);
		map.put("username", userName);
		map.put("examClass", examClass);
		list.add(map);
		JsonUtil.jsonOut(writeJson(0, list));
	}

	/**
	 * 退出系统
	 * 
	 * @author fpc
	 */
	public void exit() {
		if (mysession != null) {
			String userName = mysession.getAttribute("userName").toString();
			logger.info(new Date()+ " 用户/学员：" + userName + " 退出登录");
			mysession.removeAttribute("userName");
			mysession.invalidate();
			// 退出成功，返回成功
			try {
				JsonUtil.jsonOut("1");
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示教员端信息
	 * 
	 * @author fanhaohao
	 * @throws IOException
	 */
	public void showUserInfo() throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String userName = (String) session.get("userName");
		logger.info("userName:" + userName);
		SystemUser systemUser = (SystemUser) systemUserBiz.findSystemUserByName(userName).get(0);
		map.put("userName", systemUser.getUserName());
		map.put("remark", systemUser.getRemark());
		map.put("uId", systemUser.getId());
		map.put("uRole", systemUser.getAuthorities());
		list.add(map);
		JsonUtil.jsonOut(writeJson(0, list));
	}

	/**
	 * 修改密码
	 * 
	 * @author tane
	 */
	public void updatePassWord() {
		String examClass = ValueUtil.formatRequestStr(request.getParameter("examClass"));
		// 汉字进行解码
		String examineeName = request.getParameter("examineeName");
		String oldpwd = Encrypt.md5AndSha(request.getParameter("oldPwd"));
		String newPwd = Encrypt.md5AndSha(request.getParameter("newPwd"));
		// 修改后得到的信息
		// String updateInfo = "";
		// 得到班级ID
		int classid = examineeBiz.searchClassid(examClass);
		String pwd = examineeBiz.getPwd(examineeName, classid);
		if (pwd == null || !oldpwd.equals(pwd)) {
			try {
				JsonUtil.jsonOut("0");
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		} else {
			// 判断考生名和班级是否输入正确
			boolean isExaminee = examineeBiz.isClassExaminee(examineeName, classid);
			if (!isExaminee) {
				try {
					JsonUtil.jsonOut("1");
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			} else {
				// 判断更改成不成功
				boolean flag = examineeBiz.updatePassword(classid, examineeName, newPwd);
				if (flag) {
					// updateInfo = "考生:" + examineeName + " 的密码已成功修改！";
					// 修改成功，返回标识2
					try {
						JsonUtil.jsonOut("2");
					} catch (IOException e) {
						logger.error(e);
						e.printStackTrace();
					}
				} else {
					// updateInfo = "考生:" + examineeName + " 修改密码不成功！请联系管理员。";
					// 修改密码不成功！请联系管理员，返回标识3
					try {
						JsonUtil.jsonOut("3");
					} catch (IOException e) {
						logger.error(e);
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 修改教员密码
	 * 
	 * @author tane
	 */
	public void updateTeacherPassWord() {
		// 汉字进行解码
		String teacherName = request.getParameter("examineeName");
		String oldpwd = Encrypt.md5AndSha( (request.getParameter("oldPwd")));
		String newPwd = Encrypt.md5AndSha(request.getParameter("newPwd"));
		String pwd = examineeBiz.getTeacherPwd(teacherName);
		if (pwd == null || !oldpwd.equals(pwd)) {
			// 修改密码不成功，原密码不正确，返回标识0
			try {
				JsonUtil.jsonOut("0");
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		} else {
			// 判断是否修改成功
			boolean flag = examineeBiz.updateTeacherPassword(teacherName, newPwd);
			if (flag) {
				// 修改成功，返回标识1
				try {
					JsonUtil.jsonOut("1");
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			} else {
				// 修改失败，返回标识2
				try {
					JsonUtil.jsonOut("2");
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}

	}

	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}
}
