package com.yc.webexam.actions;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.yc.biz.TempBiz;
import com.yc.utils.JsonUtil;

public class TempAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	@Resource(name = "pointInfoBiz")
	private TempBiz tempBiz;
	private String className;
	private Integer subjectId;

	/**
	 * 根据classname和课程名字来查找信息
	 */
	public void findTempInfoSidAndCname() {
		String jsonStr = "";
		try {
			List<String> list = tempBiz.findTempInfoByClassNameAndSubjectId(className, subjectId);
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setTempBiz(TempBiz tempBiz) {
		this.tempBiz = tempBiz;
	}

}
