package com.yc.bean;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
@Repository
public class UserTalk implements Serializable {
	//--用户评论表
	private static final long serialVersionUID = 2838065158221135168L;
	private Integer utid;//	--评论id
	private Integer nid;//	--小说id
	private Integer uid ;//		--用户id
	private String utdate ;//		--评论时间
	private String utcontent ;//	--评论内容
	private String uname;//用户名字
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getUtid() {
		return utid;
	}
	public void setUtid(Integer utid) {
		this.utid = utid;
	}
	public Integer getNid() {
		return nid;
	}
	public void setNid(Integer nid) {
		this.nid = nid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUtdate() {
		return utdate;
	}
	public void setUtdate(String utdate) {
		this.utdate = utdate;
	}
	public String getUtcontent() {
		return utcontent;
	}
	public void setUtcontent(String utcontent) {
		this.utcontent = utcontent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nid == null) ? 0 : nid.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		result = prime * result + ((utcontent == null) ? 0 : utcontent.hashCode());
		result = prime * result + ((utdate == null) ? 0 : utdate.hashCode());
		result = prime * result + ((utid == null) ? 0 : utid.hashCode());
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
		UserTalk other = (UserTalk) obj;
		if (nid == null) {
			if (other.nid != null)
				return false;
		} else if (!nid.equals(other.nid))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		if (utcontent == null) {
			if (other.utcontent != null)
				return false;
		} else if (!utcontent.equals(other.utcontent))
			return false;
		if (utdate == null) {
			if (other.utdate != null)
				return false;
		} else if (!utdate.equals(other.utdate))
			return false;
		if (utid == null) {
			if (other.utid != null)
				return false;
		} else if (!utid.equals(other.utid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserTalk [utid=" + utid + ", nid=" + nid + ", uid=" + uid + ", utdate=" + utdate + ", utcontent="
				+ utcontent + ", uname=" + uname + "]";
	}
	
	
	
}
