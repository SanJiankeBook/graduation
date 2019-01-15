package com.yc.biz;

import java.util.HashMap;
import java.util.List;

import com.yc.po.Chapter;
import com.yc.po.Subject;
import com.yc.vo.ChapterPage;

public interface ChapterBiz {
	/**
	 * 查询n条有效的课程
	 * @return
	 */
	public  List<Chapter> getEightChapter(Integer id,Integer classID ,Integer num);
	
	/*
	 * 得到所有的课程列表
	 */
	public List<Chapter> getChapterList(ChapterPage chapterPage,Integer pageStart, Integer pageSize );
	
	public int getAllChapterCount(ChapterPage chapterPage);

	/**
	 * 通过科目编号得到所有的章节信息
	 * 
	 * @param subjectId
	 *          int 科目编号
	 * @return ArrayList 返回对应的章节集合
	 * @throws Exception
	 */
	public List<String> searchChapter(int subjectId);
	/**
	 * 通过科目编号得到所有的章节信息
	 * 
	 * @param subjectId
	 *          int 科目编号
	 * @return ArrayList 返回对应的章节集合
	 * @throws Exception
	 */
	public List<Chapter> findChapter(int subjectId);
	/**
	 * 通过科目编号,章节名称,得到章节ID
	 * 
	 * @param subjectId
	 *          int 科目编号
	 * @param chapterName
	 *          String 章节名称
	 * @return int 章节ID
	 * @throws Exception
	 */
	public int getChapterId(int subjectId, String chapterName);

	/**
	 * 通过章节编号得到章节名称
	 * 
	 * @param chapterId
	 *          int
	 * @return String
	 * @throws Exception
	 */
	public String getChapterName(int chapterId) ;
	
	/**
	 * 根据编号查询Chapter
	 * @param chapterId
	 * @return
	 */
	public Chapter findChapterById(int chapterId);

	/**
	 * 通过章节ID，得到本章题库中有多少道题
	 */
	public int getQuestionAmount(int chapterId);

	/**
	 * 找指定课程有多少章节
	 */
	public int getChapterCount(int subjectId);

	public int updateChapter(Chapter chapter);

	public int deleteChapter(int chapterId);

	public int addChapter(Chapter chapter);

	/*
	 * 根据chapterId得到SubjectId
	 */
	public int getSubjectId(int id);
	
	/**
	 *根据版本号 查询所有的课程
	 * @param editionId ：版本号
	 * @return
	 */
	public List<Chapter> getAllChapter1(Integer classId);
}
