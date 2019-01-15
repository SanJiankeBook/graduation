package com.yc.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.LabQuestionBiz;
import com.yc.dao.BaseDao;
import com.yc.po.LabQuestion;
@Service("labQuestionBiz")
public class LabQuestionBizImpl implements LabQuestionBiz {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public int addLabQuestion(LabQuestion lq) {
		return 0;
	}

	@Override
	public int getNextIdentity() {
		return 0;
	}

	@Override
	public int updateLabQuestion(LabQuestion lq) {
		return 0;
	}

	@Override
	public int deleteLabQuestion(LabQuestion lq) {
		return 0;
	}

	@Override
	public List searchLabQuestion(LabQuestion lq) {
		return null;
	}

	@Override
	public boolean isExistQuestionId(int skillCodeId, int questionId) {
		boolean flag = false;
		String sql="select semester from LabQuestion where Id=? and skillCodeId=?";
		String[] params=new String[]{questionId+"",skillCodeId+""};
		List list= baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public List searchLabQuestion(LabQuestion lq, int displayRows, int nextNum) {
		return null;
	}

	@Override
	public int getSearchMaxRow(LabQuestion lq) {
		return 0;
	}

	@Override
	public List searchQuestion(int questionId) {
		return null;
	}

	@Override
	public LabQuestion getLabQuestion(int id) {
		return null;
	}

}
