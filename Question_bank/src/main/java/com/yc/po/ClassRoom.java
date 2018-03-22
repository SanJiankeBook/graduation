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

@Entity
@Table(name = "ClassRoom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassRoom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer classroomid;// 教室编号
	@Column(length = 50)
	private String classroomname;// 教室名称
	@Column(length = 50)
	private String local;// 教室地点
	@Column
	private Integer machineNumber; // 教室机器数
	@Column
	private Integer status; // 教室状态
	public Integer getClassroomid() {
		return classroomid;
	}
	public void setClassroomid(Integer classroomid) {
		this.classroomid = classroomid;
	}
	public String getClassroomname() {
		return classroomname;
	}
	public void setClassroomname(String classroomname) {
		this.classroomname = classroomname;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public Integer getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(Integer machineNumber) {
		this.machineNumber = machineNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
 
	 
 
}
