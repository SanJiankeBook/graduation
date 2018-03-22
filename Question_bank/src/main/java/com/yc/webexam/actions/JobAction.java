package com.yc.webexam.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.yc.biz.ExamineeBiz;
import com.yc.biz.EnterpriseBiz;
import com.yc.biz.JobBiz;
import com.yc.po.Examinee;
import com.yc.po.Job;
import com.yc.utils.JedisUtils;
import com.yc.utils.JsonUtil;
import com.yc.utils.MapConfig;
import com.yc.vo.ExamineePage;
import com.yc.vo.JobPool;

public class JobAction  extends BaseAction implements ServletRequestAware, ServletResponseAware{

	private static final Logger logger = Logger.getLogger(EnterpriseAction.class);


	@Resource(name = "jobBiz")
	private JobBiz jobBiz;

	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;



	public ExamineeBiz getExamineeBiz() {
		return examineeBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		examineeBiz = examineeBiz;
	}

	public JobBiz getJobBiz() {
		return jobBiz;
	}

	public void setJobBiz(JobBiz jobBiz) {
		this.jobBiz = jobBiz;
	}

	private String jsonStr;
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
		}
	}
	
	public void findJobByProvinceTotal() throws IOException{
		String province=request.getParameter("province");
		int total=this.jobBiz.findSalaryByProvincetotal(province);
		jsonStr = super.writeJson(0, total);
		JsonUtil.jsonOut(jsonStr);
	}

	public void findJobByProvince() throws IOException{
		String province=request.getParameter("province");
		String page=request.getParameter("page");
		String row=request.getParameter("row");
		if(!(page!=null&&!page.equals("")&&row!=null&&!row.equals(""))){
			page="1";
			row="10";
		}
		List<Job> list=this.jobBiz.findSalaryByProvince(province, page, row);
		int total=this.jobBiz.findSalaryByProvincetotal(province);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", total);
		try {
			if(list.size()>0){
				jsonStr = super.writeJson(0, map);
			}else{
				jsonStr = super.writeJson(1, "无数据");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);
	}


	public void findexamineejob() throws IOException{
		int eid=Integer.valueOf(request.getParameter("eid"));
		List<Job> list=this.jobBiz.findJobByeid(eid);
		try {
			if(list.size()>0){
				jsonStr = super.writeJson(0, list);
			}else{
				jsonStr = super.writeJson(1, "查询失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);
	}

	public void findsalarystar() throws IOException{
		List<Job> list=this.jobBiz.findStudentStar();
		try {
			if(list.size()>0){
				jsonStr = super.writeJson(0, list);
			}else{
				jsonStr = super.writeJson(1, "查询失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);
	}


	public void findsalarybyyear() throws IOException{
		int year=Integer.valueOf(request.getParameter("year"));
		List<Job> list=this.jobBiz.findSalaryByYear(year);
		try {
			if(list.size()>0){
				jsonStr = super.writeJson(0, list);
			}else{
				jsonStr = super.writeJson(1, "查询失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}

	public void findJobInfo() throws Exception{
		JedisUtils ju=JedisUtils.getInstance();
		Map<String,Integer> map =MapConfig.getInstance();
		Set<String> keys=map.keySet();
		List<JobPool> list=new ArrayList<JobPool>();
		for(String key:keys){
			JobPool jobPool=(JobPool) ju.get(key);
			list.add(jobPool);
		}
		try {
			if(list.size()>0){
				jsonStr = super.writeJson(0, list);
			}else{
				jsonStr = super.writeJson(1, "查询失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);

	}



	public void findexamineedetail() throws IOException{
		int examineeid=Integer.valueOf(request.getParameter("examineeid"));
		List<Examinee> examinees=this.examineeBiz.getExamineeDetail(examineeid);
		ExamineePage examineePage=new ExamineePage();
		examineePage.setEmail(examinees.get(0).getEmail());
		examineePage.setName(examinees.get(0).getName());
		examineePage.setQq(examinees.get(0).getQq());
		examineePage.setWeixin(examinees.get(0).getWeixin());
		examineePage.setTel(examinees.get(0).getTel());
		try {
			if(examineePage!=null){
				jsonStr = super.writeJson(0, examineePage);
			}else{
				jsonStr = super.writeJson(1, "无数据");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);
	}


}
