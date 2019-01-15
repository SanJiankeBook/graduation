package com.yc.bean;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
@Repository
public class NovelChapter implements Serializable {
	//小说章节信息
	
	private static final long serialVersionUID = 6662575089312146692L;
	private Integer cid ;//		--小说章节id
	private Integer nid ;//			--小说id
	private String cname ;//			--章节名字
	private String caddress ;//		--章节存放地址
	private String standby_1 ;//章节状态 --通过，未通过，待审
	private Integer standby_2; //章节编号
	
	private String tname;      //类型名
	private String nname ;//		--小说名字
	
	public Integer getStandby_2() {
		return standby_2;
	}
	public void setStandby_2(Integer standby_2) {
		this.standby_2 = standby_2;
	}
	private String npicture ;//		--小说封面存放地址
	
	
	
	public String getNpicture() {
		return npicture;
	}
	public void setNpicture(String npicture) {
		this.npicture = npicture;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getStandby_1() {
		return standby_1;
	}
	public void setStandby_1(String standby_1) {
		this.standby_1 = standby_1;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getNid() {
		return nid;
	}
	public void setNid(Integer nid) {
		this.nid = nid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCaddress() {
		return caddress;
	}
	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caddress == null) ? 0 : caddress.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((cname == null) ? 0 : cname.hashCode());
		result = prime * result + ((nid == null) ? 0 : nid.hashCode());
		result = prime * result + ((standby_1 == null) ? 0 : standby_1.hashCode());
		result = prime * result + ((standby_2 == null) ? 0 : standby_2.hashCode());
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
		NovelChapter other = (NovelChapter) obj;
		if (caddress == null) {
			if (other.caddress != null)
				return false;
		} else if (!caddress.equals(other.caddress))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (cname == null) {
			if (other.cname != null)
				return false;
		} else if (!cname.equals(other.cname))
			return false;
		if (nid == null) {
			if (other.nid != null)
				return false;
		} else if (!nid.equals(other.nid))
			return false;
		if (standby_1 == null) {
			if (other.standby_1 != null)
				return false;
		} else if (!standby_1.equals(other.standby_1))
			return false;
		if (standby_2 == null) {
			if (other.standby_2 != null)
				return false;
		} else if (!standby_2.equals(other.standby_2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NovelChapter [cid=" + cid + ", nid=" + nid + ", cname=" + cname + ", caddress=" + caddress
				+ ", standby_1=" + standby_1 + ", standby_2=" + standby_2 + "]";
	}
	
	
	
}
