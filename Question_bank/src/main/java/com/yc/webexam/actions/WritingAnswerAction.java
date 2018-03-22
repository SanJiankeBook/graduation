package com.yc.webexam.actions;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yc.biz.WritingAnswerBiz;
import com.yc.po.WritingAnswer;
import com.yc.utils.JsonProtocol;
import com.yc.utils.JsonUtil;

public class WritingAnswerAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WritingAnswerAction.class);
	private String wpid;
	@Resource(name = "writingAnswerBiz")
	private WritingAnswerBiz writingAnswerBiz;

	public void setWritingAnswerBiz(WritingAnswerBiz writingAnswerBiz) {
		this.writingAnswerBiz = writingAnswerBiz;
	}

	/**
	 * 查询考试的人数
	 * 
	 * @author fanhaohao
	 */
	public void getCountOfExamPerson() {
		String jsonStr = "";
		try {
			List<WritingAnswer> list = writingAnswerBiz.searchExamineeInfoByPid(wpid);
			if (list != null && list.size() > 0) {
				for (WritingAnswer w : list) {
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
			}

			jsonStr = JSON.toJSONString(new JsonProtocol(0, null, list),
					SerializerFeature.DisableCircularReferenceDetect);
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

	public String getWpid() {
		return wpid;
	}

	public void setWpid(String wpid) {
		this.wpid = wpid;
	}

}
