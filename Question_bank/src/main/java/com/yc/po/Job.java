package com.yc.po;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "Job")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Lazy(value=true)
public class Job implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键由数据库自动生成
	private Integer id;  //主键
	
	@Column
	private Integer salary; //工资
	
	@Column
	private String examineename;//学员名字
	
	@Column
	private String province;//入职省份
	

	@Column
	private Integer examineeid;  //学员id
	
	@Column
	private Integer eid;  //入职公司
	
	@Column
	private int entrydate;  //入职时间  --年份

	
	
	
	
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getExamineeid() {
		return examineeid;
	}

	public void setExamineeid(Integer examineeid) {
		this.examineeid = examineeid;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}


	public int getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(int entrydate) {
		this.entrydate = entrydate;
	}
	
	public String getExamineename() {
		return examineename;
	}

	public void setExamineename(String examineename) {
		this.examineename = examineename;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", salary=" + salary + ", examineename=" + examineename + ", province=" + province
				+ ", examineeid=" + examineeid + ", eid=" + eid + ", entrydate=" + entrydate + "]";
	}


	

}
