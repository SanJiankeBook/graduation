package com.yc.biz;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.po.WritingPaperTemplate;
import com.yc.po.WritingQuestion;
import com.yc.vo.ChapterQuestionCount;
import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.Page;

/**
 * Title: 笔试卷操作数据库类
 */
public interface WritingPaperBiz {
	/**
	 * 添加笔试卷
	 * @param wp WritingPaper 笔试卷Bean实体对像
	 * @return int 返回成功添加的行数
	 */
	public int addWritingPaper(WritingPaper wp) ;

	/*
	 * 更新试卷数量字段
	 */
	public int updateCountOfQuestion(String paperId, int count) ;

	/**
	 * 评卷后,更新试卷评卷信息
	 * 
	 * @param wp
	 *          WritingPaper
	 * @return int
	 * @throws Exception
	 */
	public int updateWritingPaper(WritingPaper wp) ;

	/**
	 * 根据编号更新笔试卷
	 * 
	 * @param wp
	 *          WritingPaper 笔试卷Bean实体对像
	 * @return int 返回成功更新的行数
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int updateWritingPaperById(WritingPaper wp);

	/**
	 * 修改试卷头部信息，为真表示清除原有的分数信息和修改班级等信息，为假只是一般性的修改
	 */
	public int updatePaperInfo(WritingPaper wp, boolean clearScore);

	/**
	 * 通过试卷编号改变试卷状态
	 */
	public int updateWritingPaper(String paperId, int paperStatus);
	


	/**
	 *  通过试卷编号改变试卷的正确答案 */
	public int updateWritingPaper(String paperId, String questionCorrect);

	/**
	 * 根据编号删除笔试卷
	 * 
	 * @param wp
	 *          WritingPaper 笔试卷Bean实体对像
	 * @return int 返回成功删除的行数
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int deleteWritingPaper(WritingPaper wp);
	/**
	 * 通过试卷编号,得到此卷信息
	 * 
	 * @param paperId
	 *          String
	 * @return WritingPaper
	 */
	public List<WritingPaper> searchWritingPaper(String paperId);
	/**
	 * 根据班级查找笔试卷,如果条件为空,查找所有的试卷
	 * 
	 * @param wp
	 *          WritingPaper 笔试卷Bean实体对像
	 * @return ArrayList 返回查询结果
	 * @throws Exception
	 *           抛出系统异常
	 */
	@SuppressWarnings("unchecked")
	public List<WritingPaper> searchWritingPaper(WritingPaper wp);
	/**
	 * 判断试卷编号和密码是否正确
	 * 
	 * @param wp
	 *          WritingPaper 笔试卷Bean实体对像
	 * @return boolean 返回验证结果
	 * @throws Exception
	 *           抛出系统异常
	 */
	public boolean validateWritingPaperPassword(WritingPaper wp);
	/**
	 * 通过题编号得到题目内容,存入Bean中
	 * 
	 * @param questionID
	 *          int 参数传递题目编号
	 * @return WritingQuestion 返回笔试卷Bean实体对像
	 * @throws Exception
	 *           抛出系统异常
	 */
//	public List<WritingQuestion> getWritingQuestionBean(int questionID);
	/**
	 * 根据多个id查试卷信息
	 * @param ids:题目编号的集合
	 * @return
	 */
	public List<WritingQuestion> findWritingQuestionByIds(List<Integer> ids);
	/**
	 * 通过试卷编号,获得试卷的所有题目编号,放在集合中
	 * 
	 * @param paperId
	 *          String 参数传递试卷编号
	 * @return ArrayList 返回一个集合
	 * @throws Exception
	 *           抛出系统异常
	 */
	public List<WritingQuestion> getWritingPaperQuestions(String paperId);
	/**
	 * 添加笔试答卷的初始字段值
	 * 
	 * @param wa
	 *          WritingAnswer 笔试答卷Bean实体对像
	 * @return int 返回成功添加的行数
	 * @throws Exception
	 *           抛出系统异常
	 */
	public int addInitAnswer(WritingAnswer wa);

	/**
	 * 通过试卷ID得到试卷对象
	 * 
	 * @param paperId
	 *          String
	 * @return WritingPaper
	 * @throws Exception
	 */
	public WritingPaper getWritingPaper(String paperId);

	/**
	 * 通过试卷编号得到笔试卷部分信息
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> searchWritingPaperInfo(String paperId);

	/**
	 * 根据班级查找所有笔试卷
	 * @param wp  WritingPaper 笔试卷Bean实体对像
	 * @return ArrayList 返回查询结果
	 */
	@SuppressWarnings("unchecked")
	public List searchWritingPaper(WritingPaper wp, int displayRows, int nextNum);

	/**
	 * 根据试卷的状态查找所有笔试卷,分页查询
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
	public DataGaidModel searchWritingPaper(int paperStatus,DataarrayActionModel dm) ;

	/**
	 * 查询当前考生所在班级可以考试的试卷列表，密码不显示出来，并且只显示大于等于今天的试卷，过期的不再显示出来
	 * 
	 * @param paperStatus int 试卷状态
	 * @param paperStatusSec 试卷状态2
	 * @param examClass 考试班级
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchWritingPaper(int paperStatus,int paperStatusSec, String examClass) ;

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
	public boolean isExistsPaperByClass(String paperId, String examClass);
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
	public boolean isExistsPaperByState(String paperId, int paperStatus);

	/**
	 * 通过指定条件,得到笔试卷查询到的最大行数
	 * 
	 * @param wq
	 *          WritingQuestion
	 * @return int
	 * @throws Exception
	 */
	public int getSearchWritingMaxRow(WritingPaper wp);

	/**
	 * 通过试卷编号得到,每个分数阶段的考生数,从底到高存入集合中
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 */
	public List<String> getGradationExamineeCount(String paperId);

	/**
	 * 通过试卷编号统计出考生章节答对率
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List getcChapterCensus(String paperId);

	/**
	 * 如果是单科卷,通过试卷编号得到试卷中每一章节出题的数 如果是综合卷,通过试卷编号得到试卷中每一科目出题的数
	 * 
	 * @param paperId
	 *          String
	 * @return ArrayList
	 */
	public List<List<ChapterQuestionCount>> getChaptersCount(String paperId);

	/**
	 * 统计每一科下的每一章节的 所有考生的平均答对率
	 * 
	 * @param paperId  String 试卷编号
	 * @return ArrayList
	 */
	public List<String> getCorrectRateOfChapterOrSubject(String paperId);

	/**
	 * 统计每一科下的每一章节的考生答对率
	 * 
	 * @param paperId String 试卷编号
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List getExamineeCorrectRateOfChapterOrSubject(String paperId, String examineeName);

	/**
	 * 通过试卷编号,考生答案,从指定第几题开始,后多少题中和正确答案比较,累计考生所答对数量
	 * 
	 * @param paperId String 试卷编号
	 * @param examineeAnswer  String 考生所答答案用";"组成的字符串
	 * @param index int 从第几题开始比较的索引
	 * @param count int 共比较多少题 就是每一章答对多少题
	 * @param rightAnswer 正确答案，不用每次都查数据库，直接传进来就可以了
	 * @return int 考生所答对数量
	 */
	public int getExamineeCorrect(String paperId,String rightAnswer, String examineeAnswer, int index, int count) ;
		

	/**
	 * 考生端根据考生姓名查询考生笔试成绩,如果没有指定试卷编号,查询所有考过的试卷
	 * 
	 * @param paperID
	 *          String
	 * @param examClass
	 *          String
	 * @param examineeName
	 *          String
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchExaminneWritAchievement(String paperID, String examClass,
			String examineeName, int paperStatus) ;

	/**
	 * 通过指定条件,得到考生笔试成绩的最大行数
	 * 
	 * @param wa
	 *          WritingAnswer
	 * @param examineeName
	 *          String
	 * @return int
	 * @throws Exception
	 */
	public int getExamineeWritAchievementMaxRow(WritingAnswer wa, String examineeName);

	/**
	 * 查询班级笔试成绩,如果没有指定试卷编号,查询班级所有考过的试卷成绩
	 * 
	 * @param wa
	 *          WritingAnswer
	 * @param wp
	 *          WritingPaper
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
	public List searchClassWritingScors(String paperId, String examClass);

	/**
	 * 获得班级查询笔试成绩的最大行数
	 * 
	 * @param wp
	 *          WritingPaper
	 * @param wa
	 *          WritingAnswer
	 * @param examineeClass
	 *          String
	 * @return int
	 */
	public int getSearchClassWritingScorsRowMax(WritingPaper wp, String examineeClass);

	/*
	 * 重考：1、更新试卷 2、删除考生成绩
	 */
	public int reExamWritingPaper(String paperId) throws Exception;
	
	/**
	 * 根据班级,出题科目查找串连的题目ID字符串
	 * @param examClass 班级名称
	 * @param examSubject 出题科目名称
	 * @return 返回包含多个ID字符串的ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List getQuestionIds(String examClass,String examSubject);
	
	/**
	 * 根据id模糊查询（带分页）
	 * @param page用来存储分页信息
	 * @param wp 要查询的类
	 * @author fanhaohao
	 */
	public void searchWritingPaperPage(Page<WritingPaper> page,WritingPaper wp);
	
	/**
	 * 根据试卷ID查考试班级
	 * @param paperId   试卷ID
	 * @return
	 */
	public String getExamClass(String paperId);
	/**
	 * 根据id模糊查询（带分页）
	 * @param page用来存储分页信息
	 * @param wp 要查询的类
	 @author fanhaohao
	 */
	public void searchWritingPaperPageById(Page<WritingPaper> page, WritingPaper wp);

	/**
	 * 根据id来修改试卷的状态
	 * @param wp
	 * @author fanhaohao
	 */

	public int updateWritingStatusPaperById(WritingPaper wp);
	/**
	 * 更具编号查出试卷
	 * @param id
	 * @return
	 */
	public WritingPaper findWritingPaperById(String id);
	/**
	 * 根据id获取模板试卷的内容
	 * @param id
	 * @return
	 */
	public WritingPaperTemplate findWritingPaperByIdTemlate(String id);
	
	public void updatePaper(WritingPaper wp);

	public int updatePaperStatusById(WritingPaper wp);
	/**
	 * 根据平均分，最高分，最低分来更新
	 * @param wp
	 */
	public int  updateWpByScore(WritingPaper wp);
	
	//查询当天的考试试卷
	public List<WritingPaper>  searchTodayPaper();
	
	/**
	 * 添加模板卷
	 * @param wp
	 * @return
	 */
	int addWritingPaperTemplate(WritingPaperTemplate wp);

	/**
	 * 查询模板卷
	 * @param page
	 * @param wp
	 */
	public void searchWritingPaperPage(Page<WritingPaperTemplate> page, WritingPaperTemplate wp);
	/**
	 * 查询模板卷 根据id
	 * 
	 */
	public List<WritingPaperTemplate> searchWritingPaperTemplate(String wpid);


}
