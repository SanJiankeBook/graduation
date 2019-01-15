package com.yc.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.EnterpriseBiz;
import com.yc.dao.BaseDao;
import com.yc.po.EnterprisePage;
import com.yc.po.Job;



@Service("enterpriseBiz")
public class EnterpriseBizImpl implements EnterpriseBiz{
	
	
	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<EnterprisePage> findEnterprise(String addr,String name) {
		String sql="select * from enterprise where 1=1 ";
		Map<String,Object> map=new HashMap<String,Object>();
		if(addr!=null||!"".equals(addr)){
			sql+=" and address like :address";
			map.put("address", "%"+addr+"%");
		}
		if(name!=null&&!"".equals(name)){
			sql+=" and ename like :name";
			map.put("name", "%"+name+"%");
		}
		return this.baseDao.findDatabyhql(sql, map, new EnterprisePage(), null, null);
	}

	@Override
	public int findstudentcount(int eid) {
		String sql="select count(0) from job where eid=:eid";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("eid",eid);
		return this.baseDao.findcount(sql, map);
	}

	@Override
	public EnterprisePage findEnterprisebyeid(int eid) {
		String sql="select * from enterprise where eid=:eid";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("eid",eid);
		return (EnterprisePage) this.baseDao.findDatabyhql(sql, map, new EnterprisePage(), null, null).get(0);
	}

	@Override
	public List<Job> findStudentSetail(int eid) {
		String sql="select * from job where eid=:eid";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("eid",eid);
		return  this.baseDao.findDatabyhql(sql, map, new Job(), null, null);
	}

}
