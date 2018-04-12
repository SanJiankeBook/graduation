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

@Entity
@Table(name="PointInfo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PointInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid; //知识点编号
	
	@Column(length=200,nullable = false)
	private String pcontent;  //知识点内容
	private Integer status;  //状态: 1.可用   3.不可用（删除）
	@Column(length=200)
	private String remark;  //扩充字段
	private String flag;  //扩充字段

	@ManyToOne(fetch=FetchType.LAZY)//测试加上的
    @JoinColumn(name="cid")
	private XSubject subject;
	

	
	
	public PointInfo() {
		super();
	}


	public PointInfo(Integer pid, String pcontent, Integer status,
			String remark, String flag) {
		super();
		this.pid = pid;
		this.pcontent = pcontent;
		this.status = status;
		this.remark = remark;
		this.flag = flag;
	}


	public Integer getPid() {
		return pid;
	}


	public void setPid(Integer pid) {
		this.pid = pid;
	}


	public String getPcontent() {
		return pcontent;
	}


	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public XSubject getSubject() {
		return subject;
	}


	public void setSubject(XSubject subject2) {
		this.subject = subject2;
	}


	
	
}
