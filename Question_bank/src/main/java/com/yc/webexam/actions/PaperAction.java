package com.yc.webexam.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.EditionBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.po.WritingPaper;
import com.yc.po.WritingPaperTemplate;
import com.yc.utils.ExamUtil;
import com.yc.utils.JsonUtil;
import com.yc.vo.WritingPaperModel;

public class PaperAction extends BaseAction implements ModelDriven<WritingPaperModel> {
	private WritingPaperModel writingPaperModel = new WritingPaperModel();
	private static final Logger logger = Logger.getLogger(PaperAction.class);

	private ServletContext s = ServletActionContext.getServletContext();
	@Resource(name = "writingPaperBiz")
	private WritingPaperBiz writingPaperBiz;
	@Resource(name = "editionBiz")
	private EditionBiz editionBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;
	@Resource(name = "writingQuestionBiz")
	private WritingQuestionBiz writingQuestionBiz;
	/*
	 * public void searchDifficultyRate(){ String
	 * questionInfo=writingPaperModel.getQuestionInfo();//取出试卷试题信息 String
	 * strAr[] =questionInfo.split("MMM"); String strQ[] = strAr[0].split(":");
	 * int subjID = subjectBiz.getSubjectId(strQ[0]); String chapterN[] =
	 * strQ[1].split(";"); // String chapterN[] = strQ[1].split(",");
	 * 
	 * @SuppressWarnings("unused") List chapterIDandCountList = new ArrayList();
	 * for (int i = 0; i < chapterN.length; i++) { int chapterId =
	 * chapterBiz.getChapterId(subjID, chapterN[i].split(",")[0]); }
	 * writingQuestionBiz.getDifficultyRate(writingPaperModel.getSemester(),
	 * subjID, chapterId, difficulty); }
	 */

	/*
	 * public void addWritingPaper(){ String jsonStr=""; try { WritingPaper
	 * wp=new WritingPaper();//试题对象 String
	 * questionInfo=writingPaperModel.getQuestionInfo();//取出试卷试题信息 List
	 * alQuestions=new ArrayList(); //生成试卷编号 String strAr[]
	 * =questionInfo.split("MMM"); // 拆分出题条件 String paperId="";//试卷编号 //
	 * 连接生成试卷的题目编号 String strQuestionID = ""; //查出符合要求的试题 if (strAr.length == 1)
	 * { paperId=this.createWritingPaperId(writingPaperModel.getEdition(),
	 * writingPaperModel.getSemester(), writingPaperModel.getExamSubject(),
	 * writingPaperModel.getExamineeClass());
	 * alQuestions=writingPaperBiz.getQuestionIds(writingPaperModel.
	 * getExamineeClass(), writingPaperModel.getExamSubject()); strQuestionID =
	 * ""; // 连接生成试卷的题目编号 strQuestionID =
	 * writingQuestionBiz.getQuestionIdsOf(strAr[0],
	 * writingPaperModel.getSemester(), writingPaperModel.getCountOfQuestion(),
	 * writingPaperModel.getRate1(), writingPaperModel.getRate2(),
	 * writingPaperModel.getRate3(), alQuestions); }else{
	 * paperId=this.createWritingPaperId(writingPaperModel.getEdition(),
	 * writingPaperModel.getSemester(), "0",
	 * writingPaperModel.getExamineeClass());
	 * alQuestions=writingPaperBiz.getQuestionIds(writingPaperModel.
	 * getExamineeClass(), "综合"); strQuestionID = ""; for (int i = 0; i <
	 * strAr.length; i++) { String ss=
	 * writingQuestionBiz.getQuestionIdsOf(strAr[i],
	 * writingPaperModel.getSemester(),
	 * writingPaperModel.getCountOfQuestion(),writingPaperModel.getRate1(),
	 * writingPaperModel.getRate2(),writingPaperModel.getRate3(), alQuestions);
	 * if (ss.equals("0")) { strQuestionID = ss; break; } else { strQuestionID
	 * += ss; } } }
	 * 
	 * if (strQuestionID.equals("0")) { jsonStr=super.writeJson(1,
	 * "数据库中符合条件的题目不够数!请重新再出题.");
	 * 
	 * } else{ wp.setId(paperId);
	 * wp.setExamineeClass(writingPaperModel.getExamineeClass());
	 * wp.setExamSubject(writingPaperModel.getExamSubject());
	 * wp.setPaperName(writingPaperModel.getPaperName());
	 * wp.setPaperPwd(writingPaperModel.getPaperPwd()); wp.setPaperStatus(1);
	 * wp.setCountOfQuestion(writingPaperModel.getCountOfQuestion());
	 * wp.setExamDate(writingPaperModel.getExamDate());
	 * wp.setQuestionId(strQuestionID);
	 * wp.setTimesConsume(writingPaperModel.getTimesConsume());
	 * wp.setRemark(writingPaperModel.getRemark());
	 * wp.setQuestionCorrect(writingQuestionBiz.getAnswers(strQuestionID));
	 * wp.setQuestionInfo(writingPaperModel.getQuestionInfo()); String
	 * userName=(String) session.get("userName"); wp.setOperator(userName); //
	 * 添加试卷 writingPaperBiz.addWritingPaper(wp); jsonStr=super.writeJson(0,
	 * "试卷生成成功"); } } catch (Exception e) { jsonStr=super.writeJson(1,
	 * "试卷生成出现异常，请联系管理员或稍候再试。"); logger.error(e); }finally{ try {
	 * JsonUtil.jsonOut(jsonStr); } catch (IOException e) { logger.error(e); } }
	 * 
	 * }
	 */

	/**
	 * 添加考试试卷
	 */
	public void addWritingPaper() {
		String jsonStr = "";
		try {
			WritingPaper wp = new WritingPaper();// 试题对象
			String questionInfo = writingPaperModel.getQuestionInfo();// 取出试卷试题信息
			// 生成试卷编号
			String strAr[] = questionInfo.split("MMM"); // 拆分出题条件
			String paperId = "";// 试卷编号
			// 连接生成试卷的题目编号
			String strQuestionID = "";
			// 查出符合要求的试题
			if (strAr.length == 1) {
				paperId = this.createWritingPaperId(writingPaperModel.getEdition(), writingPaperModel.getSemester(),
						writingPaperModel.getExamSubject(), writingPaperModel.getExamineeClass());//三次调用basedao，三条语句，sql无需优化
				strQuestionID = "";
				// 连接生成试卷的题目编号
				strQuestionID = writingQuestionBiz.getQuestionIdsOf(strAr[0], writingPaperModel.getSemester(),
						writingPaperModel.getCountOfQuestion(), writingPaperModel.getRate1(),
						writingPaperModel.getRate2(), writingPaperModel.getRate3());
			} else {
				paperId = this.createWritingPaperId(writingPaperModel.getEdition(), writingPaperModel.getSemester(),
						"0", writingPaperModel.getExamineeClass());
				strQuestionID = "";
				for (int i = 0,len=strAr.length; i < len; i++) {//[软件技术基础1:?????1,2;, SQL Server数据库开发:?????1,8;]
					String ss = writingQuestionBiz.getQuestionIdsOf(strAr[i], writingPaperModel.getSemester(),
							writingPaperModel.getCountOfQuestion(), writingPaperModel.getRate1(),
							writingPaperModel.getRate2(), writingPaperModel.getRate3());
					if (ss.equals("0")) {
						strQuestionID = ss;
						break;
					} else {
						strQuestionID += ss;
					}
				}
			}

			if (strQuestionID.equals("0")) {
				jsonStr = super.writeJson(1, "数据库中符合条件的题目不够数!请重新再出题.");

			} else {
				wp.setId(paperId);
				wp.setExamineeClass(writingPaperModel.getExamineeClass());
				wp.setExamSubject(writingPaperModel.getExamSubject());
				wp.setPaperName(writingPaperModel.getPaperName());
				wp.setPaperPwd(writingPaperModel.getPaperPwd());
				wp.setPaperStatus(1);
				wp.setCountOfQuestion(writingPaperModel.getCountOfQuestion());
				wp.setExamDate(writingPaperModel.getExamDate());
				wp.setQuestionId(strQuestionID);
				wp.setTimesConsume(writingPaperModel.getTimesConsume());
				wp.setRemark(writingPaperModel.getRemark());
				wp.setQuestionCorrect(writingQuestionBiz.getAnswers(strQuestionID));
				wp.setQuestionInfo(writingPaperModel.getQuestionInfo());
				String userName = (String) session.get("userName");
				wp.setOperator(userName);
				// 添加试卷
				writingPaperBiz.addWritingPaper(wp);
				if(writingPaperModel.getExamineeClass().equals("模板卷班级")){
					jsonStr = super.writeJson(0, "模板卷设定成功");
				}else{
					jsonStr = super.writeJson(0, "试卷生成成功");
				}
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "试卷生成出现异常，请联系管理员或稍候再试。");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	public void addWritingPaperTemplate() {
		String jsonStr = "";
		try {
			WritingPaperTemplate wp = new WritingPaperTemplate();// 试题对象
			String questionInfo = writingPaperModel.getQuestionInfo();// 取出试卷试题信息
			// 生成试卷编号
			String strAr[] = questionInfo.split("MMM"); // 拆分出题条件
			String paperId = "";// 试卷编号
			// 连接生成试卷的题目编号
			String strQuestionID = "";
			// 查出符合要求的试题
			if (strAr.length == 1) {
				paperId = this.createWritingPaperId(writingPaperModel.getEdition(), writingPaperModel.getSemester(),
						writingPaperModel.getExamSubject(), writingPaperModel.getExamineeClass());//三次调用basedao，三条语句，sql无需优化
				strQuestionID = "";
				// 连接生成试卷的题目编号
				strQuestionID = writingQuestionBiz.getQuestionIdsOf(strAr[0], writingPaperModel.getSemester(),
						writingPaperModel.getCountOfQuestion(), writingPaperModel.getRate1(),
						writingPaperModel.getRate2(), writingPaperModel.getRate3());
			} else {
				paperId = this.createWritingPaperId(writingPaperModel.getEdition(), writingPaperModel.getSemester(),
						"0", writingPaperModel.getExamineeClass());
				strQuestionID = "";
				for (int i = 0,len=strAr.length; i < len; i++) {//[软件技术基础1:?????1,2;, SQL Server数据库开发:?????1,8;]
					String ss = writingQuestionBiz.getQuestionIdsOf(strAr[i], writingPaperModel.getSemester(),
							writingPaperModel.getCountOfQuestion(), writingPaperModel.getRate1(),
							writingPaperModel.getRate2(), writingPaperModel.getRate3());
					if (ss.equals("0")) {
						strQuestionID = ss;
						break;
					} else {
						strQuestionID += ss;
					}
				}
			}

			if (strQuestionID.equals("0")) {
				jsonStr = super.writeJson(1, "数据库中符合条件的题目不够数!请重新再出题.");

			} else {
				wp.setId(paperId);
				wp.setExamSubject(writingPaperModel.getExamSubject());
				wp.setPaperName(writingPaperModel.getPaperName());
				wp.setPaperStatus(1);
				wp.setCountOfQuestion(writingPaperModel.getCountOfQuestion());
				wp.setQuestionId(strQuestionID);
				wp.setTimesConsume(writingPaperModel.getTimesConsume());
				wp.setRemark(writingPaperModel.getRemark());
				wp.setQuestionCorrect(writingQuestionBiz.getAnswers(strQuestionID));
				wp.setQuestionInfo(writingPaperModel.getQuestionInfo());
				String userName = (String) session.get("userName");
				wp.setOperator(userName);
				// 添加试卷
				writingPaperBiz.addWritingPaperTemplate(wp);
					jsonStr = super.writeJson(0, "模板卷生成成功");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "试卷生成出现异常，请联系管理员或稍候再试。");
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
	 * 根据版本,学期,班级,科目(综合科:0)和当前时间生成试卷编号
	 * 
	 * @param version
	 *            String 版本
	 * @param semester
	 *            String 学期
	 * @param examClass
	 *            String 班级
	 * @return String
	 */
	private String createWritingPaperId(String version, String semester, String subject, String examClass) {
		String paperID = ""; // 考卷ID
		/*String vid = Integer.toString(editionBiz.getEditionId(version));
		String eid = Integer.toString(examineeClassBiz.getClassIdOfname(examClass));*/
		String sid;
		if (subject.endsWith("0")) {
			sid = 0 + "";
		} else {
			sid = Integer.toString(subjectBiz.getSubjectId(subject));
		}
		// 根据系统时间生成年月日时分组成的字符串
		String nowT = ExamUtil.getNowDate("yyyyMMddhhmmss");
		// 版本，学期，考试班级，科目编号(综合试卷的科目为:0)生成考卷ID
		paperID =  sid + nowT;
		return paperID;
	}
	
	
	// 将笔试试卷指派给其他的班级
			public void examPaperToAnotherClass() {
				String jsonStr = "";
				String paperId = "";// 试卷编号
				try {
					WritingPaper wp=writingPaperBiz.findWritingPaperById(writingPaperModel.getPaperPwd());
					paperId = writingPaperModel.getPaperPwd()+writingPaperModel.getPaperStatus();
					
					WritingPaper wpFin=new WritingPaper();
					//System.out.println("ExamineeClass:"+writingPaperModel.getExamineeClass());
					wpFin.setId(paperId);
					wpFin.setExamineeClass(writingPaperModel.getExamineeClass());
					wpFin.setExamSubject(wp.getExamSubject());
					wpFin.setPaperName(wp.getPaperName());
					wpFin.setPaperPwd(wp.getPaperPwd());
					wpFin.setPaperStatus(1);
					wpFin.setCountOfQuestion(wp.getCountOfQuestion());
					wpFin.setExamDate(writingPaperModel.getExamDate());
					wpFin.setQuestionId(wp.getQuestionId());
					wpFin.setTimesConsume(wp.getTimesConsume());
					wpFin.setQuestionCorrect(wp.getQuestionCorrect());
					wpFin.setQuestionInfo(wp.getQuestionInfo());
				
					wpFin.setOperator(wp.getOperator());
					// 添加试卷
					writingPaperBiz.addWritingPaper(wpFin);
					jsonStr = super.writeJson(0, "试卷生成成功!");
					//wp.setExamineeClass(examineeClass);
					//writingQuestionBiz.updateWritingQuestion(writingQuestion);
				} catch (Exception e) {
					jsonStr = super.writeJson(1, "不能将一套试卷重复指定给同一个班！！");
					logger.error(e);
				} finally {
					try {
						JsonUtil.jsonOut(jsonStr);
					} catch (IOException e) {
						logger.error(e);
					}
				}
			}
			// 模板卷指派
			public void examPaperToAnotherClassTemplate() {
				String jsonStr = "";
				String paperId = "";// 试卷编号
				try {
					
					WritingPaperTemplate wp=writingPaperBiz.findWritingPaperByIdTemlate(writingPaperModel.getPaperPwd());
					paperId = writingPaperModel.getPaperPwd()+writingPaperModel.getPaperStatus();
					
					WritingPaper wpFin=new WritingPaper();
					//System.out.println("ExamineeClass:"+writingPaperModel.getExamineeClass());
					wpFin.setId(paperId);
					wpFin.setExamineeClass(writingPaperModel.getExamineeClass());
					wpFin.setExamSubject(wp.getExamSubject());
					wpFin.setPaperName(wp.getPaperName());
					wpFin.setPaperPwd("a");
					wpFin.setPaperStatus(1);
					wpFin.setCountOfQuestion(wp.getCountOfQuestion());
					wpFin.setExamDate(writingPaperModel.getExamDate());
					wpFin.setQuestionId(wp.getQuestionId());
					wpFin.setTimesConsume(wp.getTimesConsume());
					wpFin.setQuestionCorrect(wp.getQuestionCorrect());
					wpFin.setQuestionInfo(wp.getQuestionInfo());
					
					wpFin.setOperator(wp.getOperator());
					// 添加试卷
					writingPaperBiz.addWritingPaper(wpFin);
					jsonStr = super.writeJson(0, "试卷生成成功!");
					//wp.setExamineeClass(examineeClass);
					//writingQuestionBiz.updateWritingQuestion(writingQuestion);
				} catch (Exception e) {
					jsonStr = super.writeJson(1, "该试卷已经指定给这个班，不能重复指定！！");
					logger.error(e);
				} finally {
					try {
						JsonUtil.jsonOut(jsonStr);
					} catch (IOException e) {
						logger.error(e);
					}
				}
			}

	@Override
	public WritingPaperModel getModel() {
		return this.writingPaperModel;
	}

	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}

}