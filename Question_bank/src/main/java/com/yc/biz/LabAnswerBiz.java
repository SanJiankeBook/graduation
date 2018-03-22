package com.yc.biz;

import java.util.List;

import com.yc.po.LabAnswer;

public interface LabAnswerBiz {
	/**
	 * 添加机试答卷
	 * 
	 * @param labAnswer
	 *          LabAnswer //机试答卷Bean实体对像
	 * @return int //返回成功添加的行数
	 * @throws Exception
	 *           //抛出系统异常
	 */
	public int addLabAnswer(LabAnswer labAnswer);

	/**
	 * 根据考卷ID、考生名得到工程名
	 * 
	 * @param paperId
	 *          String 考卷id
	 * @param examineeName
	 *          String 考生名
	 * @return projectName String 工程名
	 * @throws Exception
	 */
	public String searchProjectName(String paperId, String examineeName);

	/**
	 * 根据考卷ID、考生名得到代码
	 * 
	 * @param paperId
	 *          String 考卷id
	 * @param examineeName
	 *          String 考生名
	 * @return code String 工程名
	 * @throws Exception
	 */
	public String searchCode(String paperId, String examineeName);

	/**
	 * 根据考卷ID、考生名更新分数
	 * 
	 * @param labAnswer
	 *          LabAnswer 机试答卷Bean
	 * @return row int 更新行数
	 * @throws Exception
	 */
	public int exeUpdateAnswer(LabAnswer labAnswer);

	/**
	 * 根据考卷ID,得到最高分
	 * 
	 * @param paperId
	 *          String 机试试卷id
	 * @return maxScore int 机试答卷最高分
	 * @throws Exception
	 */
	public int searchMaxScore(String paperId);

	/**
	 * 根据考卷ID,得到最低分
	 * 
	 * @param paperId
	 *          String 机试试卷id
	 * @return minScore int 机试答卷最高分
	 * @throws Exception
	 */
	public int searchMinScore(String paperId) ;

	/**
	 * 根据考卷ID,得到平均分
	 * 
	 * @param paperId
	 *          String 机试试卷id
	 * @return avgScore int 机试答卷最高分
	 * @throws Exception
	 */
	public int searchAvgScore(String paperId) ;

	/**
	 * 判断考生是否已经考过此卷
	 * 
	 * @param paperId
	 *          String
	 * @param examinee
	 *          String
	 * @return boolean
	 */
	public boolean isExamOfExaminee(String paperId, String examineeName);

	/**
	 * 插入答卷信息
	 * 
	 * @param la
	 *          LabAnswer
	 * @return int
	 * @throws SQLException
	 */
	public int insertAnswer(LabAnswer la);

	/**
	 * 通过试卷编号得到参加过此次考试的所有考生
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List searchLabPaperExaminee(String paperId);

	/**
	 * 通过试卷编号和考生姓名,得到此考生的答卷信息
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 */
	public LabAnswer searchExamineeLabPaper(String paperId, String examineeName);

	/**
	 * 通过试卷ID得到各分数段的考生数
	 * 
	 * @param paperId
	 *          String
	 * @return String
	 */
	public String searchScorePercent(String paperId);

	/*
	 * 删除某考生的所有机试卷
	 */
	public int deleteLabAnswerByExamineeName(String examineeName);
	/**
	 * 找此考生参加的所有机试卷
	 * 
	 * @param examineeName
	 * @return
	 */
	public List<LabAnswer> getExamineeAllLabAnswer(String examineeName);

	/*
	 * 看此考生在此试卷中有没有项目文件，有返回名字，没有返回null
	 */
	public String getExistProjectName(String paperId, String examineeName);

	/**
	 * 根据考生姓名和考卷ID删除考卷
	 * 
	 * @param paperId
	 * @param examineeName
	 * @return
	 */
	public int deleteLabAnswer(String paperId, String examineeName);
}
