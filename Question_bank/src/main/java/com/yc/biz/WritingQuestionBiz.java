package com.yc.biz;

import java.util.List;

import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;
import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.Page;
import com.yc.vo.WritingQuestionInfo;

public interface WritingQuestionBiz {
	/**
	 * 添加笔试题
	 * 
	 * @param wq
	 *            WritingQuestion //笔试题Bean实体对像
	 * @return int //返回成功添加的行数
	 * @throws Exception
	 *             //抛出系统异常
	 */
	public int addWritingQuestion(WritingQuestion wq);

	/**
	 * 根据科目的名称和题目的部分内容查询是否有本题存在
	 */
	public List<WritingQuestion> searchQuesExist(String question);

	/**
	 * 根据编号更新笔试题，用PreparedStatement，少了很多麻烦
	 */
	public int updateQuestion(WritingQuestion wq);

	/**
	 * 更新笔试题，从试卷过来的
	 */
	public int updateWritingQuestionFromPaper(WritingQuestionBiz wq);

	/**
	 * 根据编号删除笔试题
	 * 
	 * @param wq
	 *            WritingQuestion //笔试题Bean实体对像
	 * @return int //返回成功删除的行数
	 * @throws Exception
	 *             //抛出系统异常
	 */
	public int deleteWritingQuestion(WritingQuestion wq);

	public int deleteManyWritingQuestion(String quesiontIDs);

	/**
	 * 查询笔试题, 如果条件不为空或只指定部分条件, 用指定条件查询题目,否则,查询所有题目, 返回一个题目集合,第一条记录代表一个对象.
	 * 
	 * @param wq
	 *            WritingQuestion //笔试题Bean实体对像
	 * @param displayRows
	 *            int //一页显示的行数
	 * @param nextNum
	 *            int //当前第几页
	 * @return ArrayList //返回查询结果
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> searchWritingQuestion(WritingQuestionBiz wq,
			int displayRows, int nextNum, int sortBy);

	/**
	 * 通过指定条件,得到查询到的最大行数
	 * 
	 * @param wq
	 *            WritingQuestion
	 * @return int
	 * @throws Exception
	 */
	public int getSearchMaxRow(WritingQuestionBiz wq);

	public int getQuestionCount(int chapterId);

	/**
	 * 通过题目编号得到题目答案
	 * 
	 * @param id
	 *            int
	 */
	public String getAnswerOfId(int id);

	/**
	 * 通过题目编号,找到题目所在的章节编号
	 * 
	 * @param id
	 *            int
	 * @return int
	 * @throws Exception
	 */
	public int getChapterId(int id);

	/**
	 * 通过条件得到每一科目随机产生的题目编号
	 * 
	 * @param sQuestionCount
	 *            String 每一科目出题信息连接字符串
	 * @param semester
	 *            String 学期
	 * @param questionCount
	 *            int 出题数量
	 * @param rate1
	 *            String 难度比
	 * @param rate2
	 *            String 中度比
	 * @param rate3
	 *            String 易度比
	 * @return String 产生的题目编号字符串
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public String getQuestionIdsOf(String sQuestionCount, String semester,
			int questionCount, String rate1, String rate2, String rate3,
			List alQuestions);*/
	
	/**
	 * 通过条件得到每一科目随机产生的题目编号
	 */
	@SuppressWarnings("unchecked")
	public String getQuestionIdsOf(String sQuestionCount, String semester,
			int questionCount, String rate1, String rate2, String rate3);

	/**
	 * 根据生成试卷的条件,随机产生指定章节的一组题
	 * 
	 * @param wq
	 *            WritingQuestion
	 * @return ArrayList
	 */
	/*@SuppressWarnings("unchecked")
	public String searchQuestionOfRandom(String semester, int subjectId,
			int chapterId, int chapterCount, String rate1, String rate2,
			String rate3, List alQuestions);*/
	
	/**
	 * 根据生成试卷的条件,随机产生指定章节的一组题
	 */
	public String searchQuestionOfRandom(String semester, int subjectId,
			int chapterId, int chapterCount, String rate1, String rate2,
			String rate3);

	/**
	 * 通过条件查询每一种类型题目的编号
	 * 
	 * @param semester
	 *            String 学期
	 * @param subjectId
	 *            int 科目ID
	 * @param difficulty
	 *            int 难易度:1,2,3
	 * @param count
	 *            int 多少题
	 * @param maxCount
	 *            int 随机数所在的最大题目编号
	 * @return String 串连后的题目ID字符串
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchQuestionIDsOf(String semester, int subjectId,
			int chapterId, int difficulty);

	/**
	 * 通过题目编号的字符串,得到题目答案组成的字符串
	 * 
	 * @param strQuestionIds
	 *            String 题目编号的字符串
	 * @return String 题目答案组成的字符串
	 * @throws Exception
	 */
	public String getAnswers(String strQuestionIds);

	/**
	 * 删除当前试题，显示下一道题目
	 */
	@SuppressWarnings("unchecked")
	public List<Object> delQuestionToNext(int questionId);

	/**
	 * 通过题目ID得此题的详细内容
	 * 
	 * @param questionId
	 *            String 题目ID
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public List<Object> searchQuestion(int questionId);

	/**
	 * 调用存储过程，找当前ID的下一个ID的记录，如果是最后一条，就返回最后一条
	 * 如果第二个参数是next则下一条，如果等于prev就是上一条，第一条的上一条返回第一条 如果数据库中没有记录，则返回空
	 */
	@SuppressWarnings("unchecked")
	public List<Object> searchWritingQuestionByForward(int id, String forward);

	/**
	 * 找笔试题中最大的ID号
	 */
	public int maxWQuestionId();

	/**
	 * 通过ID得到试题对象，没有返回空
	 */
	public WritingQuestion searchWQuestion(int id);

	/**
	 * 查询指定课程是否还存在题目
	 */
	public int getSubjectCount(int subjectId);

	/**
	 * 得到下一个identity
	 */
	public int getNextIdentity();

	/**
	 * 根据一组id查询
	 * 
	 * @param ids
	 * @return 返回一组WritingQuestion集合
	 * @fanhaohao
	 */
	public List<WritingQuestion> findWritingQuestionByIds(List<Integer> ids);

	/**
	 * 更新WritingQuestion，注意先查后更新
	 * 
	 * @param wq
	 * @return
	 * @fanhaohao
	 */
	public int updateWritingQuestion(WritingQuestion wq);

	/**
	 * 查询所有试题（带分页）
	 * 
	 * @param page
	 */
	public void searchAllWritingQuestionPage(Page<WritingQuestion> page);

	// easyui 分页
	public DataGaidModel searchAllWritingQuestionPage(DataarrayActionModel da);

	/**
	 * 查询下一条数据
	 * 
	 * @param id
	 * @return
	 */
	public int getNextId(int id);

	/**
	 * 查询上一条数据
	 * 
	 * @param id
	 * @return
	 */
	public int getPrevId(int id);

	/**
	 * 根据章节统计题目数
	 * 
	 * @param chapterId
	 * @return
	 */
	public Integer getCount(int chapterId);

	/**
	 * 获得相应学期、科目以及章节的题目难度数量
	 * @param semester
	 * @param subjectId
	 * @param chapterId
	 * @param difficulty
	 * @return
	 */
	public int getDifficultyRate(String semester, int subjectId, int chapterId,
			int difficulty);

	/**
	 * 查找最近插入的数据
	 * @return
	 */
	public WritingQuestion sreachLastRecord();
}
