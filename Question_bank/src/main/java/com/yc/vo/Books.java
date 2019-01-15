package com.yc.vo;

import java.io.Serializable;

import javax.persistence.Column;

public class Books implements Serializable {
	private static final long serialVersionUID = -2924481607329368481L;
	private Integer bid; // 唯一标志号
	private String cid; // 书籍类型名
	private String author; // 作者
	private String isbn;//书籍编号
	private Float price; //书籍价格
	private String title;//书籍名称
		
	private String imagesUrl;//图片地址
		
	private String pdfsUrl;//pdf地址
		
	private String codesUrl;//源码地址
		
	private Integer resourceTypeId;//书籍？源码？工具类
		
	private String  description;//书籍？源码？工具类
		
	private Integer edtionId;//版本id
		
	private String semeter;//学期编号
		
	private Integer subjectid;//课程id
	
	private String edtionName;//学期名
	
	private String subjectName;//课程名

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

	public String getEdtionName() {
		return edtionName;
	}

	public void setEdtionName(String edtionName) {
		this.edtionName = edtionName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	

}
