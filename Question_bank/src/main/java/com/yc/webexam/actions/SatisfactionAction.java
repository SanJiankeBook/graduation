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
import org.apache.struts2.ServletActionContext;

import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SatisfactionBiz;
import com.yc.biz.SatisfactionDetailBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.po.Satisfaction;
import com.yc.po.SatisfactionDetail;
import com.yc.po.SystemUser;
import com.yc.utils.JsonUtil;



public class SatisfactionAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(SatisfactionDetailAction.class);
	
	private static final long serialVersionUID = 1L;

	private String jsonStr = null;
	
	@Resource(name = "satisfactionBiz")
	private SatisfactionBiz satisfactionBiz;
	
	@Resource(name = "systemUserBiz")
	private SystemUserBiz SystemUserBiz;
	
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	
	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;
	
	@Resource(name = "satisfactionDetailBiz")
	private SatisfactionDetailBiz satisfactionDetailBiz;

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
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public SatisfactionBiz getSatisfactionBiz() {
		return satisfactionBiz;
	}

	public void setSatisfactionBiz(SatisfactionBiz satisfactionBiz) {
		this.satisfactionBiz = satisfactionBiz;
	}
	

	public SystemUserBiz getSystemUserBiz() {
		return SystemUserBiz;
	}

	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		SystemUserBiz = systemUserBiz;
	}

	
	public ExamineeClassBiz getExamineeClassBiz() {
		return examineeClassBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}
	

	public ExamineeBiz getExamineeBiz() {
		return examineeBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}
	
	public SatisfactionDetailBiz getSatisfactionDetailBiz() {
		return satisfactionDetailBiz;
	}

	public void setSatisfactionDetailBiz(SatisfactionDetailBiz satisfactionDetailBiz) {
		this.satisfactionDetailBiz = satisfactionDetailBiz;
	}

	// 打开满意度调查表
	public void openSatisfaction() {
		try {
			String teacherid = request.getParameter("teacherid");
			String teacherName = request.getParameter("teacherName");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String openteacherName = request.getParameter("openteacherName");
			String className = request.getParameter("className");
			
			List<SystemUser> list=this.SystemUserBiz.findSystemUserByName(openteacherName);
			Integer openteacherid=list.get(0).getId();
			Satisfaction satisfaction=new Satisfaction();
			satisfaction.setClassName(className);
			satisfaction.setTeacherName(teacherName);
			satisfaction.setTeacherid(Integer.parseInt(teacherid));
			satisfaction.setOpenYear(year);
			satisfaction.setOpenMonth(month);
			satisfaction.setStartTime(startDate);
			satisfaction.setEndTime(endDate);
			satisfaction.setOpenPersonId(openteacherid);
			satisfaction.setOpenPersonName(openteacherName);
			Integer result=this.satisfactionBiz.insertSatisfaction(satisfaction);
			
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	//显示老师满意调查表数据
	public void showTeacherSatisfaction(){
		
		String teacherName = request.getParameter("teacherName");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		try {
			
			Satisfaction satisfaction=new Satisfaction();
			satisfaction.setOpenYear(year);
			satisfaction.setTeacherName(teacherName);
			satisfaction.setOpenMonth(month);
			List<Satisfaction> list=this.satisfactionBiz.showTeacherSatisfaction(satisfaction);
			//将班级人数和 样本数传到前台 显示
			if(list.size()>0){
				for(Satisfaction li:list){
					int id=li.getId();
					Integer sampleCount =this.satisfactionDetailBiz.satisDetailCount(id);
					li.setOpenPersonId(sampleCount);
					Integer classPepoleNumber=this.examineeClassBiz.searchSingleClassExamineeCountByClassName(li.getClassName());
					li.setTeacherid(classPepoleNumber);
					
				}
			}
			
			
			if (list!=null) {
				jsonStr = super.writeJson(0, list);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		}finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	
	//显示班级满意调查表数据
	public void showClassSatisfaction(){
		
		String className = request.getParameter("className");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		
		try {
			Satisfaction satisfaction=new Satisfaction();
			satisfaction.setOpenYear(year);
			satisfaction.setClassName(className);
			satisfaction.setOpenMonth(month);
			List<Satisfaction> list=this.satisfactionBiz.showClassSatisfaction(satisfaction);
			//将班级人数和 样本数传到前台 显示
			if(list.size()>0){
				for(Satisfaction li:list){
					int id=li.getId();
					Integer sampleCount =this.satisfactionDetailBiz.satisDetailCount(id);
					li.setOpenPersonId(sampleCount);
					Integer classPepoleNumber=this.examineeClassBiz.searchSingleClassExamineeCountByClassName(li.getClassName());
					li.setTeacherid(classPepoleNumber);
					
				}
			}
			
			if (list!=null) {
				jsonStr = super.writeJson(0, list);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		}finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	
	
	//判断是否开启了调查表
	public void isOpenSatisfaction(){
		String time=request.getParameter("date");
	
		String userName=request.getParameter("userName");
		String userid=request.getParameter("userid");
		try {
			List<Satisfaction> list=this.satisfactionBiz.isOpenSatisfaction(time,userid);
			//List<SatisfactionDetail> detailList=new ArrayList<SatisfactionDetail>();
			List detailList=new ArrayList();
			List<Satisfaction> finalList=new ArrayList<Satisfaction>();//最终还有多少满意度没填
			//int flag=0;//判断 学生是否所有的调查表都填写了
			if(list.size()>0){//开启了满意度调查表
				for(Satisfaction sa:list){//判断是否填写过
					detailList=this.satisfactionDetailBiz.isWriteSatisfactionDetail(userid, sa.getId());
					if(detailList.size()<=0){
						//flag++;//填写过了
						finalList.add(sa);
					}
					
				}
				/*for(Satisfaction sa:list){
					detailList=this.satisfactionDetailBiz.isWriteSatisfactionDetail(userid, sa.getId());
					if(detailList.size()>0){
						flag++;
					}
				}*/
			}
			/*if(flag!=list.size()){
				detailList=null;
			}*/
			
			if (list!=null && finalList!=null && !"".equals(finalList)) {
				jsonStr = super.writeJson(0, finalList);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		}finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	//满意度调查表汇总
	public void findSummaryInfomation(){
		String month=request.getParameter("month");
		String year=request.getParameter("year");
		try {
			List<Satisfaction> list=this.satisfactionBiz.findSummaySatisInfo(year,month);
			if (list!=null) {
				jsonStr = super.writeJson(0, list);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	
	
	
	//查找满意不满意的细节
		public void getEvaluateDetail() {
			try {
			
				String month=request.getParameter("month");
				String year=request.getParameter("year");
				String id=request.getParameter("id");
				Integer ids=Integer.parseInt(id);
				
				try {
					Satisfaction list=this.satisfactionBiz.findSummaySatisInfoDetail(year, month, ids);
					if (list!=null) {
						jsonStr = super.writeJson(0, list);
					} else {
						jsonStr = super.writeJson(1, null);
					}
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}finally {
					try {
						JsonUtil.jsonOut(jsonStr);
					} catch (IOException e) {
						logger.error(e);
						e.printStackTrace();
					}
				}
				
				
				
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				jsonStr = super.writeJson(1, "出现异常");
			} finally {
				try {
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
	
}
