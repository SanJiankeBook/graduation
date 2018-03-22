package com.yc.webexam.actions;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ExaminInterviewRecordBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.po.ExaminInterviewRecord;
import com.yc.po.ExamineeClass;
import com.yc.po.LabPaper;
import com.yc.po.Work;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.utils.ExamUtil;
import com.yc.utils.JsonUtil;
import com.yc.vo.ChapterQuestionCount;
import com.yc.vo.Page;
import com.yc.vo.TeacherInterviewdetail;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;

/**
 * 访谈记录的action
 * @author pengtao
 */
public class ExaminInterviewRecordAction extends BaseAction
{
	private static final long serialVersionUID = 1646406592963558319L;
	private static final Logger logger = Logger.getLogger(ExaminInterviewRecordAction.class);
	
	private String className; // 班级名
	private String teacherName; // 教师名
	private String studentName;//学生名
	private String remark;//备注
	private String content;//内容
	private String interviewAddress;//地址
	private Date pdate; // 开讲日期
	
	private int displayRows; // 要显示的页数
	private int pageNume; // 当前请求的第几页数
	private int id; //唯一标志号
	private Date startpdate;
	private Date endpdate;
	
	private String jsonStr;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private ExaminInterviewRecordBiz examinInterviewRecordbiz;
	private HttpSession mysession = request.getSession();
	

	public Date getStartpdate() {
		return startpdate;
	}
	public void setStartpdate(Date startpdate) {
		this.startpdate = startpdate;
	}
	public Date getEndpdate() {
		return endpdate;
	}
	public void setEndpdate(Date endpdate) {
		this.endpdate = endpdate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExaminInterviewRecordBiz getExaminInterviewRecordbiz() {
		return examinInterviewRecordbiz;
	}
	@Resource(name="examinInterviewRecordBiz")
	public void setExaminInterviewRecordBiz(ExaminInterviewRecordBiz examinInterviewRecordbiz) {
		this.examinInterviewRecordbiz = examinInterviewRecordbiz;
	}
	public int getDisplayRows() {
		return displayRows;
	}
	public void setDisplayRows(int displayRows) {
		this.displayRows = displayRows;
	}
	public int getPageNume() {
		return pageNume;
	}
	public void setPageNume(int pageNume) {
		this.pageNume = pageNume;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInterviewAddress() {
		return interviewAddress;
	}
	public void setInterviewAddress(String interviewAddress) {
		this.interviewAddress = interviewAddress;
	}
	
	/**
	 *以班级和时间条件显示数据
	 * @throws IOException 
	 */
	public void findClassWorkdetail() throws IOException{
		int year=Integer.valueOf(request.getParameter("year"));//获取年份
		int month=Integer.valueOf(request.getParameter("month"));//获取月份
		int className=Integer.valueOf(request.getParameter("classid"));//获取班级id
		List<ExaminInterviewRecord> listwork=this.examinInterviewRecordbiz.findWorkdetail(className, year, month);
		if(listwork!=null){
			jsonStr = super.writeJson(0, listwork);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 * 一开始从文件中获取所有数据
	 * @throws IOException 
	 */
	public void findClassWorkpool() throws IOException{
		String year=request.getParameter("year");//获取年份
		String month=request.getParameter("month");//获取月份
		List<Workdetail> listwork=this.examinInterviewRecordbiz.getclassWorkdetail(year, month,null);

		if(listwork!=null){
			jsonStr = super.writeJson(0, listwork);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
	}
	
	/**
	 * 添加访谈记录
	 */
	public void addInfo(){
		String jsonStr = "";
		try {
				ExaminInterviewRecord er = new ExaminInterviewRecord();
				er.setClassName(className);
				er.setContent(content);
				er.setInterviewAddress(interviewAddress);
				er.setPdate(pdate);
				er.setRemark(remark);
				er.setStudentName(studentName);
				er.setTeacherName(teacherName);
//				Page<ExaminInterviewRecord> page = new Page<ExaminInterviewRecord>();
//				page.setPageSize(displayRows);
//				page.setCurrentPage(pageNume);
				
				this.examinInterviewRecordbiz.addinfo( er);
				jsonStr = super.writeJson(0, "添加成功");

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "添加失败!");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	/**
	 * 根据条件查询出所有符合条件的访问记录
	 */
	public void showInfo(){
		String jsonStr = "";
		try {
				ExaminInterviewRecord er = new ExaminInterviewRecord();
				er.setClassName(className);
				er.setContent(content);
				er.setInterviewAddress(interviewAddress);
				er.setPdate(pdate);
				er.setRemark(remark);
				er.setStudentName(studentName);
				er.setTeacherName(teacherName);
				er.setStartpdate(startpdate);
				er.setEndpdate(endpdate);
				Page<ExaminInterviewRecord> page = new Page<ExaminInterviewRecord>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				//查询出所有符合条件的数据
				page=this.examinInterviewRecordbiz.findinfo(page, er);
				jsonStr = super.writeJson(0, page);

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败!");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	

	/**
	 * 根据条件查询出符合条件的访问记录
	 */
	public void showInfoById(){
		String jsonStr = "";
		try {
			
				List list=this.examinInterviewRecordbiz.showinfo(id);
				jsonStr = super.writeJson(0, list);

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败!");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	//页面一加载显示访谈记录
	public void getTeacherInterview() throws IOException{
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		List<TeacherInterviewdetail> listwork=this.examinInterviewRecordbiz.getTeacherInterviewdetail(year, month);
		if(listwork!=null){
			jsonStr = super.writeJson(0, listwork);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);

	}
	
	//根据教师名字和日期查找
	public void getTeacherInterviewByName() throws IOException{
		String teachername =request.getParameter("teachername");
		int year=Integer.valueOf(request.getParameter("year"));
		int month=Integer.valueOf(request.getParameter("month"));
		List<ExaminInterviewRecord> list=this.examinInterviewRecordbiz.getTeacherInterviewByName(teachername, year, month);
		if(list!=null){
			jsonStr = super.writeJson(0, list);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
		}

}
