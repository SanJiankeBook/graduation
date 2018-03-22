package com.yc.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name="LabAnswer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LabAnswer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=20,nullable = false)
	private  String examineeName;
	@Column(length=50)
	private String projectName;
	@Column(length=5000)
	private String code;
	
	private Double score;
	@Column(length=255)
	private String comment;
	

	//机试试卷的id，外键，LabPaper(id)
	
	  @ManyToOne
	    @JoinColumn(name="paperId",nullable=false)
	private LabPaper labPaper;
	
	  
	  
	
	
	
	
	
	
	
	
	


	public LabPaper getLabPaper() {
		return labPaper;
	}

	public void setLabPaper(LabPaper labPaper) {
		this.labPaper = labPaper;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExamineeName() {
		return examineeName;
	}

	public void setExamineeName(String examineeName) {
		this.examineeName = examineeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	


}
