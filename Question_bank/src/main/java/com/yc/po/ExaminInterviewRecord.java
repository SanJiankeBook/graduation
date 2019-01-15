package com.yc.po;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name ="examininterviewrecord")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
/**
 * 访谈记录表映射实体类
 * @author pengtao
 *
 */
public class ExaminInterviewRecord implements Serializable {
	private static final long serialVersionUID = 3505346683679390781L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 唯一标识号
	@Column(length = 50)
	private String className; // 班级名
	@Column(length = 50)
	private String teacherName; // 教师名
	@Column(length=50)
	private String studentName;//学生名
	@Column(length=225)
	private String remark;//备注
	@Column
	private String content;//内容
	@Column(length=100)
	private String interviewAddress;//地址
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date pdate; // 开讲日期
	
	private Date startpdate;//开始时间
	private Date endpdate;//结束时间
	
	
	public Date getStartpdate() {
		return startpdate;
	}
	public void setStartpdate(Date startpdate) {
		this.startpdate = startpdate;
	}
	public Date getEndpdate() {
		return endpdate;
	}
	public void setEndpdate(Date endpdate) {
		this.endpdate = endpdate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInterviewAddress() {
		return interviewAddress;
	}
	public void setInterviewAddress(String interviewAddress) {
		this.interviewAddress = interviewAddress;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((interviewAddress == null) ? 0 : interviewAddress.hashCode());
		result = prime * result + ((pdate == null) ? 0 : pdate.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
		result = prime * result + ((teacherName == null) ? 0 : teacherName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminInterviewRecord other = (ExaminInterviewRecord) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (interviewAddress == null) {
			if (other.interviewAddress != null)
				return false;
		} else if (!interviewAddress.equals(other.interviewAddress))
			return false;
		if (pdate == null) {
			if (other.pdate != null)
				return false;
		} else if (!pdate.equals(other.pdate))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (studentName == null) {
			if (other.studentName != null)
				return false;
		} else if (!studentName.equals(other.studentName))
			return false;
		if (teacherName == null) {
			if (other.teacherName != null)
				return false;
		} else if (!teacherName.equals(other.teacherName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ExaminInterviewRecord [id=" + id + ", className=" + className + ", teacherName=" + teacherName
				+ ", studentName=" + studentName + ", remark=" + remark + ", content=" + content + ", interviewAddress="
				+ interviewAddress + ", pdate=" + pdate + "]";
	}
	
}
