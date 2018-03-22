package com.yc.vo;

import java.io.Serializable;
import java.util.Date;

public class AdailyTalkPage implements Serializable {
	
	private int id;
	
	private String assessment;  //教员评价
	
	private String descript;  // 描述
	
	private String knowledge; // 讲解知识点
	
	private String name; // 讲解学员姓名
	
	private String remark; // 标识
	
	private Date speakdate; // 开讲日期
	
	private Integer status; // 状态 1.知识点已经添加还未开讲 2.开讲但未上传资料 3.学生可以下载 4.禁止学员下载
	
	private Integer cid;
	
	private String className;
	
	private String sta;	//状态名
	
	private String uname;  //登陆学员的姓名
	
	
	
	
	public String getSta() {
		return sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSpeakdate() {
		return speakdate;
	}

	public void setSpeakdate(Date speakdate) {
		this.speakdate = speakdate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
//		if("1".equals(status)){
//			this.status="资料准备中";
//		}else if("2".equals(status)){
//			this.status="未上传";
//		}else{
//			this.status="下传";
//		}
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
	/**
	 * 考勤Model
	 */
	private int checkid; // checing 表主键

	private String semester; // 学期编号s1,s2,s3

	private Date checkDate; // 考勤日期

	private String checkTime; // 考勤阶段 1.上午 2.下午 3.晚上

	private String checkResult; // 考勤结果 张三,1-家里有事|李四,2-病假 1.已到 2.迟到 3.病假 4.请假
								// 5.旷课 6.早退
	private String checkRemark; // 备注 可以写这次课的内容或安排
	
	private String userName;
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public int getCheckid() {
		return checkid;
	}

	public void setCheckid(int checkid) {
		this.checkid = checkid;
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
	
	
}
