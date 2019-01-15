package com.yc.webexam.actions;

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

import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SatisfactionDetailBiz;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.SatisfactionDetail;
import com.yc.utils.JsonUtil;



public class SatisfactionDetailAction extends BaseAction implements ModelDriven<SatisfactionDetail>{
	
	private static final Logger logger = Logger.getLogger(SatisfactionDetailAction.class);
	private static final long serialVersionUID = 1L;

	private String jsonStr = null;
	
	@Resource(name = "satisfactionDetailBiz")
	private SatisfactionDetailBiz satisfactionDetailBiz;
	
	
	private SatisfactionDetail satisfactionDetail;
	
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	
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


	public SatisfactionDetailBiz getSatisfactionDetailBiz() {
		return satisfactionDetailBiz;
	}

	public void setSatisfactionDetailBiz(SatisfactionDetailBiz satisfactionDetailBiz) {
		this.satisfactionDetailBiz = satisfactionDetailBiz;
	}

	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;

	
	public SatisfactionDetail getSatisfactionDetail() {
		return satisfactionDetail;
	}

	public void setSatisfactionDetail(SatisfactionDetail satisfactionDetail) {
		this.satisfactionDetail = satisfactionDetail;
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
	
	private SatisfactionDetail satisfactionDetails=new SatisfactionDetail();
	
	 //接口要求的方法  
    public SatisfactionDetail getModel() {  
        // TODO Auto-generated method stub  
        return satisfactionDetails;  
    }  

	//显示老师满意度细节表内容
	public void showTeacherSatisfactionDetail() {
		try {
			String id=request.getParameter("id");
			String className = request.getParameter("className");
			
			List<SatisfactionDetail> list=this.satisfactionDetailBiz.showSatisfactionDetail(Integer.parseInt(id));
			Integer sampleCount =this.satisfactionDetailBiz.satisDetailCount(Integer.parseInt(id));//样本总数
			
			Integer classPepoleNumber=this.examineeClassBiz.searchSingleClassExamineeCountByClassName(className);
			Integer len=list.size();
			for(SatisfactionDetail li:list){
				li.setSampleCount(sampleCount);
				li.setClassPeopleCount(classPepoleNumber);
				
			}
			
			jsonStr = super.writeJson(0, list);
			
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

	//显示班级满意度细节表数据
	public void showClassSatisfactionDetail() {
		try {
		//调查表id
			String id=request.getParameter("id");
			String className = request.getParameter("className");
			
			List<SatisfactionDetail> list=this.satisfactionDetailBiz.showSatisfactionDetail(Integer.parseInt(id));
			
			Integer sampleCount =this.satisfactionDetailBiz.satisDetailCount(Integer.parseInt(id));
			
			Integer classPepoleNumber=this.examineeClassBiz.searchSingleClassExamineeCountByClassName(className);
			Integer len=list.size();
			for(SatisfactionDetail li:list){
				li.setSampleCount(sampleCount);
				li.setClassPeopleCount(classPepoleNumber);
				
			}
	
			jsonStr = super.writeJson(0, list);
			
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
	
	//显示班级总人数
	public void showClassPeopleCount() {
		try {
		
			String className = request.getParameter("className");
			
			Integer classPepoleNumber=this.examineeClassBiz.searchSingleClassExamineeCountByClassName(className);
			
			jsonStr = super.writeJson(0, classPepoleNumber);
			
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
	
	
	
	//学生端 调查表 插入数据库
	public void insertSatisfactionDetail() {
		try {
			
			
			/*String a1=request.getParameter("a1");
			String a2 = request.getParameter("a2");
			String a3=request.getParameter("a3");
			String a4=request.getParameter("a4");
			String a5 = request.getParameter("a5");String a6=request.getParameter("a6");
			String a7 = request.getParameter("a7");String a8=request.getParameter("a8");
			String a9 = request.getParameter("a9");String a10=request.getParameter("a10");
			String a11 = request.getParameter("a11");String a12=request.getParameter("a12");
			String a13 = request.getParameter("a13");String a14=request.getParameter("a14");
			String a15 = request.getParameter("a15");
			String a16=request.getParameter("a16");
			String a17 = request.getParameter("a17");String a18=request.getParameter("a18");
			String a19 = request.getParameter("a19");String a20=request.getParameter("a20");
			String a21=request.getParameter("a21");
			String a22 = request.getParameter("a22");
			String a23=request.getParameter("a23");
			String a24=request.getParameter("a24");
			String a25 = request.getParameter("a25");String a26=request.getParameter("a26");
			String satisfaction=request.getParameter("satisfaction");String unsatisfaction=request.getParameter("unsatisfaction");
			String totalScore=request.getParameter("totalScore");String userName=request.getParameter("className");
			String said=request.getParameter("said");
			String teacherName=request.getParameter("teacherName");
			SatisfactionDetail satisfactionDetail=new SatisfactionDetail();
			satisfactionDetail.setA1(Integer.parseInt(a1));satisfactionDetail.setA2(Integer.parseInt(a2));satisfactionDetail.setA3(Integer.parseInt(a3));satisfactionDetail.setA4(Integer.parseInt(a4));
			satisfactionDetail.setA5(Integer.parseInt(a5));satisfactionDetail.setA6(Integer.parseInt(a6));satisfactionDetail.setA7(Integer.parseInt(a7));satisfactionDetail.setA8(Integer.parseInt(a8));
			satisfactionDetail.setA9(Integer.parseInt(a9));satisfactionDetail.setA10(Integer.parseInt(a10));satisfactionDetail.setA11(Integer.parseInt(a11));satisfactionDetail.setA12(Integer.parseInt(a12));
			satisfactionDetail.setA13(Integer.parseInt(a13));satisfactionDetail.setA14(Integer.parseInt(a14));satisfactionDetail.setA15(Integer.parseInt(a15));satisfactionDetail.setA16(Integer.parseInt(a16));
			satisfactionDetail.setA17(Integer.parseInt(a17));satisfactionDetail.setA18(Integer.parseInt(a18));satisfactionDetail.setA19(Integer.parseInt(a19));satisfactionDetail.setA20(Integer.parseInt(a20));
			satisfactionDetail.setA21(Integer.parseInt(a21));satisfactionDetail.setA22(Integer.parseInt(a22));satisfactionDetail.setA23(Integer.parseInt(a23));satisfactionDetail.setA24(Integer.parseInt(a24));
			satisfactionDetail.setA25(Integer.parseInt(a25));satisfactionDetail.setA26(Integer.parseInt(a26));satisfactionDetail.setTotalScore(Integer.parseInt(totalScore));
			satisfactionDetail.setSatisfaction(satisfaction);
			satisfactionDetail.setUnsatisfaction(unsatisfaction);
			satisfactionDetail.setSaid(Integer.parseInt(said));
			satisfactionDetail.setTeacherName(teacherName);
			List<Examinee> list=this.examineeBiz.searchExamineeByExamineeName(userName);
			
			satisfactionDetail.setStudentid(list.get(0).getId());
			this.satisfactionDetailBiz.insertSatisfaction(satisfactionDetail);
			*/
			String classname=(String) mysession.getAttribute("examClass");
			ExamineeClass examineeClass=this.examineeClassBiz.findExamineeClassByName(classname);
			List<Examinee> list=this.examineeBiz.searchExamineeByExamineeName(satisfactionDetails.getClassName(),examineeClass.getId()+"");
			
			satisfactionDetails.setStudentid(list.get(0).getId());
			this.satisfactionDetailBiz.insertSatisfaction(satisfactionDetails);
			
			jsonStr = super.writeJson(0, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
