package com.yc.biz.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.SubjectBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Chapter;
import com.yc.po.Edition;
import com.yc.po.PointPaper;
import com.yc.po.Subject;
import com.yc.po.XSubject;
import com.yc.vo.SubjectPage;
@Service("subjectBiz")
public class SubjectBizImpl implements SubjectBiz {

	
	private BaseDao baseDao;
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}
	
	
	@Override
	public String findSubjectName(int pid) {
		String sql="select subjectName from XSubject where id=?";
		String[] params = new String[]{pid+""};
		List<String> list=baseDao.search(sql, params);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public List<Subject> findSubjectByEndAnswer(int cid, String sid) {
		return null;
	}

	@Override
	public List searchSubject(String semester) {
		return null;
	}

	@Override
	public int updateSubject(Subject subject) {
		try {
			baseDao.update(subject);
			return 1;
		} catch (Exception e) {
			return 0;
		}
		
		
	}

	@Override
	public int addSubject(Subject subject) {
		Serializable result=baseDao.add(subject);
		return (Integer) result;
	}

	@Override
	public List searchSubject(String semester, int editionId) {
		return null;
	}

	@Override
	public List<Subject> findSubject(String semester, int editionId) {
		List<Subject> list = new ArrayList();
		String sql="from Subject where semester=? and editionId=?";
		String[] params = new String[] { semester, editionId + "" };
		list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public int getSubjectId(String subjectName) {
		String hql=" select *  from Subject  sb  where  sb.subjectName=:subjectName ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("subjectName", subjectName);
		List<XSubject> subject=baseDao.findDatabyhql(hql, map, new XSubject(), null, null);
		return subject.get(0).getId();
	}

	@Override
	public int getChapterCount(String subjectName) {
		
		return 0;
	}

	@Override
	public int getSubjectCount(int editionId) {
		return 0;
	}

	public List<XSubject> getSubjects() {
		String hql="from XSubject ";
		List<XSubject> subject=baseDao.search(hql);
		return subject;
	}

	@Override
	public int deleteSubject(int subjectId) {
		
		Subject subject=new Subject();
		subject.setId(subjectId);
		try {
			baseDao.del(subject);
			return 1;
		} catch (Exception e) {
			return 0;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override    //select s.id,s.subjectName,s.chapterCount,s.semester,e.id ,e.editionName  from Subject s,Edition e where e.id=s.editionId 
	public List<Subject> getAllSubject(SubjectPage subjectPage,Integer page, Integer rows) {
		List<Subject> subject=null;
		String sql="select  s.id,ed.editionName,s.editionId,s.semester,s.subjectName,s.chapterCount,s.seq from subject s right join edition ed on s.editionId=ed.id";
		//String hql="from Subject s";
		Map<String,Object> param=new HashMap<String,Object>();
		if(subjectPage!=null){
			//hql+="  where  ";
			sql+="  where  ";
			
			if(subjectPage.getEditionName()!=null && !"".equals(subjectPage.getEditionName().trim())){
				//hql+=" s.edition.editionName like :editionName and ";
				sql+=" ed.editionName like '%"+subjectPage.getEditionName()+"%' and ";
				//param.put("editionName", "%"+subjectPage.getEditionName().trim()+"%");
			}
			if(subjectPage.getSemester()!=null && !"".equals(subjectPage.getSemester().trim())){
				//hql+="  s.semester like :semester  and ";
				sql+="  s.semester like '%"+subjectPage.getSemester().trim()+"%'  and ";
				//param.put("semester", "%"+subjectPage.getSemester().trim()+"%");
			}
			//hql+=" 1=1";
			sql+=" 1=1";
		}
		
		if(page==null  ||  rows==null){
			subject =  baseDao.searchByPro(sql, null, null,param,new Subject());
			//subject = (List<Subject>) baseDao.searchByPro(hql, null, null,param);
		}else{
		//分页
		int startPage=(page-1)*rows;
		sql+=" limit  "+startPage+","+rows;
		
		subject= baseDao.searchByPro(sql, startPage, rows,param,new Subject());
			//subject = (List<Subject>) baseDao.searchByPro(hql, startPage, rows,param);
		}
		
		return subject;
	}

	@Override
	public int updateChapterCount(int chapterCount, int id) {
		String sql = "update Subject set chapterCount=:chapterCount where id=:id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("chapterCount", chapterCount);
		params.put("id", id);
		try {
			baseDao.executeHql(sql, params);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override

	public XSubject findSubjectById(int subjectId)
	{
		int i=0;
		String sql="from XSubject where id=?";
		String[] params =new String[]{subjectId+""};
		List<XSubject> list=baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Subject> getSubjectTotal(String semester, int cid) {
		return null;
	}


	@Override
	public int getAllSubjectCount() {
		String hql = "select count(*)  from Subject ";
		
		int count=baseDao.searchCount(hql);
		return count;
	}


	@Override
	public List<String> getSubjectNamesBySemesterAndClassName(String semester,
			String className) {
		String[] params = new String[] { className,semester };
		String hql="select distinct p.subject.id ,p.subject.subjectName from  PointPaper p  where p.examineeClass.className=? and p.examineeClass.semester=?";
		List<String> list=this.baseDao.search(hql, params);
		if(list!=null&&list.size()>0){
		return list;
		}
		return null;
	}


	@Override
	public List<PointPaper> getPointPaperByIdAndClassName(Integer subjectId,
			String className) {
		String[] params = new String[] { className }; //,subjectId+""
		//String hql="select * from  PointPaper p where p.examineeClass.className='"+className+"' and p.subject.id="+subjectId;
		String sql="select p.* from ExamineeClass ec inner join  pointpaper p on p.cid=ec.Id inner join subject s on p.sid=s.Id where ec.className = :className  ORDER BY pdate DESC ";//
		//List<PointPaper> list=this.baseDao.search(hql, params);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("className", className);
		List<PointPaper> list=this.baseDao.findDatabyhql(sql, map,new PointPaper(), null, null);
		if(list!=null&&list.size()>0){
			return list;
			}
		return null;
	}


	@Override
	public List<String> findSubjectNameBySemester(String semester) {
		String[] params = new String[] {semester };
		String hql="select s.id,s.subjectName from  Subject s where s.semester=?";
		List<String> list=this.baseDao.search(hql, params);
		if(list!=null&&list.size()>0){
			return list;
			}
		return null;
	}


	@Override
	public List<Subject> getSubjectsByedit(String id) {
		String hql="select * from Subject where editionId= :id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		List<Subject> subject=baseDao.findDatabyhql(hql, map,new Subject(), null, null);
		return subject;
	}

}
