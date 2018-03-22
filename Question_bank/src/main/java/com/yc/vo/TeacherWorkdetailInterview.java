package com.yc.vo;

import java.io.Serializable;

public class TeacherWorkdetailInterview  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String className;
	
	private Integer total;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "TeacherWorkdetail [className=" + className + ", total=" + total + "]";
	}
	
	
}
