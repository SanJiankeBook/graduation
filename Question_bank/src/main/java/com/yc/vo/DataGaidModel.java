package com.yc.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器向客户端发送数据
 * @author Administrator
 *
 */
public class DataGaidModel implements Serializable {
	private Long total;
	private List rows = new ArrayList();

	

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
