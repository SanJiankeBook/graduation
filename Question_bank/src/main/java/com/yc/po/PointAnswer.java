package com.yc.po;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name="PointAnswer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PointAnswer implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer opid;  //编号

	@Column(length=2000)
	private String answer; // --自评结果
	@Column(length=400)
	private String idea;  // --意见
	
	@Column(length=400)
	private String aname;	//学生名字
	
	private Integer claddid;
	
	@Column(length=200)
	private String remark;  //扩充字段
	private String status;  //扩充字段
	
	//这里用到了组合键 （name,classId) 需要 。。。。。。！！！！！！！！
	//private int pid;  //试题编号    外键
	
	@ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="pid")
	private PointPaper pointPaper;
	
//	@ManyToOne
//    @JoinColumn(name="classId")
//	private Examinee examinee;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)	//添加级联
    @JoinColumns ({
        @JoinColumn(name="name", referencedColumnName = "name"),
        @JoinColumn(name="classId", referencedColumnName = "classId")
    })
	 private Examinee examinee;

	
	
	public Integer getCladdid() {
		return claddid;
	}
	public void setCladdid(Integer claddid) {
		this.claddid = claddid;
	}
	public String getName() {
		return aname;
	}
	public void setName(String name) {
		this.aname = name;
	}
	public Examinee getExaminee() {
		return examinee;
	}
	public void setExaminee(Examinee examinee) {
		this.examinee = examinee;
	}
	public Integer getOpid() {
		return opid;
	}
	public void setOpid(Integer opid) {
		this.opid = opid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getIdea() {
		return idea;
	}
	public void setIdea(String idea) {
		this.idea = idea;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PointPaper getPointPaper() {
		return pointPaper;
	}
	public void setPointPaper(PointPaper pointPaper) {
		this.pointPaper = pointPaper;
	}
	

	
	
	
	
	
}
