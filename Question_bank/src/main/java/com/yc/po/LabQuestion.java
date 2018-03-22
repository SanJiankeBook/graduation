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
@Table(name="LabQuestion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class LabQuestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;			//	Int	是	否	试题编号，为主键
	@Column(length=50,nullable = false)
	private String semester;    //	varchar(50)	否	否	学期
	@Column(length=255,nullable = false)
	private String questionTitle;		//	varchar(255)	否	否	考试标题
	@Column(length=5000,nullable = false)
	private String question;		//	Text	否	否	题目
	@Column(length=500)
	private String image;		//	varchar(500)	否	可以为空	图片
	@Column(nullable = true)
	private Integer difficulty	;		//int	否	可以为空	难易等级
	@Column(length=200)
	private String remark;		//	varchar(200)	否	可以为空	标记 提示
	


	//private int editionId;		//	int	否	否	版本号,外键Edition(id)
	//private int skillCodeId	;
	 @ManyToOne(fetch=FetchType.LAZY)//测试加上的
	    @JoinColumn(name="editionId")//int	否	否	外键，LabQuestionType(id)
    private Edition edition;
	 
	 @ManyToOne(fetch=FetchType.LAZY)//测试加上的
	    @JoinColumn(name="skillCodeId")
    private LabQuestionType labQuestionType;
	
	 
	
	
	
	public Edition getEdition() {
		return edition;
	}
	public void setEdition(Edition edition) {
		this.edition = edition;
	}
	public LabQuestionType getLabQuestionType() {
		return labQuestionType;
	}
	public void setLabQuestionType(LabQuestionType labQuestionType) {
		this.labQuestionType = labQuestionType;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
