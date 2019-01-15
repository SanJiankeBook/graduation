package com.yc.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.Cache;


import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;
@Entity
@Table(name="Checking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Checking implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer checkId;
	@Column(length=10,nullable=false)
	private String semester;  //学期编号   S1,S2,S3
	
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date checkDate;  //考勤日期
	
	
	
	@Column(length=10,nullable=false)
	private String checkTime;    //考勤阶段  1.上午    2.下午     3.晚上
	@Column(length=4000,nullable=false)
	private String checkResult;   //考勤结果 张三,1-家里有事|李四,2-病假 1.已到  2.迟到  3.病假  4.请假  5.旷课 6.早退
	@Column(length=1000)
	private String checkRemark; //备注  可以写这次课的内容或安排
	
	
	@Column(columnDefinition="int default 1")
	@Range(min=0,max=1)
	private Integer checkStatus;
	@Column(length=500)
	private String checkFlag;   //扩充字段
	@Column(length=2000)
	private String checkDescript;   //扩充字段
	//Calendar cld = Calendar.getInstance(); //初始化日历
	
	
	//这里需要配置关系
//	private int uid;
//	private int cid;
	
	 @ManyToOne(fetch=FetchType.LAZY)//测试加上的
	 @JoinColumn(name="uid",nullable=false)
	private SystemUser systemUser;
	 
	 @ManyToOne(fetch=FetchType.LAZY)//测试加上的
	 @JoinColumn(name="cid",nullable=false)
	private ExamineeClass examineeClass;
	
	public Integer getCheckId() {
		return checkId;
	}



	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}



	public String getSemester() {
		return semester;
	}



	public void setSemester(String semester) {
		this.semester = semester;
	}




	public Date getCheckDate() {
		return checkDate;
	}



	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}



	public String getCheckTime() {
		return checkTime;
	}



	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}



	public String getCheckResult() {
		return checkResult;
	}



	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}



	public String getCheckRemark() {
		return checkRemark;
	}



	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}



	public Integer getCheckStatus() {
		return checkStatus;
	}



	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}



	public String getCheckFlag() {
		return checkFlag;
	}



	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}



	public String getCheckDescript() {
		return checkDescript;
	}



	public void setCheckDescript(String checkDescript) {
		this.checkDescript = checkDescript;
	}



	public SystemUser getSystemUser() {
		return systemUser;
	}



	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}



	public ExamineeClass getExamineeClass() {
		return examineeClass;
	}



	public void setExamineeClass(ExamineeClass examineeClass) {
		this.examineeClass = examineeClass;
	}


	
	
	
	
	
	
}
