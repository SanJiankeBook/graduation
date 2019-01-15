package com.yc.biz;

import java.util.List;

import com.yc.po.LabQuestion;

public interface LabQuestionBiz {
	/**
	 * 添加机试题库
	 * 
	 * @param lq
	 *          LabQuestion //机试题Bean实体对像
	 * @return int //返回成功添加的行数
	 * @throws Exception
	 *           //抛出系统异常
	 */
	public int addLabQuestion(LabQuestion lq);
	/*
	 * 得到下一个identity
	 */
	public int getNextIdentity();

	/**
	 * 根据编号更新机试题
	 * 
	 * @param lq
	 *          LabQuestion //机试题Bean实体对像
	 * @return int //返回成功更新的行数
	 * @throws Exception
	 *           //抛出系统异常
	 */
	public int updateLabQuestion(LabQuestion lq);

	/**
	 * 根据编号删除机试题
	 * 
	 * @param lq
	 *          LabQuestion //机试题Bean实体对像
	 * @return int //返回成功删除的行数
	 * @throws Exception
	 *           //抛出系统异常
	 */
	public int deleteLabQuestion(LabQuestion lq);

	/**
	 * 查询机试题, 如果条件不为空或只指定部分条件, 用指定条件查询题目,否则,查询所有题目, 返回一个题目集合,第一条记录代表一个对象.
	 * 
	 * @param lq
	 *          WritingQuestion //机试题Bean实体对像
	 * @return ArrayList //返回查询结果
	 * @throws Exception
	 *           //抛出系统异常
	 */
	@SuppressWarnings("unchecked")
	public List searchLabQuestion(LabQuestion lq);

	/**
	 * 通过技术组合类型编号,题目编号,判断是否存在此题
	 * 
	 * @param skillCodeId
	 *          int 技术组合
	 * @param questionId
	 *          int 题目编号
	 * @return boolean
	 */
	public boolean isExistQuestionId(int skillCodeId, int questionId);

	/**
	 * 查询笔试题, 如果条件不为空或只指定部分条件, 用指定条件查询题目,否则,查询所有题目, 返回一个题目集合,第一条记录代表一个对象.
	 * 
	 * @param lq
	 *          WritingQuestion //笔试题Bean实体对像
	 * @param displayRows
	 *          int //一页显示的行数
	 * @param nextNum
	 *          int //当前第几页
	 * @return ArrayList //返回查询结果
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchLabQuestion(LabQuestion lq, int displayRows, int nextNum);

	/**
	 * 通过指定条件,得到查询到的最大行数
	 * 
	 * @param lq
	 *          WritingQuestion
	 * @return int
	 * @throws Exception
	 */
	public int getSearchMaxRow(LabQuestion lq);

	/**
	 * 通过题目ID得此题的详细内容
	 * 
	 * @param questionId
	 *          String 题目ID
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List searchQuestion(int questionId);
	/*
	 * 通过ID得到机试题
	 */
	public LabQuestion getLabQuestion(int id);

}
