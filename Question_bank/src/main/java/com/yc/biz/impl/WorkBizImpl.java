package com.yc.biz.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.WorkBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Work;
import com.yc.utils.CsvUtils;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;
@Service("workBiz")
public class WorkBizImpl implements WorkBiz{

	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Serializable addWork(Work work) {
		Serializable result = baseDao.add(work);
		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<Work> findWork(Integer classid, String semester, String checkdate,String page,String rows) {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="select *from Work w where w.semester=:semester "
				+ "and w.examineeclassid=:examineeclassid ";

		params.put("semester", semester);
		params.put("examineeclassid", classid);
		DateFormat sdf=new SimpleDateFormat("");
		if(!checkdate.equals("") && checkdate!=null){
			sql+="and checkdate <:checkdate";
			params.put("checkdate", checkdate);
		}
		sql+=" order by w.wid desc";
		List<Work> list=null;
		list=this.baseDao.findDatabyhql(sql, params,new Work(), page, rows);
		return list;
	}

	@Override
	public Work findWorkbyid(Integer wid) {
		String sql="select * from Work w where w.wid=:wid ";
		sql+=" order by w.wid desc";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wid", wid);
		List<Work> list=null;
		list=this.baseDao.findDatabyhql(sql, params, new Work(), null, null);
		return list.get(0);
	}

	@Override
	public int updateWorkresult(Integer wid, String result,String checkcount) {
		String sql="update work set result=:result,checkcount=:checkcount where wid=:wid ";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wid", wid);
		params.put("result", result);
		params.put("checkcount", checkcount);
		return this.baseDao.executeSql(sql, params);
	}

	@Override
	public List<Work> findWorkbyclass(Work work,String page,String rows) {
		String sql="select * from Work w where"
				+ " w.semester=:semester and w.examineeclassid=:examineeclassid ";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("semester", work.getSemester());
		params.put("examineeclassid", work.getExamineeclassid());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(work.getEditionid()!=0){
			sql+=" and w.editionid=:editionid ";
			params.put("editionid", work.getEditionid());
		}
		if(work.getSubjectid()!=0){
			sql+=" and w.subjectid=:subjectid ";
			params.put("subjectid", work.getSubjectid());
		}
		if(work.getCheckdate()!=null){
			sql+=" and w.checkdate<=:checkdate ";
			String date=sdf.format(work.getCheckdate());
			params.put("checkdate",date );
		}
		if(work.getChapterid()!=0){
			sql+=" and w.chapterid=:chapterid";
			params.put("chapterid", work.getChapterid());
		}
		sql+=" order by w.wid desc";
		return this.baseDao.findDatabyhql(sql, params,new Work(), page, rows);
	}

	@Override
	public int findworknum(Integer classid,String editionid,String subid,String chapid,String date) {
		String sql="select count(0) from work where examineeclassid =:examineeclassid " ;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("examineeclassid", classid);
		if(editionid!=null&&!"".endsWith(editionid)){
			sql+=" and editionid =:edition";
			params.put("edition", editionid);
		}
		if(subid!=null&&!"".endsWith(subid)){
			sql+=" and subjectid=:subjectid ";
			params.put("subjectid", subid);
		}
		if(chapid!=null&&!"".endsWith(chapid)){
			sql+=" and chapterid=:chapterid ";
			params.put("chapterid", chapid);
		}
		if(date!=null&&!"".endsWith(date)){
			sql+=" and checkdate=:checkdate ";
			params.put("checkdate", date);
		}
		return this.baseDao.findcount(sql, params);
	}

	@Override
	public int getClassWorkNum(Integer classid,String startime,String endtime) {
		String sql="select wid from work where examineeclassid=:examineeclassid and checkdate > :startime and checkdate <:endtime";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("examineeclassid", classid);
		params.put("startime", startime);
		params.put("endtime", endtime);
		return this.baseDao.findDatabyhql(sql, params, null, null).size();
	}


	/*@Override
	public void updateWorkCheckcount() {
		String sql="from Work";
		List<Work> list=this.baseDao.findData(sql, null, null, null);
		for(Work work:list){
			int num=0;
			String[] s = null;
				String result=work.getResult();
				System.out.println(result);
				if(result==null||"".equals(result)){
					continue;
				}
				String[] str=result.split("|");
				for(String str1:str){
					System.out.println(str1);
					String[] st=str1.split(",");
					for(String str2:st){
						System.out.println(str2);
					}
				}

				try {
					s = st[1].split("-");
					if("1".equals(s)){
						num++;
					}
				} catch (Exception e) {
				}
				work.setCheckcount(num);
				System.out.println("=============");
				System.out.println(num);
				this.baseDao.update(work);

		}

	}
	 */
	@Override
	public int getWorkCheckcount(Integer classid, String startime, String endtime) {
		String sql="select sum(checkcount) from Work where  examineeclassid=:examineeclassid  and checkdate > :startime and checkdate <:endtime";
		//String sql="select sum(checkcount) as checkcount from Work where  examineeclassid=?  and checkdate >? and checkdate <?";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("examineeclassid", classid);
		params.put("startime", startime);
		params.put("endtime", endtime);
		String[] str=new String[]{classid+"",startime,endtime};
		List<Long> list=this.baseDao.findDatabyhql(sql, params, null, null);
		int num=0;
		if(list.get(0)!=null){
			num  =Integer.valueOf(String.valueOf(list.get(0)));
		}
		return num;
	}

	
	@Override
	public int getTeacherchekcount(String teachername, String startime, String endtime) {
		String sql="select count(0) from work where checkdate > :startime and checkdate <:endtime and checkcreator=:checkcreator ";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("checkcreator", teachername);
		params.put("startime", startime);
		params.put("endtime", endtime);
		return this.baseDao.findcount(sql, params);
	}

	@Override
	public List<Workdetail> getclassWorkdetail(String year, String month,String path) {
		CsvUtils cu=new CsvUtils();
		path=this.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		List<String[]> list=cu.readeCsv(path+"/csv/class_"+year+"-"+month+".csv");
		List<Workdetail> listwork=new ArrayList<Workdetail>();
		if(list==null){
			return null;
		}
		for(String[] str:list){
			Workdetail wd=new Workdetail();
			wd.setExamineeclassid(Integer.valueOf(str[0]));
			wd.setClassName(str[1]);
			wd.setWorkcount(Integer.valueOf(str[2]));
			wd.setClasscount(Integer.valueOf(str[3]));
			wd.setCheckcount(Integer.valueOf(str[4]));
			wd.setCompletionrate(str[5]);
			listwork.add(wd);
		}
		return listwork;
	}

	@Override
	public List<Work> findWorkdetail(int classid, int year, int month) {
		String sql="select * from Work where examineeclassid=:examineeclassid and checkdate>:startime and checkdate<:endtime";
		Map<String,Object> map=new HashMap<String,Object>();
		int starmonth=0;
		int staryear=0;
		if(month==1){
			staryear=year-1;
			starmonth=12;
		}else{
			staryear=year;
			starmonth=month-1;
		}
		
		String startime=staryear+"-"+starmonth+"-25";
		String endtime=year+"-"+month+"-26";
		map.put("examineeclassid", classid);
		map.put("startime", startime);
		map.put("endtime", endtime);
		return this.baseDao.findDatabyhql(sql, map,new Work(), null, null);
	}

	@Override
	public List<TeacherWorkdetail> getteacherWorkdetail(String year, String month) {
		CsvUtils cu=new CsvUtils();
		String path=this.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		List<String[]> list=cu.readeCsv(path+"/csv/teacher_"+year+"-"+month+".csv");
		List<TeacherWorkdetail> listwork=new ArrayList<TeacherWorkdetail>();
		if(list==null){
			return null;
		}
		for(String[] str:list){
			TeacherWorkdetail twd=new TeacherWorkdetail();
			twd.setCheckcreator(str[0]);
			twd.setCheckcount(Integer.valueOf(str[1]));
			listwork.add(twd);
		}

		return listwork;
	}

	@Override
	public List<Work> getTeacherchekwork(String teachername,int year,int month) {
		String sql="select * from Work where checkcreator=:checkcreator  and checkdate>:startime and checkdate<:endtime";
		Map<String,Object> map=new HashMap<String,Object>();
		int starmonth=0;
		int staryear=0;
		if(month==1){
			staryear=year-1;
			starmonth=12;
		}else{
			staryear=year;
			starmonth=month-1;
		}
		String startime=staryear+"-"+starmonth+"-25";
		String endtime=year+"-"+month+"-26";
		map.put("checkcreator", teachername);
		map.put("startime", startime);
		map.put("endtime", endtime);
		return this.baseDao.findDatabyhql(sql, map, new Work(), null, null);
	}

	@Override
	public List<Work> getstudentwork(Integer classid,String semeter, String startDate, String endDate) {
		String sql="select * from work where examineeclassid=:examineeclassid and semester=:semester and checkdate < :endDate and checkdate > :startDate";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("examineeclassid", classid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("semester", semeter);
		return this.baseDao.findDatabyhql(sql, map, new Work(), null, null);
	}

	@Override
	public List<Work> findWorkbydictation(Integer classid, String semester) {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="select * from Work w where w.semester=:semester "
				+ "and w.examineeclassid=:examineeclassid and status=0 and result IS NULL";
		params.put("semester", semester);
		params.put("examineeclassid", classid);
		return this.baseDao.findDatabyhql(sql, params, new Work(), null, null);
	}

	@Override
	public int  updateworkstatus(int wid) {
		String sql="update work set status=1 where wid=:wid";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("wid", wid);
		return this.baseDao.executeSql(sql, params);
		
	}

	@Override
	public List<Work> findDictation(int classid) {
		String sql="select * from work where examineeclassid=:examineeclassid and status=1";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("examineeclassid", classid);
		return this.baseDao.findDatabyhql(sql, params, new Work(), null, null);
	}



}
