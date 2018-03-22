package com.yc.webexam.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.po.ExamineeClass;
import com.yc.po.WritingPaper;
import com.yc.utils.ExamUtil;
import com.yc.utils.JsonUtil;
import com.yc.vo.ChapterQuestionCount;

public class WritingPaperAction extends BaseAction {
	private static final long serialVersionUID = 105539930431105149L;
	private static final Logger logger = Logger.getLogger(WritingPaperAction.class);
	private String semester;
	private String wpid;
	private int paperStatus;
	private String examineeName;

	private String examName;

	private String questionId;

	@Resource(name = "aDailyTalkBiz")
	private ADailyTalkBiz aDailyTalkBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "writingPaperBiz")
	private WritingPaperBiz writingPaperBiz;
	@Resource(name = "writingAnswerBiz")
	private WritingAnswerBiz writingAnswerBiz;
	private WritingPaper writingPaper;

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public ADailyTalkBiz getaDailyTalkBiz() {
		return aDailyTalkBiz;
	}

	public void setaDailyTalkBiz(ADailyTalkBiz aDailyTalkBiz) {
		this.aDailyTalkBiz = aDailyTalkBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	/**
	 * 查找班级的名字
	 * 
	 * @author fanhaoaho
	 */
	public void getExamineeClassName() {
		String jsonStr = null;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassBySemester(semester);
		if (examineeClassList != null && examineeClassList.size() > 0)
		{
			for (int i = 0,len=examineeClassList.size(); i < len; i++)
			{
				sb.append("{");
				sb.append("\"className\":" + "\"" + examineeClassList.get(i).getClassName() + "\"");
				sb.append(","+"\"Id\":" + "\"" + examineeClassList.get(i).getId() + "\"");
				sb.append("}");
				if (examineeClassList.size() == i + 1) {
					sb.append("");
				} else {
					sb.append(",");
				}

			}
		}
		sb.append("]");
		String examineeClassListStr = JSON.toJSONString(sb.toString());
		logger.info("examineeClassListStr:" + examineeClassListStr);
		try {
			JsonUtil.jsonOut(examineeClassListStr);
		} catch (IOException e) {
			logger.error(e);
		}

	}

	public void getId() {
		int classId = aDailyTalkBiz.getCid(examName);
		try {
			JsonUtil.jsonOut(classId + "");
		} catch (IOException e) {
			logger.error(e);
		}

	}

	/**
	 * 查找班级的名字和班级id
	 * 
	 * @author fanhaoaho
	 */
	public void getExamineeClassNameAndClassId() {
		String jsonStr = "";
		try {
			List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassAndClassIdBySemester(semester);
			for (int i = 0, len = examineeClassList.size(); i < len; i++) {
				examineeClassList.get(i).setaDailyTalks(null);
				examineeClassList.get(i).setCheckings(null);
				examineeClassList.get(i).setCreateDate(null);
				examineeClassList.get(i).setNotice(null);
				examineeClassList.get(i).setOverDate(null);
				examineeClassList.get(i).setPointPapers(null);
				examineeClassList.get(i).setRemark(null);
				examineeClassList.get(i).setSemester(null);
				examineeClassList.get(i).setStudentcount(null);
			}
			jsonStr = super.writeJson(0, examineeClassList);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "修改失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	/**
	 * 根据WritingPaperid更新questionid
	 * 
	 * @author fanhaohao
	 */
	public void updateWritingPaperQuestionId() {
		String jsonStr = "";
		try {
			writingPaperBiz.updateWritingPaperById(writingPaper);
			jsonStr = super.writeJson(0, writingPaper.getId());
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "修改失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据根据WritingPaperid添加questionid
	 */
	public void addWritingPaperQuestionId() {
		String jsonStr = "";
		try {

			List<WritingPaper> lists = writingPaperBiz.searchWritingPaper(wpid);
			String questionIds = "";
			if (null != lists && lists.size() > 0) {
				questionIds = lists.get(0).getQuestionId();
			}
			questionIds += (questionId + ",");
			WritingPaper w = new WritingPaper();
			w.setQuestionId(questionIds);
			w.setId(wpid);
			writingPaperBiz.updateWritingPaperById(w);
			jsonStr = super.writeJson(0, w.getId());
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "修改失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据id来删除试卷
	 * 
	 * @return
	 * @author fanhaohao
	 */
	public void delWritingPaperById() {
		String jsonStr = "";
		try {
			WritingPaper wp = new WritingPaper();
			wp.setId(wpid);
			writingPaperBiz.deleteWritingPaper(wp);
			jsonStr = super.writeJson(0, "删除成功！");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "删除失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据id更新试卷装突然为重考
	 * 
	 * @author fanhaohao
	 */
	public void updatePaperStatuById() {
		String jsonStr = "";
		int flag = 1;
		try {
			WritingPaper wp = new WritingPaper();
			wp.setId(wpid);
			wp.setPaperStatus(paperStatus);
			flag = writingPaperBiz.updatePaperStatusById(wp);
			if (flag == 0) {
				jsonStr = super.writeJson(flag, "修改成功！");
			} else {
				jsonStr = super.writeJson(1, "修改失败！");
			}

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "修改失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 如果是单科卷,通过试卷编号得到试卷中每一章节出题的数 如果是综合卷,通过试卷编号得到试卷中每一科目出题的数
	 * 
	 * @param paperId
	 *            String
	 * @return ArrayList
	 * @author fanhaohao
	 */
	private ArrayList<ArrayList<ChapterQuestionCount>> getChaptersCount(String paperId) {
		WritingPaper wp = null;
		ArrayList<ArrayList<ChapterQuestionCount>> list = new ArrayList<ArrayList<ChapterQuestionCount>>();
		// 得到当前试卷信息
		List<WritingPaper> listwps = writingPaperBiz.searchWritingPaper(wpid);
		if (listwps != null && listwps.size() > 0) {
			wp = listwps.get(0);
		}
		// 用 MMM 拆分出每一科
		if (wp.getQuestionInfo() == null) {
			return null;
		}
		String strQuestion[] = wp.getQuestionInfo().split("MMM");
		// 如果只有一个,说明是单科卷,统计章节答对率,否则综合卷,统计科目答对率
		for (int j = 0,len=strQuestion.length; j <len ; j++) {
			ArrayList<ChapterQuestionCount> arr = new ArrayList<ChapterQuestionCount>();
			// 后面用分号分开的是每一章，第0个元素是课程名
			System.out.println(strQuestion[j]);
			String str[] = strQuestion[j].split(":")[1].split(";");
			for (int i = 0,len1=str.length; i <len1 ; i++) {
				String aa[] = str[i].split(",");
				// 一个课程名，章节名和问题数的类
				ChapterQuestionCount cqCount = new ChapterQuestionCount();
				cqCount.setSubjectName(strQuestion[j].split(":")[0]);
				cqCount.setChapterName(aa[0]);
				cqCount.setQuestionCount(Integer.parseInt(aa[1]));
				arr.add(cqCount);
			}
			list.add(arr);
		}
		return list;
	}

	/**
	 * 通过试卷编号,考生答案,从指定第几题开始,后多少题中和正确答案比较,累计考生所答对数量
	 * 
	 * @param paperId
	 *            String 试卷编号
	 * @param examineeAnswer
	 *            String 考生所答答案用";"组成的字符串
	 * @param index
	 *            int 从第几题开始比较的索引
	 * @param count
	 *            int 共比较多少题 就是每一章答对多少题
	 * @param rightAnswer
	 *            正确答案，不用每次都查数据库，直接传进来就可以了
	 * @return int 考生所答对数量
	 * @author fanhaohao
	 */
	private int getExamineeCorrect(String paperId, String rightAnswer, String examineeAnswer, int index, int count) {
		int correctCount = 0; // 累计指定阶段中考生答对数
		// 通过试卷编号,得到此卷答案,把答案字符串分隔拆分在数组中
		String rightAns[] = rightAnswer.split(";");
		// 考生答案没有
		if (examineeAnswer == null || "".equals(examineeAnswer)) {
			return 0;
		}
		// 没做的用空格填充
		examineeAnswer = ExamUtil.fillSpaceAnswer(examineeAnswer, rightAns.length);
		// 把考生答案字符串分隔拆分在数组中
		String examineeAns[] = examineeAnswer.split(";");
		// 定义一个哈希table,键存放试卷中的题目编号,值存放考生所答答案
		HashMap<String,String> numbers = new HashMap<String,String>();
		for (int i = 0,len=examineeAns.length; i <len ; i++) {
			numbers.put(examineeAns[i].split(",")[0], examineeAns[i].split(",")[1]);
		}
		// 定义一个哈希table,键存放试卷中的题目编号,值存放正确答案
		HashMap<String, String> rNumbers = new HashMap<String, String>();
		for (int i = index; i < count; i++) {
			rNumbers.put(rightAns[i].split(",")[0], rightAns[i].split(",")[1]);
		}
		for (int i = index; i < count; i++) {
			// 正确答案和所答的答案相同时,考生答对数 correctCount+1
			String num = numbers.get((i + 1) + "").toString();
			String rNum = rNumbers.get((i + 1) + "").toString();
			if (num.equalsIgnoreCase(rNum)) {
				correctCount++;
			}
		}
		return correctCount;
	}

	/**
	 * 统计每一科下的每一章节的 所有考生的平均答对率
	 * 
	 * @param paperId
	 *            String 试卷编号
	 * @return ArrayList
	 * @author fanhaohao
	 */
	public void getCorrectRate() {
		String jsonStr = "";
		try {
			WritingPaper wp = null;
			List<String> arr = new ArrayList<String>();
			// 得到当前试卷信息
			List<WritingPaper> listwps = writingPaperBiz.searchWritingPaper(wpid);
			if (listwps != null && listwps.size() > 0) {
				wp = listwps.get(0);
			}
			// 用来保存一个ArrayList，而每个元素又是一个包含ChapterQuestionCount元素的ArrayList
			ArrayList<ArrayList<ChapterQuestionCount>> cqCountArr = this.getChaptersCount(wpid);
			for (int s = 0,len=cqCountArr.size(); s < len; s++) {
				int n = 0; // 题目ID字符中从第几题开始算起属于指定的章节
				int count = 0; // 题目ID字符中到第几题结束,属于指定的章节
				ArrayList<ChapterQuestionCount> strArr = (ArrayList<ChapterQuestionCount>) cqCountArr.get(s);
				// 查询此卷的所有考生答案字符串
				List<String> arrList = writingAnswerBiz.searchAnswers(wpid);
				for (int i = 0,len1=strArr.size(); i <len1 ; i++) {
					ChapterQuestionCount cqCount = (ChapterQuestionCount) strArr.get(i);
					n = count;
					// 累加每章出题的数目
					count += cqCount.getQuestionCount();
					int correctCount = 0; // 累计考生在每一阶段的答对数
					// j代表每个考生的答案
					if(arrList!=null&&wp!=null){
						for (int j = 0,len2=arrList.size(); j <len2 ; j++) {
							correctCount += this.getExamineeCorrect(wpid, wp.getQuestionCorrect(), arrList.get(j), n, count);
						}
					}

					// 所有考生在这一阶段的平均答对数
					double avgCorrectCount = Double.parseDouble(String.valueOf(correctCount))
							/ Double.parseDouble(String.valueOf(arrList.size()));
					// 所有考生在这一阶段的平均答对率
					double avgCorrectRate = avgCorrectCount / cqCount.getQuestionCount();
					arr.add(cqCount.getSubjectName()); // 向集合中添加科目
					arr.add(cqCount.getChapterName()); // 往集合中添加章节
					arr.add(String.valueOf(avgCorrectRate)); // 往集合中添加答对率
				}
				if (arr != null && arr.size() > 0) {
					jsonStr = super.writeJson(0, arr);
				} else {
					jsonStr = super.writeJson(0, "查询失败！");
				}
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 统计每一科下的每一章节的考生答对率
	 * 
	 * @param paperId
	 *            String 试卷编号
	 * @return ArrayList
	 * @author fanhaohao
	 */
	@SuppressWarnings("unchecked")
	public void getExamineeCorrectRate() {
		WritingPaper wp = null;
		String jsonStr = "";
		try {
			List<WritingPaper> listwps = writingPaperBiz.searchWritingPaper(wpid);
			if (listwps != null && listwps.size() > 0) {
				wp = listwps.get(0);
			}
			ArrayList arr = new ArrayList();
			ArrayList cqCountArr = new ArrayList();


			cqCountArr = this.getChaptersCount(wpid);
			for (int s = 0; s < cqCountArr.size(); s++) {
				int n = 0; // 题目ID字符中从第几题开始算起属于指定的章节
				int count = 0; // 题目ID字符中到第几题结束,属于指定的章节
				ArrayList strArr = (ArrayList) cqCountArr.get(s);
				// 查询考生答案字符串
				String strExamineeAn = writingAnswerBiz.searchAnswer(wpid, examineeName);
				if (strArr != null) {
					for (int i = 0; i < strArr.size(); i++) {
						ChapterQuestionCount cqCount = (ChapterQuestionCount) strArr.get(i);
						n = count;
						count += cqCount.getQuestionCount();
						int correctCount = 0; // 累计考生在每一阶段的答对数
						// 所有考生在这一阶段的答对数
						// FIXME 先让程序运行，请修改 张影
						// FIXME: 上线后要去掉 界面上的新增班级功能，这个功能应该出现在缴费系统中
						correctCount += this.getExamineeCorrect(wpid, wp.getQuestionCorrect(), strExamineeAn, n, count);

						// 所有考生在这一阶段的平均答对率
						// double d1 = Double.valueOf(correctCount+"");
						// double d2 =
						// Double.valueOf(cqCount.getQuestionCount()+"");
						// double avgCorrectRate = d1/ d2;

						// arr.add(cqCount.getSubjectName()); // 向集合中添加科目
						// arr.add("答对题数"); // 向集合中添加科目
						arr.add(cqCount.getChapterName()); // 往集合中添加章节
						arr.add(correctCount + ""); // 往集合中添加答对题目数
						// arr.add("总题目数量"); // 向集合中添加科目
						// arr.add(cqCount.getChapterName()); // 往集合中添加章节
						arr.add((count - n) + ""); // 往集合中添加章节题目总数量
					}
				}
				if (arr != null && arr.size() > 0) {
					jsonStr = super.writeJson(0, arr);
				} else {
					jsonStr = super.writeJson(0, "查询失败！");
				}

			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 评出时，由老师再次评出成绩为null的学员成绩
	 */
	public List<String> handInPaper() {
		List<String> eNames=null;
		try {
			// 通过试卷编号得到试卷的正确答案
			WritingPaper wp = new WritingPaper();
			List<WritingPaper> listwp = writingPaperBiz.searchWritingPaper(wpid);
			if (listwp != null && listwp.size() > 0) {
				wp = listwp.get(0);
			}

			// 得到正确答案
			String rightAnswer = wp.getQuestionCorrect();
			// 得到共有多少道题
			int countQuestion = wp.getCountOfQuestion();
			// 计算出考生成绩，考生姓名不能从session中得到了，找所有score为null的学员姓名加到ArrayList中，再循环
			eNames = writingAnswerBiz.getScoreIsNullExamineeName(wpid);
			if (eNames != null && eNames.size() > 0) {
				for (String eName : eNames) {
					int score = writingAnswerBiz.searchComputeScore(wpid, eName, rightAnswer, countQuestion);
					// WritingAnswer waa=new WritingAnswer();
					// waa=writingAnswerBiz.searchWritingAnswer(wpid, eName);
					// waa.setScore(Float.parseFloat(score+""));
					// writingAnswerBiz.u
					// 更新考生答卷表
					// writingAnswerBiz.updateGrade(eName,wpid,score);
					writingAnswerBiz.updateGrade(eName, wpid, Float.parseFloat(score + ""));
				}
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return eNames;
	}

	/**
	 * 评笔试卷 gradeWritingPaper
	 */
	public void gradeThisWritingPaper() {
		String jsonStr = "";
		List<String> bool=handInPaper();
		WritingPaper wp = new WritingPaper();
		// 评试卷的时候对每个成绩为null的学员再评一次分，并更新题库，为空的学员主要是没有点交卷按钮
		try {
				// 得到各分数段的考生数
				System.out.println("wpid::::"+wpid);
				String scoreP = writingAnswerBiz.searchScorePercent(wpid);
				wp.setId(wpid);
				wp.setScorePercent(scoreP);
				wp.setPaperStatus(3);
				//TODO:如果一个人都没考过试卷  点击评卷 这里就会报错 空指针异常
				try {
					wp.setAvgScore(writingAnswerBiz.searchAvgScore(wpid));
					wp.setMinScore(writingAnswerBiz.searchMinScore(wpid));
					wp.setMaxScore(writingAnswerBiz.searchMaxScore(wpid));
				} catch (Exception e) {
					jsonStr = super.writeJson(1, "没有一个学生进行答题，不予评卷");
					return;
				}
				// 评卷后更新试卷信息
				writingPaperBiz.updateWpByScore(wp);
				jsonStr = super.writeJson(0, "获取成功！");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "获取失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 重考试卷
	 * 
	 * @return
	 */

	public void reExamThisWritingPaper() {
		String jsonStr = "";
		try {
			// 得到各分数段的考生数
			int n = this.writingPaperBiz.reExamWritingPaper(wpid);
			jsonStr = super.writeJson(0, "重考成功！");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "重考失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public WritingPaper getWritingPaper() {
		return writingPaper;
	}

	public void setWritingPaper(WritingPaper writingPaper) {
		this.writingPaper = writingPaper;
	}

	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

	public String getWpid() {
		return wpid;
	}

	public void setWpid(String wpid) {
		this.wpid = wpid;
	}

	public int getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(int paperStatus) {
		this.paperStatus = paperStatus;
	}

	public void setWritingAnswerBiz(WritingAnswerBiz writingAnswerBiz) {
		this.writingAnswerBiz = writingAnswerBiz;
	}

	public String getExamineeName() {
		return examineeName;
	}

	public void setExamineeName(String examineeName) {
		this.examineeName = examineeName;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

}
