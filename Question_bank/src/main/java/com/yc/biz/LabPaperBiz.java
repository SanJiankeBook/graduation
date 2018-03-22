package com.yc.biz;

import java.util.List;

import com.yc.po.LabAnswer;
import com.yc.po.LabPaper;
import com.yc.po.LabQuestion;
import com.yc.po.WritingPaper;
import com.yc.vo.Page;

public interface LabPaperBiz {

	/**
	 * 添加机试卷
	 * 
	 * @param lp
	 *          LabPaper 机试卷Bean实体对像
	 * @return int 返回成功添加的行数
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int addLabPaper(LabPaper lp);
	/**
	 * 根据编号更新机试卷
	 * 
	 * @param lp
	 *          LabPaper 机试卷Bean实体对像
	 * @return int 返回成功更新的行数
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int updateLabPaperOfId(LabPaper lp);

	/**
	 * 通过试卷编号改变试卷状态
	 * 
	 * @param paperId
	 *          String
	 * @return int
	 * @throws Exception
	 */
	public int updateLabPaper(String paperId, int paperStatus);

	/**
	 * 根据编号删除机试卷
	 * 
	 * @param lp
	 *          LabPaper 笔机试卷Bean实体对像
	 * @return int 返回成功删除的行数
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int deleteLabPaper(LabPaper lp) ;

	/**
	 * 根据班级查找机试卷,如果条件为空,查找所有的试卷
	 * 
	 * @param lp
	 *          LabPaper 机试卷Bean实体对像
	 * @return ArrayList 返回查询结果
	 * @throws Exception
	 *           抛出系统异常
	 */
	@SuppressWarnings("unchecked")
	public List searchLabPaper(LabPaper lp);

	/**
	 * 判断试卷编号和密码是否正确
	 * 
	 * @param lp
	 *          LabPaper 笔试卷Bean实体对像
	 * @return boolean 返回验证结果
	 * @throws Exception
	 *           抛出系统异常
	 */
	public boolean validateLabPaperPassword(LabPaper lp);

	/**
	 * 通过试卷编号,获得试卷的题目编号
	 * 
	 * @param paperId
	 *          String 参数传递试卷编号
	 * @return ArrayList 返回一个集合
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int getQuestionId(String paperId);

	/**
	 * 通过题编号得到题目内容,存入Bean中
	 * 
	 * @param questionID
	 *          int 参数传递题目编号
	 * @return LabQuestion 返回笔试卷Bean实体对像
	 * @throws Exception
	 *           抛出系统异常
	 */
	public LabQuestion getLabQuestionBean(String paperId);
	/**
	 * 通过试卷编号得到机试卷信息
	 * 
	 * @param paperId
	 *          String
	 * @return LabPaper
	 */
	public LabPaper getLabPaper(String paperId);
	/**
	 * 根据班级查找机试卷
	 * 
	 * @param lp
	 *          LabPaper 笔试卷Bean实体对像
	 * @return ArrayList 返回查询结果
	 * @throws Exception
	 *           抛出系统异常
	 */
	@SuppressWarnings("unchecked")
	public List searchLabPaper(LabPaper lp, int displayRows, int nextNum);

	/**
	 * 判断所在班级是否有此编号的考卷
	 * 
	 * @param paperId
	 *          String
	 * @param examClass
	 *          String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isExistsPaper(String paperId, String examClass);

	/**
	 * 判断是否存在指定编号和状态的试卷
	 * 
	 * @param paperId
	 *          String 试卷编号
	 * @param paperStatus
	 *          int 试卷状态
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isExistsPaper(String paperId, int paperStatus);

	/**
	 * 根据试卷的状态查找所有机试卷,分页查询
	 * 
	 * @param paperStatus
	 *          int 试卷状态
	 * @param displayRows
	 *          int 显示的行数
	 * @param nextNum
	 *          int 当前第几页
	 * @return ArrayList 查询到的集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchLabPaper(int paperStatus, int displayRows, int nextNum);

	/**
	 * 查询当前考生所在班级可以考试的试卷列表,paperPwd as 密码 没有密码了
	 * 
	 * @param paperStatus
	 *          int 试卷状态
	 * @param examClass
	 *          String 考试班级
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchLabPaper(int paperStatus, String examClass);

	/**
	 * 通过试卷编号得到笔试卷部分信息
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchLabPaperInfo(String paperId);

	/**
	 * 通过指定条件,得到机度卷列表查询到的最大行数
	 * 
	 * @param wq
	 *          LabQuestion
	 * @return int
	 * @throws Exception
	 */
	public int getSearchLabMaxRow(LabPaper lp);
	/**
	 * 评卷后,更新试卷评卷信息
	 * 
	 * @param lp
	 *          LabPaper
	 * @return int
	 * @throws Exception
	 */
	public int updateLabPaper(LabPaper lp);
	/**
	 * 通过试卷编号得到,每个分数阶段的考生数,从底到高存入集合中
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List getGradationexamineeCount(String paperId);

	/**
	 * 根据考生姓名查询考生机试成绩,如果没有指定试卷编号,查询所有考过的试卷
	 * 
	 * @param paperId
	 *          String
	 * @param examClass
	 *          String
	 * @param examineeName
	 *          String
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	public List searchExaminneLabAchievement(String paperId, String examClass,
			String examineeName);

	/**
	 * 通过指定条件,得到考生查询机试成绩的最大行数
	 * 
	 * @param wa
	 *          LabAnswer
	 * @param examineeName
	 *          String
	 * @return int
	 * @throws Exception
	 */
	public int getExamineeLabAchievementMaxRow(LabAnswer wa, String examineeName);

	/**
	 * 查询班级机试成绩,如果没有指定试卷编号,查询班级所有考过的试卷成绩
	 * 
	 * @param la
	 *          LabAnswer
	 * @param lp
	 *          LabPaper
	 * @param examineeClass
	 *          String
	 * @param displayRows
	 *          int
	 * @param nextNum
	 *          int
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchClassLabScore(String paperId, String examClass);

	/**
	 * 获得班级查询笔试成绩的最大行数
	 * 
	 * @param wp
	 *          LabPaper
	 * @param wa
	 *          LabAnswer
	 * @param examineeClass
	 *          String
	 * @return int
	 */
	public int getSearchClassLabScorsRowMax(LabPaper lp, String examineeClass);
	/**
	 * 根据id对笔试卷分页
	 * @param page分页bean用来存储分页信息，即对象的值
	 * @param lp笔试卷类
	 * @author fanhaohao
	 */
	public void searchLabPaperPageById(Page<LabPaper> page, LabPaper lp);
}
