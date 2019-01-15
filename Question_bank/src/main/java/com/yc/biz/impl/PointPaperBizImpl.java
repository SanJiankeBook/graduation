package com.yc.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.PointPaperBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ExamineeClass;
import com.yc.po.PointAnswer;
import com.yc.po.PointPaper;
import com.yc.po.PointPaperTemplate;
import com.yc.vo.DataGaidModel;
import com.yc.vo.Page;
import com.yc.vo.PointPaperModel;
@Service("pointPaperBiz")
public class PointPaperBizImpl implements PointPaperBiz {
	@Resource(name = "baseDao")
	private BaseDao baseDao;
	
	private String sql="";
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private Map<String, Object> param=new HashMap<String,Object>();

	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}
	@Override
	public int addPointPaper(PointPaper po) {
		 
		baseDao.add(po);
		return 0;
	}
	public void addPointPaper(PointPaperTemplate po) {
		
		baseDao.add(po);
	}

	@Override
	public DataGaidModel getAllPointPaper(PointPaperModel pm) {
		String sql="from PointPaper p where 1=1 ";
		String sqlCount="select count(*) from PointPaper p where 1=1 ";
		Map<String,Object> param=new HashMap<String,Object>();
		int page=pm.getPage();
		int rows=pm.getRows();
		int offset=(page-1)*rows;
		if(pm!=null){
			if(pm.getSid()!=null){
				sql+=" and sid="+pm.getSid();
				sqlCount+=" and sid="+pm.getSid();
			}
			if(pm.getCid()!=null){
				sql+=" and cid="+pm.getCid();
				sqlCount+=" and cid="+pm.getCid();
			}
			if(pm.getExamDate()!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				sql+=" and pdate='"+sdf.format(pm.getExamDate())+"'";
				sqlCount+=" and pdate='"+sdf.format(pm.getExamDate())+"'";
			}
			
		}
		sql+=" order by pdate desc ";
		List<PointPaper> list=baseDao.findByProperty(sql, param, offset, rows);
		List<Long> count=baseDao.search(sqlCount);
		Long total=(long) 0;
		if(count!=null&&count.size()>0){
			total=count.get(0);
		}
		DataGaidModel dgm=new DataGaidModel();
		dgm.setTotal(total);
		dgm.setRows((list!=null&&list.size()>0?list:null));
		return dgm;
	}
	@Override
	public void getAllPointPaperTemplate(Page<PointPaperTemplate> page, PointPaperTemplate wp) {
		if (wp.getSid() != null && !"".equals(wp.getSid())) {
			String[] params = new String[] { wp.getSid()+"" };
			String hqlString = "from PointPaperTemplate wp where wp.sid=? order by pdate desc";
			String queryCountHql = "select count(wp) from PointPaperTemplate wp where wp.sid=?";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else if (wp.getPid() != null && !"".equals(wp.getPid())) {
			String[] params = new String[] { wp.getPid()+"" };
			String hqlString = "from PointPaperTemplate wp where wp.pid=? order by pdate desc";
			String queryCountHql = "select count(wp) from PointPaperTemplate wp where wp.pid=?";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else {
			String hqlString = "from PointPaperTemplate wp order by pdate desc  ";
			String queryCountHql = "select count(wp) from PointPaperTemplate wp";
			baseDao.showPage(hqlString, queryCountHql, page);
		}
		
	}

	@Override
	public int updatePointPaperStatus(PointPaper po) {
		baseDao.update(po);
		return 0;
	}

	@Override
	public int delPointPaperById(PointPaper po) {
		baseDao.del(po);
		return 0;
	}

	@Override
	public List<PointPaper> findPointPaper(int sid, int cid, String date) {
		String sql="select * from PointPaper where 1=1";
		Map<String,Object> map=new HashMap<String,Object>();		
		if(sid>0){
			sql+=" and sid=:sid";
			map.put("sid",sid);
		}
		if(cid>0){
			sql+=" and cid=:cid";
			map.put("cid",cid);
		}
		
		
		if(date!=null&&!"".equals(date)&&date!="null"){
			sql+=" and pdate like '"+date+"'";
		}
		List<PointPaper> list=baseDao.findDatabyhql(sql, map,new PointPaper(), null, null);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public PointPaper findPointPaperById(Integer pid) {
		String sql="from PointPaper p where p.pid=?";
		String [] params=new String[]{pid+""};
		List<PointPaper> list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public PointPaperTemplate findPointPaperByIdTemplate(Integer pid) {
		String sql="from PointPaperTemplate p where p.pid=?";
		String [] params=new String[]{pid+""};
		List<PointPaperTemplate> list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int updatePointPaper(PointPaper po) {
		baseDao.update(po);
		return 0;
	}

	@Override
	public List<PointPaper> findOpenPointPaper(String name, String classid) {
		sql="from PointPaper pp where pp.examineeClass.id=? and pid not in(select pa.pointPaper.pid from PointAnswer pa where pa.aname=? and pa.claddid=?) and pp.pstatus=2";
		String[] params=new String[]{classid,name,classid};
		List<PointPaper> list=this.baseDao.search(sql, params);
		return list;
	}

	@Override
	public List<PointPaper> findEndPointPaper(String name, String classid) {
		int id=Integer.parseInt(classid);
		sql="from PointPaper where pid in(select pa.pointPaper.pid from PointAnswer pa where pa.aname=? and pa.claddid=?)";
		String[] params=new String[]{name,id+""};
		List<PointPaper> list=this.baseDao.search(sql, params);
		return list;
	}

	@Override
	public List<PointPaper> findPointPaperBySidAndCid(int subjectid, int classid) {
		return null;
	}

	@Override
	public int findMyPointPaper(int pid, String name, int cid) {
		return 0;
	}

	@Override
	public PointPaper getPointPaperInfo(int pid) {
		return null;
	}
	@Override
	public String getPointPaperTitleByPid(int pid) {
		sql="select pp.ptitle from PointPaper pp where pp.pid=? ";
		String[] params=new String[]{pid+""};
		List<String> list=this.baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public int findSid(int pid, int cid) {
		sql="select pp.subject.id from PointPaper pp where pid=? and pp.examineeClass.id=?";
		String[] params=new String[]{pid+"",cid+""};
		List<Integer> list=this.baseDao.search(sql, params);
		
		if(list!=null&&list.size()>0){
			int sid=list.get(0);
			return sid;
		}
		return 0;
	}
	
	
}
