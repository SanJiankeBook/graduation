package com.yc.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;

public class WtitingModel {
	private String answer;
	private String correctRate;
	private int spareTime;
	private WritingPaper writingPaper;
	private String examineeName;
	private Float score;
	private Integer id;
	private String paperName;
	private String paperPwd;
	private String examSubject;
	private Integer paperStatus;// 考试状态
	private String questionId;
	private int countOfQuestion;
	private Date examDate;
	private String scorePercent;
	private String questionCorrect;
	private Float avgScore;
	private Float maxScore;
	private Float minScore;
	private String remark;
	private String operator;
	private String examineeClass;
	private Integer timesConsume;
	private String questionInfo;
	private Set<WritingAnswer> writingAnswers = new HashSet<WritingAnswer>();
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getCorrectRate() {
		return correctRate;
	}
	public void setCorrectRate(String correctRate) {
		this.correctRate = correctRate;
	}
	public int getSpareTime() {
		return spareTime;
	}
	public void setSpareTime(int spareTime) {
		this.spareTime = spareTime;
	}
	public WritingPaper getWritingPaper() {
		return writingPaper;
	}
	public void setWritingPaper(WritingPaper writingPaper) {
		this.writingPaper = writingPaper;
	}
	public String getExamineeName() {
		return examineeName;
	}
	public void setExamineeName(String examineeName) {
		this.examineeName = examineeName;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
