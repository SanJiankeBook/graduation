package com.yc.vo;

import java.io.Serializable;
import java.util.Date;

public class CheckingPage implements Serializable {
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
