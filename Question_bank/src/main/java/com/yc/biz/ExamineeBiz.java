package com.yc.biz;

import java.util.List;

import com.yc.po.Examinee;
import com.yc.vo.ExamineePage;

public interface ExamineeBiz {
	/**
	 * 判断根据本班级id,判断是否本班考生
	 * 
	 * @param name
	 *            //姓名
	 * @param classId
	 *            //班级id
	 * @return boolean //返回是否存在的布尔值
	 * @throws Exception
	 *             //抛出系统异常
	 */
	public boolean isClassExaminee(String name, int classId);

	/**
	 * 查询学生信息通过班级和学生信息
	 * --hwh
	 * @param name
	 * @param className
	 * @return
	 */
	public List searchExamineeByExamineeName(String name,String className);

	/**
	 * 判断根据姓名,本班级id,密码,判断是否本班考生
	 * 
	 * @param name
	 *            //姓名
	 * @param classId
	 *            //班级id
	 * @param password
	 *            //密码
	 * @return boolean //返回是否存在的布尔值
	 * @throws Exception
	 *             //抛出系统异常
	 */
	public boolean isClassExaminee(String name, int classId, String password);

	/**
	 * 通过班级编号得到所有的考生姓名
	 * 
	 * @param classId
	 *            int 班级编号
	 * @return ArrayList 返回对应的考生姓名的集合
	 * @throws Exception
	 */

	public List<Examinee> findAllStuNameByClassName(String className);


	/**
	 * 通过班级编号得到所有的考生姓名
	 * 
	 * @param classId
	 *            int 班级编号
	 * @return ArrayList 返回对应的考生姓名的集合
	 * @throws Exception
	 */

	public List<String> findAllStuNameByClassId(Integer classId);
	/**
	 * 通过班级编号得到所有的考生姓名
	 * 
	 * @param classId
	 *            int 班级编号
	 * @return ArrayList 返回对应的考生集合
	 * @throws Exception
	 */

	public List<Examinee> findAllStudentByClassName(String className);
	/**
	 * 通过班级名称得到所有的考生姓名
	 * 
	 * @param classId
	 *            int 班级编号
	 * @return ArrayList 返回对应的考生集合
	 * @throws Exception
	 */

	public List<Examinee> findAllStudent(int classId);

	/**
	 * 根据班级编号查询有自评记录的学生姓名
	 * 
	 * @param classId
	 * @return
	 */
	public List<Examinee> findAllStudentAndExaminee(int classId);

	@SuppressWarnings("unchecked")
	public List searchExaminee(int classId);

	/*
	 * 修改考生姓名，根据旧姓名和班级
	 */
	public int updateExaminee(Examinee examinee);

	/**
	 * 判断考生姓名和班级找考生是否存在
	 * 
	 * @param examinee
	 *            Examinee //Examinee实体对像
	 * @return boolean //返回是否存在的布尔值
	 * @throws Exception
	 *             //抛出系统异常
	 */
	public boolean isExists(Examinee examinee);


	/*
	 * 更新一个班级所有考生的密码
	 */
	public int updateClassPwd(String className, String pwd);

	/**
	 * 根据考生班级,返回所对应的id号
	 * 
	 * @param classname
	 *            班级名称
	 * @return classId 返回班级名称编号
	 * @throws Exception
	 */
	public int searchClassid(String classname);

	/**
	 * 根据班级编号，得到考生数量
	 * 
	 * @param classId
	 *            int
	 * @return int
	 * @throws Exception
	 */
	public int getExamineeCount(int classId);

	/**
	 * 根据班级id,返回所对应的考生集合
	 * 
	 * @param clasid
	 *            班级id
	 * @return list 返回本班的考生集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchClassExaminee(int clasid);

	/**
	 * 根据考生姓名,密码,班级id插入考生列表
	 * @param tel 
	 * @param idCard 
	 * 
	 * @param name_
	 *            考生姓名
	 * @param password_
	 *            考生密码
	 * @param clasId
	 *            班级id
	 * @return int 返回插入的记录行数
	 * @throws Exception
	 */
	public int addExaminee(String examName, String password, int classId, String idCard, String tel);

	/**
	 * 根据考生班级,班级名称,修改考生密码,返回是否修改成功
	 * 
	 * @param clasid
	 *            班级id
	 * @param name
	 *            姓名
	 * @param newPassword
	 *            新密码
	 * @return classId 返回是否修改成功
	 * @throws Exception
	 */
	public boolean updatePassword(int clasid, String name, String newPassword);

	/**修改教员密码*/
	public boolean updateTeacherPassword(String name, String newPassword);

	/**
	 * 查询考生列表, 如果条件不为空或只指定部分条件, 用指定条件查询考生,否则,查询所有考生, 返回一个考生集合,第一条记录代表一个对象.
	 * 
	 * @param Examinee
	 *            examinee //考生Bean实体对像
	 * @param displayRows
	 *            int //一页显示的行数
	 * @param nextNum
	 *            int //当前第几页
	 * @return ArrayList //返回查询结果
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchExamineelist(Examinee examinee, int displayRows,
			int nextNum);

	/**
	 * 通过指定条件,得到查询到的最大行数
	 * 
	 * @param Examinee
	 *            examinee //考生Bean实体对像
	 * @return int
	 * @throws Exception
	 */
	public int getSearchMaxRow(Examinee examinee);

	/**
	 * 根据班级编号,姓名去删除考生表数据行
	 * 
	 * @param claid
	 *            //班级编号
	 * @param strname
	 *            //姓名
	 * @return int //返回成功删除的行数
	 * @throws Exception
	 *             //抛出系统异常
	 */
	public int deletelistexam(int classId, String name);

	/*
	 * 根据班级名称，找出班级对应的考生姓名列表
	 */
	public List<String> searchExamineeNames(String className);
	/**
	 * 根据考生的名字密码和班级获得一条考生记录
	 * @param name
	 * @param password
	 * @param className
	 * @return
	 */
	public Examinee getExaminee(String name, String password, String className);

	public List<Examinee> getAllExaminee(String className);

	/**获取学员原始密码*/
	public String getPwd(String name,int classid);

	/**获取教员原始密码*/
	public String getTeacherPwd(String name);

	//根据状态得到学生人数
	public int getStudentCount(String className,int studentStatus);

	public List searchExamineeByExamineeName(String name);
	
	/*
	 * 获取学生信息   通过id
	 */
	public List<Examinee> getExamineeDetail(int Examineeid);
	
	

}
