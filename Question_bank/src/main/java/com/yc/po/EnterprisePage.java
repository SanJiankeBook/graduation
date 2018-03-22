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
@Entity
@Table(name = "Enterprise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnterprisePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键由数据库自动生成
	private Integer eid;  //主键id 
	@Column
	private String  ename; //公司名称
	@Column
	private String address; //公司地址
	@Column
	private String etelephone; //公司电话
	@Column
	private String eFax; //公司电话
	@Column
	private String ePost; //公司联系方式
	@Column
	private String eURL; //公司联系方式
	@Column
	private String eDescription; //公司描述
	@Column
	private String type; //公司类型
	
	private Integer worknum; //公司入职人数
	
	

	public Integer getWorknum() {
		return worknum;
	}

	public void setWorknum(Integer worknum) {
		this.worknum = worknum;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEtelephone() {
		return etelephone;
	}

	public void setEtelephone(String etelephone) {
		this.etelephone = etelephone;
	}

	public String geteFax() {
		return eFax;
	}

	public void seteFax(String eFax) {
		this.eFax = eFax;
	}

	public String getePost() {
		return ePost;
	}

	public void setePost(String ePost) {
		this.ePost = ePost;
	}

	public String geteURL() {
		return eURL;
	}

	public void seteURL(String eURL) {
		this.eURL = eURL;
	}

	public String geteDescription() {
		return eDescription;
	}

	public void seteDescription(String eDescription) {
		this.eDescription = eDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
}
