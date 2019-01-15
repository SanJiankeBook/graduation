package com.yc.po;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bid; // 唯一标志号
	@Column
	private String cid; // 书籍类型名
	@Column
	private String author; // 作者
	@Column	
	private String isbn;//书籍编号
	@Column	
	private Float price; //书籍价格
	@Column	
	private String title;//书籍名称
	@Column	
	private String imagesUrl;//图片地址
	@Column	
	private String pdfsUrl;//pdf地址
	@Column	
	private String codesUrl;//源码地址
	@Column	
	private Integer resourceTypeId;//书籍？源码？工具类
	@Column	
	private String  description;//书籍？源码？工具类
	@Column	
	private Integer edtionId;//版本id
	@Column	
	private String semeter;//学期编号
	@Column	
	private Integer subjectid;//课程id
	

	public Integer getEdtionId() {
		return edtionId;
	}
	public void setEdtionId(Integer edtionId) {
		this.edtionId = edtionId;
	}
	public String getSemeter() {
		return semeter;
	}
	public void setSemeter(String semeter) {
		this.semeter = semeter;
	}
	public Integer getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImagesUrl() {
		return imagesUrl;
	}
	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
	public String getPdfsUrl() {
		return pdfsUrl;
	}
	public void setPdfsUrl(String pdfsUrl) {
		this.pdfsUrl = pdfsUrl;
	}
	public String getCodesUrl() {
		return codesUrl;
	}
	public void setCodesUrl(String codesUrl) {
		this.codesUrl = codesUrl;
	}
	public Integer getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(Integer resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bid == null) ? 0 : bid.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((codesUrl == null) ? 0 : codesUrl.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((edtionId == null) ? 0 : edtionId.hashCode());
		result = prime * result + ((imagesUrl == null) ? 0 : imagesUrl.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((pdfsUrl == null) ? 0 : pdfsUrl.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((resourceTypeId == null) ? 0 : resourceTypeId.hashCode());
		result = prime * result + ((semeter == null) ? 0 : semeter.hashCode());
		result = prime * result + ((subjectid == null) ? 0 : subjectid.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bid == null) {
			if (other.bid != null)
				return false;
		} else if (!bid.equals(other.bid))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (codesUrl == null) {
			if (other.codesUrl != null)
				return false;
		} else if (!codesUrl.equals(other.codesUrl))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (edtionId == null) {
			if (other.edtionId != null)
				return false;
		} else if (!edtionId.equals(other.edtionId))
			return false;
		if (imagesUrl == null) {
			if (other.imagesUrl != null)
				return false;
		} else if (!imagesUrl.equals(other.imagesUrl))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (pdfsUrl == null) {
			if (other.pdfsUrl != null)
				return false;
		} else if (!pdfsUrl.equals(other.pdfsUrl))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (resourceTypeId == null) {
			if (other.resourceTypeId != null)
				return false;
		} else if (!resourceTypeId.equals(other.resourceTypeId))
			return false;
		if (semeter == null) {
			if (other.semeter != null)
				return false;
		} else if (!semeter.equals(other.semeter))
			return false;
		if (subjectid == null) {
			if (other.subjectid != null)
				return false;
		} else if (!subjectid.equals(other.subjectid))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", cid=" + cid + ", author=" + author + ", isbn=" + isbn + ", price=" + price
				+ ", title=" + title + ", imagesUrl=" + imagesUrl + ", pdfsUrl=" + pdfsUrl + ", codesUrl=" + codesUrl
				+ ", resourceTypeId=" + resourceTypeId + ", description=" + description + ", edtionId=" + edtionId
				+ ", semeter=" + semeter + ", subjectid=" + subjectid + "]";
	}

}
