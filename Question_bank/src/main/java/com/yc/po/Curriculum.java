package com.yc.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
* @author lyh
* @version 创建时间：2017年4月22日 下午7:53:30
* @ClassName 类名称
* @Description 用来记录课表信息的实体类
 */
@Entity
@Table(name = "Curriculum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Curriculum implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键由数据库自动生成
	private Integer id;// 课表编号
	@Column
	private Integer classid;// 班级id
	@Column 
	private Integer chapterid;// 课程id
	@Column
	private Integer teacherid; // 教师id
	@Column
	private Integer classroomid; // 教室id
	@Column(length=20)
	private String date;//日期
	@Column(length=20)
	private String timeperiods;//具体时间
	@Column(length=20)
	private String chapterName="";
	
	public String getChapterName() {
		if(   chapterName==null){
			return "";
		}else{
			return chapterName;
		}
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public Integer getChapterid() {
		return chapterid;
	}
	public void setChapterid(Integer chapterid) {
		this.chapterid = chapterid;
	}
	public Integer getTeacherid() {
		return teacherid;
	}
	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}
	public Integer getClassroomid() {
		return classroomid;
	}
	public void setClassroomid(Integer classroomid) {
		this.classroomid = classroomid;
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
	
	@Override
	public String toString() {
		return "Curriculum [id=" + id + ", classid=" + classid + ", chapterid=" + chapterid + ", teacherid=" + teacherid
				+ ", classroomid=" + classroomid + ", date=" + date + ", timeperiods=" + timeperiods + ", chapterName="
				+ chapterName + "]";
	}
	
	 
 
}
