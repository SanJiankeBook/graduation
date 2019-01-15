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
/**
 * 作业表
 * @author hwh
 *
 */
@Entity
@Table(name = "work")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Work implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wid; //主键

	private Integer examineeclassid;  //班级id
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date  checkdate;  //检查时间
	
	@Column(length = 20,nullable=false)
	private String checkcreator;  //作业布置者
	@Column(length = 50,nullable=false)
	private String wname;  //作业名
	
	@Column(length = 500,nullable=false)
	private String remark;  //备注
	@Column(length = 500,nullable=false)
	private String description;  //作业描述

	@Column(length = 4000)
	private String result;  //作业检查情况
	
	@Column
	private Integer status;  //作业检查情况

	@Column(length = 10,nullable=false)
	private String semester;   //学期

	private Integer classcount; //班级人数
	private Integer checkcount; //检查人数
	
	private Integer editionid;//版本
	private Integer subjectid;//技术
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	private Integer chapterid;//课程
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public Integer getClasscount() {
		return classcount;
	}
	public void setClasscount(Integer classcount) {
		this.classcount = classcount;
	}
	public Integer getCheckcount() {
		return checkcount;
	}
	public void setCheckcount(Integer checkcount) {
		this.checkcount = checkcount;
	}

	public Date getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public Integer getWid() {
		return wid;
	}
	public void setWid(Integer wid) {
		this.wid = wid;
	}
	public Integer getExamineeclassid() {
		return examineeclassid;
	}
	public void setExamineeclassid(Integer examineeclassid) {
		this.examineeclassid = examineeclassid;
	}
	public String getCheckcreator() {
		return checkcreator;
	}
	public void setCheckcreator(String checkcreator) {
		this.checkcreator = checkcreator;
	}
	public String getWname() {
		return wname;
	}
	public void setWname(String wname) {
		this.wname = wname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public Integer getEditionid() {
		return editionid;
	}
	public void setEditionid(Integer editionid) {
		this.editionid = editionid;
	}
	public Integer getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}
	public Integer getChapterid() {
		return chapterid;
	}
	public void setChapterid(Integer chapterid) {
		this.chapterid = chapterid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Work [wid=" + wid + ", examineeclassid=" + examineeclassid + ", checkdate=" + checkdate
				+ ", checkcreator=" + checkcreator + ", wname=" + wname + ", remark=" + remark + ", description="
				+ description + ", result=" + result + ", status=" + status + ", semester=" + semester + ", classcount="
				+ classcount + ", checkcount=" + checkcount + ", editionid=" + editionid + ", subjectid=" + subjectid
				+ ", chapterid=" + chapterid + "]";
	}




}
