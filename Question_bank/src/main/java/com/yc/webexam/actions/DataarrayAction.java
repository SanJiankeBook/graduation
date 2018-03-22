package com.yc.webexam.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ChapterBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;
import com.yc.utils.JsonUtil;
import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.WritingQuestionPaper;

public class DataarrayAction extends BaseAction implements ModelDriven<DataarrayActionModel> {

	private DataarrayActionModel dataarrayActionModel = new DataarrayActionModel();
	private static final Logger logger = Logger.getLogger(DataarrayAction.class);


	@Resource(name = "writingQuestionBiz")
	private WritingQuestionBiz writingQuestionBiz;
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;
	@Resource(name = "chapterBiz")
	private ChapterBiz chapterBiz;
	@Resource(name = "writingPaperBiz")
	private WritingPaperBiz writingPaperBiz;

	// 查询所有题目信息
	public void searchAllPaper() {
		try {
			List<WritingQuestionPaper> lists = new ArrayList<WritingQuestionPaper>();
			DataGaidModel dgm = null;
			dgm = writingQuestionBiz.searchAllWritingQuestionPage(dataarrayActionModel);
			DataGaidModel d = new DataGaidModel();
			List<WritingQuestion> list = dgm.getRows();
			for (int i = 0,len=list.size(); i < len; i++) {
				WritingQuestionPaper wqp = new WritingQuestionPaper();
				wqp.setId(list.get(i).getId());
				String question=list.get(i).getQuestion();
				question=question.replaceAll("<p>", "");
				question=question.replaceAll("</p>", "");
				question=question.replaceAll("&nbsp;", "");
				
				wqp.setQuestion("<xmp>" + question + "</xmp>");
				wqp.setSubjectName(subjectBiz.findSubjectName(list.get(i).getSubjectId()));
				wqp.setChapterName(chapterBiz.getChapterName(list.get(i).getChapterId()));
				if (("1").equals(list.get(i).getQuestionType())) {
					wqp.setType("单选");
				} else if (("2").equals(list.get(i).getQuestionType())) {
					wqp.setType("多选");
				}
				lists.add(wqp);
			}
			d.setRows(lists);
			d.setTotal(dgm.getTotal());
			JsonUtil.writeJson(d);
		} catch (Exception e) {
			DataGaidModel dgm = new DataGaidModel();
			List<WritingQuestionPaper> list = new ArrayList<WritingQuestionPaper>();
			dgm.setRows(list);
			dgm.setTotal((long) 0);
			JsonUtil.writeJson(dgm);
			logger.error(e);
		}
	}

	// public void findAllPaper(){
	// String jsonStr="";
	//
	// Page<WritingQuestionPaper> questionPaper =new
	// Page<WritingQuestionPaper>();
	// Page<WritingQuestion> page=new Page<WritingQuestion>();
	// try {
	// page.setPageSize(dataarrayActionModel.getDisplayRows());
	// page.setCurrentPage(dataarrayActionModel.getPageNume());
	// writingQuestionBiz.searchAllWritingQuestionPage(page);
	// for(int i=0;i<page.getResult().size();i++){
	// WritingQuestionPaper wqp=new WritingQuestionPaper();
	// wqp.setId(page.getResult().get(i).getId());
	// wqp.setQuestion(page.getResult().get(i).getQuestion());
	// wqp.setSubjectName(subjectBiz.findSubjectName(page.getResult().get(i).getSubjectId()));
	// wqp.setChapterName(chapterBiz.getChapterName(page.getResult().get(i).getChapterId()));
	// if(("1").equals(page.getResult().get(i).getQuestionType())){
	// wqp.setType("单选");
	// }else if(("2").equals(page.getResult().get(i).getQuestionType())){
	// wqp.setType("多选");
	// }
	// questionPaper.getResult().add(wqp);
	// }
	// questionPaper.setCurrentPage(page.getCurrentPage());
	// questionPaper.setPageSize(page.getPageSize());
	// questionPaper.setTotalsCount(page.getTotalsCount());
	// questionPaper.setTotalsPage(page.getTotalsPage());
	// JsonProtocol jsonProtocol = null;
	// jsonProtocol = new JsonProtocol(0, null, questionPaper);
	// jsonStr = JSON.toJSONString(jsonProtocol,
	// SerializerFeature.DisableCircularReferenceDetect);
	// } catch (Exception e) {
	// jsonStr=super.writeJson(1, "查询失败了！");
	// logger.error(e);
	// }finally{
	// try {
	// JsonUtil.jsonOut(jsonStr);
	// } catch (IOException e) {
	// logger.error(e);
	// }
	// }
	// }
	//
	// public void findPaper(){
	// String jsonStr="";
	// Page<WritingQuestionPaper> questionPaper =new
	// Page<WritingQuestionPaper>();
	// Page<WritingQuestion> page=new Page<WritingQuestion>();
	// try {
	// page.setPageSize(dataarrayActionModel.getDisplayRows());
	// page.setCurrentPage(dataarrayActionModel.getPageNume());
	// writingQuestionBiz.searchWritingQuestionPage(dataarrayActionModel, page);
	// for(int i=0;i<page.getResult().size();i++){
	// WritingQuestionPaper wqp=new WritingQuestionPaper();
	// wqp.setId(page.getResult().get(i).getId());
	// wqp.setQuestion(page.getResult().get(i).getQuestion());
	// wqp.setSubjectName(subjectBiz.findSubjectName(page.getResult().get(i).getSubjectId()));
	// wqp.setChapterName(chapterBiz.getChapterName(page.getResult().get(i).getChapterId()));
	// if(("1").equals(page.getResult().get(i).getQuestionType())){
	// wqp.setType("单选");
	// }else if(("2").equals(page.getResult().get(i).getQuestionType())){
	// wqp.setType("多选");
	// }
	// questionPaper.getResult().add(wqp);
	// }
	// questionPaper.setCurrentPage(page.getCurrentPage());
	// questionPaper.setPageSize(page.getPageSize());
	// questionPaper.setTotalsCount(page.getTotalsCount());
	// questionPaper.setTotalsPage(page.getTotalsPage());
	// JsonProtocol jsonProtocol = null;
	// jsonProtocol = new JsonProtocol(0, null, questionPaper);
	// jsonStr = JSON.toJSONString(jsonProtocol,
	// SerializerFeature.DisableCircularReferenceDetect);
	// } catch (Exception e) {
	// jsonStr=super.writeJson(1, "查询失败了！");
	// logger.error(e);
	// }finally{
	// try {
	// JsonUtil.jsonOut(jsonStr);
	// } catch (IOException e) {
	// logger.error(e);
	// }
	// }
	// }

	public void delQuestion() {
		String jsonStr = "";
		try {
			WritingQuestion wq = new WritingQuestion();
			wq.setId(dataarrayActionModel.getId());
			writingQuestionBiz.deleteWritingQuestion(wq);
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

	public void delQuestions() {
		String jsonStr = "";
		try {
			String ids = dataarrayActionModel.getIds().substring(0, dataarrayActionModel.getIds().length() - 1);
			writingQuestionBiz.deleteManyWritingQuestion(ids);
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

	public void findQuestion() {
		String jsonStr = "";
		try {
			WritingQuestion wq = new WritingQuestion();
			wq = writingQuestionBiz.searchWQuestion(dataarrayActionModel.getId());
			jsonStr = super.writeJson(0, wq);
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

	// 查询下一条或者上一条数据
	public void nextQuestion() {
		String jsonStr = "";
		try {
			int id = 0;
			if (dataarrayActionModel.getFalg().equals("next")) {
				id = writingQuestionBiz.getNextId(dataarrayActionModel.getId());
			} else if (dataarrayActionModel.getFalg().equals("prev")) {
				id = writingQuestionBiz.getPrevId(dataarrayActionModel.getId());
			}
			jsonStr = super.writeJson(0, id);
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

	// 查询未开考的试卷
	public void findAllWritingPaper() {
		try {
			List<WritingPaper> lists = new ArrayList<WritingPaper>();
			DataGaidModel dgm = writingPaperBiz.searchWritingPaper(1, dataarrayActionModel);
			List<WritingPaper> list = dgm.getRows();
			for (int i = 0,len=list.size(); i <len; i++) {
				WritingPaper wp = new WritingPaper();
				wp.setId(list.get(i).getId());
				wp.setPaperName(list.get(i).getPaperName());
				wp.setPaperPwd(list.get(i).getPaperPwd());
				wp.setExamDate(list.get(i).getExamDate());
				wp.setExamineeClass(list.get(i).getExamineeClass());
				lists.add(wp);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", dgm.getTotal());
			map.put("rows", dgm.getRows());
			dgm.setRows(lists);
			JsonUtil.writeJson(dgm);
		} catch (Exception e) {
			DataGaidModel dgm = new DataGaidModel();
			List<WritingPaper> list = new ArrayList<WritingPaper>();
			dgm.setRows(list);
			dgm.setTotal((long) 0);
			JsonUtil.writeJson(dgm);
			logger.error(e);
		}

	}

	// 开考试卷
	public void updatePaperStatus() {
		String jsonStr = "";
		try {
			WritingPaper wp = writingPaperBiz.findWritingPaperById(dataarrayActionModel.getWpid());
			wp.setPaperStatus(4);
			writingPaperBiz.updatePaper(wp);
			jsonStr = super.writeJson(0, "成功");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "失败");
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
	public DataarrayActionModel getModel() {
		return this.dataarrayActionModel;
	}

	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}

	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

}
