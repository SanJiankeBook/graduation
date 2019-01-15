package com.yc.bean;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
@Repository
public class Admin implements Serializable{
	private static final long serialVersionUID = 1224968287234154560L;
	private Integer adid ;//	--管理员id	
	private String adnumber ;//	--管理员账号
	private String adpassword ;//	--管理员密码
	public Integer getAdid() {
		return adid;
	}
	public void setAdid(Integer adid) {
		this.adid = adid;
	}
	public String getAdnumber() {
		return adnumber;
	}
	public void setAdnumber(String adnumber) {
		this.adnumber = adnumber;
	}
	public String getAdpassword() {
		return adpassword;
	}
	public void setAdpassword(String adpassword) {
		this.adpassword = adpassword;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adid == null) ? 0 : adid.hashCode());
		result = prime * result + ((adnumber == null) ? 0 : adnumber.hashCode());
		result = prime * result + ((adpassword == null) ? 0 : adpassword.hashCode());
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
		Admin other = (Admin) obj;
		if (adid == null) {
			if (other.adid != null)
				return false;
		} else if (!adid.equals(other.adid))
			return false;
		if (adnumber == null) {
			if (other.adnumber != null)
				return false;
		} else if (!adnumber.equals(other.adnumber))
			return false;
		if (adpassword == null) {
			if (other.adpassword != null)
				return false;
		} else if (!adpassword.equals(other.adpassword))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Admin [adid=" + adid + ", adnumber=" + adnumber + ", adpassword=" + adpassword + "]";
	}
	
	
}
