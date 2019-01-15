package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.PointAnswerBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.PointAnswer;
import com.yc.po.PointPaper;
@Service("pointAnswerBiz")
public class PointAnswerBizImpl implements PointAnswerBiz {
	@Resource(name = "baseDao")
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public String findPointAnswer(String name, String classid, String pid) {
		String[] params = new String[] { pid,classid,name};
		//String hql="select answer from PointAnswer where pid=? and claddId=? and aname=?";
		String sql="select * from PointAnswer p where p.opid=:pid and p.claddid=:classid and p.aname=:name";
		//List<String> list=this.baseDao.search(hql, params);
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("pid",pid );
		map.put("classid",classid );
		map.put("name",name );
		List<PointAnswer> list=this.baseDao.findDatabyhql(sql, map,new PointAnswer(), null, null);
		
		if(list!=null&&list.size()>0){
			String a=list.get(0).getAnswer();
			return a;
		 }
		return "";
		
	}

	@Override
	public int addPointAnswer(PointAnswer pa) {
		PointAnswer p=new PointAnswer();
		p.setAnswer(pa.getAnswer());
		p.setName(pa.getName());
		p.setCladdid(pa.getCladdid());
		p.setIdea(pa.getIdea());
		p.setPointPaper(pa.getPointPaper());
		p.setRemark(pa.getRemark());
		p.setStatus(pa.getStatus());
		this.baseDao.add(p);
		return 1;
	}

	@Override
	public PointAnswer findPaperByPidAndSname(String pid, String name,int cid) {
		String[] params = new String[] { pid,name,cid+""};
		String hql="from PointAnswer pa where pa.pointPaper.pid=? and pa.aname=? and pa.claddid=?";
		List<PointAnswer> list=this.baseDao.search(hql, params);
		if(list!=null&&list.size()>0){
			 return list.get(0);
		 }
		return null;
	}

	@Override
	public int delPaperAnswer(String pid, String uname, String cid) {
		return 0;
	}

	@Override
	public List<String> getPointAnswerByPid(int pid, int cid) {
		return null;
	}

	@Override
	public List<PointAnswer> findSubjectTotal(String semester, int cid) {
		//String[] params = new String[] { cid+""};
		String hql=" select * from PointAnswer p where p.claddid=:cid";
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("cid",cid );
		// List<PointAnswer>list=this.baseDao.search(hql, params);
		 List<PointAnswer>list=this.baseDao.findDatabyhql(hql, map, new PointAnswer(), null, null);
		 if(list!=null&&list.size()>0){
			 return list;
		 }

		return null;
	}

	@Override
	public List<PointAnswer> findMessageInfo(int subjectid, int classid,
			String uname) {
		String[] params=null;
		if(uname==null||uname.equals("")){
			 params = new String[] {classid+""};
		}else{
			 params = new String[] { uname,classid+""};
		}
		
		String hql="select p from PointAnswer p  where p.aname=? and p.claddid=?";
		 List<PointAnswer>list=this.baseDao.search(hql, params);
		 if(list!=null&&list.size()>0){
			 return list;
		 }

		return null;
	}

	@Override
	public List<PointAnswer> findAnswerByPid(int opid) {
		String[] params = new String[] { opid+""};
		String hql="from PointAnswer  where pid=?";
		 List<PointAnswer> list=this.baseDao.search(hql, params);
		 if(list!=null&&list.size()>0){
			 return list;
		 }
		return null;
	}
	@Override
	public List<PointAnswer> findAnswersByPid(int pid) {
		//String[] params = new String[] { pid+""};
//		String hql="select * from PointAnswer p where p.pointPaper.pid=?";
//		 List<PointAnswer> list=this.baseDao.search(hql, params);
		String hql="select pa.* from PointAnswer pa inner join pointpaper pp on pa.pid=pp.pid where pp.pid=:pid";
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("pid",pid );
		List<PointAnswer> list=this.baseDao.findDatabyhql(hql, map,new PointAnswer(), null, null);
		 if(list!=null&&list.size()>0){
			 return list;
		 }
		return null;
	}
	@Override
	public int getStudentCount(int pid) {
		long count = 0;
		String sql="select count(*) from PointAnswer where pid=?";
		String[] params=new String[]{pid+""};
		List<Long> list=baseDao.search(sql, params);
		
		if(list!=null){
			count=list.get(0);
			return (int)count;
		}
		return 0;
	}

	@Override
	public int delPaperAnswer(PointAnswer p) {
		this.baseDao.del(p);
		return 0;
	}

	@Override
	public List<PointAnswer> findPointAnswerInfo(Integer subjectid,
			Integer classid, String stuName) {

		//System.out.println("classid:"+classid);
		String[] params={};
		if(subjectid!=-1&&classid!=-1&&(stuName!=null&&!"".equals(stuName))){
			 params = new String[] {stuName,classid+"",subjectid+"" };
		}else if(subjectid==-1&&classid!=-1&&(stuName!=null&&!"".equals(stuName))){
			 params = new String[] {stuName,classid+""};
		}
		else if(subjectid!=-1&&classid==-1&&(stuName!=null&&!"".equals(stuName))){
			 params = new String[] {stuName,subjectid+""};
		}
		else if(subjectid!=-1&&classid!=-1&&(stuName!=null||"".equals(stuName))){
			 params = new String[] {classid+"",subjectid+""};
		}
		else if(subjectid!=-1&&classid==-1&&(stuName==null||"".equals(stuName))){
			 params = new String[] {subjectid+""};
		}
		else if(subjectid==-1&&classid!=-1&&(stuName==null||"".equals(stuName))){
			 params = new String[] {classid+""};
		}
		else if(subjectid==-1&&classid==-1&&(stuName!=null&&!"".equals(stuName))){
			 params = new String[] {stuName};
		}
		else if(subjectid==-1&&classid==-1&&(stuName==null||"".equals(stuName))){
			 params = new String[] {};
		}
		//String hql="select p from PointAnswer p left join  p.examinee e where e.pk.name=? and e.pk.examineeClass.id=? and p.pointPaper.chapter.subject.id=?";
		String hql="select p from PointAnswer p where 1=1 ";
		if(stuName!=null&&!"".equals(stuName)){
			hql+="and p.aname=? ";
		}
		if(classid!=-1){
			hql+="and p.claddid=? ";
		}
		if(subjectid!=-1){
			hql+="and p.pointPaper.subject.id=?";
		}
		 List<PointAnswer>list=this.baseDao.search(hql, params);
		 if(list!=null&&list.size()>0){
			 return list;
		 }

		return null;
	}

}
