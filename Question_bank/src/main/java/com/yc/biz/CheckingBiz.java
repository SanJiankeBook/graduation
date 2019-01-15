package com.yc.biz;

import java.util.Date;
import java.util.List;

import com.yc.po.Checking;

public interface CheckingBiz {

	/**
	 * 添加考勤信息
	 * @param ck：要添加的考勤记录对象
	 * @return
	 */
	public int addCheckingInfo(Checking ck);


	public int updateCheckingInfo(Checking ck);
	/**
	 * 获取最新的考勤记录记录，考勤班级名称存放在checkflag中
	 * @return
	 */
	public Checking findNewCheckingId();
	
	/**
	 * 获取指定的考勤记录信息，考勤班级名称存放在checkflag中
	 * @return
	 */
	public Checking findNewCheckingIdes(int checkId);


	/**
	 * 根据考勤编号查询考勤记录
	 * @param checkId：要查询的考勤编号
	 * @return
	 */
	public Checking findCheckingInfoById(int checkId);

	
	/**
	 * 根据给定的条件组合查询
	 * @param semester：学期
	 * @param cid：班级编号
	 * @param stuname：学生姓名
	 * @param startdate：查询开始时间
	 * @param enddate：查询结束时间
	 * @param datetime：查询时间段
	 * @return
	 */
	public List<Checking> findCheckingByAll(String semester,int cid,String stuname,String startdate,String enddate,String datetime);
	
	
	/**
	 * 根据给定的条件组合查询满足条件的考勤编号
	 * @param semester：学期
	 * @param cid：班级编号
	 * @param stuname：学生姓名
	 * @param startdate：查询开始时间
	 * @param enddate：查询结束时间
	 * @param datetime：查询时间段
	 * @return
	 */
	public List<Integer> findCheckingAllCheckId(String semester,int cid,String stuname,String startdate,String enddate,String datetime);
	
	/**
	 * 根据跟定的条件查询考勤信息（学生端）
	 * @param semester：学期
	 * @param startDate：查询开始时间
	 * @param endDate：查询结束时间
	 * @param dateTime：查询时间段
	 * @param uname：学生姓名
	 * @return
	 */
	public List<Checking> findCheckingResullByStudentName(String semester,String startDate,String endDate,String dateTime,String uname);
	
	/**
	 * 根据给定的条件组合查询
	 * @param semester：学期
	 * @param cid：班级编号
	 * @param startdate：查询开始时间
	 * @param enddate：查询结束时间
	 * @param datetime：查询时间段
	 * @return
	 */////////////////////////////////////////////////
	public List<Checking> showCheckingResult(String semester,int cid,String startdate,String enddate,String datetime);
	
	/**
	 * 查询当前班级当前时间段是否已经考勤过
	 * @param cid：考勤班级编号
	 * @param date：考勤日期
	 * @param time：考勤时间段
	 * @return
	 */
	public Checking findCheckingByClassIdAndDateTime(int cid,Date date,String time);
	
	/**
	 * 
	 */
	
	public  List<Checking>  findCheckingByClassIdAndUidAndTime(  int cid,String time,String startDate,String endDate);
	
	public List<Checking> findCheckByCheckid(int checkid);
	
//  原生 根据条件查id
	public List<Integer> findCheckingAllCheckIdExcuteSql(String semester, int cid,
			String stuname, String startdate, String enddate, String datetime) ;
	// 查询 一个list的ID类的所有Check值
	public List<Checking> findCheckInCheckid(List<Integer> checkid) ;
	//  原生 根据 班级id查 checking
	public List<Checking> findCheckByClassId(int cid,String startDate,String endDate,String datetime);
}


