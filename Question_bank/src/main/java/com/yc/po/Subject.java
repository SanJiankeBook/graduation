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
@Table(name = "suv")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 100, nullable = false)
	private String subjectName;// 课程名
	@Column(nullable = false)
	private Integer chapterCount;// 所含章节数
	@Column(length = 50, nullable = false)
	private String semester;// 课程所属学期
	@Column
	private Integer seq;
	  
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "editionId", nullable = false)
	private Edition edition;

	@OneToMany(mappedBy = "subject",fetch=FetchType.LAZY)
	private Set<Chapter> chapters = new HashSet<Chapter>();

	@OneToMany(mappedBy = "subject",fetch=FetchType.LAZY)
	private Set<PointInfo> pointInfos = new HashSet<PointInfo>();
	
	 @OneToMany(mappedBy="subject",fetch=FetchType.LAZY)
		private Set<PointPaper> pointPapers= new HashSet<PointPaper>();

	 
	 
	public Set<PointPaper> getPointPapers() {
		return pointPapers;
	}

	public void setPointPapers(Set<PointPaper> pointPapers) {
		this.pointPapers = pointPapers;
	}

	public Set<PointInfo> getPointInfos() {
		return pointInfos;
	}

	public void setPointInfos(Set<PointInfo> pointInfos) {
		this.pointInfos = pointInfos;
	}

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(Integer chapterCount) {
		this.chapterCount = chapterCount;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

}
