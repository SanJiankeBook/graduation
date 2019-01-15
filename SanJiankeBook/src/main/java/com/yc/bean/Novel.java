package com.yc.bean;

import java.io.Serializable;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
@Repository
public class Novel implements Serializable {
	private static final long serialVersionUID = -3615390225191887788L;
	private Integer nid ;//		--小说ID
	private Integer tid ;//			--类型id
	private Integer aid ;//		--作者id
	private String nname ;//		--小说名字
	private String npicture ;//		--小说封面存放地址	
	private String ndescription ;//	--小说描述
	private String nstatus ;//	--小说状态
	
	private String tname;      //类型名
	private String aname;      //作者名
	private String pan_name;  //笔名
	
	private String standby_1 ;//审核状态  待审核 通过 未通过
	private Author author;
	private NovelType novelType;
	
	private String ubdate ;//		--小说记录时间
	
	
	private List<String> originalFilename;//小说图片的原名
	private List<MultipartFile> pdfsUrl;//文件集合，对应前台上的<input type="file" name="pdfsUrl">
	
	
	public String getUbdate() {
		return ubdate;
	}
	public void setUbdate(String ubdate) {
		this.ubdate = ubdate;
	}
	public String getStandby_1() {
		return standby_1;
	}
	public void setStandby_1(String standby_1) {
		this.standby_1 = standby_1;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public NovelType getNovelType() {
		return novelType;
	}
	public void setNovelType(NovelType novelType) {
		this.novelType = novelType;
	}
	public List<String> getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(List<String> originalFilename) {
		this.originalFilename = originalFilename;
	}
	public List<MultipartFile> getPdfsUrl() {
		return pdfsUrl;
	}
	public void setPdfsUrl(List<MultipartFile> pdfsUrl) {
		this.pdfsUrl = pdfsUrl;

	}
	

	
	@Override
	public String toString() {
		return "Novel [nid=" + nid + ", tid=" + tid + ", aid=" + aid + ", nname=" + nname + ", npicture=" + npicture
				+ ", ndescription=" + ndescription + ", nstatus=" + nstatus + ", tname=" + tname + ", aname=" + aname
				+ ", pan_name=" + pan_name + ", standby_1=" + standby_1 + ", author=" + author + ", novelType="
				+ novelType + ", ubdate=" + ubdate + ", originalFilename=" + originalFilename + ", pdfsUrl=" + pdfsUrl
				+ "]";
			
	}
	public String getPan_name() {
		return pan_name;
	}

	public void setPan_name(String pan_name) {
		this.pan_name = pan_name;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	
	
	public Integer getNid() {
		return nid;
	}
	public void setNid(Integer nid) {
		this.nid = nid;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getNpicture() {
		return npicture;
	}
	public void setNpicture(String npicture) {
		this.npicture = npicture;
	}
	public String getNdescription() {
		return ndescription;
	}
	public void setNdescription(String ndescription) {
		this.ndescription = ndescription;
	}
	public String getNstatus() {
		return nstatus;
	}
	public void setNstatus(String nstatus) {
		this.nstatus = nstatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((ndescription == null) ? 0 : ndescription.hashCode());
		result = prime * result + ((nid == null) ? 0 : nid.hashCode());
		result = prime * result + ((nname == null) ? 0 : nname.hashCode());
		result = prime * result + ((novelType == null) ? 0 : novelType.hashCode());
		result = prime * result + ((npicture == null) ? 0 : npicture.hashCode());
		result = prime * result + ((nstatus == null) ? 0 : nstatus.hashCode());
		result = prime * result + ((originalFilename == null) ? 0 : originalFilename.hashCode());
		result = prime * result + ((pan_name == null) ? 0 : pan_name.hashCode());
		result = prime * result + ((pdfsUrl == null) ? 0 : pdfsUrl.hashCode());
		result = prime * result + ((standby_1 == null) ? 0 : standby_1.hashCode());
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
		result = prime * result + ((tname == null) ? 0 : tname.hashCode());
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
		Novel other = (Novel) obj;
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
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (ndescription == null) {
			if (other.ndescription != null)
				return false;
		} else if (!ndescription.equals(other.ndescription))
			return false;
		if (nid == null) {
			if (other.nid != null)
				return false;
		} else if (!nid.equals(other.nid))
			return false;
		if (nname == null) {
			if (other.nname != null)
				return false;
		} else if (!nname.equals(other.nname))
			return false;
		if (novelType == null) {
			if (other.novelType != null)
				return false;
		} else if (!novelType.equals(other.novelType))
			return false;
		if (npicture == null) {
			if (other.npicture != null)
				return false;
		} else if (!npicture.equals(other.npicture))
			return false;
		if (nstatus == null) {
			if (other.nstatus != null)
				return false;
		} else if (!nstatus.equals(other.nstatus))
			return false;
		if (originalFilename == null) {
			if (other.originalFilename != null)
				return false;
		} else if (!originalFilename.equals(other.originalFilename))
			return false;
		if (pan_name == null) {
			if (other.pan_name != null)
				return false;
		} else if (!pan_name.equals(other.pan_name))
			return false;
		if (pdfsUrl == null) {
			if (other.pdfsUrl != null)
				return false;
		} else if (!pdfsUrl.equals(other.pdfsUrl))
			return false;
		if (standby_1 == null) {
			if (other.standby_1 != null)
				return false;
		} else if (!standby_1.equals(other.standby_1))
			return false;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}

	
	
}
