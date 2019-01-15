package com.yc.webexam.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.yc.utils.JsonProtocol;

/**
 * 基础ACTION,其他ACTION继承此ACTION来获得writeJson和ActionSupport的功能
 * 
 * @version 1.1 2013/4/3
 * @author jp
 */
public class BaseAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = -1666495965099449593L;
	protected Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 将对象转换成JSON字符串，并返回
	 * @param respCode
	 * @param obj
	 * @return JSON字符串
	 */
	public String writeJson(int respCode, Object obj) {
		JsonProtocol jsonProtocol = null;
		// 请求成功的包装方式
		if (respCode == 0) {
			jsonProtocol = new JsonProtocol(respCode, null, obj);
		} else if (respCode == 1) { // 请求失败的包装方式
			jsonProtocol = new JsonProtocol(respCode, (String) obj, null);
		}
		return JSON.toJSONStringWithDateFormat(jsonProtocol, "yyyy-MM-dd HH:mm:ss");
	}

	private Integer page; // 第几页
	private Integer rows; // 每页多少条
	private String sort; // 排序的列名
	private String order; // 排序的方法

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
