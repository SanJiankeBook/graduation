package com.yc.po;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name="PointPaper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PointPaper implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid;  //知识点调查试题编号
	
	@Column(length=100,nullable = false)
	private String pname;  //试题名称
	@Column(length=2000,nullable = false)
	private String ptitle;  //试题题目
	
	//这里 是String 数据库是 Date??????
	@Column(length=20)
	@Temporal(TemporalType.DATE)
	private Date pdate;  //考试时间
	@Column(length=50,nullable = false)
	private String paperPwd;  //考试密码
    private Integer pstatus;  //试题状态  1.未开考   2.开考     3.结束
    @Column(length=400)
    private String descript;  //描述
    @Column(length=200)
	private String remark;  //扩充字段
	private String flag;  //扩充字段


	//这里外键    这里 是String 数据库是 Date??????
	@ManyToOne(fetch=FetchType.LAZY)//测试加上的
    @JoinColumn(name="sid")
	private Subject subject;
	
	@ManyToOne(fetch=FetchType.LAZY)//测试加上的
    @JoinColumn(name="cid")
	private ExamineeClass examineeClass;
	
	@OneToMany(mappedBy="pointPaper",fetch=FetchType.LAZY)
	private Set<PointAnswer>pointAnswers= new HashSet<PointAnswer>();
	
	public PointPaper() {
		super();
	}

	public Set<PointAnswer> getPointAnswers() {
		return pointAnswers;
	}

	public void setPointAnswers(Set<PointAnswer> pointAnswers) {
		this.pointAnswers = pointAnswers;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPtitle() {
		return ptitle;
	}

	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}


	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public String getPaperPwd() {
		return paperPwd;
	}

	public void setPaperPwd(String paperPwd) {
		this.paperPwd = paperPwd;
	}

	public Integer getPstatus() {
		return pstatus;
	}

	public void setPstatus(Integer pstatus) {
		this.pstatus = pstatus;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ExamineeClass getExamineeClass() {
		return examineeClass;
	}

	public void setExamineeClass(ExamineeClass examineeClass) {
		this.examineeClass = examineeClass;
	}

	@Override
	public String toString() {
		return "PointPaper [pid=" + pid + ", pname=" + pname + ", ptitle=" + ptitle + ", pdate=" + pdate + ", paperPwd="
				+ paperPwd + ", pstatus=" + pstatus + ", descript=" + descript + ", remark=" + remark + ", flag=" + flag
				+ ", subject=" + subject + ", examineeClass=" + examineeClass + ", pointAnswers=" + pointAnswers + "]";
	}


	
	
	
}
