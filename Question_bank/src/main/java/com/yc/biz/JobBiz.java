package com.yc.biz;

import java.util.List;

import com.yc.po.Job;

public interface JobBiz {
	
	
	/*
	 * 按年份查询学员入职情况
	 */
	public List<Job> findJob(String year);
	
	
	/*
	 * 查询在eid公司最近入职过的20条信息
	 */
	public List<Job> findJobByeid(Integer eid); 
	
	/*
	 * 查询明细学员  --4年内的工资排名
	 */
	public List<Job> findStudentStar();
	
	/*
	 * 按省统计就业信息 汇总
	 */
	public List<Job> findJobPool();
	
	/*
	 * 按年份查询就业
	 */
	public List<Job>  findSalaryByYear(int year);
	
	/*
	 * 按省查找就业
	 */
	public List<Job>  findSalaryByProvince(String province,String page ,String row);
	
	
	public int findSalaryByProvincetotal(String province);
	
}
