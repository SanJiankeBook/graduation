package com.yc.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>Title:存储随机和手动生成的笔试卷信息 </p>
 */
@SuppressWarnings("serial")
@Entity
@Table(name="WritingPaper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WritingPaper implements Serializable {
  @Id
  private String id;
  @Column(length=50,nullable = false)	
  private String paperName;
  @Column(length=50,nullable = false)
  private String paperPwd;
  @Column(length=200,nullable = false)
  private String examSubject;
  
  @Column(nullable = false)
  private Integer paperStatus;//考试状态
  @Column(length=5000,nullable = false)
  private String questionId;
  
  @Column(nullable = false)
  private int countOfQuestion;
  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date examDate;
  @Column(length=255)
  private String scorePercent;
  @Column(length=5000)
  private String questionCorrect;
  
  private Float avgScore;
  private Float maxScore;
  private Float minScore;
  
  @Column(length=255)
  private String correctRate;
  @Column(length=500)
  private String remark;
  @Column(length=50)
  private String operator;
  private String examineeClass;
  //private Integer timesConsume;
 // @Transient
  private Integer timesConsume;
  
  @Column(length=5000)
  private String questionInfo;
  
  @OneToMany(mappedBy="writingPaper",fetch=FetchType.LAZY)//测试加上的
  private Set<WritingAnswer>writingAnswers= new HashSet<WritingAnswer>();
  
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

public Float getAvgScore() {
	return avgScore;
}

public void setAvgScore(Float avgScore) {
	this.avgScore = avgScore;
}

public Float getMaxScore() {
	return maxScore;
}

public void setMaxScore(Float maxScore) {
	this.maxScore = maxScore;
}

public Float getMinScore() {
	return minScore;
}

public void setMinScore(Float minScore) {
	this.minScore = minScore;
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

public Set<WritingAnswer> getWritingAnswers() {
	return writingAnswers;
}

public void setWritingAnswers(Set<WritingAnswer> writingAnswers) {
	this.writingAnswers = writingAnswers;
}

}
