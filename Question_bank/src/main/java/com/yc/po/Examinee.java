package com.yc.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.annotation.Lazy;

import javax.persistence.JoinColumn;



@Entity
@Table(name = "Examinee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Lazy(value=true)
public class Examinee implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "classId")
	private ExamineeClass examineeClass;

	@Column(length = 20)
	private String name;

	@Column(length = 20, nullable = false)
	private String password;// 学生密码

	@Column(length = 18)
	private String idCard; // 学生身份证
	@Column
	private String weixin; // 微信
	@Column
	private String qq; 
	@Column
	private String email;  
	
	@Column(length = 11)
	private String tel; // 学生手机
	@Column
	private Integer studentStatus;//学生的状态
	
	public Integer getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(Integer studentStatus) {
		this.studentStatus = studentStatus;
	}

	// @OneToMany
	// @JoinColumn(name="examinee")
	// private Set<PointAnswer>pointAnswers= new HashSet<PointAnswer>();

	@OneToMany(cascade = CascadeType.ALL ,fetch=FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "name", referencedColumnName = "name"),
			@JoinColumn(name = "classId", referencedColumnName = "classId") })
	private Set<PointAnswer> pointAnswers = new HashSet<PointAnswer>();

	public Set<PointAnswer> getPointAnswers() {
		return pointAnswers;
	}

	public void setPointAnswers(Set<PointAnswer> pointAnswers) {
		this.pointAnswers = pointAnswers;
	}

	public ExamineeClass getExamineeClass() {
		return examineeClass;
	}

	public void setExamineeClass(ExamineeClass examineeClass) {
		this.examineeClass = examineeClass;
	}

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((examineeClass == null) ? 0 : examineeClass.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCard == null) ? 0 : idCard.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((pointAnswers == null) ? 0 : pointAnswers.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
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
		Examinee other = (Examinee) obj;
		if (examineeClass == null) {
			if (other.examineeClass != null)
				return false;
		} else if (!examineeClass.equals(other.examineeClass))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCard == null) {
			if (other.idCard != null)
				return false;
		} else if (!idCard.equals(other.idCard))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pointAnswers == null) {
			if (other.pointAnswers != null)
				return false;
		} else if (!pointAnswers.equals(other.pointAnswers))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		return true;
	}

	public Examinee() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Examinee [id=" + id + ", examineeClass=" + examineeClass + ", name=" + name + ", password=" + password
				+ ", idCard=" + idCard + ", weixin=" + weixin + ", qq=" + qq + ", email=" + email + ", tel=" + tel
				+ ", studentStatus=" + studentStatus + ", pointAnswers=" + pointAnswers + "]";
	}

	
}
