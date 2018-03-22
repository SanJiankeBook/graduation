package com.yc.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.ChangeClassBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ChangeClass;

@Service("changeClassBiz")
public class ChangeClassBizImpl implements ChangeClassBiz {

	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public void insertChangeClass(ChangeClass changeClass) {
		
		this.baseDao.saveOrUpdate(changeClass);
		
	}

}
