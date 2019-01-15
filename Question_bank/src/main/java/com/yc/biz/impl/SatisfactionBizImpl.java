package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.SatisfactionBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Satisfaction;

@Service("satisfactionBiz")
public class SatisfactionBizImpl implements SatisfactionBiz {
	
	private BaseDao baseDao;

	
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	//插入满意度调查表
	@Override
	public Integer insertSatisfaction(Satisfaction satisfaction) {
		/*String sql="insert into satisfaction (className,teacherid,teacherName,startTime,endTime,openPersonId,openPersonName,openYear,openMonth) values('"+
				satisfaction.getClassName()+"',"+satisfaction.getTeacherid()+",'"+satisfaction.getTeacherName()+"','"+satisfaction.getStartTime()+"','"+satisfaction.getEndTime()+"',"+
				satisfaction.getOpenPersonId()+",'"+satisfaction.getOpenPersonName()+"','"+satisfaction.getOpenYear()+"','"+satisfaction.getOpenMonth()+"')";
		*/
		/*String sql="insert into Satisfaction (className,teacherid,teacherName,startTime,endTime,openPersonId,openPersonName,openYear,openMonth) values(?,?,?,?,?,?,?,?,?)";
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("className", satisfaction.getClassName());
		param.put("teacherid", satisfaction.getTeacherid());
		param.put("teacherName", satisfaction.getTeacherName());
		param.put("startTime", satisfaction.getStartTime());
		param.put("endTime", satisfaction.getEndTime());
		param.put("openPersonId", satisfaction.getOpenPersonId());
		param.put("openPersonName", satisfaction.getOpenPersonName());
		param.put("openYear", satisfaction.getOpenYear());
		param.put("openMonth", satisfaction.getOpenMonth());*/
		return (Integer) this.baseDao.add(satisfaction);
	}


	//显示老师的满意度调查表
	@Override
	public List<Satisfaction> showTeacherSatisfaction(Satisfaction satisfaction) {

		String sql="select * from Satisfaction where openYear=:openYear  and openMonth=:openMonth ";
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("openYear", satisfaction.getOpenYear());
		param.put("openMonth", satisfaction.getOpenMonth());
		if(!satisfaction.getTeacherName().equals("--请选择--")){
			sql+="and teacherName=:teacherName";
			param.put("teacherName", satisfaction.getTeacherName());
		}
			
		List<Satisfaction> list=this.baseDao.findDatabyhql(sql, param, new Satisfaction(), null, null);
		
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();
	}
	
	//显示班级满意度调查表
	@Override
	public List<Satisfaction> showClassSatisfaction(Satisfaction satisfaction) {

		String sql="select * from Satisfaction where openYear=:openYear  and openMonth=:openMonth ";
			
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("openYear", satisfaction.getOpenYear());
		param.put("openMonth", satisfaction.getOpenMonth());
		if(!satisfaction.getClassName().equals("--请选择--")){
			sql+="and className=:className";
			param.put("className", satisfaction.getClassName());
		}
		
		List<Satisfaction> list=this.baseDao.findDatabyhql(sql, param,new Satisfaction(), null, null);
		
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();
	}


	//判断是否开启满意度调查表
	@Override
	public List<Satisfaction> isOpenSatisfaction(String time,String userid) {
	
		String year=time.substring(0,4);
		Integer id=Integer.parseInt(userid);
		String sql="select * from Satisfaction where classname=(select className from ExamineeClass where id=( select classId from Examinee where id=:id) ) and  openYear=:year and :time between startTime and endTime ";
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("id", id);
		param.put("year", year);
		param.put("time", time);
		List<Satisfaction> list=this.baseDao.findDatabyhql(sql, param,new Satisfaction(),  null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		
		return Collections.emptyList();
	}


	//获取满意度调查表的汇总
	@Override
	public List<Satisfaction> findSummaySatisInfo(String year,String month) {
		String sql="select * from Satisfaction where openMonth=:month and openYear=:year ";
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("month", month);
		param.put("year", year);
		List<Satisfaction> list=this.baseDao.findDatabyhql(sql, param,new Satisfaction(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();
		
	}
	
	
	//获取满意度调查表的汇总的细节
		@Override
		public Satisfaction findSummaySatisInfoDetail(String year,String month,Integer id) {
			String sql="select * from Satisfaction where openMonth=:month and openYear=:year and id=:id  ";
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("month", month);
			param.put("year", year);
			param.put("id", id);
			
			List<Satisfaction> list=this.baseDao.findDatabyhql(sql, param,new Satisfaction(), null, null);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return  null;
			
		}
	
}
