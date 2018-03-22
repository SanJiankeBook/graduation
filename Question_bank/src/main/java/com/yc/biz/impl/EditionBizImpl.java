package com.yc.biz.impl;


import java.util.ArrayList;


import java.io.Serializable;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import java.util.List;


import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;

import org.springframework.stereotype.Service;

import com.yc.biz.EditionBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Edition;
import com.yc.webexam.actions.CourseAction;

@Service("editionBiz")
public class EditionBizImpl implements EditionBiz{

	private BaseDao baseDao;

	private static final Logger logger = Logger.getLogger(CourseAction.class);
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}


	@Override
		
	public String getEditionName()
	{
		Edition edition = (Edition) baseDao.get(Edition.class, 1);
		// return edition.getEditionName();
		return edition.toString();

	}
	
	



	@Override
	public List<Edition> searchAllEdition() {
		List<Edition> editions = (List<Edition>) baseDao.search("from Edition");
		return editions;
	}


	@Override
	public void updateEdition(Edition edition) {
		baseDao.update(edition);		
	}


	@Override
	public void deleteEdition(Edition edition) {
		baseDao.del(edition);
		
	}


	@Override
	public Serializable addEdition(Edition edition) {
		Serializable result=baseDao.add(edition);
		return result;
	}



	
	


	@Override
	public List searchEdition()
	{
		String sql = "select editionName from Edition ";
		List<String> editionArr = (List<String>) baseDao.search(sql);
		if (editionArr != null && editionArr.size() > 0)
		{
			return editionArr;
		}
		else
		{
			return null;
		}
	}

	@Override
	public int getEditionId(String editionName)
	{
		int editionId = 0;
		String sql = "select id from Edition where editionName=?";
		String[] params = new String[] { editionName };
		List<Integer> list = (List<Integer>) baseDao.search(sql, params);
		if (list != null && list.size() > 0)
		{
			editionId = list.get(0);
		}
		return editionId;
	}


	@Override
	public List<Edition> getAllEdition() {
		String sql="from Edition";
		List <Edition> list=null;
		list=baseDao.search(sql);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public void updateCurrentUse(int id) {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="update Edition set currentUse=0";
		baseDao.executeHql(sql, null);
		String hql="update Edition set currentUse=1 where id=:id";
		params.put("id", id);
		baseDao.executeHql(hql, params);
	}


	@Override
	public String searchEditionName(int id) {
		String editionName="";
		String sql="select editionName from Edition where id=?";
		int[] params = new int[] { id };
	 
		List<String> list=baseDao.searchs(sql, params);
		
		if (list != null && list.size() > 0)
		{
			editionName = list.get(0);
		}
		return editionName;
		
	}

}
