package com.yc.biz;

import java.io.Serializable;
import java.util.List;

import com.yc.po.Work;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;

public interface WorkBiz {
	
	/**
	 * 查找某个班级开启默写的作业
	 * @param classid
	 * @return
	 */
	public List<Work> findDictation(int classid);
	
	/**
	 * 开始默写
	 */
	public int updateworkstatus(int wid);
	
	public Serializable addWork(Work work);
	
	public List<Work> findWork(Integer classid,String semester,String checkdate,String page,String rows);
	
	public List<Work> findWorkbydictation(Integer classid,String semester);
	
	public List<Work> findWorkdetail(int classid, int year, int month);
	
	public int updateWorkresult(Integer wid,String result,String checkcount);
	
	public Work findWorkbyid(Integer wid);
	
	public int findworknum(Integer classid,String editionid,String subid,String chapid,String date);
	
	public List<Work> findWorkbyclass(Work work,String page,String rows);
	
	public int getClassWorkNum(Integer classid,String startime,String endtime);
	
	//public void updateWorkCheckcount();
	
	public List<Work> getTeacherchekwork(String teachername,int year,int month);
	
	public int getTeacherchekcount(String teachername,String startime,String endtime);
	
	public int getWorkCheckcount(Integer classid,String startime,String endtime);
	
	public List<Workdetail> getclassWorkdetail(String year,String month,String path);
	
	public List<TeacherWorkdetail> getteacherWorkdetail(String year,String month);
	
	public List<Work> getstudentwork(Integer classid,String semeter,String startDate,String endDate);
}
