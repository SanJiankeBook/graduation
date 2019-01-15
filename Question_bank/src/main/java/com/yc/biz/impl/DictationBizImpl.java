package com.yc.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.DictationBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Dictation;
@Service("dictationBiz")
public class DictationBizImpl implements DictationBiz{

	@Resource(name="baseDao")
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public int addDictation(Dictation dictation) {
		return (int) this.baseDao.add(dictation);
	}

}
