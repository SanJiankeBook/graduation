package com.yc.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="TeacherCurriModel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeacherCurriModel implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@NotFound(action=NotFoundAction.IGNORE)
	private Integer id;   
	
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String classname;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String chapterName;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String classroomname;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String date;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String timeperiods ;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String username ;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String bgcolor ;
	@Column(length=20)
	@NotFound(action=NotFoundAction.IGNORE)
	private String fontcolor ;
	
	@NotFound(action=NotFoundAction.IGNORE)
	private Integer chapterid;
	
	
	
	
	
	
	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getFontcolor() {
		return fontcolor;
	}

	public void setFontcolor(String fontcolor) {
		this.fontcolor = fontcolor;
	}

	public Integer getChapterid() {
		return chapterid;
	}

	public void setChapterid(Integer chapterid) {
		this.chapterid = chapterid;
	}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getClassroomname() {
		return classroomname;
	}

	public void setClassroomname(String classroomname) {
		this.classroomname = classroomname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeperiods() {
		return timeperiods;
	}

	public void setTimeperiods(String timeperiods) {
		this.timeperiods = timeperiods;
	}

	 

}
