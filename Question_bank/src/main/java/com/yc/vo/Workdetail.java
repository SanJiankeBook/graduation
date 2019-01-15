package com.yc.vo;

import java.io.Serializable;

public class Workdetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer examineeclassid;   //班级编号

	private String className;  //班级名
	
	private Integer workcount;  //作业布置次数
	
	private Integer classcount;  //班级人数
	
	private Integer checkcount=0; //检查人次
	
	private String completionrate;  //完成率
	
	private String workname;  //作业名  用于教师作业查询
	



	public String getCompletionrate() {
		return completionrate;
	}

	public void setCompletionrate(String completionrate) {
		this.completionrate = completionrate;
	}

	public String getWorkname() {
		return workname;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}

	public Integer getExamineeclassid() {
		return examineeclassid;
	}

	public void setExamineeclassid(Integer examineeclassid) {
		this.examineeclassid = examineeclassid;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getWorkcount() {
		return workcount;
	}

	public void setWorkcount(Integer workcount) {
		this.workcount = workcount;
	}

	public Integer getClasscount() {
		return classcount;
	}

	public void setClasscount(Integer classcount) {
		this.classcount = classcount;
	}

	public Integer getCheckcount() {
		return checkcount;
	}

	public void setCheckcount(Integer checkcount) {
		this.checkcount = checkcount;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return examineeclassid+","+className+","+workcount+","+classcount+","+checkcount+","+completionrate;
	}

	

}
