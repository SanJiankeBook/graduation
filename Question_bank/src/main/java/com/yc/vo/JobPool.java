package com.yc.vo;

import java.io.Serializable;

public class JobPool implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;  //省id  配合前端页面显示
	
	private Integer worknum;  //省就业人数
	
	private double salary;   //平均工资
	
	
	private String name; //省名称


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getWorknum() {
		return worknum;
	}


	public void setWorknum(Integer worknum) {
		this.worknum = worknum;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "JobPool [id=" + id + ", worknum=" + worknum + ", salary=" + salary + ", name=" + name + "]";
	}
	
	
	

}
