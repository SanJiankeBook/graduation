package com.yc.webexam.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
//import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ChangeClassBiz;
import com.yc.biz.EditionBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.po.ChangeClass;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.utils.Encrypt;
import com.yc.utils.JsonUtil;
import com.yc.vo.ListClassPage;

public class ExamineeClassAction extends BaseAction implements ModelDriven<ListClassPage>, ServletRequestAware, ServletResponseAware {
	
	private static final Logger logger = Logger.getLogger(ExamineeClassAction.class);
	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;

	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	
	@Resource(name = "changeClassBiz")
	private ChangeClassBiz changeClassBiz;

	private String jsonStr = null;
	
	@Resource(name = "editionBiz")
	private EditionBiz editionBiz;

	private ListClassPage listClassPage = new ListClassPage();
	// private ExamineeClass examinClass=new ExamineeClass();

	// private PrintWriter out;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	private HttpSession mysession = request.getSession();

	
	
	public EditionBiz getEditionBiz() {
		return editionBiz;
	}

	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}
	
	

	public ChangeClassBiz getChangeClassBiz() {
		return changeClassBiz;
	}

	public void setChangeClassBiz(ChangeClassBiz changeClassBiz) {
		this.changeClassBiz = changeClassBiz;
	}

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

	public ListClassPage getListClassPage() {
		return listClassPage;
	}

	public void updateClass() {
		// int id=listClassPage.getId();
		String id = request.getParameter("id");
		String semester = request.getParameter("semester").toString().trim();
		String className = request.getParameter("className").toString().trim();
		String createDate = request.getParameter("createDate").toString().trim();
		String overDate = request.getParameter("overDate").toString().trim();
		String remark = request.getParameter("remark").toString().trim();
		try {
			ExamineeClass examineeClass = examineeClassBiz.findExamineeClassById(Integer.parseInt(id));
			examineeClass.setClassName(className);
			examineeClass.setCreateDate(createDate);
			examineeClass.setOverDate(overDate);
			examineeClass.setRemark(remark);
			examineeClass.setSemester(semester);
			examineeClassBiz.updateExamineeClass(examineeClass);

			jsonStr = super.writeJson(0, null);
		} catch (Exception e) {
			logger.error(e);
			jsonStr = super.writeJson(1, "更新出现异常");
		}
		try {
			JsonUtil.jsonOut(jsonStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	// 录入考生
	public void addExamClass() {
		String pwd = Encrypt.md5AndSha(listClassPage.getPwd());
		String exaimineeNames = listClassPage.getExamineeNames();
		String className = listClassPage.getClassName();
		String idCard = listClassPage.getIdCard();
		String tel = listClassPage.getTel();
		int classId = examineeClassBiz.getClassIdOfname(className);
		// ExamineePK pk=new ExamineePK();
		// ExamineeClass examineeClass=new ExamineeClass();
		// Examinee examinee=new Examinee();
		String examinees[] = exaimineeNames.split("\n");
		for (int i = 0,len=examinees.length; i < len; i++) {
			String examineeName = examinees[i].toString().trim();
			if (!"".equals(examineeName)) {
				int count = examineeBiz.addExaminee(examineeName, pwd, classId, idCard, tel);
				if (count > 0) {
					jsonStr = super.writeJson(0, count);
				} else {
					jsonStr = super.writeJson(1, "添加失败");
				}
				try {
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e);
				}

			}
		}
	}

	// 检查班级是否存在
	public void checkClassName() {
		try {
			String className = listClassPage.getClassName();
			Integer id = examineeClassBiz.getClassIdOfname(className);
			if (id > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "校验出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}

	// 新增班级
	public void addClass() {
		ExamineeClass examineeClass = new ExamineeClass();
		BeanUtils.copyProperties(listClassPage, examineeClass);
		try {
			examineeClassBiz.addExamineeClass(examineeClass);
			jsonStr = super.writeJson(0, null);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "新增班级出现异常" + e.toString());
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	// 修改全班考生的密码
	public void updateAllExaminee() throws IOException {
		String className = listClassPage.getClassName();
		String pwd = Encrypt.md5AndSha(  listClassPage.getPwd() );
		try {
			List<Examinee> examinees = examineeBiz.getAllExaminee(className);
			if (examinees != null && examinees.size() > 0) {
				for (Examinee examinee : examinees) {
					examinee.setPassword(pwd);
					int result = examineeBiz.updateExaminee(examinee);
					if (result > 0) {
						jsonStr = super.writeJson(0, null);
					} else {
						jsonStr = super.writeJson(1, "修改全班密码失败");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "修改出现异常" + e.toString());
		} finally {
			JsonUtil.jsonOut(jsonStr);
		}
	}

	// 删除考生
	public void deleteExaminee() {
		String className = listClassPage.getClassName();
		String examineeName = listClassPage.getName();
		try {
			int classId = examineeClassBiz.getClassIdOfname(className);
			int result = examineeBiz.deletelistexam(classId, examineeName);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "删除失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "删除出现异常" + e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	
	
	public void findPassword() throws IOException{
		String newpwd = Encrypt.md5AndSha(  listClassPage.getPwd() );
		int userid=listClassPage.getId();
		Examinee examinee=this.examineeBiz.getExamineeDetail(userid).get(0);
		String oldpwd=examinee.getPassword();
		if(newpwd.equals(oldpwd)){
			jsonStr = super.writeJson(0, null);
		}else {
			jsonStr = super.writeJson(1, "修改失败");
		}
		JsonUtil.jsonOut(jsonStr);
	}

	// 修改考生密码
	public void updatepassword() throws IOException {
		String className = listClassPage.getClassName();
		String newpwd = Encrypt.md5AndSha(   listClassPage.getPwd() );
		String examineeName = listClassPage.getName();
		try {
			Examinee examinee = examineeBiz.getExaminee(examineeName, null, className);
			examinee.setPassword(newpwd);
			int result = examineeBiz.updateExaminee(examinee);

			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "修改失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "修改出现异常" + e.toString());
		} finally {
			JsonUtil.jsonOut(jsonStr);
		}
	}

	// 修改考生姓名
	public void updateExaminee() {
		String name = listClassPage.getName();
		String className = listClassPage.getClassName();
		String oldname = listClassPage.getOldname();
		String tel = listClassPage.getTel();
		String idCard = listClassPage.getIdCard();
		Examinee examinee = examineeBiz.getExaminee(oldname, null, className);
		examinee.setName(name);
		examinee.setIdCard(idCard);
		examinee.setTel(tel);
		// 根据班级名查到班级编号
		try {
			// 根据班级编号和学生旧姓名查出单个学生的信息
			// ExamineePK examineePK=examinee.getPk();
			int result = examineeBiz.updateExaminee(examinee);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "修改失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "修改出现异常" + e.toString());

		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	// 根据班级编号显示同一班级的学生信息
		public void showExamineeList() {
			String className = listClassPage.getClassName();
			try {
				List<Examinee> examinee = examineeBiz.findAllStuNameByClassName(className);
				List<ListClassPage> list = new ArrayList<ListClassPage>();
				if (examinee != null && examinee.size() > 0) {
					for (int i = 0,len=examinee.size(); i < len; i++) {
						ListClassPage listClassPage = new ListClassPage();
						listClassPage.setName(examinee.get(i).getName());
						listClassPage.setClassName(examinee.get(i).getExamineeClass().getEditionId().toString());
						//传回去的实际是学生状态  （修改结果）
						listClassPage.setIdCard(examinee.get(i).getStudentStatus().toString());
						listClassPage.setId(examinee.get(i).getId());
						listClassPage.setTel(examinee.get(i).getTel());
						list.add(i, listClassPage);
					}
				}
				jsonStr = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				jsonStr = super.writeJson(0, "查询出现异常" + e.toString());
				logger.error(e);
				try {
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e1) {
					logger.error(e);
					e1.printStackTrace();
				}
			}
		}
	// 显示班级列表
	public void showClassList() {
		try {
			String semester = listClassPage.getSemester();
			List<ExamineeClass> examineeClass = examineeClassBiz.findExamineeClassBySemester(semester);
			List<ListClassPage> list = new ArrayList<ListClassPage>();
			if (examineeClass != null && examineeClass.size() > 0) {
				for (int i = 0,len=examineeClass.size(); i < len; i++) {
					ListClassPage listClassPage = new ListClassPage();
					BeanUtils.copyProperties(examineeClass.get(i), listClassPage);
					list.add(i, listClassPage);
					int studentCount = examineeClassBiz.searchExamineeCount(examineeClass.get(i).getId());
					list.get(i).setStudentCount(studentCount);
				}
				jsonStr = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
				JsonUtil.jsonOut(jsonStr);
			} else {
				jsonStr = super.writeJson(0, "查询数据库失败");
				JsonUtil.jsonOut(jsonStr);
			}
		} catch (Exception e) {
			logger.error(e);
			jsonStr = super.writeJson(0, "出现异常：" + e.toString() + "请与管理员联系");
			e.printStackTrace();
		}
	}
	
	//显示相同 版本的班级
		public void showSameEditList(){
			String className = listClassPage.getClassName();
			
				String editName=editionBiz.searchEditionName(Integer.parseInt(className));
				try {
				List<ExamineeClass> examineeClass = examineeClassBiz.findExamineeClassByEdit(editName);
				
				List<ListClassPage> list = new ArrayList<ListClassPage>();
				if (examineeClass != null && examineeClass.size() > 0) {
					for (int i = 0, len=examineeClass.size(); i <len; i++) {
						ListClassPage listClassPage = new ListClassPage();
						BeanUtils.copyProperties(examineeClass.get(i), listClassPage);
						list.add(i, listClassPage);
					
					}
					jsonStr = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
					JsonUtil.jsonOut(jsonStr);
				} else {
					jsonStr = super.writeJson(0, "查询数据库失败");
					JsonUtil.jsonOut(jsonStr);
				}
				
				jsonStr = super.writeJson(0, examineeClass);
				//JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				jsonStr = super.writeJson(0, "出现异常：" + e.toString() + "请与管理员联系");
				e.printStackTrace();
				
			}finally {
				try {
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e) {
					logger.error(e);
				}
			}
			
		}
		//转班操作
		public void changeClass(){
			Integer id = listClassPage.getId();
			String className=listClassPage.getClassName();
			String userName=listClassPage.getExamineeNames();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date=df.format(new Date()).toString();
			
			try {
				ChangeClass changeClass=new ChangeClass(); 
				changeClass.setUser(userName);
				changeClass.setAuditor(userName);
				changeClass.setChangeClassDate(date);
				
				//插入转班信息
				//1.根据班级名获取要转到的新班级
				ExamineeClass ecNew=this.examineeClassBiz.findExamineeClassByName(className);
				changeClass.setNewClassId(ecNew.getId());
				changeClass.setNewClassName(ecNew.getClassName());
				//changeClass.setNewEdition(ecNew.getEditionId());
				//2.根据学生id获取以前班级的信息
				ExamineeClass ecOld=this.examineeClassBiz.findExamineeClassByStuId(id);
				changeClass.setOldClassId(ecOld.getId());
				changeClass.setOldClassName(ecOld.getClassName());
				changeClass.setExamineeId(id);
				//changeClass.setUser(user);
				//3.方向
				String edition=this.editionBiz.searchEditionName(ecOld.getEditionId());
				changeClass.setNewEdition(edition);
				changeClass.setOldEdition(edition);
				//3.插入classchange表
				changeClass.setType("转班");
				/*changeClass.setNewContractedAmount(0);
				changeClass.setOldContractedAmount(0);*/
				changeClass.setNewTuitionId(0);
				changeClass.setOldTuitionId(0);
				changeClass.setIdentify(1);
				
				this.changeClassBiz.insertChangeClass(changeClass);
				
				this.examineeClassBiz.updateClassSQL(id, className);
				
				
				jsonStr = super.writeJson(0, null);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				jsonStr = super.writeJson(1, "转班失败");
			}finally {
				try {
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
			
		}

	// 删除班级
	public void deleteClass() {
		try {
			Integer id = listClassPage.getId();
			Integer result = examineeClassBiz.deleteExaminee(id);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "删除失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "删除出现异常" + e);
		} finally {
			try {
				
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	

	//修改班级注意事项
	public void addClassNotice() {
		try {
			Integer id = listClassPage.getId();
			String notice=listClassPage.getRemark();
			Integer result = examineeClassBiz.addClassNotice(id, notice);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "添加失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "添加出现异常" + e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	

	//根据班级id获取班级注意事项
	public void showClassNotice() {
		try {
			Integer id = listClassPage.getId();
			
			String result = examineeClassBiz.getClassNotice(id);
				jsonStr = super.writeJson(0, result);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "显示出现异常" + e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	
	//获取所有的班级注意事项
	public void findAllClassNotice(){
		
		try {
			List<String> list=this.examineeClassBiz.getAllClassNotice();
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "显示出现异常" + e);
		}finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	

	//通过班级名获得cid
	public void findClassById(){
		String examClass = request.getParameter("examClass").toString().trim();
		try {
			List<ExamineeClass> list=this.examineeClassBiz.getInfo(examClass);
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "显示出现异常" + e);
		}finally {

			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public ListClassPage getModel() {
		return listClassPage;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

}
