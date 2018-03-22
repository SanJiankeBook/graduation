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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "Chapter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chapter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 章节编号
	@Column(length = 50, nullable = false)
	private String chapterName;// 章节名称
	@Column(length = 50)
	private String remark;// 约束、标记

	private Integer seq; // 排序

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "subjectId", nullable = false)
	private Subject subject;

	// @OneToMany(mappedBy="chapter")
	// private Set<PointPaper> pointPapers= new HashSet<PointPaper>();
	//
	//
	//
	//
	// public Set<PointPaper> getPointPapers() {
	// return pointPapers;
	// }
	//
	//
	//
	// public void setPointPapers(Set<PointPaper> pointPapers) {
	// this.pointPapers = pointPapers;
	// }

	public Integer getId() {
		return id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
