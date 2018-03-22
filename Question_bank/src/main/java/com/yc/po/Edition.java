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
@Table(name="Edition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Edition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(length=50,nullable = false)
	private String editionName;//版本号
	
	@Column(nullable = false)
	private Integer currentUse;//使用状态  0.未使用  1.正在使用
	
	 @OneToMany(mappedBy="edition",fetch=FetchType.LAZY)
	 /*fetch=FetchType.EAGER  关闭懒加载，加载机构树形结构第一层的同时会把树
	 的所有数据都一起加载至内存，在session关闭后也可以使用这些数据*/
	private Set<Subject> subjects;
	 
	 @OneToMany(mappedBy="edition" ,fetch=FetchType.LAZY)
	// @OneToMany(mappedBy="edition",fetch=FetchType.EAGER )
	 private Set<LabQuestion> labQuestions;
	 
	public Integer getCurrentUse() {
		return currentUse;
	}

	public void setCurrentUse(Integer currentUse) {
		this.currentUse = currentUse;
	}

	public Set<LabQuestion> getLabQuestions() {
		return labQuestions;
	}

	public void setLabQuestions(Set<LabQuestion> labQuestions) {
		this.labQuestions = labQuestions;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

	 

	
	
}
