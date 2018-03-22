package com.yc.biz.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.JobBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Job;



@Service("jobBiz")
public class JobBizImpl implements JobBiz{

	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public List<Job> findJob(String year) {
		String sql="select * from Job where entrydate=:entrydate ";
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("entrydate", year);
		
		return this.baseDao.findDatabyhql(sql, map, new Job(), null, null);
		
	}

	@Override
	public List<Job> findJobByeid(Integer eid) {
		String sql="select * from Job where eid=:eid ORDER BY entrydate desc";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("eid", eid);
		
		return this.baseDao.findDatabyhql(sql, map, new Job(), "1", "10");
	}

	@Override
	public List<Job> findStudentStar() {
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		String sql="select distinct j.examineeid,j.examineename,j.id,j.salary,j.eid,j.entrydate,e.ename as province from job j inner join enterprise e on j.eid=e.eid  where entrydate in ( ";
		for(int i=0;i<4;i++){
			sql+=""+(year-i)+",";
		}
		sql=sql.substring(0, sql.length()-1);
		sql+=" ) ORDER BY salary desc";
		
		return this.baseDao.findDatabyhql(sql, null, new Job(), "1", "10");
	}

	@Override
	public List<Job> findJobPool() {
		String sql="select province,eid,entrydate,examineeid,examineename,count(id) as id ,sum(salary)  as salary  from job  group by province";
		return this.baseDao.findDatabyhql(sql, null, new Job(), null, null);
	}

	@Override
	public List<Job> findSalaryByYear(int year) {
		String sql="select distinct j.examineeid,j.examineename,j.id,j.salary,j.eid,j.entrydate,e.ename as province from job j inner join enterprise e on j.eid=e.eid where entrydate =:year  ORDER BY salary desc";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year", year);
		return this.baseDao.findDatabyhql(sql, map, new Job(), "1", "10");
	}

	@Override
	public List<Job> findSalaryByProvince(String province,String page ,String row) {
		String sql=" select * from job where province =:province ORDER BY salary desc";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("province", province);
		return this.baseDao.findDatabyhql(sql, map, new Job(), page, row);
	}

	@Override
	public int findSalaryByProvincetotal(String province) {
		String sql="select count(0) from  job where province=:province";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("province", province);
		return this.baseDao.findcount(sql, map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
