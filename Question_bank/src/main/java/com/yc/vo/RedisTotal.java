package com.yc.vo;

import java.io.Serializable;

public class RedisTotal implements Serializable {
	private static final long serialVersionUID = 9197894871264489496L;
	
	private Integer checkCount;//每个学期考勤的总次数
	private Integer checkCountLastM;//最近一个月的考勤次数
	private Integer checkGood;//考勤时到的次数
	private Integer checkGoodLastM;//最近一个月到的考勤次数
	
	private Integer  workCount;//每个学期的检查作业次数
	private Integer workCountLastM;//最近一个月的作业检查次数
	private Integer workGood;//检查作业时完成的次数
	private Integer workGoodLastM;//最近一个月完成的作业检查次数
	
	private Integer aDailyTalkCount;//总学期每日一讲的次数
	private Integer aDailyTalkCountLastM;//上个月的每日一讲的次数
	
	private Integer writingPaperCount;//总学期考试的次数
	private Integer writingPaperCountLastM;//最近一个月的考试次数
	private Integer writingPaperGood;//总共考了多少次
	private Integer writingPaperGoodLastM;//最近一个月考了多少次
	public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
	}
	public Integer getCheckCountLastM() {
		return checkCountLastM;
	}
	public void setCheckCountLastM(Integer checkCountLastM) {
		this.checkCountLastM = checkCountLastM;
	}
	public Integer getCheckGood() {
		return checkGood;
	}
	public void setCheckGood(Integer checkGood) {
		this.checkGood = checkGood;
	}
	public Integer getCheckGoodLastM() {
		return checkGoodLastM;
	}
	public void setCheckGoodLastM(Integer checkGoodLastM) {
		this.checkGoodLastM = checkGoodLastM;
	}
	public Integer getWorkCount() {
		return workCount;
	}
	public void setWorkCount(Integer workCount) {
		this.workCount = workCount;
	}
	public Integer getWorkCountLastM() {
		return workCountLastM;
	}
	public void setWorkCountLastM(Integer workCountLastM) {
		this.workCountLastM = workCountLastM;
	}
	public Integer getWorkGood() {
		return workGood;
	}
	public void setWorkGood(Integer workGood) {
		this.workGood = workGood;
	}
	public Integer getWorkGoodLastM() {
		return workGoodLastM;
	}
	public void setWorkGoodLastM(Integer workGoodLastM) {
		this.workGoodLastM = workGoodLastM;
	}
	public Integer getaDailyTalkCount() {
		return aDailyTalkCount;
	}
	public void setaDailyTalkCount(Integer aDailyTalkCount) {
		this.aDailyTalkCount = aDailyTalkCount;
	}
	public Integer getaDailyTalkCountLastM() {
		return aDailyTalkCountLastM;
	}
	public void setaDailyTalkCountLastM(Integer aDailyTalkCountLastM) {
		this.aDailyTalkCountLastM = aDailyTalkCountLastM;
	}
	public Integer getWritingPaperCount() {
		return writingPaperCount;
	}
	public void setWritingPaperCount(Integer writingPaperCount) {
		this.writingPaperCount = writingPaperCount;
	}
	public Integer getWritingPaperCountLastM() {
		return writingPaperCountLastM;
	}
	public void setWritingPaperCountLastM(Integer writingPaperCountLastM) {
		this.writingPaperCountLastM = writingPaperCountLastM;
	}
	public Integer getWritingPaperGood() {
		return writingPaperGood;
	}
	public void setWritingPaperGood(Integer writingPaperGood) {
		this.writingPaperGood = writingPaperGood;
	}
	public Integer getWritingPaperGoodLastM() {
		return writingPaperGoodLastM;
	}
	public void setWritingPaperGoodLastM(Integer writingPaperGoodLastM) {
		this.writingPaperGoodLastM = writingPaperGoodLastM;
	}
	
	

}
