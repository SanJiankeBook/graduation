package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.LabQuestionTypeBiz;
import com.yc.dao.BaseDao;
@Service("labQuestionTypeBiz")
public class LabQuestionTypeBizImpl implements LabQuestionTypeBiz {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public String getSkillCode(int id) {
		return null;
	}

	@Override
	public int getSkillCodeId(String skillCode) {
		int id = 0;
		String sql="select id from LabQuestionType where skillCode = ?";
		String[] params=new String[]{skillCode};
		List<Integer> list=(List<Integer>) baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			id=list.get(0);
		}
		return id;
	}

	@Override
	public List getSkillbySemester(String semester) {
		List list=new ArrayList();
		String sql="select skillCode from LabQuestionType where semester = ?";
		String[] params=new String[]{semester};
		list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

}
