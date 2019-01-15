package com.yc.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dictation")
public class Dictation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6395774128764177801L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键由数据库自动生成
	private Integer id;
	@Column(length=20)
	private String examineename;
	@Column
	private Integer wid;
	@Column(length=50)
	private String workinfo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWid() {
		return wid;
	}
	public void setWid(Integer wid) {
		this.wid = wid;
	}
	public String getWorkinfo() {
		return workinfo;
	}
	public void setWorkinfo(String workinfo) {
		this.workinfo = workinfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getExamineename() {
		return examineename;
	}
	public void setExamineename(String examineename) {
		this.examineename = examineename;
	}
	@Override
	public String toString() {
		return "Dictation [id=" + id + ", examineename=" + examineename + ", wid=" + wid + ", workinfo=" + workinfo
				+ "]";
	}
	
	

}
