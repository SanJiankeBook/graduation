package com.yc.biz.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yc.biz.ChapterBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Chapter;
import com.yc.po.Subject;
import com.yc.vo.ChapterPage;

@Service("chapterBiz")
public class ChapterBizImpl implements ChapterBiz {
	@Resource(name = "baseDao")
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public List<Chapter> getEightChapter(Integer id,Integer classID,Integer num) {
		String sql="select c.id,c.chapterName ,c.remark, c.subjectId,c.seq from Chapter c inner join subject s on c.subjectId=s.id  where s.editionId in (select editionId from examineeclass where id="+classID+" ) and c.seq>"+id+" ORDER BY c.seq limit 0, "+num+"";
		List<Chapter> list = this.baseDao.excuteSelectSql(sql, new Chapter());
		//List<Chapter> list = this.baseDao.excuteSelectSqlWithoutBean(sql);
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public List<Chapter> getChapterList(ChapterPage chapterPage,Integer page, Integer rows) {
		List<Chapter> chapter = null;
		String hql="from Chapter c";
		//String[] params = new String[]{};
	//	Map<String,Object> param=new HashMap<String,Object>();
		if(chapterPage!=null){
			hql+="  where  ";
			if(chapterPage.getChapterName()!=null && !"".equals(chapterPage.getChapterName().trim())){
				hql+="  c.chapterName like '%"+chapterPage.getChapterName().trim()+"%'   and";
				//param.put("chapterName", "%"+chapterPage.getChapterName().trim()+"%");
			}
			
			if(chapterPage.getEditionName()!=null && !"".equals(chapterPage.getEditionName().trim())){
				hql+=" c.subject.edition.editionName like '%"+chapterPage.getEditionName().trim()+"%' and ";
				//param.put("editionName", "%"+chapterPage.getEditionName().trim()+"%");
			}
			if(chapterPage.getSemester()!=null && !"".equals(chapterPage.getSemester().trim())){
				hql+="  c.subject.semester like '%"+chapterPage.getSemester().trim()+"%'  and ";
				//param.put("semester", "%"+chapterPage.getSemester().trim()+"%");
			}
			
			if(chapterPage.getSubjectName()!=null && !"".equals(chapterPage.getSubjectName().trim())){
				hql+=" c.subject.subjectName like '%"+chapterPage.getSubjectName().trim()+"%' and ";
				//param.put("subjectName", "%"+chapterPage.getSubjectName().trim()+"%");
			}
			hql+=" 1=1 order by  seq";
		}
		
		if(page==null  ||  rows==null){
			chapter = (List<Chapter>) baseDao.searchByfpc(hql, null, null);
		}else{
		
		int startPage=(page-1)*rows;
			chapter = (List<Chapter>) baseDao.searchByfpc(hql, startPage, rows);
		}
		return chapter;
	}

	@Override
	public int getAllChapterCount(ChapterPage chapterPage) {
		String hql = "select count(*)  from Chapter c";

		int count = baseDao.searchCount(hql);
		return count;
	}

	@Override
	public List<String> searchChapter(int subjectId) {
		List<String> list = new ArrayList<String>();
		String sql = "select chapterName from Chapter where subjectId=?";
		String[] params = new String[] { subjectId + "" };
		list = (List<String>) baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public int getChapterId(int subjectId, String chapterName) {
		Number chapterID = 0;
		String sql = "select id from Chapter where subjectId =? and chapterName =?";
		String[] params = new String[] { subjectId + "", chapterName };
		List<Number> list = (List<Number>) baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			chapterID = list.get(0);
		}
		return (Integer) chapterID;
	}

	@Override
	public String getChapterName(int chapterId) {
		String sql = "select pcontent from PointInfo where pid=?";
		String[] params = new String[] { chapterId + "" };
		List<String> list = baseDao.search(sql, params);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int getQuestionAmount(int chapterId) {
		long count = 0;
		String sql = "select count(*) from WritingQuestion where chapterId=?";
		String[] params = new String[] { chapterId + "" };
		List<Long> list = (List<Long>) baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			count = list.get(0);
		}
		return (int) count;
	}

	@Override
	public int getChapterCount(int subjectId) {
		String sql = "select count(*)  from Chapter c where c.subject.id= "
				+ subjectId;
		List<Long> list = baseDao.search(sql);
		long result = 0;
		if (list != null && list.size() > 0) {

			result = list.get(0);
			return (int) result;
		} else {
			return 0;
		}

	}

	@Override
	public int updateChapter(Chapter chapter) {
		try {
			baseDao.update(chapter);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int deleteChapter(int id) {
		String sql = "delete from Chapter where id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
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
	public int addChapter(Chapter chapter) {
		Serializable result = baseDao.add(chapter);
		return (Integer) result;
	}

	@Override
	public int getSubjectId(int id) {
		return 0;
	}

	@Override
	public Chapter findChapterById(int chapterId) {
		String sql = "from Chapter where id= " + chapterId;
		List<Chapter> chapter = baseDao.search(sql);
		return chapter.get(0);
	}

	@Override
	public List<Chapter> findChapter(int subjectId) {
		String sql = "from Chapter where subjectId=?";
		String[] params = new String[] { subjectId + "" };
		List<Chapter> list = baseDao.search(sql, params);
		return list != null && list.size() > 0 ? list : null;
	}
	
	 
	public List<Chapter> getAllChapter1(Integer classId) {
		String sql="select c.id ,c.chapterName,c.remark,c.seq,c.subjectId  from Chapter  c join Subject s where c.subjectId=s.id  and s.editionId in(select editionId from examineeclass where id="+classId+") order by c.seq";
		List<Chapter> list = this.baseDao.excuteSelectSql(sql,new Chapter());
		return list != null && list.size() > 0 ? list : null;
	}

}
