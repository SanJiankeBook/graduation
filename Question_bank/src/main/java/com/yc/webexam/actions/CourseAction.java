package com.yc.webexam.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.yc.biz.EditionBiz;
import com.yc.po.Edition;
import com.yc.utils.JsonUtil;

public class CourseAction extends ActionSupport implements ServletResponseAware {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CourseAction.class);

	private HttpServletResponse response;
	// private Map<String,Object> session;

	private PrintWriter out;

	@Resource(name = "editionBiz")
	private EditionBiz editionBiz;

	private String result;

	private Edition edition = new Edition();

	// @Override
	// public void setSession(Map<String, Object> session) {
	// this.session = session;
	//
	// }

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	// 显示版本
	public void edition() {
		String jsonStr = "";
		List<Edition> currentUse=null;
		try {
			//out = response.getWriter();
			 currentUse = editionBiz.searchAllEdition();
			for (int i = 0,len=currentUse.size(); i < len; i++) {
				currentUse.get(i).setLabQuestions(null);
				currentUse.get(i).setSubjects(null);
			}
			JsonUtil.jsonOut(JSON.toJSONString(currentUse));
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			try {
				JsonUtil.jsonOut(JSON.toJSONString(currentUse));
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	// 更改当前正在使用的版本
	public void changeCurrentUse() throws IOException {
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			int id = Integer.parseInt(req.getParameter("id"));
			editionBiz.updateCurrentUse(id);
			JsonUtil.jsonOut(JSON.toJSONString(1));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			JsonUtil.jsonOut(JSON.toJSONString(0));
		}
	}

	// 添加版本
	public void addEdition() throws IOException {

		HttpServletRequest req = ServletActionContext.getRequest();
		String editionName = req.getParameter("editionName");

		edition.setEditionName(editionName);
		edition.setCurrentUse(0);

		int results = (Integer) editionBiz.addEdition(edition);

		if (results > 0) {
			JsonUtil.jsonOut(JSON.toJSONString(1));
		} else {
			JsonUtil.jsonOut(JSON.toJSONString(0));
		}
	}

	// 更新当前版本
	public void updateCurrent() throws IOException {
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			Integer editionId = Integer.parseInt(req.getParameter("editionId"));
			String editionName = req.getParameter("editionName");
			Integer currentUse = Integer.parseInt(req.getParameter("currentUse"));

			edition.setId(editionId);
			edition.setEditionName(editionName);
			edition.setCurrentUse(currentUse);

			editionBiz.updateEdition(edition);
			JsonUtil.jsonOut(JSON.toJSONString(1));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			JsonUtil.jsonOut(JSON.toJSONString(0));
		}
	}

	// 删除版本
	public void deleteEdition() throws IOException {
		HttpServletRequest req = ServletActionContext.getRequest();
		Integer editionId = Integer.parseInt(req.getParameter("editionId"));
		edition.setId(editionId);
		try {
			editionBiz.deleteEdition(edition);
			JsonUtil.jsonOut(JSON.toJSONString(1));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			JsonUtil.jsonOut(JSON.toJSONString(0));
		}
	}

	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}

}
