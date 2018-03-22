package com.yc.webexam.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.LabPaper;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.po.WritingPaperTemplate;
import com.yc.po.WritingQuestion;
import com.yc.utils.JsonProtocol;
import com.yc.utils.JsonUtil;
import com.yc.vo.Page;

@SuppressWarnings({ "unused", "serial" })
public class DataarraylistAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(DataarraylistAction.class);
	private int displayRows; // 要显示的页数
	private int pageNume; // 当前请求的第几页数
	private String examClassName;
	private String wpid;
	private String pid;
	private String writingPaperJsonStr;
	private String questionId;
	private String paperId;
	private String stuName;
	private String paid;
	private String waid;
	private String examinationType;
	private Integer classId;
	private String examSubject;
	
	
	public String getExamSubject() {
		return examSubject;
	}

	public void setExamSubject(String examSubject) {
		this.examSubject = examSubject;
	}

	private String semester;

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	private WritingQuestion writingQuestion;
	@Resource(name = "writingPaperBiz")
	private WritingPaperBiz writingPaperBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "writingQuestionBiz")
	private WritingQuestionBiz writingQuestionBiz;
	@Resource(name = "labPaperBiz")
	private LabPaperBiz labPaperBiz;
	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;
	@Resource(name = "writingAnswerBiz")
	private WritingAnswerBiz writingAnswerBiz;

	/**
	 * 笔试卷查询（带分页功能）
	 * 
	 * @author fanhaohao
	 */

	public void showWritingPaperPages() {
		String jsonStr = "";
		WritingPaper wp = new WritingPaper();
		wp.setExamineeClass(examClassName);
		Page<WritingPaper> page = new Page<WritingPaper>();
		try {
			page.setPageSize(displayRows);
			page.setCurrentPage(pageNume);
			writingPaperBiz.searchWritingPaperPage(page, wp);
			wp.setWritingAnswers(null);
			for (WritingPaper w : page.getResult()) {
				w.setWritingAnswers(null);
				// w.setMaxScore(null);
				// w.setMinScore(null);
				w.setQuestionCorrect(null);
				// w.setQuestionInfo(null);
				// w.setAvgScore(null);
				w.setCorrectRate(null);
				// w.setQuestionId(null);
				// w.setScorePercent(null);
				w.setTimesConsume(null);
				w.setRemark(null);
			}
			jsonStr = super.writeJson(0, page);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败了！");
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
	 * 显示所有的模板卷
	 */
	public void showWritingPaperPagesTemplate() {
		String jsonStr = "";
		WritingPaperTemplate wp = new WritingPaperTemplate();
		wp.setExamSubject(examSubject);
		Page<WritingPaperTemplate> page = new Page<WritingPaperTemplate>();
		try {
			page.setPageSize(displayRows);
			page.setCurrentPage(pageNume);
			writingPaperBiz.searchWritingPaperPage(page, wp);
			for (WritingPaperTemplate w : page.getResult()) {
				// w.setMaxScore(null);
				// w.setMinScore(null);
				w.setQuestionCorrect(null);
				// w.setQuestionInfo(null);
				// w.setAvgScore(null);
				w.setCorrectRate(null);
				// w.setQuestionId(null);
				// w.setScorePercent(null);
				w.setTimesConsume(null);
				w.setRemark(null);
			}
			jsonStr = super.writeJson(0, page);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败了！");
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
	 * 根据id笔试卷或机试卷查询（带分页功能）
	 * 
	 * @author fanhaohao
	 */

	public void showPaperPages() {
		String jsonStr = "";
		try {
			// 当请求的是查询笔试成绩时
			if (examinationType == null || "writ".equals(examinationType)) {
				WritingPaper wp = new WritingPaper();
				wp.setExamineeClass(examClassName);
				wp.setId(paperId);
				Page<WritingPaper> page = new Page<WritingPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				writingPaperBiz.searchWritingPaperPageById(page, wp);
				// wp.setWritingAnswers(null);
				for (WritingPaper w : page.getResult()) {
					w.setWritingAnswers(null);
					w.setQuestionCorrect(null);
					// w.setQuestionInfo(null);
					w.setCorrectRate(null);
					w.setQuestionId(null);
					w.setTimesConsume(null);
					w.setRemark(null);
				}
				JsonProtocol jp = new JsonProtocol(0, null, page);
				jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd");
			}
			// 当请求的是查询机试成绩时
			else if ("lab".equals(examinationType)) {
				LabPaper lp = new LabPaper();
				lp.setExamineeClass(examClassName);
				lp.setId(paperId);
				Page<LabPaper> page = new Page<LabPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				labPaperBiz.searchLabPaperPageById(page, lp);
				// wp.setWritingAnswers(null);
				for (LabPaper w : page.getResult()) {
					w.setLabAnswers(null);
					w.setMaxScore(null);
					w.setMinScore(null);
					w.setAvgScore(null);
					w.setScorePercent(null);
					w.setRemark(null);
				}
				jsonStr = super.writeJson(0, page);
			}

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败了！");
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
	 * 根据姓名进行笔试卷或机试卷查询（带分页功能）
	 * 
	 * @author fanhaohao
	 */
	public void showPaperPagesByName() {
		String jsonStr = "";
		try {
			// 当请求的是查询笔试成绩时
			if (examinationType == null || "writ".equals(examinationType)) {
				WritingPaper wp = new WritingPaper();
				wp.setExamineeClass(examClassName);
				wp.setId(paperId);
				WritingAnswer wa = new WritingAnswer();
				wa.setExamineeName(stuName);
				Page<WritingAnswer> page = new Page<WritingAnswer>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				writingAnswerBiz.searchWritingPaperPageByName(page, wp, wa);
				for (WritingAnswer w : page.getResult()) {
					w.setAnswer(null);
					w.setCorrectRate(null);
					w.setSpareTime(null);
					w.getWritingPaper().setAvgScore(null);
					w.getWritingPaper().setCorrectRate(null);
					w.getWritingPaper().setMaxScore(null);
					w.getWritingPaper().setMinScore(null);
					w.getWritingPaper().setPaperPwd(null);
					w.getWritingPaper().setQuestionCorrect(null);
					w.getWritingPaper().setQuestionId(null);
					w.getWritingPaper().setRemark(null);
					w.getWritingPaper().setWritingAnswers(null);
					w.getWritingPaper().setTimesConsume(null);
					w.getWritingPaper().setScorePercent(null);
				}
				jsonStr = super.writeJson(0, page);
			}
			// 当请求的是查询机试成绩时
			else if ("lab".equals(examinationType)) {
				LabPaper lp = new LabPaper();
				lp.setExamineeClass(examClassName);
				lp.setId(paperId);
				Page<LabPaper> page = new Page<LabPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				labPaperBiz.searchLabPaperPageById(page, lp);
				// wp.setWritingAnswers(null);
				for (LabPaper w : page.getResult()) {
					w.setLabAnswers(null);
					w.setMaxScore(null);
					w.setMinScore(null);
					w.setAvgScore(null);
					w.setScorePercent(null);
					w.setRemark(null);
				}
				jsonStr = super.writeJson(0, page);
			}

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败!");
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
	 * 根据WritingPaper WritingAnswer两着的id进行笔试卷或机试卷查询查出一张试卷来
	 * 
	 * @author fanhaohao
	 */
	public void showAnswerPagesDetail() {
		String jsonStr = "";
		try {
			// 当请求的是查询笔试成绩时
			if (examinationType == null || "writ".equals(examinationType)) {
				WritingPaper wp = new WritingPaper();
				wp.setId(wpid);
				WritingAnswer wa = new WritingAnswer();
				wa.setId(Integer.parseInt(waid));

				wa = writingAnswerBiz.searchPaperById(wp, wa);
				wa.setCorrectRate(null);
				wa.setSpareTime(null);
				wa.getWritingPaper().setAvgScore(null);
				wa.getWritingPaper().setCorrectRate(null);
				wa.getWritingPaper().setMaxScore(null);
				wa.getWritingPaper().setMinScore(null);
				wa.getWritingPaper().setPaperPwd(null);
				wa.getWritingPaper().setQuestionCorrect(null);
				wa.getWritingPaper().setRemark(null);
				wa.getWritingPaper().setScorePercent(null);
				wa.getWritingPaper().setWritingAnswers(null);
				jsonStr = super.writeJson(0, wa);
			}
			// 当请求的是查询机试成绩时
			else if ("lab".equals(examinationType)) {
				LabPaper lp = new LabPaper();
				lp.setExamineeClass(examClassName);
				lp.setId(paperId);
				Page<LabPaper> page = new Page<LabPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				labPaperBiz.searchLabPaperPageById(page, lp);
				// wp.setWritingAnswers(null);
				for (LabPaper w : page.getResult()) {
					w.setLabAnswers(null);
					w.setMaxScore(null);
					w.setMinScore(null);
					w.setAvgScore(null);
					w.setScorePercent(null);
					w.setRemark(null);
				}
				jsonStr = super.writeJson(0, page);
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败!");
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
	 * 根据id删除笔试卷或机试卷然后查询（带分页功能）
	 * 
	 * @author fanhaohao
	 */

	public void showPagesAfterDelete() {
		String jsonStr = "";
		try {
			// 当请求的是查询笔试成绩时
			if (examinationType == null || "writ".equals(examinationType)) {
				WritingPaper wp = new WritingPaper();
				wp.setExamineeClass(examClassName);
				wp.setId(pid);
				writingPaperBiz.deleteWritingPaper(wp);
				if (paperId != null && !"".equals(paperId)) {
					wp.setId(paperId);
				} else {
					wp.setId("");
				}

				Page<WritingPaper> page = new Page<WritingPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				writingPaperBiz.searchWritingPaperPageById(page, wp);
				// wp.setWritingAnswers(null);
				for (WritingPaper w : page.getResult()) {
					w.setWritingAnswers(null);
					w.setMaxScore(null);
					w.setMinScore(null);
					w.setQuestionCorrect(null);
					w.setQuestionInfo(null);
					w.setAvgScore(null);
					w.setCorrectRate(null);
					w.setQuestionId(null);
					w.setScorePercent(null);
					w.setTimesConsume(null);
					w.setRemark(null);
				}
				jsonStr = super.writeJson(0, page);
			}
			// 当请求的是查询机试成绩时
			else if ("lab".equals(examinationType)) {
				LabPaper lp = new LabPaper();
				lp.setId(pid);
				labPaperBiz.deleteLabPaper(lp);
				if (paperId != null && !"".equals(paperId)) {
					lp.setId(paperId);
				} else {
					lp.setId("");
				}
				Page<LabPaper> page = new Page<LabPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				labPaperBiz.searchLabPaperPageById(page, lp);
				// wp.setWritingAnswers(null);
				for (LabPaper w : page.getResult()) {
					w.setLabAnswers(null);
					w.setMaxScore(null);
					w.setMinScore(null);
					w.setAvgScore(null);
					w.setScorePercent(null);
					w.setRemark(null);
				}
				jsonStr = super.writeJson(0, page);
			}

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败了！");
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
	 * 根据WritingAnswer id或labAnswer id删除笔试卷或机试卷然后查询（带分页功能）
	 * 
	 * @author fanhaohao
	 */
	public void showAnswerAfterDelete() {
		String jsonStr = "";
		try {
			// 当请求的是查询笔试成绩时
			if (examinationType == null || "writ".equals(examinationType)) {
				WritingPaper wp = new WritingPaper();
				wp.setExamineeClass(examClassName);
				wp.setId(paperId);
				WritingAnswer wa = new WritingAnswer();
				wa.setId(Integer.parseInt(paid));
				writingAnswerBiz.deleteWritingAnswer(wa);
				Page<WritingAnswer> page = new Page<WritingAnswer>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				wa.setExamineeName(stuName);
				writingAnswerBiz.searchWritingPaperPageByName(page, wp, wa);
				for (WritingAnswer w : page.getResult()) {
					w.setAnswer(null);
					w.setCorrectRate(null);
					w.setSpareTime(null);
					w.getWritingPaper().setAvgScore(null);
					w.getWritingPaper().setCorrectRate(null);
					w.getWritingPaper().setMaxScore(null);
					w.getWritingPaper().setMinScore(null);
					w.getWritingPaper().setPaperPwd(null);
					w.getWritingPaper().setQuestionCorrect(null);
					w.getWritingPaper().setQuestionId(null);
					w.getWritingPaper().setRemark(null);
					w.getWritingPaper().setWritingAnswers(null);
					w.getWritingPaper().setTimesConsume(null);
					w.getWritingPaper().setScorePercent(null);
				}
				jsonStr = super.writeJson(0, page);
			}
			// 当请求的是查询机试成绩时
			else if ("lab".equals(examinationType)) {
				LabPaper lp = new LabPaper();
				lp.setId(pid);
				labPaperBiz.deleteLabPaper(lp);
				if (paperId != null && !"".equals(paperId)) {
					lp.setId(paperId);
				} else {
					lp.setId("");
				}
				Page<LabPaper> page = new Page<LabPaper>();
				page.setPageSize(displayRows);
				page.setCurrentPage(pageNume);
				labPaperBiz.searchLabPaperPageById(page, lp);
				// wp.setWritingAnswers(null);
				for (LabPaper w : page.getResult()) {
					w.setLabAnswers(null);
					w.setMaxScore(null);
					w.setMinScore(null);
					w.setAvgScore(null);
					w.setScorePercent(null);
					w.setRemark(null);
				}
				jsonStr = super.writeJson(0, page);
			}

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败了！");
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
	 * 显示试卷详细信息 fanhaohao
	 */
	public void showWritingPaperPagesDetail() {
		String jsonStr = "";
		try {
			WritingPaper wp = new WritingPaper();
			wp.setId(wpid);
			List<WritingPaper> writingPapers = writingPaperBiz.searchWritingPaper(wpid);
			for (WritingPaper w : writingPapers) {
				w.setWritingAnswers(null);
				// w.setMaxScore(null);
				// w.setMinScore(null);
				w.setQuestionCorrect(null);
				// w.setQuestionInfo(null);
				// w.setAvgScore(null);
				w.setCorrectRate(null);
				// w.setScorePercent(null);
			}

			JsonProtocol jp = new JsonProtocol(0, null, writingPapers.get(0));
			jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd");
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
	 * 显示模板试卷详细信息 fanhaohao
	 */
	public void showWritingPaperPagesDetailTemplate() {
		String jsonStr = "";
		try {
			WritingPaperTemplate wp = new WritingPaperTemplate();
			wp.setId(wpid);
			List<WritingPaperTemplate> writingPapers = writingPaperBiz.searchWritingPaperTemplate(wpid);
			for (WritingPaperTemplate w : writingPapers) {
				// w.setMaxScore(null);
				// w.setMinScore(null);
				w.setQuestionCorrect(null);
				// w.setQuestionInfo(null);
				// w.setAvgScore(null);
				w.setCorrectRate(null);
				// w.setScorePercent(null);
			}
			
			JsonProtocol jp = new JsonProtocol(0, null, writingPapers.get(0));
			jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd");
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
	 * 查找所有的班级 fanhaohao
	 */
	public void findAllExamineeClass() {
		String jsonStr = "";
		try {
			List<String> list = examineeClassBiz.searchAllExamineeClassName();
			jsonStr = super.writeJson(0, list);
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
	 * 查找所有的班级和它的id fanhaohao
	 */
	public void findAllExamineeClassAndId() {
		String jsonStr = "";
		try {
			// List<String>
			// list=examineeClassBiz.searchAllExamineeClassNameAndId();
			List<ExamineeClass> list = examineeClassBiz.findExamineeClassBySemester(semester);
			jsonStr = super.writeJson(0, list);
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
	 * 更新试卷表信息 fanhaohao
	 */
	public void updateWritingPaperPagesDetail() {
		String jsonStr = "";
		try {
			WritingPaper wp = JSON.parseObject(writingPaperJsonStr, WritingPaper.class);
			writingPaperBiz.updateWritingPaper(wp);
			jsonStr = super.writeJson(0, "修改成功！");
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
	 * 根据多个ids查询试题 fanhaohao
	 */
	public void findQuestionByids() {
		String jsonStr = "";
		List<Integer> listid = new ArrayList<Integer>();
		try {
			JSONArray jsonArray = JSON.parseArray(questionId);
			for (Object o : jsonArray) {
				listid.add(Integer.parseInt(o.toString()));
			}
			List<WritingQuestion> list = writingQuestionBiz.findWritingQuestionByIds(listid);

			jsonStr = super.writeJson(0, list);
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
	 * 根据班级名称来查所有的学生姓名
	 * 
	 * @author fanhaohao
	 */
	public void showExamineeNames() {
		String jsonStr = "";
		try {
			List<Examinee> list = examineeBiz.findAllStudentByClassName(examClassName);
			for (Examinee l : list) {
				l.getExamineeClass().setCheckings(null);
				l.getExamineeClass().setaDailyTalks(null);
				l.getExamineeClass().setCreateDate(null);
				l.getExamineeClass().setPointPapers(null);
				l.getExamineeClass().setCreateDate(null);
				l.getExamineeClass().setRemark(null);
				l.getExamineeClass().setOverDate(null);
				l.getExamineeClass().setSemester(null);
				l.getExamineeClass().setClassName(null);
				l.setPassword(null);
			}
			jsonStr = JSON.toJSONString(new JsonProtocol(0, null, list),
					SerializerFeature.DisableCircularReferenceDetect);

		} catch (Exception e) {
			jsonStr = JSON.toJSONString(new JsonProtocol(1, "查询失败！", null),
					SerializerFeature.DisableCircularReferenceDetect);
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
	 * 根据班级名称来查所有的学生姓名
	 * 
	 * @author fanhaohao
	 */
	public void showExamineeNames1() {
		String jsonStr = "";
		try {
			//List<Examinee> examinee = examineeBiz.findAllStuNameByClassName(className);
			List<Examinee> list = examineeBiz.findAllStuNameByClassName(examClassName);
			
			jsonStr = super.writeJson(0, list);

		} catch (Exception e) {
			jsonStr = super.writeJson(0, "查询失败！");
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
	 * 根据班级id来查所有的学生姓名
	 * 
	 * @author fanhaohao
	 */
	public void showExamineeNamesByClassId() {
		String jsonStr = "";
		try {
			List<String> list = examineeBiz.findAllStuNameByClassId(classId);

			jsonStr = super.writeJson(0, list);

		} catch (Exception e) {
			jsonStr = super.writeJson(0, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	// 根据题号更新试题信息
	public void updateWritingQuestion() {
		String jsonStr = "";
		try {
			writingQuestionBiz.updateWritingQuestion(writingQuestion);
			jsonStr = super.writeJson(0, "修改成功！");
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
	
	

	public int getDisplayRows() {
		return displayRows;
	}

	public void setDisplayRows(int displayRows) {
		this.displayRows = displayRows;
	}

	public int getPageNume() {
		return pageNume;
	}

	public void setPageNume(int pageNume) {
		this.pageNume = pageNume;
	}

	public String getExamClassName() {
		return examClassName;
	}

	public void setExamClassName(String examClassName) {
		this.examClassName = examClassName;
	}

	public String getWpid() {
		return wpid;
	}

	public void setWpid(String wpid) {
		this.wpid = wpid;
	}

	public String getWritingPaperJsonStr() {
		return writingPaperJsonStr;
	}

	public void setWritingPaperJsonStr(String writingPaperJsonStr) {
		this.writingPaperJsonStr = writingPaperJsonStr;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public WritingQuestion getWritingQuestion() {
		return writingQuestion;
	}

	public void setWritingQuestion(WritingQuestion writingQuestion) {
		this.writingQuestion = writingQuestion;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getExaminationType() {
		return examinationType;
	}

	public void setExaminationType(String examinationType) {
		this.examinationType = examinationType;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getWaid() {
		return waid;
	}

	public void setWaid(String waid) {
		this.waid = waid;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}

	public void setLabPaperBiz(LabPaperBiz labPaperBiz) {
		this.labPaperBiz = labPaperBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	public void setWritingAnswerBiz(WritingAnswerBiz writingAnswerBiz) {
		this.writingAnswerBiz = writingAnswerBiz;
	}

}
