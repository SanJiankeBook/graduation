package com.yc.task.impl;

import java.util.List;


import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yc.biz.WritingPaperBiz;
import com.yc.dao.BaseDao;
import com.yc.po.WritingPaper;
import com.yc.task.examTask;
import com.yc.utils.ExamUtil;
import com.yc.utils.JsonUtil;
import com.yc.webexam.actions.ExamAction;

@Service
public class ExamTaskImpl implements examTask {

	private static final Logger logger = Logger.getLogger(ExamTaskImpl.class);
	
	private BaseDao baseDao;
	private WritingPaperBiz writingPaperBiz;
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	
	@Resource(name = "writingPaperBiz")
	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}
	
	//将今天以前的试卷  的考试状态和未评状态的变为以评状态
	@Scheduled(cron= "0 30 22 * * ?")   //每天晚上十点半执行一次   

	@Transactional
	@Override
	public void examtask() {
		
		List<WritingPaper> list=this.writingPaperBiz.searchTodayPaper();
		int n =0;
		if(list.size()>0){
			for(WritingPaper li:list){
				 n = writingPaperBiz.updateWritingPaper(li.getId(),ExamUtil.PAPER_STATUS_VIEWED);
			}
		}
		
		//System.out.println(n);
		if (n == 1) {
			logger.info("将今天以前的试卷  的考试状态和未评状态的变为已评状态");
		}

	}

}
