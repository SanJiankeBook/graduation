package com.yc.biz.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yc.biz.ConvertQuestionEditionBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ConvertQuestionEdition;
import com.yc.webexam.actions.QuestionAction;
@Service("convertQuestionEditionBiz")
public class ConvertQuestionEditionBizImpl implements ConvertQuestionEditionBiz {

	private BaseDao baseDao;
	private static final Logger logger = Logger.getLogger(QuestionAction.class);
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void insertConvertInfo(ConvertQuestionEdition convertQuestionEdition) {
		try {
			baseDao.add(convertQuestionEdition);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

	}
}
