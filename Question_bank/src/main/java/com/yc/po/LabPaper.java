package com.yc.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="LabPaper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class LabPaper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id; //	Varchar(50)	是	否	序号，为主键
	@Column(length=50,nullable = false)
	private String examineeClass;//	varchar(50)	否	否	考生班级
	@Column(length=50,nullable = false)
	private String paperName;   //	varchar(50)	否	否	试卷名
	@Column(length=50,nullable = false)
	private String paperPwd;   //	varchar(50)	否	否	试卷密码
	@Column(nullable = false)
	private int paperStatus	;   //int	否	否	试卷状态
	@Column(nullable = false)
	private int questioned;   //int	否	否	答案编号
	
	private int skillCodeId;   //	int	否	可以为空	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date  examDate;		//	datetime	否	否	考试开始时间
	@Column(nullable = false)
	private int timesConsume;	//	int	否	否	
	@Column(length=255)
	private String scorePercent	;	//	Varchar(50)	否	可以为空	
	
	private Double avgScore	;     //float	否	可以为空	平均分数
	
	private Double maxScore;      //	float	否	可以为空	最大分数
	
	private Double minScore	;      //float	否	可以为空	最小分数
	@Column(length=200)
	private String remark;				//	varchar(200)	否	可以为空	
	@Column(length=50)
	private String operator;	
	
	//	varchar(50)	否	可以为空	

	 @OneToMany(mappedBy="labPaper",fetch=FetchType.LAZY)//测试加上的
	private Set<LabAnswer> labAnswers= new HashSet<LabAnswer>();
	
	
		
	

	public Set<LabAnswer> getLabAnswers() {
		return labAnswers;
	}

	public void setLabAnswers(Set<LabAnswer> labAnswers) {
		this.labAnswers = labAnswers;
	}


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamineeClass() {
		return examineeClass;
	}

	public void setExamineeClass(String examineeClass) {
		this.examineeClass = examineeClass;
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

	public int getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(int paperStatus) {
		this.paperStatus = paperStatus;
	}

	public int getQuestioned() {
		return questioned;
	}

	public void setQuestioned(int questioned) {
		this.questioned = questioned;
	}

	public int getSkillCodeId() {
		return skillCodeId;
	}

	public void setSkillCodeId(int skillCodeId) {
		this.skillCodeId = skillCodeId;
	}


	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public int getTimesConsume() {
		return timesConsume;
	}

	public void setTimesConsume(int timesConsume) {
		this.timesConsume = timesConsume;
	}

	public String getScorePercent() {
		return scorePercent;
	}

	public void setScorePercent(String scorePercent) {
		this.scorePercent = scorePercent;
	}

	public Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

	public Double getMinScore() {
		return minScore;
	}

	public void setMinScore(Double minScore) {
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

	
	
	
}
