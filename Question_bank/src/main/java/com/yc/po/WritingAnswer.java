package com.yc.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>Title:存储每一张笔试卷的考后信息 </p>
 */
@SuppressWarnings("serial")
@Entity
@Table(name="WritingAnswer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WritingAnswer implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
 
  @Column(length=20,nullable = false)
  private String examineeName;
  @Column(length=500)
  private String answer;
  private Float score;
  @Column(length=255)
  private String correctRate;
  private Integer spareTime;
  
  @ManyToOne(fetch=FetchType.LAZY)//测试加上的
  @JoinColumn(name="paperId")
  private WritingPaper writingPaper;
  
 
public WritingAnswer() {
	super();
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getExamineeName() {
	return examineeName;
}

public void setExamineeName(String examineeName) {
	this.examineeName = examineeName;
}

public String getAnswer() {
	return answer;
}

public void setAnswer(String answer) {
	this.answer = answer;
}



public Float getScore() {
	return score;
}

public void setScore(Float score) {
	this.score = score;
}

public String getCorrectRate() {
	return correctRate;
}

public void setCorrectRate(String correctRate) {
	this.correctRate = correctRate;
}

public Integer getSpareTime() {
	return spareTime;
}

public void setSpareTime(Integer spareTime) {
	this.spareTime = spareTime;
}

public WritingPaper getWritingPaper() {
	return writingPaper;
}

public void setWritingPaper(WritingPaper writingPaper) {
	this.writingPaper = writingPaper;
}

@Override
public String toString() {
	return "WritingAnswer [id=" + id + ", examineeName=" + examineeName + ", answer=" + answer + ", score=" + score
			+ ", correctRate=" + correctRate + ", spareTime=" + spareTime + ", writingPaper=" + writingPaper + "]";
}


	
  
}
