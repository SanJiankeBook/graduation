package com.yc.po;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ADailyTalk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ADailyTalk implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 序号
	@Column(length = 20)
	private String name; // 讲解学员姓名
	@Column(length = 1000, nullable = false)
	private String knowledge; // 讲解知识点
	@Column(length = 20)
	@Temporal(TemporalType.TIMESTAMP)
	private Date speakdate; // 开讲日期
	private Integer status; // 状态 1.知识点已经添加还未开讲 2.开讲但未上传资料 3.学生可以下载 4.禁止学员下载
	@Column(length = 1000, columnDefinition = "varchar(1000) default ''")
	private String assessment; // 教员评价
	// 这里跟 db.sql
	private byte[] dateinfo; // 文档只能为.rar或.zip

	@Column(length = 200)
	private String remark; // 标识
	@Column(length = 2000)
	private String descript; // 描述

	// ！！！！！！！！！！！！！！！！！！
	// 特殊关系没配，，，，约束没陪
	// private int cid; //班级编号
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cid")
	private ExamineeClass examineeClass;

	public ADailyTalk() {
		super();
	}

	public ADailyTalk(byte[] dateinfo, String remark) {
		this.dateinfo = dateinfo;
		this.remark = remark;
	}

	public ADailyTalk(Integer id, String name, String knowledge, Date speakdate, Integer status, String assessment, String remark) {
		this.id = id;
		this.name = name;
		this.knowledge = knowledge;
		this.speakdate = speakdate;
		this.status = status;
		this.assessment = assessment;
		this.remark = remark;
	}

	public ADailyTalk(Integer id, String name, String knowledge, Date speakdate, Integer status, String assessment, String remark, String descript) {
		this.id = id;
		this.name = name;
		this.knowledge = knowledge;
		this.speakdate = speakdate;
		this.status = status;
		this.assessment = assessment;
		this.remark = remark;
		this.descript = descript;
	}

	public ADailyTalk(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public Date getSpeakdate() {
		return speakdate;
	}

	public void setSpeakdate(Date speakdate) {
		this.speakdate = speakdate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public byte[] getDateinfo() {
		return dateinfo;
	}

	public void setDateinfo(byte[] dateinfo) {
		this.dateinfo = dateinfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public ExamineeClass getExamineeClass() {
		return examineeClass;
	}

	public void setExamineeClass(ExamineeClass examineeClass) {
		this.examineeClass = examineeClass;
	}

	@Override
	public String toString() {
		return "ADailyTalk [id=" + id + ", name=" + name + ", knowledge=" + knowledge + ", speakdate=" + speakdate + ", status=" + status + ", assessment=" + assessment + ", dateinfo=" + Arrays.toString(dateinfo) + ", remark=" + remark
				+ ", descript=" + descript + ", examineeClass=" + examineeClass + "]";
	}
	
	

}
