package com.yc.vo;

import java.io.Serializable;

import javax.persistence.Column;

public class JobPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;  //主键

	private Integer salary; //工资

	private Integer examineeid;  //学员id

	private Integer eid;  //入职公司

	private String entrydate;  //入职时间  --年份

	private String eDescription; //公司地址

}
