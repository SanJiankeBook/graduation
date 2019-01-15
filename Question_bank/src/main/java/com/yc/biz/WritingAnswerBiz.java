package com.yc.biz;

import java.util.List;

import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.vo.Page;

public interface WritingAnswerBiz {
	/**
	 * 添加笔试答卷
	 * 
	 * @param writingAnswer
	 *            WritingAnswer //笔试答卷Bean实体对像
	 * @return int //返回成功添加的行数
	 */
	public int addWritingAnswer(WritingAnswer writingAnswer);

	/**
	 * 根据考卷ID、考生名得到答案
	 * 
	 * @param paperId
	 *            String 考卷id
	 * @param examineeName
	 *            String 考生名
	 * @return answer String 答案
	 * @throws Exception
	 */
	public String searchAnswer(String paperId, String examineeName);
	/**
	 * 通过试卷编号得到,此卷的所有考生答案
	 * 
	 * @param paperId
	 *            String
	 * @return ArrayList
	 * @throws Exception
	 */
	public List<String> searchAnswers(String paperId);

	/**
	 * 更改考生答题时的答案
	 * 
	 * @param answer
	 *            String 更新的答案
	 * @param paperId
	 *            String 试卷编号
	 * @param examineeName
	 *            String 考生姓名
	 * @return int
	 * @throws Exception
	 */
	public int updateAnswer(String answer, String paperId, String examineeName);

	/**
	 * 
	 */
	/*
	 * 删除某考生的所有笔试卷
	 */
	public int deleteWritingAnswerByExamineeName(String examineeName);

	/**
	 * 删除考生答卷，根据考卷ID、考生名
	 * 
	 * @param writingAnswer
	 * @return
	 */
	public int deleteWritingAnswer(WritingAnswer writingAnswer) ;

	/**
	 * 根据考卷ID、考生名更新分数
	 * 
	 * @param writingAnswer
	 *            WritingAnswer 笔试答卷Bean
	 * @return row int 更新行数
	 * @throws Exception
	 */
	public int exeUpdateGrade(Float score,int id,String paperId,String userName);

	/**
	 * 根据考卷ID,得到最高分
	 * 
	 * @param paperId
	 *            String 机试试卷id
	 * @return maxScore int 机试答卷最高分
	 * @throws Exception
	 */
	public float searchMaxScore(String paperId);

	/**
	 * 根据考卷ID,得到最低分
	 * 
	 * @param paperId
	 *            String 机试试卷id
	 * @return minScore int 机试答卷最高分
	 * @throws Exception
	 */
	public float searchMinScore(String paperId);
	/**
	 * 根据考卷ID,得到平均分
	 * 
	 * @param paperId
	 *            String 机试试卷id
	 * @return avgScore int 机试答卷最高分
	 * @throws Exception
	 */
	public float searchAvgScore(String paperId);
	/**
	 * 根据考卷ID,得到此卷的考生数
	 * 
	 * @param paperId
	 *            String 机试试卷id
	 * @return minScore int 参加考生的人数
	 * @throws Exception
	 */
	public int searchExamineeCount(String paperId) ;

	/**
	 * 通过试卷ID得到各分数段的考生数
	 * 
	 * @param paperId
	 *            String
	 * @return String
	 */
	public String searchScorePercent(String paperId);
	/**
	 * 通过试卷编号,考生姓名和正确答案,计算出此考生的成绩
	 * 
	 * @param paperId
	 *            String 试卷编号
	 * @param examineeName
	 *            String 考生姓名
	 * @param rightAnswer
	 *            String 正确答案
	 * @return int
	 */
	public int computeScore(String paperId, String examineeName,
			String rightAnswer, int countQuestion);

	/**
	 * 判断考生是否已经进入了考试，有可能是异常、死机或点关闭按钮退出的
	 * 
	 * @param paperId
	 *            String
	 * @param examinee
	 *            String
	 * @return boolean
	 */
	public boolean isWritingAnswerExist(String paperId, String examineeName);

	/**
	 * 学生正常的按了提交按钮，而不是退出的
	 * 
	 * @param paperId
	 * @param examineeName
	 * @return
	 */
	public boolean isHandInPaper(String paperId, String examineeName);

	/**
	 * 通过试卷编号得到此卷的所有考生的答案信息
	 * 
	 * @param paperId
	 *            String
	 * @return ArrayList
	 * @throws Exception
	 */
	public List<WritingAnswer> searchWritingAnswer(String paperId);

	/**
	 * 找指定试卷中分数为null的学号，即没有点交卷的学员
	 */
	public List<String> getScoreIsNullExamineeName(String paperId);

	/**
	 * 根据试卷ID和考生姓名找到此考生的答卷
	 */
	public WritingAnswer searchWritingAnswer(String paperId, String examineeName);

	/**
	 * 根据考生姓名和试卷ID，更新剩余时间字段
	 */
	public int updateSpareTime(String paperId, String examineeName,
			String spareTime);
	/**
	 * 根据WritingPaper WritingAnswer的条件进行多表多条件查询
	 * @param page
	 * @param wp
	 * @param wa
	 */
	public void searchWritingPaperPageByName(Page<WritingAnswer> page,
			WritingPaper wp,WritingAnswer wa);
	/**
	 * 根据WritingPaper和WritingAnswer两者的id进行查询
	 * @param wp试卷
	 * @param wa答卷
	 * @return
	 */
	public WritingAnswer searchPaperById(WritingPaper wp, WritingAnswer wa);
	/**
	 * 根据试卷编号查找所有的信息
	 * @param wpid
	 * @return
	 * @author fanhaohao
	 */
	public List<WritingAnswer> searchExamineeInfoByPid(String wpid);
	/**
	 * 通过试卷编号,考生姓名和正确答案,计算出此考生的成绩
	 * 
	 * @param paperId
	 *            String 试卷编号
	 * @param examineeName
	 *            String 考生姓名
	 * @param rightAnswer
	 *            String 正确答案
	 * @return int
	 */
	public int searchComputeScore(String paperId, String examineeName,
			String rightAnswer, int countQuestion);
	/**
	 * 根据考卷ID、考生名更新分数
	 * 
	 * @param writingAnswer
	 *            WritingAnswer 笔试答卷Bean
	 * @return row int 更新行数
	 * @throws Exception
	 */
	public int updateGrade(String eName, String wpid, float score);
	
	/**
	 * hwh  --通过redis获取答案
	 * @param paperId
	 * @param examineeName
	 * @return
	 */
	public String getAnswer(String paperId, String examineeName);
}
