package com.yc.vo;





import java.util.ArrayList;

import java.util.List;

public class Page<T> {

	// 当前页数

	private Integer currentPage=1;

	// 总页数

	private Integer totalsPage=1000;

	// 每页显示记录条数

	private Integer pageSize=10;

	// 总记录条数

	private Integer totalsCount=10;

	// 查询返回结果

	private List<T> result = new ArrayList<T>();

	// 分页链接
	private String uri;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalsPage() {
		return totalsPage;
	}

	public void setTotalsPage(Integer totalsPage) {
		this.totalsPage = totalsPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalsCount() {
		return totalsCount;
	}

	public void setTotalsCount(Integer totalsCount) {
		this.totalsCount = totalsCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentPage == null) ? 0 : currentPage.hashCode());
		result = prime * result + ((pageSize == null) ? 0 : pageSize.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((totalsCount == null) ? 0 : totalsCount.hashCode());
		result = prime * result + ((totalsPage == null) ? 0 : totalsPage.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Page other = (Page) obj;
		if (currentPage == null) {
			if (other.currentPage != null)
				return false;
		} else if (!currentPage.equals(other.currentPage))
			return false;
		if (pageSize == null) {
			if (other.pageSize != null)
				return false;
		} else if (!pageSize.equals(other.pageSize))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (totalsCount == null) {
			if (other.totalsCount != null)
				return false;
		} else if (!totalsCount.equals(other.totalsCount))
			return false;
		if (totalsPage == null) {
			if (other.totalsPage != null)
				return false;
		} else if (!totalsPage.equals(other.totalsPage))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", totalsPage=" + totalsPage + ", pageSize=" + pageSize
				+ ", totalsCount=" + totalsCount + ", result=" + result + ", uri=" + uri + "]";
	}

	
}