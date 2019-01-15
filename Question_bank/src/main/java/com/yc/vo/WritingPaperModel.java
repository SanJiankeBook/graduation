package com.yc.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class WritingPaperModel implements Serializable {
	// 难易度比例 难
	private String rate1;
	// 难易度比例 中
	private String rate2;
	// 难易度比例 易
	private String rate3;
	// 版本号
	private String edition;
	// 学期编号
	private String semester;

	private String id;		//试卷标编号
	private String paperName;
	private String paperPwd;  //试卷密码
	private String examSubject;//试卷知识点
	private Integer paperStatus;// 考试状态
	private String questionId;	//题目编号
	private int countOfQuestion; //出题量
	private Date examDate;		//考试日期
	private String scorePercent;
	private String questionCorrect;//考试答案
	private String correctRate;
	private String remark;//试卷说明
	private String operator;//出卷人
	private String examineeClass;//考试班级
	private Integer timesConsume;//考试时间
	private String questionInfo;
	public String getRate1() {
		return rate1;
	}
	public void setRate1(String rate1) {
		this.rate1 = rate1;
	}
	public String getRate2() {
		return rate2;
	}
	public void setRate2(String rate2) {
		this.rate2 = rate2;
	}
	public String getRate3() {
		return rate3;
	}
	public void setRate3(String rate3) {
		this.rate3 = rate3;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getPaperPwd() {
		return paperPwd;
	}
	public void setPaperPwd(String paperPwd) {
		this.paperPwd = paperPwd;
	}
	public String getExamSubject() {
		return examSubject;
	}
	public void setExamSubject(String examSubject) {
		this.examSubject = examSubject;
	}
	public Integer getPaperStatus() {
		return paperStatus;
	}
	public void setPaperStatus(Integer paperStatus) {
		this.paperStatus = paperStatus;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public int getCountOfQuestion() {
		return countOfQuestion;
	}
	public void setCountOfQuestion(int countOfQuestion) {
		this.countOfQuestion = countOfQuestion;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public String getScorePercent() {
		return scorePercent;
	}
	public void setScorePercent(String scorePercent) {
		this.scorePercent = scorePercent;
	}
	public String getQuestionCorrect() {
		return questionCorrect;
	}
	public void setQuestionCorrect(String questionCorrect) {
		this.questionCorrect = questionCorrect;
	}
	public String getCorrectRate() {
		return correctRate;
	}
	public void setCorrectRate(String correctRate) {
		this.correctRate = correctRate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getExamineeClass() {
		return examineeClass;
	}
	public void setExamineeClass(String examineeClass) {
		this.examineeClass = examineeClass;
	}
	public Integer getTimesConsume() {
		return timesConsume;
	}
	public void setTimesConsume(Integer timesConsume) {
		this.timesConsume = timesConsume;
	}
	public String getQuestionInfo() {
		return questionInfo;
	}
	public void setQuestionInfo(String questionInfo) {
		this.questionInfo = questionInfo;
	}
	
	
	
}
