package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.CurriculumBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Chapter;
import com.yc.po.ClassRoom;
import com.yc.po.Curriculum;
import com.yc.po.TeacherCurriModel;

@Service("curriculumBiz")
public class CurriculumBizImpl implements CurriculumBiz {

	@Resource(name = "baseDao")
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Curriculum> getCurriculum(Integer cid, String date, String timeperiods, Integer num) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select c.id ,c.chapterid,c.classid ,c.classroomid,c.date,c.teacherid,c.timeperiods,cp.chapterName  from Curriculum c left join chapter cp on c.chapterId=cp.id where c.classid=:cid and c.chapterid > 0";
		 map.put("cid", cid);
		if (date != null && timeperiods!=null){
			sql += " and  DATE_FORMAT( concat_ws(' ',c.date,substring_index(c.timeperiods,'-', 1)), '%Y-%m-%d %k:%i')<= DATE_FORMAT( concat_ws(' ',:date,substring_index(:timeperiods,'-', 1)), '%Y-%m-%d %k:%i') ";
			map.put("date", date);
			map.put("timeperiods", timeperiods);
		}
		sql += "   order by  DATE_FORMAT( c.date, '%Y-%m-%d') DESC,substring_index(c.timeperiods, ':', 1)+0 DESC   limit "
				+ num + "";
		List<Curriculum> list = this.baseDao.findDatabyhql(sql, map, new Curriculum(), null, null);
		for (int i = 0; i < list.size()-1; i++) {//去除重复将两小节课只取其中一节
            for (int j = list.size()-1; j > i; j--) {
                if (String.valueOf(list.get(j).getChapterid()).equals(String.valueOf(list.get(i).getChapterid()))) {
                	list.remove(j);
                }
            }
        } 
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/*
	 * 批量插入课程信息
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yc.biz.CurriculumBiz#insertIntoCurriculum(java.util.List)
	 */
	@Override
	public Integer insertIntoCurriculum(List<Curriculum> list) {
		String sql = "INSERT INTO Curriculum( chapterid, classid, classroomid, date, teacherid, timeperiods) values";
		Map<String, Object> params = new HashMap<String, Object>();
		if (list != null && list.size() > 0) {
			for (Curriculum c : list) {
				sql += "(" + c.getChapterid() + ",";
				sql += c.getClassid() + ",";
				sql += c.getClassroomid() + ",";
				sql += "'" + c.getDate() + "',";
				sql += c.getTeacherid() + ",";
				sql += "'" + c.getTimeperiods() + "'),";
			}
		}
		sql = sql.substring(0, sql.length() - 1);
		try {
			this.baseDao.executeSql(sql, params);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * //status: 0 老师 1 班级 2 教室 获取所有的正常的课程信息
	 */
	@Override
	public List<String> getCurriInfo(String startDate, String endDate, String id, Integer status) {
		Integer ids = Integer.parseInt(id); // ifnull(su.username,'')函数，如果su.username不为null，那么就返回su.username,否则返回''
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select curri.id, curri.date,classname,curri.timeperiods,curri.chapterid, "
				+ " CASE  chapterid WHEN -2 THEN '自习' when -1 then '放假' when -3 then '复习'  "
				+ " when -4 then '补课' when -5 then '就业指导' when -6 then '测试' ELSE ifnull(ch.chapterName,'') END  as chaptername, "
				+ "CASE  chapterid WHEN -2 THEN '#FFFFCD' when -1 then '#FCFCFC'  when -4 then '#FCFCFC' when -6 then '#B3BDCD' ELSE ifnull(su.bgcolor,'') END  as bgcolor, CASE  chapterid  WHEN -2 THEN '#000000' when -1 then '#FE4044'  when -4 then '#000000' when -6 then '#000000' ELSE ifnull(su.fontcolor, '') END  as fontcolor,"
				+ " CASE  curri.teacherid WHEN -1 THEN '无' ELSE ifnull(su.username,'')  END  as username, "
				+ " CASE  curri.classroomid  WHEN -1 THEN '无'  ELSE ifnull(classroomname,'') END as classroomname "
				+ "from curriculum curri " +

				"left join ClassRoom cr " + "on curri.classroomid=cr.classroomid " + "left join examineeClass ec "
				+ "on ec.id=curri.classid " + "left join chapter ch " + "on ch.id=curri.chapterid "
				+ "left join systemuser su " + "on su.id=curri.teacherid ";
		// 有时间的查询
		if (startDate != null && !"".equals(startDate)) {
			sql += " where DATE_FORMAT( curri.date, '%Y-%m-%d')>= :startDate and DATE_FORMAT( curri.date, '%Y-%m-%d')<= :endDate ";
			map.put("startDate", startDate);
			map.put("endDate", endDate);
		}
		// 有老师
		if (status == 0 && ids != -1) {
			sql += " and teacherid=:teacherid";
			map.put("teacherid", id);
		}
		// 有班级
		if (status == 1 && ids != -1) {
			sql += " and classid=:classid";
			map.put("classid", id);
		}
		// 有教室
		if (status == 2 && ids != -1) {
			sql += " and curri.classroomid=:classroomid";
			map.put("classroomid", id);
		}
		sql += " order by su.username ";
		List<String> list = this.baseDao.findDatabyhql(sql, map, new TeacherCurriModel(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();// 异常情况，返回长度为0的容器
	}

	/**
	 * 获取所有的老师信息
	 */
	@Override
	public List<String> getAllTeacherCurriInfo(String startDate, String endDate, String teacherid) {
		Integer id = Integer.parseInt(teacherid);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select curri.id, su.username,su.bgcolor,su.fontcolor,su.remark as chapterName,curri.date,classname,classroomname,curri.chapterid,curri.timeperiods from curriculum curri "
				+ "inner  join ClassRoom cr " + "on curri.classroomid=cr.classroomid " + "inner join examineeClass ec "
				+ "on ec.id=curri.classid " + "inner  join systemuser su " + "on su.id=curri.teacherid ";

		if (startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)) {
			sql += " where DATE_FORMAT( curri.date, '%Y-%m-%d')>= :startDate and DATE_FORMAT(  curri.date, '%Y-%m-%d')<= :endDate ";
			map.put("startDate", startDate);
			map.put("endDate", endDate);
		}
		if (id != 0 && startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)) {
			sql += " and teacherid=:teacherid";
			map.put("teacherid", id);
		}

		if (id != 0 && (startDate == null || endDate == null || "".equals(startDate) || "".equals(endDate))) {
			sql += " where teacherid=:id";
			map.put("id", id);
		}
		List<String> list = this.baseDao.findDatabyhql(sql, map, new TeacherCurriModel(), null, null);
		// List<String> list=this.baseDao.excuteSelectSqlWithoutBean(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();// 异常情况，返回长度为0的容器
	}

	/**
	 * 月度课时总和
	 */
	@Override
	public int getTotalCount(String startTime, String endTime) {
		String sql = "select count(*)  from  Curriculum ";
		sql += " where DATE_FORMAT(date, '%Y-%m-%d')>= :startTime and DATE_FORMAT(date, '%Y-%m-%d')<= :endTime";
		sql += " and teacherid>0";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		int count = this.baseDao.findcount(sql, map);
		return count;
	}

	/*
	 * @Override public List<Curriculum> getFiveCurriculum(Integer cid, Integer
	 * currid) { String hql =
	 * "from Curriculum c where c.classid=? and c.chapterid > 0 and c.id<? GROUP BY chapterid order by  c.date  DESC ,c.timeperiods ASC "
	 * ; String[] params = new String[] { cid + "",currid+""}; List<Curriculum>
	 * list = (List<Curriculum>) this.baseDao.searchByfpc(hql, 0, 5, params); if
	 * (list != null && list.size() > 0) { return list; } return null; }
	 */

	@Override
	public Integer updateCurri(Curriculum curriculum) {
		String sql = "update Curriculum c set c.chapterid=:chapterid ,c.classroomid=:classroomid , c.teacherid=:teacherid where c.id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("chapterid", curriculum.getChapterid());
		params.put("classroomid", curriculum.getClassroomid());
		params.put("teacherid", curriculum.getTeacherid());
		params.put("id", curriculum.getId());
		try {
			this.baseDao.executeSql(sql, params);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Curriculum> isHaveCurris(Curriculum curriculum) {
		String sql = "select * from Curriculum where classid=:classid and date=:date and timeperiods=:timeperiods";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classid", curriculum.getClassid());
		map.put("date", curriculum.getDate());
		map.put("timeperiods", curriculum.getTimeperiods());
		List<Curriculum> list = this.baseDao.findDatabyhql(sql, map, new Curriculum(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return Collections.emptyList();// 异常情况，返回长度为0的容器
	}

	@Override
	public Integer deleteCurri(Integer currId) {
		String hql= "  delete  from  Curriculum where id=:currId";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("currId", currId);
		Integer result=baseDao.executeSql(hql, map);
		if(result>0){
			return 1;
		}else{
			return 0;
		}
	}

}
