package com.yc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public class UserBook implements Serializable {
	private static final long serialVersionUID = 2775440435282379616L;
	//用户书架
	private Integer ubid ;//	--书架id
	private Integer nid ;//		--小说id
	private Integer uid ;//		--用户id
	private String ubdate ;//		--记录时间
	private String pan_name ;//	--作者笔名
	private String [] list1; //小说id集合
	
	
	public String[] getList1() {
		return list1;
	}
	public void setList1(String[] list1) {
		this.list1 = list1;
	}
	public String getPan_name() {
		return pan_name;
	}
	public void setPan_name(String pan_name) {
		this.pan_name = pan_name;
	}
	public Integer getUbid() {
		return ubid;
	}
	public void setUbid(Integer ubid) {
		this.ubid = ubid;
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
	public String getUbdate() {
		return ubdate;
	}
	public void setUbdate(String ubdate) {
		this.ubdate = ubdate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nid == null) ? 0 : nid.hashCode());
		result = prime * result + ((ubdate == null) ? 0 : ubdate.hashCode());
		result = prime * result + ((ubid == null) ? 0 : ubid.hashCode());
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
		UserBook other = (UserBook) obj;
		if (nid == null) {
			if (other.nid != null)
				return false;
		} else if (!nid.equals(other.nid))
			return false;
		if (ubdate == null) {
			if (other.ubdate != null)
				return false;
		} else if (!ubdate.equals(other.ubdate))
			return false;
		if (ubid == null) {
			if (other.ubid != null)
				return false;
		} else if (!ubid.equals(other.ubid))
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
		return "UserBook [ubid=" + ubid + ", nid=" + nid + ", uid=" + uid + ", ubdate=" + ubdate + ", pan_name="
				+ pan_name + ", list1=" + Arrays.toString(list1) + "]";
	}
	
}
