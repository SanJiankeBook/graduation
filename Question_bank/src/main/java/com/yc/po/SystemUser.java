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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * Title:用户信息
 * </p>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SystemUser")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SystemUser implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 20, nullable = false)
	private String userName;
	@Column(length = 64, nullable = false)
	private String password;
	@Column(length = 20, nullable = false)
	private String authorities;
	@Column(length = 255)
	private String remark;
	@Column(length = 255)
	private String bgcolor;
	@Column(length = 255)
	private String fontcolor;
	@Column
	private Integer status;

	@OneToMany(mappedBy = "systemUser",fetch=FetchType.LAZY)//测试加上的
	private Set<Checking> checkings = new HashSet<Checking>();

	public Set<Checking> getCheckings() {
		return checkings;
	}

	public void setCheckings(Set<Checking> checkings) {
		this.checkings = checkings;
	}

	public SystemUser() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getAuthorities() {
		return authorities;
	}

	public String getRemark() {
		return remark;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
