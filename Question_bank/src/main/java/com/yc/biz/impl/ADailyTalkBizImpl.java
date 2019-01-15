
package com.yc.biz.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.ADailyTalkBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ADailyTalk;
import com.yc.po.ExamineeClass;

@Service("aDailyTalkBiz")
public class ADailyTalkBizImpl implements ADailyTalkBiz {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private Map<String, Object> param = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	@Override
	public List<ADailyTalk> findADailyTalkByCid(int cid, int status) {
		String sql = "select * from ADailyTalk where cid=:cid and status=:status  order by status,speakdate desc";
		Map<String,Object> param=new HashMap<String,Object>();
		 param.put("cid", cid);
		 param.put("status", status);

		return this.baseDao.findDatabyhql(sql, param, new ADailyTalk(), null, null);
	}

	@Override
	public List<ADailyTalk> findHistoryADailyTalkByCid(int cid, int status, Integer offset, Integer length) {
		String sql = "select * from ADailyTalk where cid=:cid and status!=1  order by status,speakdate desc";
		Map<String,Object> param=new HashMap<String,Object>();
		 param.put("cid", cid);
		return this.baseDao.findDatabyhql(sql, param, new ADailyTalk(),null, null);
	}

	@Override
	public List<ADailyTalk> findADailyTalkByCidHistoryStudent(int cid, String name) {
		// String sql="select new
		// ADailyTalk(id,name,knowledge,speakdate,status,assessment,remark) from
		// ADailyTalk where examineeClass.id=:cid and name=:name and status
		// in(2,3) order by status,speakdate desc";
		String sql = "select new ADailyTalk(id,name,knowledge,speakdate,status,assessment,remark) from ADailyTalk where examineeClass.id=? and name=? and status in(2,3) order by status,speakdate desc";
		// param.put("cid", cid);
		// param.put("name", name);
		String[] params = new String[] { cid + "", name };
		List<ADailyTalk> list = this.baseDao.searchByfpc(sql, null, null, params);
		// List<ADailyTalk>
		// list=this.baseDao.findByProperty(sql,param,null,null);
		return list;
	}

	@Override
	public List<ADailyTalk> findADailyTalkByCidHistoryTeacher(int cid) {
		return null;
	}

	@Override
	public List<ADailyTalk> findADailyTalkBypages(int cid, String uname, int pageNo, int pageSize) {
		int a = (pageNo - 1) * pageSize;
		String sql = "from ADailyTalk where examineeClass.id=? and status in(1,2,3) order by status,speakdate desc";
		// Map<String, Object> param=new HashMap<String,Object>();
		// params.put("cid", Integer.parseInt(cid+""));
		String[] params = new String[] { cid + "" };
		List<ADailyTalk> list = this.baseDao.searchByfpc(sql, a, pageSize, params);
		return list;
	}

	@Override
	public List<ADailyTalk> findADailyTalkByAllToTeacher(int cid, String uname, String startDate, String endDate) {
		String sql = "from ADailyTalk where cid=:cid and name=:name and status!=1  ";
		 Map<String, Object> param=new HashMap<String,Object>();
		param.put("cid", cid);
		param.put("name", uname);
		if (startDate != null && !"".equalsIgnoreCase(startDate)) {
			sql += " and speakdate>=  :startDate"; // DATEDIFF('"+startdate+"',checkDate)>=0";
			param.put("startDate", startDate);
		}
		if (endDate != null && !"".equalsIgnoreCase(endDate)) {
			sql += " and speakdate <=：endDate"; // DATEDIFF(checkDate,'"+enddate+"')>=0";
			param.put("endDate", endDate);
		}
		sql += " order by status,speakdate desc";
		return this.baseDao.findByProperty(sql,param,null,null);
	}

	@Override
	public List<ADailyTalk> findADailyTalkByPageAll(int cid, String uname, String startDate, String endDate) {
		return null;
	}

	@Override
	public List<ADailyTalk> findADailyTalkByMePageAll(int cid, String uname, String startDate, String endDate) {
		return null;
	}

	@Override
	public List<ADailyTalk> findADailyTalkStudentName(int cid) {
		String sql = "select distinct(name) as name from ADailyTalk where status in(1,2,3) and cid=:cid";
		 Map<String, Object> param=new HashMap<String,Object>();
		param.put("cid", cid);
		return this.baseDao.findByProperty(sql, param, null, null);
	}

	@Override
	public List<ADailyTalk> findADailyTalkStudentNameToTeacher(int cid) {
		return null;
	}

	@Override
	public Serializable addADailyTalk(ADailyTalk adt) {
		Serializable result = baseDao.add(adt);
		if (result != null) {
			return result;
		} else {
			return null;
		}

	}

	@Override
	public int delADailyTalk(int id) {
		String hql = "delete ADailyTalk where id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		int result = baseDao.executeHql(hql, params);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateADailyTalkStatus(int id, String assessment, int status) {
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (assessment == null || "".equals(assessment)) {
			hql = "update ADailyTalk set status=:status , speakdate=CURRENT_DATE where id=:id";
			params.put("status", status);
			params.put("id", id);
		} else {
			hql = "update ADailyTalk set status=:status ,assessment=:assessment, speakdate=CURRENT_DATE where id=:id";
			params.put("status", status);
			params.put("id", id);
			params.put("assessment", assessment);
		}

		int result = baseDao.executeHql(hql, params);
		return result;
	}

	@Override
	public ADailyTalk findADailyTalkDetail(int id) {
		String sql = "from ADailyTalk  where id=?";
		String[] params = new String[] { id + "" };
		List<ADailyTalk> list = this.baseDao.search(sql, params);
		return list.get(0);
	}

	@Override
	public ADailyTalk findADailyTalkDetailInfo(int id) {
		return null;
	}

	@Override
	public int addADailyTalkAssess(String assessInfo, int id) {
		return 0;
	}

	@Override
	public int getCid(String examName) {
		String sql = "select a.id from ExamineeClass a  where className=?";
		String[] params = new String[] { examName };
		List<Integer> list = this.baseDao.search(sql, params);
		return list.get(0);
	}

	@Override
	public int updateUploadKnowledgeInfos(byte[] bt, int id, String fileName) {
		if (this.baseDao.updateploadKnowledgeInfo(bt, id, fileName) > 0) {
			return updateADailyTalkStatus(id, null, 3);
		}
		return 0;
	}

	public List<ADailyTalk> downloadKnowledgeInfos(int id) {
		String[] params = new String[] { id + "" };
		String sql = "select new ADailyTalk(dateinfo,remark) from ADailyTalk a where a.id=?";
		List<ADailyTalk> list = this.baseDao.search(sql, params);
		return list;
	}

	public List<ADailyTalk> findSize(int cid) {
		String sql = "select * from ADailyTalk where cid=:cid";
		 Map<String, Object> param=new HashMap<String,Object>();
		param.put("cid", cid);
		List<ADailyTalk> list = this.baseDao.findDatabyhql(sql, param,new ADailyTalk(), null, null);
		return list;
	}

	@Override
	public List<ADailyTalk> findADailyTalkBytime(int cid, String uname, String startDate, String endDate,
			String stuname, int pageNo, int pageSize) {
		int a = (pageNo - 1) * pageSize;
		String sql = "select * from ADailyTalk where cid=:cid"; // and status
		 Map<String, Object> param=new HashMap<String,Object>();															// in(1,2,3)
		param.put("cid", cid);													// order by
																	// status,speakdate
																	// desc";
		if (stuname != null) {
			sql += " and name=:name ";
			param.put("name", stuname);
		}
		if (startDate != null && !"".equalsIgnoreCase(startDate)) {
			sql += " and speakdate>= :startDate"; // DATEDIFF('"+startdate+"',checkDate)>=0";
			param.put("startDate", startDate);
		}
		if (endDate != null && !"".equalsIgnoreCase(endDate)) {
			sql += " and   speakdate <= :endDate"; // DATEDIFF(checkDate,'"+enddate+"')>=0";
			param.put("endDate", endDate);
		}
		sql += " and status in(1,2,3) order by status";
		List<ADailyTalk> list = this.baseDao.findDatabyhql(sql, param, new ADailyTalk(), pageNo+"", pageSize+"");
		return list;
	}

	@Override
	public List<ADailyTalk> findADailyTalkSize(int cid, String uname, String startDate, String endDate,
			String stuname) {
		String sql = "select * from ADailyTalk where cid=:cid"; // and status
		 Map<String, Object> param=new HashMap<String,Object>();															// in(1,2,3)
		param.put("cid", cid);												// order by
																	// status,speakdate
																	// desc";
		if (stuname != null) {
			sql += " and name=:stuname ";
			param.put("stuname", stuname);
		}
		if (startDate != null && !"".equalsIgnoreCase(startDate)) {
			sql += " and speakdate>= :startDate"; // DATEDIFF('"+startdate+"',checkDate)>=0";
			param.put("startDate", startDate);
		}
		if (endDate != null && !"".equalsIgnoreCase(endDate)) {
			sql += " and   speakdate <= :endDate"; // DATEDIFF(checkDate,'"+enddate+"')>=0";
			param.put("endDate", endDate);
		}
		sql += " and status in(1,2,3) order by status";
		List<ADailyTalk> list = this.baseDao.findDatabyhql(sql, param, new ADailyTalk(), null, null);
		return list;
	}
	
	@Override
	public List<ADailyTalk> findClassName(String className) {
		String hql="select * from ADailyTalk a where a.cid= :cid";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cid", Integer.parseInt(className));
		List<ADailyTalk> list=this.baseDao.findDatabyhql(hql, map, new ADailyTalk(), null, null);
		return list;
	}

}
