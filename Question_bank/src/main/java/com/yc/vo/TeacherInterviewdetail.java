package com.yc.vo;

import java.io.Serializable;

public class TeacherInterviewdetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private  String teacherName;
	private Integer num;
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	
	@Override
	public String toString() {
			return teacherName+","+num;
	}
	
	
	
	
	 
}
