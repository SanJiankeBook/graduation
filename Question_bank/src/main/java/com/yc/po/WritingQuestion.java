
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

/**
 * <p>Title:存储笔试题信息的Bean </p>
 */

@Entity
@Table(name="WritingQuestion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WritingQuestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6203254141072965496L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
  private Integer editionId;
	@Column(length=50,nullable = false)
  private String semester;//学期
	@Column(nullable = false)
  private Integer subjectId;//课程编号
	@Column(nullable = false)
  private Integer chapterId;//章节编号
	@Column(length=2,nullable = false)
  private String questionType;
	@Column(length=5000,nullable = false)
  private String question;//题目i
	@Column(length=2000,nullable = false)
  private String optionA;//a选项
	@Column(length=2000,nullable = false)
  private String optionB;
	@Column(length=2000,nullable = false)
  private String optionC;
	@Column(length=2000,nullable = false)
  private String optionD;
	@Column(length=500)
  private String image;
	@Column(length=20)
  private String answer;
	
  private Integer difficulty;
  @Column(length=1000)
  
  private String remark;
  
  

  public WritingQuestion() {
  }


public Integer getId() {
	return id;
}


public void setId(Integer id) {
	this.id = id;
}


public Integer getEditionId() {
	return editionId;
}


public void setEditionId(Integer editionId) {
	this.editionId = editionId;
}


public String getSemester() {
	return semester;
}


public void setSemester(String semester) {
	this.semester = semester;
}


public Integer getSubjectId() {
	return subjectId;
}


public void setSubjectId(Integer subjectId) {
	this.subjectId = subjectId;
}


public Integer getChapterId() {
	return chapterId;
}


public void setChapterId(Integer chapterId) {
	this.chapterId = chapterId;
}


public String getQuestionType() {
	return questionType;
}


public void setQuestionType(String questionType) {
	this.questionType = questionType;
}


public String getQuestion() {
	return question;
}


public void setQuestion(String question) {
	this.question = question;
}


public String getOptionA() {
	return optionA;
}


public void setOptionA(String optionA) {
	this.optionA = optionA;
}


public String getOptionB() {
	return optionB;
}


public void setOptionB(String optionB) {
	this.optionB = optionB;
}


public String getOptionC() {
	return optionC;
}


public void setOptionC(String optionC) {
	this.optionC = optionC;
}


public String getOptionD() {
	return optionD;
}


public void setOptionD(String optionD) {
	this.optionD = optionD;
}


public String getImage() {
	return image;
}


public void setImage(String image) {
	this.image = image;
}


public String getAnswer() {
	return answer;
}


public void setAnswer(String answer) {
	this.answer = answer;
}


public Integer getDifficulty() {
	return difficulty;
}


public void setDifficulty(Integer difficulty) {
	this.difficulty = difficulty;
}


public String getRemark() {
	return remark;
}


public void setRemark(String remark) {
	this.remark = remark;
}



  
}  




