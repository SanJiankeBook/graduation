package com.yc.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;


public interface ExamineeClassBiz {

	/**
	 * 通过班级名称得到班级编号
	 * 
	 * @param className
	 *          String 班级名称
	 * @return int 班级编号
	 * @throws Exception
	 */
	public int getClassIdOfname(String className);
	/**
	 * 通过班级名称查询出对应的人数
	 * 
	 * @param className
	 *          String 班级名称
	 * @return int 查到的人数存入整型变量返回
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public int searchExamineeCount(String className);
	/**
	 * 通过学期查询出对应的考生班级
	 * 
	 * @param semester
	 *          String 学期名称
	 * @return ArrayList 查到的班级存入集合返回
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<String> searchExamineeClassName(String semester);
	/**
	 *根据教员姓名查询教员信息
	 * @param uname：教员姓名
	 * @return
	 */
	public List<ExamineeClass> findExamineeClassBySemester(String semester);
	/**

	 * 查询所有班级的信息
	 * @return
	 */
	public List<ExamineeClass> findAllExamineeClass();
	/**

	 *根据教员姓名查询班级的名字和id
	 * @param semester：学期
	 * @return
	 */
	public List<ExamineeClass> findExamineeClassAndClassIdBySemester(String semester);
	/**

	 * 查询所有班级的名字
	 * @param semester
	 * @return
	 */
	public List<String> searchAllExamineeClassName();



	
	/**
	 * 查找所有的ExamineeClass
	 * @return
	 */
	public List<ExamineeClass> findAllClass();
	
	/**
	 * 通过ID找出对应的班级名称
	 * 
	 * @param id
	 * @return
	 */
	public String getClassNameById(int id);
	/**
	 * 根据班级ID  查出班级信息
	 * @param id
	 * @return
	 */
	public ExamineeClass findExamineeClassById(Integer id);
	/**
	 * 更新班级信息
	 * @param examineeClass
	 * @return
	 */
	public void updateExamineeClass(ExamineeClass examineeClass);
	/**
	 * 新增班级
	 * @param examineeClass
	 */
	public void addExamineeClass(ExamineeClass examineeClass);
	/**
	 * 通过班级编号查询出考生的数量
	 * classId  班级的id
	 * @param classId
	 * @return
	 */
	public int searchExamineeCount(Integer  classId);
	/**
	 * 查询所有的班级的名称和它的id
	 * @return
	 */
	public List<String> searchAllExamineeClassNameAndId();
	
	/**
	 * 根据班级编号删除班级
	 */
	public Integer deleteExaminee(Integer id);
	
	/*
	 * 查询所有未毕业的班级
	 * overDate ：毕业时间
	 */
	public List<ExamineeClass> findExamineeClassByTime(String overDate);
	
	public ExamineeClass findExamineeClassByName(String classname);//根据班级
	
	//原生更新班级
		public void updateClassSQL(Integer id,String className);
	/**
	 * 根据 版本号查 班级
	 * @param edit
	 * @return
	 */
	public List<ExamineeClass> findExamineeClassByEdit(String edit) ;
	//修改班级注意事项
	public Integer addClassNotice(Integer classid,String content) ;
	//根据班级获取注意事项
	public String getClassNotice(Integer classid);
	//获取所有班级的注意事项
	public List<String> getAllClassNotice();
	
	public int searchSingleClassExamineeCount(Integer classId) ;
	//通过班级名统计班级人数
	public int searchSingleClassExamineeCountByClassName(String className) ;
	
	//根据学生ID 查询该学生班级班级信息
	public ExamineeClass findExamineeClassByStuId(Integer studentId);
	
	/**
	 * --hwh
	 * 修改班级人数   采用定时器
	 * @param classid    班级id
	 * @param classcount  班级人数
	 */
	public void updateclasscount(Integer classcount, Integer classid);
	
	/**
	 * --hwh
	 * 修改班级人数   
	 * @param className    班级名
	 * @param classcount  班级人数
	 */
	public void updateclasscount(Integer classcount, String className);
	/**
	 * 通过班级名获取id
	 * @param examClass 班级名
	 * @return
	 */
	public List getInfo(String examClass);

}
