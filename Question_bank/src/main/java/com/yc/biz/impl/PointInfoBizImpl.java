package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.PointInfoBiz;
import com.yc.dao.BaseDao;
import com.yc.po.PointInfo;
import com.yc.vo.DataGaidModel;
import com.yc.vo.PointInfoModel;
@Service("pointInfoBiz")
public class PointInfoBizImpl implements PointInfoBiz {
	@Resource(name = "baseDao")
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}
	@Override
	public List<PointInfo> getPointInfo(int cid) {
		String sql="from PointInfo where status=1 and cid=? order by pid desc";
		String[] params = new String[]{cid+""};
		List<PointInfo> list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	@Override
	public DataGaidModel getPointInfo(PointInfoModel pm) {
		String sql="from PointInfo where status=1 ";
		String sqlCount="select count(cid) from PointInfo where status=1 ";
		int page=pm.getPage();
		int rows=pm.getRows();
		int offset=(page-1)*rows;
		if(pm!=null){
			if(pm.getCid()!=null){
				sql+=" and cid="+pm.getCid();
				sqlCount+="and cid="+pm.getCid();
			}
			if(pm.getPid()!=null){
				sql+=" and pid="+pm.getPid();
				sqlCount+=" and pid="+pm.getPid();
			}
		}
		List<PointInfo> list=new ArrayList<PointInfo>();
		list=baseDao.findByProperty(sql, null, offset, rows);
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
	public List<PointInfo> findPointInfo(String cid) {
		List<Integer> list=new ArrayList<Integer>();
		String[] a=cid.split(",");
		for(int i=0,len=a.length;i<len;i++){
			list.add(Integer.parseInt(a[i]));
		}
		String hql="select new PointInfo(pid,pcontent,status,remark,flag) from PointInfo pi where pi.pid in (:cid) order by pid desc";
		List<PointInfo> listPointInfo=this.baseDao.searchByList(hql, list, "cid");
		return listPointInfo;
	}

	@Override
	public int delPointInfoById(PointInfo p) {
		baseDao.del(p);
		return 0;
	}

	@Override
	public int updatePointInfo(PointInfo p) {
		baseDao.update(p);
		return 0;
	}

	@Override
	public int updatePointInfoById(String cid, String pcontent, String pid) {
		return 0;
	}

	@Override
	public int addPointInfo(PointInfo p) {
		baseDao.add(p);
		return 0;
	}

	@Override
	public String findPointInfoById(int pid) {
		String sql=" select pcontent from PointInfo where pid=?";
		String[] params = new String[]{pid+""};
		List<String> list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PointInfo findPointAllInfoById(int pid) {
		return null;
	}
	@Override
	public List<PointInfo> findPointAllInfoByPids(List<Integer> pids) {
		String hql="from PointInfo pi where pi.pid in (:pids) order by pid desc";
		List<PointInfo> listPointInfo=baseDao.searchByList(hql, pids,"pids");
		return listPointInfo;
	}

}
