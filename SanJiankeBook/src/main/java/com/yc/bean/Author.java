package com.yc.bean;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
@Repository
public class Author implements Serializable{
	private static final long serialVersionUID = -8784153357923278732L;
	private Integer aid ;//	--作者id
	private Integer uid;//				--用户id
	private String aname ;//		--作者姓名
	private String pan_name ;//	--作者笔名
	private Integer aage ;//				--作者年龄
	private String agrade ;//		--作者等级
	private String acard ;//		--作者身份证号
	private String atel ;//--作者联系方式
	
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getPan_name() {
		return pan_name;
	}
	public void setPan_name(String pan_name) {
		this.pan_name = pan_name;
	}
	public Integer getAage() {
		return aage;
	}
	public void setAage(Integer aage) {
		this.aage = aage;
	}
	public String getAgrade() {
		return agrade;
	}
	public void setAgrade(String agrade) {
		this.agrade = agrade;
	}
	public String getAcard() {
		return acard;
	}
	public void setAcard(String acard) {
		this.acard = acard;
	}
	public String getAtel() {
		return atel;
	}
	public void setAtel(String atel) {
		this.atel = atel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aage == null) ? 0 : aage.hashCode());
		result = prime * result + ((acard == null) ? 0 : acard.hashCode());
		result = prime * result + ((agrade == null) ? 0 : agrade.hashCode());
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		result = prime * result + ((atel == null) ? 0 : atel.hashCode());
		result = prime * result + ((pan_name == null) ? 0 : pan_name.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		Author other = (Author) obj;
		if (aage == null) {
			if (other.aage != null)
				return false;
		} else if (!aage.equals(other.aage))
			return false;
		if (acard == null) {
			if (other.acard != null)
				return false;
		} else if (!acard.equals(other.acard))
			return false;
		if (agrade == null) {
			if (other.agrade != null)
				return false;
		} else if (!agrade.equals(other.agrade))
			return false;
		if (aid == null) {
			if (other.aid != null)
				return false;
		} else if (!aid.equals(other.aid))
			return false;
		if (aname == null) {
			if (other.aname != null)
				return false;
		} else if (!aname.equals(other.aname))
			return false;
		if (atel == null) {
			if (other.atel != null)
				return false;
		} else if (!atel.equals(other.atel))
			return false;
		if (pan_name == null) {
			if (other.pan_name != null)
				return false;
		} else if (!pan_name.equals(other.pan_name))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Author [aid=" + aid + ", uid=" + uid + ", aname=" + aname + ", pan_name=" + pan_name + ", aage=" + aage
				+ ", agrade=" + agrade + ", acard=" + acard + ", atel=" + atel + "]";
	}
	
	
}
