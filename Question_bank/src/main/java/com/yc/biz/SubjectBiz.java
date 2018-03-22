package com.yc.biz;

import java.util.List;

import com.yc.po.PointPaper;
import com.yc.po.Subject;
import com.yc.po.XSubject;
import com.yc.vo.SubjectPage;

public interface SubjectBiz {
	/**
	 * 根据课程编号，查询课程名
	 * @param pid：课程编号
	 * @return
	 */
	public String findSubjectName(int pid);
	

	/**
	 * 根据班级和学期查询已考的章节信息
	 * @param cid：考试班级
	 * @param sid：学期
	 * @return
	 */
	public List<Subject> findSubjectByEndAnswer(int cid,String sid);

	/**
	 * 通过指定的学期名称得到相应的科目
	 * 
	 * @param semester
	 *            String 学期名称
	 * @return ArrayList 返回
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchSubject(String semester);

	/*
	 * 更新科目名称
	 */
	public int updateSubject(Subject subject) ;

	/**
	 * 加入一条新的科目名称
	 * 
	 * @param subject
	 * @return
	 */
	public int addSubject(Subject subject);

	/**
	 * 通过指定的学期名称,版本号得到相应的科目
	 * 
	 * @param semester
	 *            String 学期名称
	 * @param editionId
	 *            int 版本id
	 * @return ArrayList 返回
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchSubject(String semester, int editionId);

	/**
	 * 周海军添加
	 * @param semester：学期
	 * @param editionId：版本
	 * @return
	 */
	public List<Subject> findSubject(String semester, int editionId);


	/**
	 * 通过科目名称得到科目ID
	 * 
	 * @param subject
	 *            String 科目名称
	 * @return subjectId int 返回科目编号
	 * @throws Exception
	 */
	public int getSubjectId(String subjectName);
	/**
	 * 通过科目名称得到科目的章节数
	 * 
	 * @param subject
	 *            String 科目名称
	 * @return chapterCount int 返回章节数
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getChapterCount(String subjectName);
	/*
	 * 通过版本ID得到这个版本下有多少门课
	 */
	public int getSubjectCount(int editionId);
	
	/**
	 * 查询总的科目数
	 * @return
	 */
	public int getAllSubjectCount();
	
	
	/*
	 * 得到所有的课程列表
	 */
	@SuppressWarnings("unchecked")
	public List<XSubject> getSubjects();

	/*
	 * 删除没有章节的科目，删除之前先查有没有章节
	 */
	public int deleteSubject(int subjectId);

	/*
	 * 得到所有的章节，按版本id排序
	 */
	public List<Subject> getAllSubject(SubjectPage subjectPage,Integer pageStart, Integer pageSize );

	/*
	 * 根据ID，更新科目的章节总数
	 */
	public int updateChapterCount(int count, int id);

	/**
	 * 根据ID获取科目实体对象
	 * 
	 * @param subjectId
	 * @return
	 */
	public Subject findSubjectById(int subjectId);
	
	
	/**
	 * 按课程统计查询已测评的知识点信息
	 * @param semester：测评班级
	 * @param cid：测评班级
	 * @return
	 */
	public List<Subject> getSubjectTotal(String semester,int cid);
	/**
	 * 根据Semester（学期）和班级名来查课程名
	 * @param semester 学期名
	 * @param className 班级名
	 * @return
	 * @author fanhaohao
	 */
	public List<String>  getSubjectNamesBySemesterAndClassName(String semester,String className);

	/**
	 * 根据subjectId（课程编号）和className（班级名）来查课程名
	 * @param subjectId课程编号
	 * @param className班级名
	 * @return
	 * @author fanhaohao
	 */
	public List<PointPaper> getPointPaperByIdAndClassName(Integer subjectId,
			String className);
	/**
	 * 根据学期来查询课程名称
	 * @param semester
	 */
	public List<String> findSubjectNameBySemester(String semester);

	/**
	 * 根据版本号获取课程
	 * @return
	 */

	public List<Subject> getSubjectsByedit(String id);
}
