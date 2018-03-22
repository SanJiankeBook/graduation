package com.yc.vo;

import java.io.Serializable;

/**
 * 课表表页面显示类
* @author lyh
* @version 创建时间：2017年5月5日 下午9:35:09
* @ClassName 类名称
* @Description 类描述
 */
public class CurriculumPage  implements Serializable{
 
	private static final long serialVersionUID = -7639075770864705867L;
	 
	private String className;
	private String teacherName;
	private Integer teachCount;//课时
	private Integer studentCount;//学生人数
	private Integer studentCount2;//学生流失人数
	private Integer TotalSedule; //老师课时总数
	private Integer MonthTotalSedule; //月份课时总数
	
	private  String uniqueName;
	
	
	
	
	 
	 
	public Integer getMonthTotalSedule() {
		return MonthTotalSedule;
	}
	public void setMonthTotalSedule(Integer monthTotalSedule) {
		MonthTotalSedule = monthTotalSedule;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getTeachCount() {
		return teachCount;
	}
	public void setTeachCount(Integer teachCount) {
		this.teachCount = teachCount;
	}
	public Integer getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}
	public Integer getStudentCount2() {
		return studentCount2;
	}
	public void setStudentCount2(Integer studentCount2) {
		this.studentCount2 = studentCount2;
	}
	public Integer getTotalSedule() {
		return TotalSedule;
	}
	public void setTotalSedule(Integer totalSedule) {
		TotalSedule = totalSedule;
	}
	 
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	@Override
	public String toString() {
		return "CurriculumPage [className=" + className + ", teacherName=" + teacherName + ", teachCount=" + teachCount
				+ ", studentCount=" + studentCount + ", studentCount2=" + studentCount2 + ", TotalSedule=" + TotalSedule
				+ ", MonthTotalSedule=" + MonthTotalSedule + ", uniqueName=" + uniqueName + "]";
	}
 
	
	
	

}
