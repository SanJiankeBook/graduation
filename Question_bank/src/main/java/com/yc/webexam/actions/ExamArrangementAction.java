package com.yc.webexam.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.po.WritingPaper;
import com.yc.utils.ExamUtil;
import com.yc.utils.JsonUtil;

public class ExamArrangementAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ExamArrangementAction.class);

	@Resource(name = "writingPaperBiz")
	private WritingPaperBiz writingPaperBiz;
	@Resource(name = "labPaperBiz")
	private LabPaperBiz labPaperBiz;
	// private HttpServletRequest request;
	// private HttpServletResponse response;
	private List arr = new ArrayList<WritingPaper>();

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public void examType() throws IOException {
		String paperType = type;
		String jsonStr = "";
		try {
			String strClass = (String) ActionContext.getContext().getSession().get("examClass");
			// 当请求的笔试卷时
			if (paperType == null || paperType.equals("writ")) {
				paperType = "writ";
				// 找已经安排了考试的试卷和未评的试卷，只显示这两种
				
				// 如果找不到，则找未评的试卷（避免后来的同学还没考，已经有人交了卷的时候，因为试卷状态发生了变化，所以找不到试卷了）
				arr = writingPaperBiz.searchWritingPaper(ExamUtil.PAPER_STATUS_IS_TESTING, ExamUtil.PAPER_STATUS_NOT_VIEW,
						strClass);
			}
			// 当请求的是机试卷时
			else if (paperType.equals("lab")) {
				// 这里没有改，后面要改
				arr = labPaperBiz.searchLabPaper(ExamUtil.PAPER_STATUS_IS_TESTING, strClass);
			}
			// 把查询到的信息存入请求对象中，再转向到页面
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			map.put("arr", arr);
			map.put("paperType", paperType);
			list.add(map);
			jsonStr=super.writeJson(0, list);
			
			//JsonUtil.jsonOut(writeJson(0, list));
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "失败");
			e.printStackTrace();
			logger.error(e);
		}finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

	public void setLabPaperBiz(LabPaperBiz labPaperBiz) {
		this.labPaperBiz = labPaperBiz;
	}

}
