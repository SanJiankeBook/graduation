package com.yc.biz;

import java.io.Serializable;
import java.util.List;

import com.yc.po.PointPaper;
import com.yc.po.Temp;

public interface TempBiz {
	/**
	 * 清空temp表
	 */
	public int exectueDelTemp();
	
	/**
	 * 执行自评存储过程
	 * @param pid：自评试题编号
	 * @param cid：自评班级
	 * @param uname：自评学员姓名
	 */
	public void exectueProc(int pid,int cid,String uname);
	/**
	 * 执行考勤模块存储过程
	 * @param checkId：考勤记录编号
	 */
	public void exectueCheckingProc(int checkId);
	
	/**
	 * 统计临时表中数据的总数
	 * @return
	 */
	public int getTotalInfo();
	
	/**
	 * 按知识点统计
	 * @return
	 */
	public List<PointPaper> getPointTotal();
	
	
	/**
	 * 按课程统计人数
	 * @return
	 */
	public int findSubjectTotal();
	/**
	 * 按课程统计信息
	 * @return
	 */
	public List<String> findSubjectInfo();
	/**
	 * 根据知识点统计，总分存放在sname中，平均分存放在grade中
	 * @param pid:试卷编号
	 * @return
	 */
	public List<Temp> getTotalByPoint(int pid);
	
	/**
	 * 根据学生姓名统计，总分存放在className中，平均分存放在grade中，留言存放在pcontent中
	 * @param pid:试卷编号
	 * @return
	 */
	public List<Temp> getTotalBySname(int pid);
	
	/**
	 * 查询临时表中的所有数据
	 * @return
	 */
	public List<Temp> getAllTempInfo();
	
	/**
	 * 查询有考勤记录的学员姓名
	 * @param cid：考勤班级
	 * @return
	 */
	public List<Temp> getAllStuname(int cid);
	/**
	 * 获取查询的考勤记录的考勤日期
	 * @param cid
	 * @return
	 */
	public List<Temp> getAllDateTime(int cid);
	
	
	//获取所有的考勤记录
	public List<Temp> getAllCheckingInfo(int cid);
	
	/**
	 * 查询个人记录
	 * @param uname:要查询的学生姓名
	 * @return
	 */
	public List<Temp> getSingleCheckingInfo(String uname);
	/**
	 * 根据学生信息和考勤编号查询对于的信息
	 * @param ppid
	 * @param uname
	 * @return
	 */
	public Temp getSingleCheckingResultInfo(int ppid,String uname);
	
	/**
	 * 根据学生姓名统计
	 * @param uname：要统计的学生姓名
	 * @return
	 */
	public List<Temp> getTotalByStatus(String uname);
	
	
	/**
	 * 根据学生姓名统计考勤状态
	 * @param uname：要统计的学生姓名
	 * @return
	 */
	public List<Temp> getTotalStatusBysname(String uname);
	
	
	
	/**
	 * 根据学生姓名统计考勤记录
	 * @param uname：要统计的学生姓名
	 * @return
	 */
	public int getCheckingTotalInfo(String uname);
	
	
	/**
	 * 获取警告信息
	 * @param cid
	 * @return
	 */
	public List<Temp> getWarnInfo(int cid);
	
	/**
	 * 根据班级名称和课程id来查询信息
	 * @return
	 */
	public List<String> findTempInfoByClassNameAndSubjectId(String className,Integer subjectId);
	
	/**
	 * 添加数据
	 */
	public void addTemp(Temp t);
}
