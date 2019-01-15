package com.yc.biz.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.SatisfactionDetailBiz;
import com.yc.dao.BaseDao;
import com.yc.po.SatisfactionDetail;

@Service("satisfactionDetailBiz")
public class SatisfactionDetailBizImpl implements SatisfactionDetailBiz {

	private BaseDao baseDao;
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	//插入满意度细节调查表
	@Override
	public Integer insertSatisfaction(SatisfactionDetail satisfactionDetail) {
		int result=0;
		
		try {
			
			this.baseDao.saveOrUpdate(satisfactionDetail);
			result=1;
		} catch (Exception e) {
			result=0;
			e.printStackTrace();
		}
		
		return result;
	}

	//根据id显示满意度细节表
	@Override
	public List<SatisfactionDetail> showSatisfactionDetail(Integer said) {
		String sql="from SatisfactionDetail where said=? ";
		Map<String,Object> map=new HashMap<String,Object>();
		String[] str=new String[]{said+""};
		List<SatisfactionDetail> list=this.baseDao.search(sql, str);
		return list;
	}

	//根据ID显示每个满意度调查表的细节表的总数
	@Override
	public Integer satisDetailCount(Integer said) {
		String sql="select count(*)  from SatisfactionDetail where said=:said ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("said", said);
		Integer result=this.baseDao.findcount(sql, map);
		return result;
	}
	

	//判断是否已经填写过了满意度调查表
	@Override
	public List<SatisfactionDetail> isWriteSatisfactionDetail(String userid ,Integer said) {
		Integer id=Integer.parseInt(userid);
		String sql="select id from SatisfactionDetail where studentid= :id and  said=:said ";
		//List<SatisfactionDetail> list=this.baseDao.excuteSelectSql(sql, new SatisfactionDetail());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("said", said);
		System.out.println(map);
		List list=this.baseDao.findDatabyhql(sql, map,new SatisfactionDetail(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();
		
	}

}
