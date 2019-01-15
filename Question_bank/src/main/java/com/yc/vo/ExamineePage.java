package com.yc.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yc.po.ExamineeClass;

public class ExamineePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;


	private String name;

	private String password;// 学生密码

	private String idCard; // 学生身份证
	
	private String weixin; // 微信
	
	private String qq; 
	
	private String email;  
	
	private Integer examineeid;
	
	private String tel; // 学生手机

	public Integer getId() {
		return id;
	}
	
	

	public Integer getExamineeid() {
		return examineeid;
	}



	public void setExamineeid(Integer examineeid) {
		this.examineeid = examineeid;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "ExamineePage [id=" + id + ", name=" + name + ", password=" + password + ", idCard=" + idCard
				+ ", weixin=" + weixin + ", qq=" + qq + ", email=" + email + ", tel=" + tel + "]";
	}

	
	
}
