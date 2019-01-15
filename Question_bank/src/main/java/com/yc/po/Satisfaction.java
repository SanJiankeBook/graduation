package com.yc.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="Satisfaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Satisfaction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column
	private Integer teacherid;
	@Column(length=10,nullable=false)
	private String teacherName;  
	@Column(length=10,nullable=true)
	private String startTime;  
	@Column(length=10,nullable=true)
	private String endTime;  
	@Column
	private Integer openPersonId;  
	@Column(length=10,nullable=false)
	private String openPersonName;  
	@Column(length=10,nullable=false)
	private String className;  
	
	@Column(length=10,nullable=false)
	private String openYear;  
	@Column(length=10,nullable=false)
	private String openMonth;  
	@Column
	private Double summaryGrade;
	@Column(length=10,nullable=false)
	private String satisfactionSummary;  
	@Column(length=10,nullable=false)
	private String unsatisfactedSummary;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTeacherid() {
		return teacherid;
	}
	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUnsatisfactedSummary() {
		return unsatisfactedSummary;
	}
	public void setUnsatisfactedSummary(String unsatisfactedSummary) {
		this.unsatisfactedSummary = unsatisfactedSummary;
	}
	public Integer getOpenPersonId() {
		return openPersonId;
	}
	public void setOpenPersonId(Integer openPersonId) {
		this.openPersonId = openPersonId;
	}
	public String getOpenPersonName() {
		return openPersonName;
	}
	public void setOpenPersonName(String openPersonName) {
		this.openPersonName = openPersonName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getOpenYear() {
		return openYear;
	}
	public void setOpenYear(String openYear) {
		this.openYear = openYear;
	}
	public String getOpenMonth() {
		return openMonth;
	}
	public void setOpenMonth(String openMonth) {
		this.openMonth = openMonth;
	}
	
	public Double getSummaryGrade() {
		return summaryGrade;
	}
	public void setSummaryGrade(Double summaryGrade) {
		this.summaryGrade = summaryGrade;
	}
	public String getSatisfactionSummary() {
		return satisfactionSummary;
	}
	public void setSatisfactionSummary(String satisfactionSummary) {
		this.satisfactionSummary = satisfactionSummary;
	}
	@Override
	public String toString() {
		return "Satisfaction [id=" + id + ", teacherid=" + teacherid + ", teacherName=" + teacherName + ", startTime="
				+ startTime + ", endTime=" + endTime + ", openPersonId=" + openPersonId + ", openPersonName="
				+ openPersonName + ", className=" + className + ", openYear=" + openYear + ", openMonth=" + openMonth
				+ ", summaryGrade=" + summaryGrade + ", satisfactionSummary=" + satisfactionSummary
				+ ", unsatisfactedSummary=" + unsatisfactedSummary + "]";
	}
	
	
	
}
