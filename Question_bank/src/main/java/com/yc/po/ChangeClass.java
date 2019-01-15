package com.yc.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ChangeClass")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChangeClass implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 
	
	private Integer examineeId;
	
	private Integer oldClassId;
	
	private Integer newClassId;
	
	private Integer oldTuitionId;
	
	private Integer newTuitionId;
	
	private Integer identify;
	
	@Column(length = 11)
	private String oldClassName;
	@Column(length = 50)
	private String newClassName;
	@Column(length = 11)
	private double oldContractedAmount;
	@Column(length = 50)
	private double newContractedAmount;
	@Column(length = 20)
	private String oldEdition;
	@Column(length = 20)
	private String newEdition;
	@Column(length = 20)
	private String changeClassDate;
	@Column(length = 20)
	private String user;
	
	@Column(length = 20)
	private String auditor;
	
	@Column(length = 20)
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExamineeId() {
		return examineeId;
	}

	public void setExamineeId(Integer examineeId) {
		this.examineeId = examineeId;
	}

	public Integer getOldClassId() {
		return oldClassId;
	}

	public void setOldClassId(Integer oldClassId) {
		this.oldClassId = oldClassId;
	}

	public Integer getNewClassId() {
		return newClassId;
	}

	public void setNewClassId(Integer newClassId) {
		this.newClassId = newClassId;
	}

	public Integer getOldTuitionId() {
		return oldTuitionId;
	}

	public void setOldTuitionId(Integer oldTuitionId) {
		this.oldTuitionId = oldTuitionId;
	}

	public Integer getNewTuitionId() {
		return newTuitionId;
	}

	public void setNewTuitionId(Integer newTuitionId) {
		this.newTuitionId = newTuitionId;
	}

	public Integer getIdentify() {
		return identify;
	}

	public void setIdentify(Integer identify) {
		this.identify = identify;
	}

	public String getOldClassName() {
		return oldClassName;
	}

	public void setOldClassName(String oldClassName) {
		this.oldClassName = oldClassName;
	}

	public String getNewClassName() {
		return newClassName;
	}

	public void setNewClassName(String newClassName) {
		this.newClassName = newClassName;
	}

	public double getOldContractedAmount() {
		return oldContractedAmount;
	}

	public void setOldContractedAmount(double oldContractedAmount) {
		this.oldContractedAmount = oldContractedAmount;
	}

	public double getNewContractedAmount() {
		return newContractedAmount;
	}

	public void setNewContractedAmount(double newContractedAmount) {
		this.newContractedAmount = newContractedAmount;
	}

	public String getOldEdition() {
		return oldEdition;
	}

	public void setOldEdition(String oldEdition) {
		this.oldEdition = oldEdition;
	}

	public String getNewEdition() {
		return newEdition;
	}

	public void setNewEdition(String newEdition) {
		this.newEdition = newEdition;
	}

	public String getChangeClassDate() {
		return changeClassDate;
	}

	public void setChangeClassDate(String changeClassDate) {
		this.changeClassDate = changeClassDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ChangeClass [id=" + id + ", examineeId=" + examineeId + ", oldClassId=" + oldClassId + ", newClassId="
				+ newClassId + ", oldTuitionId=" + oldTuitionId + ", newTuitionId=" + newTuitionId + ", identify="
				+ identify + ", oldClassName=" + oldClassName + ", newClassName=" + newClassName
				+ ", oldContractedAmount=" + oldContractedAmount + ", newContractedAmount=" + newContractedAmount
				+ ", oldEdition=" + oldEdition + ", newEdition=" + newEdition + ", changeClassDate=" + changeClassDate
				+ ", user=" + user + ", auditor=" + auditor + ", type=" + type + "]";
	}

}
