package com.yc.utils;

import java.io.Serializable;
import java.util.List;

public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pageNo=1;	
	private int pageSize=15;	
	private int totalSize;	
	private int totalPages;	
	private List<?> list;	
	
	
	
	public int getPrePageNo(){
		int prePageNo=1;
		if(pageNo>1){
			prePageNo=pageNo-1;	
		}else{
			prePageNo=1;			
		}
		this.pageNo=prePageNo;
		return prePageNo;	
		
	}
	
	public int getNextPageNo(){
		int nextPageNo=1;
		if(pageNo<getTotalPages()){
			nextPageNo=pageNo+1;
		}else{
			nextPageNo=getTotalPages();	
		}
		this.pageNo=nextPageNo;
		return nextPageNo;
	}
	
	public int getTotalPages() {
		totalPages=this.getTotalSize()%this.getPageSize()==0?this.getTotalSize()/this.getPageSize():this.getTotalSize()/this.getPageSize()+1;
		return totalPages;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		if(totalSize<0){
			this.totalSize=0;
		}else{
			this.totalSize = totalSize;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize<=0){
			this.pageSize=5;
		}else{
			this.pageSize = pageSize;
		}
	}

	public int getPageNo() {
		if(this.pageNo>this.getTotalPages()){
			this.pageNo=this.getTotalPages();
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo<=0){
			this.pageNo=1;
		}else{
			this.pageNo = pageNo;
		}
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
