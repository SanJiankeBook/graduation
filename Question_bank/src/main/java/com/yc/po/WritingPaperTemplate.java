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
 *模板卷
 */
@SuppressWarnings("serial")
@Entity
@Table(name="WritingPapertemplate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WritingPaperTemplate implements Serializable {
  @Id
  private String id;
  @Column(length=50,nullable = false)	
  private String paperName;
  @Column(length=200,nullable = false)
  private String examSubject;
  @Column(nullable = false)
  private Integer paperStatus;//考试状态
  @Column(length=5000,nullable = false)
  private String questionId;
  @Column(nullable = false)
  private int countOfQuestion;
  @Column(length=5000)
  private String questionCorrect;
  @Column(length=255)
  private String correctRate;
  @Column(length=500)
  private String remark;
  @Column(length=50)
  private String operator;
  //private Integer timesConsume;
  // @Transient
  private Integer timesConsume;
  @Column(length=5000)
  private String questionInfo;
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
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((correctRate == null) ? 0 : correctRate.hashCode());
	result = prime * result + countOfQuestion;
	result = prime * result + ((examSubject == null) ? 0 : examSubject.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((operator == null) ? 0 : operator.hashCode());
	result = prime * result + ((paperName == null) ? 0 : paperName.hashCode());
	result = prime * result + ((paperStatus == null) ? 0 : paperStatus.hashCode());
	result = prime * result + ((questionCorrect == null) ? 0 : questionCorrect.hashCode());
	result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
	result = prime * result + ((questionInfo == null) ? 0 : questionInfo.hashCode());
	result = prime * result + ((remark == null) ? 0 : remark.hashCode());
	result = prime * result + ((timesConsume == null) ? 0 : timesConsume.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	WritingPaperTemplate other = (WritingPaperTemplate) obj;
	if (correctRate == null) {
		if (other.correctRate != null)
			return false;
	} else if (!correctRate.equals(other.correctRate))
		return false;
	if (countOfQuestion != other.countOfQuestion)
		return false;
	if (examSubject == null) {
		if (other.examSubject != null)
			return false;
	} else if (!examSubject.equals(other.examSubject))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (operator == null) {
		if (other.operator != null)
			return false;
	} else if (!operator.equals(other.operator))
		return false;
	if (paperName == null) {
		if (other.paperName != null)
			return false;
	} else if (!paperName.equals(other.paperName))
		return false;
	if (paperStatus == null) {
		if (other.paperStatus != null)
			return false;
	} else if (!paperStatus.equals(other.paperStatus))
		return false;
	if (questionCorrect == null) {
		if (other.questionCorrect != null)
			return false;
	} else if (!questionCorrect.equals(other.questionCorrect))
		return false;
	if (questionId == null) {
		if (other.questionId != null)
			return false;
	} else if (!questionId.equals(other.questionId))
		return false;
	if (questionInfo == null) {
		if (other.questionInfo != null)
			return false;
	} else if (!questionInfo.equals(other.questionInfo))
		return false;
	if (remark == null) {
		if (other.remark != null)
			return false;
	} else if (!remark.equals(other.remark))
		return false;
	if (timesConsume == null) {
		if (other.timesConsume != null)
			return false;
	} else if (!timesConsume.equals(other.timesConsume))
		return false;
	return true;
}
  

  

}
