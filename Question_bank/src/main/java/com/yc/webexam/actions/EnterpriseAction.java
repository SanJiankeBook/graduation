package com.yc.webexam.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.EnterpriseBiz;
import com.yc.po.EnterprisePage;
import com.yc.po.Work;
import com.yc.utils.JsonUtil;

@SuppressWarnings("serial")
public class EnterpriseAction  extends BaseAction implements ServletRequestAware, ServletResponseAware{

	private static final Logger logger = Logger.getLogger(EnterpriseAction.class);
	
	@Resource(name = "enterpriseBiz")
	private EnterpriseBiz enterpriseBiz;
	
	public EnterpriseBiz getEnterpriseBiz() {
		return enterpriseBiz;
	}

	public void setEnterpriseBiz(EnterpriseBiz enterpriseBiz) {
		this.enterpriseBiz = enterpriseBiz;
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
	
	
	public void findenterprisedetail() throws IOException{
		int eid=Integer.valueOf(request.getParameter("eid"));
		EnterprisePage enterprisePage=this.enterpriseBiz.findEnterprisebyeid(eid);
		try {
			if(enterprisePage!=null){
				jsonStr = super.writeJson(0, enterprisePage);
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
	
	public void findenterprise() throws IOException{
		String addr=request.getParameter("addr");
		String name=request.getParameter("name");
		List<EnterprisePage> list= this.enterpriseBiz.findEnterprise(addr,name);
		List<EnterprisePage> enterprisePages=new ArrayList<EnterprisePage>();
		for(EnterprisePage enterprisePage:list){
			enterprisePage.setWorknum(this.enterpriseBiz.findstudentcount(enterprisePage.getEid()));
			enterprisePages.add(enterprisePage);
		}
		try {
			if(enterprisePages.size()>0){
				jsonStr = super.writeJson(0, enterprisePages);
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
	
}
