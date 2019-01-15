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
@Table(name="LabQuestionType")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class LabQuestionType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;		//	Int	是	否	试题类型编号，为主键
	@Column(length=50,nullable = false)
	private String skillCode;		//	varchar(50)	否	否	
	@Column(length=50,nullable = false)
	private String semester;		//	varchar(50)	否	否	学期

	
	 @OneToMany(mappedBy="labQuestionType",fetch=FetchType.LAZY)//测试加上的
	 private Set<LabQuestion> labQuestions= new HashSet<LabQuestion>();
	
	
	
	public Set<LabQuestion> getLabQuestions() {
		return labQuestions;
	}

	public void setLabQuestions(Set<LabQuestion> labQuestions) {
		this.labQuestions = labQuestions;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkillCode() {
		return skillCode;
	}

	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	
	
	
}
