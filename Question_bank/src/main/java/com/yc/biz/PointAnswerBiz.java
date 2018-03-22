package com.yc.biz;

import java.util.List;

import com.yc.po.PointAnswer;

public interface PointAnswerBiz {

	/**
	 * 根据学生姓名和班级查询未考的试卷编号
	 * @param name：学生姓名
	 * @param classid：所在班级
	 * @param pid：判断是否已考的试题编号
	 * @return：已考返回0，否则返回未来试题编号
	 */
	public String findPointAnswer(String name,String classid,String pid);


	/**
	 * 添加答卷信息
	 * @param pa
	 * @return
	 */
	public int addPointAnswer(PointAnswer pa);

	/**
	 * 根据试卷编号、学生姓名和班级查询答案信息
	 * @param pid：考卷编号
	 * @param name：学生姓名
	 * @param cid：学生所在班级
	 * @return
	 */
	public PointAnswer findPaperByPidAndSname(String pid,String name,int classid);

	/**
	 * 根据试卷编号、学生姓名和班级删除答卷
	 * @param pid：试卷编号
	 * @param uname：学生姓名
	 * @param cid：班级编号
	 * @return
	 */
	public int delPaperAnswer(String pid,String uname,String cid);

	/**
	 * 根据试题编号和测评班级，查询已测评学生的姓名
	 * @param pid：测评试卷
	 * @param cid：测评班级
	 * @return
	 */
	public List<String> getPointAnswerByPid(int pid,int cid);


	/**
	 * 按学期和测试班级，查询测试结果
	 * @param semester：学期
	 * @param cid：测评班级
	 * @return
	 */
	public List<PointAnswer> findSubjectTotal(String semester,int cid);


	/**
	 * 根据课程编号或班级、学生姓名查看留言
	 * @param subjectid：班级编号
	 * @param classid：班级编号
	 * @param uname：学生姓名
	 * @return
	 */
	public List<PointAnswer> findMessageInfo(int subjectid,int classid,String uname);
	
	/**
	 * 根据试卷编号，查询案卷
	 * @param pid
	 * @return
	 * @author fanhaoaho
	 */
	public List<PointAnswer> findAnswerByPid(int pid);
	/**
	 * 根据试卷编号，查询案卷
	 * @param pid
	 * @return
	 * @author fanhaoaho
	 */
	public List<PointAnswer> findAnswersByPid(int pid);
	/**
	 * 根据试卷编号统计参加测评的人数
	 * @param pid
	 * @return
	 */
	public int getStudentCount(int pid);

	/**
	 * 删除测评信息
	 * @param p
	 * @return
	 * @author fanhaoaho
	 */
	public int delPaperAnswer(PointAnswer p);

	/**
	 * 根据学生名称，课程id,班级班级名称来显示显示测评留言信息
	 * fanhaohao
	 */
	public List<PointAnswer> findPointAnswerInfo(Integer subjectid,
			Integer classid, String stuName);

}
