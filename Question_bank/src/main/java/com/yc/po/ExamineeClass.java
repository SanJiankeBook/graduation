package com.yc.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name="ExamineeClass")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamineeClass implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id ;//考试班级信息表
	@Column(length=20,nullable = false)
	private String className ;//考试班级名
	@Column(length=50,nullable = false)
	private String semester ;//学期
	@Column(length=20,nullable = false)
	private String createDate;//考试时间
	@Column(length=20,nullable = false)
	private String overDate ;//结束时间
	@Column(length=255,nullable = true)
	private String remark;//标记 
	@Column
	private Integer studentcount;
	@Column(length=1000,nullable = true)
	private String notice;//注意事项
	@Column
	private Integer editionId;
	
	
//	@OneToMany(mappedBy="examineeClass")
//	private Set<Examinee> examinees= new HashSet<Examinee>();
	
	
	 @OneToMany(mappedBy="examineeClass", fetch=FetchType.LAZY)
	private Set<PointPaper> pointPapers= new HashSet<PointPaper>();
	 
	 @OneToMany(mappedBy="examineeClass",fetch=FetchType.LAZY )
	private Set<Checking> checkings= new HashSet<Checking>();
	 
	 @OneToMany(mappedBy="examineeClass" ,fetch=FetchType.LAZY )
	 private Set<ADailyTalk>aDailyTalks= new HashSet<ADailyTalk>();
	 
	
	

//	public Set<Examinee> getExaminees() {
//		return examinees;
//	}
//
//	public void setExaminees(Set<Examinee> examinees) {
//		this.examinees = examinees;
//	}

	public Set<PointPaper> getPointPapers() {
		return pointPapers;
	}

	public void setPointPapers(Set<PointPaper> pointPapers) {
		this.pointPapers = pointPapers;
	}

	public Set<Checking> getCheckings() {
		return checkings;
	}

	public void setCheckings(Set<Checking> checkings) {
		this.checkings = checkings;
	}

	public Set<ADailyTalk> getaDailyTalks() {
		return aDailyTalks;
	}

	public void setaDailyTalks(Set<ADailyTalk> aDailyTalks) {
		this.aDailyTalks = aDailyTalks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOverDate() {
		return overDate;
	}

	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStudentcount() {
		return studentcount;
	}

	public void setStudentcount(Integer studentcount) {
		this.studentcount = studentcount;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Integer getEditionId() {
		return editionId;
	}

	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}

	
	
	 

	
	
}
