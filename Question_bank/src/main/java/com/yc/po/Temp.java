package com.yc.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name="Temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Temp implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer ppid;   //试卷编号
	@Column(length=20)
	private String sname;  //学生姓名
	private Integer classid;  //学生所在班级
	@Column(length=20)
	private String className;  //学生所在班级
	private Integer subid;  //课程编号
	@Column(length=100)
	private String subname;    //课程名
	private Integer pointid;    //知识点编号
	@Column(length=2000)
	private String pcontent;     //知识点内容
	private Float grade;   //分数
	
	
	

	public Integer getPpid() {
		return ppid;
	}






	public void setPpid(Integer ppid) {
		this.ppid = ppid;
	}






	public String getSname() {
		return sname;
	}






	public void setSname(String sname) {
		this.sname = sname;
	}






	public Integer getClassid() {
		return classid;
	}






	public void setClassid(Integer classid) {
		this.classid = classid;
	}






	public String getClassName() {
		return className;
	}






	public void setClassName(String className) {
		this.className = className;
	}






	public Integer getSubid() {
		return subid;
	}






	public void setSubid(Integer subid) {
		this.subid = subid;
	}






	public String getSubname() {
		return subname;
	}






	public void setSubname(String subname) {
		this.subname = subname;
	}






	public Integer getPointid() {
		return pointid;
	}






	public void setPointid(Integer pointid) {
		this.pointid = pointid;
	}






	public String getPcontent() {
		return pcontent;
	}






	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}






	public Float getGrade() {
		return grade;
	}






	public void setGrade(Float grade) {
		this.grade = grade;
	}



	public String getSubidStr(){
		if(this.getSubid()==6){
			return "早退";
		}else if(this.getSubid()==2){
			return "迟到";
		}else if(this.getSubid()==3){
			return "病假";
		}else if(this.getSubid()==4){
			return "请假";
		}else if(this.getSubid()==5){
			return "旷课";
		}else {
			return "已到";
		}
	}

	




	public String getPointidTime(){
		if(this.getPointid()==1){
			return "上午";
		}else if(this.getPointid()==2){
			return "下午";
		}else{
			return "晚上";
		}
	}








	
}
