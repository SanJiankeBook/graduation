package com.yc.biz.impl;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yc.biz.ExaminInterviewRecordBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Chapter;
import com.yc.po.ExaminInterviewRecord;
import com.yc.po.PointPaper;
import com.yc.po.Work;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.utils.CsvUtils;
import com.yc.utils.JsonUtil;
import com.yc.vo.Page;
import com.yc.vo.Workdetail;
import com.yc.vo.TeacherInterviewdetail;
import com.yc.webexam.actions.PointPaperAction;
/**
 * 访谈业务层实现类
 * @author pengtao
 *
 */

@Service("examinInterviewRecordBiz")
public class ExaminInterviewRecordBizImpl implements ExaminInterviewRecordBiz {
	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@Override
	public void addinfo( ExaminInterviewRecord er) {
		baseDao.add(er);
	}
	/**
	 * 根据条件查询访谈记录
	 */
	@Override
	public Page<ExaminInterviewRecord>  findinfo(Page<ExaminInterviewRecord> page, ExaminInterviewRecord er) {
		String sql="from ExaminInterviewRecord  where 1=1   ";
		//Map<String,Object> param=new HashMap<String,Object>();//原本是用map来存放的，结果发现hashmap是无序的
		//用来存放拼接语句存放的参数
		List<String> listParams=new ArrayList<String>();
		if(er.getClassName()!=null && er.getClassName()!="" && !er.getClassName().equals("")){
			sql+="and className = ? ";
			//param.put("className", er.getClassName() );
			listParams.add(er.getClassName());
		}
		if(er.getTeacherName()!=null && er.getTeacherName()!="" && !er.getTeacherName().equals("")){
			sql+="and teacherName = ? ";
			//param.put("teacherName", er.getTeacherName());
			listParams.add(er.getTeacherName());
		}
		if(er.getStudentName()!=null && er.getStudentName()!=""&& !er.getStudentName().equals("")){
			sql+="and studentName=? ";
			//param.put("studentName", er.getStudentName());
			listParams.add(er.getStudentName());
		}
		
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(er.getStartpdate()!=null &&er.getEndpdate()!=null ){
			sql+="and (pdate between  ? and ? ) ";
			//param.put("pdate", df.format(er.getPdate()));
			listParams.add(df.format(er.getStartpdate()));
			listParams.add(df.format(er.getEndpdate()));
		}
		if(er.getStartpdate()!=null && (er.getEndpdate()==null|| "".equals (er.getEndpdate()))){
			sql+=" and pdate>? ";
			listParams.add(df.format(er.getStartpdate()));
		}
		if(er.getEndpdate()!=null && (er.getStartpdate()==null|| "".equals (er.getStartpdate()))){
			sql+=" and pdate<? ";
			listParams.add(df.format(er.getEndpdate()));
		}
		 sql+=" order by pdate desc,id desc";
		int startPage=(page.getCurrentPage()-1)*page.getPageSize();
		//Object [] obj=param.values().toArray();
		String [] str=new String[listParams.size()];
		//将list集合里面的数据存进一个string数组
		for(int i=0,len=listParams.size();i<len;i++){
			str[i]=listParams.get(i);
		}
		//计算在这种条件下总共有多少条结果
		List listSum=baseDao.searchByfpc(sql, null, null, str);
		int totalsPage=0;
		if(listSum.size()%10!=0){
			totalsPage=listSum.size()/10+1;
		}else{
			totalsPage=listSum.size()/10;
		}
		//设置显示数据的总页面
		page.setTotalsPage(totalsPage);
		//设置数据的总条数
		page.setTotalsCount(listSum.size());
		//分页查询
		List list=(List<ExaminInterviewRecord>)baseDao.searchByfpc(sql, startPage, page.getPageSize(),str);
		page.setResult(list);
		return page;
	}
	@Override
	public List showinfo(Integer id) {
		//根据条件查询，根据id查询访谈记录
		String hql="from ExaminInterviewRecord  where id=?";
		String str=id+"";
		str=str.trim();
		
		return this.baseDao.searchs_sql(hql, str);
	}
	
	@Override
	public List findinfo(String className) {
		String sql="from ExamineeClass where className=? ";
		String str=className.trim();
		
		return this.baseDao.searchs_sql(sql, str);
	}
	@Override
	public List<Workdetail> getclassWorkdetail(String year, String month, String path) {
		CsvUtils cu=new CsvUtils();
		path=this.getClass().getResource("/").getPath();//取到项目的class文件的路径  /E:/apache-tomcat-8.0.44-windows-x64/apache-tomcat-8.0.44/webapps/Examination2.0/WEB-INF/classes/
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));//得到服务器的根路径  /E:/apache-tomcat-8.0.44-windows-x64/apache-tomcat-8.0.44/webapps
		List<String[]> list=cu.readeCsv(path+"/csv/classInterview_"+year+"-"+month+".csv");
		List<Workdetail> listwork=new ArrayList<Workdetail>();
		if(list==null){
			return null;
		}
		for(String[] str:list){
			Workdetail wd=new Workdetail();
			wd.setExamineeclassid(Integer.valueOf(str[0]));
			wd.setClassName(str[1]);
			wd.setClasscount(Integer.valueOf(str[3]));
			wd.setCheckcount(Integer.valueOf(str[4]));
			wd.setCompletionrate(str[5]+"%");
			listwork.add(wd);
		}
		return listwork;
	}
	@Override
	public List<ExaminInterviewRecord> findWorkdetail(int className, int year, int month) {
		String sql="select ed.endpdate,ed.startpdate, ed.interviewAddress,ed.content,ed.remark,ed.id,ed.pdate, ed.className,ed.teacherName,ed.studentName from examininterviewrecord ed ,examineeclass es where ed.className=es.className and es.id=:id and ed.pdate>:startime and ed.pdate<:endtime";
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
		map.put("id", className);
		map.put("startime", startime);
		map.put("endtime", endtime);
		return this.baseDao.findDatabyhql(sql,map,new ExaminInterviewRecord(),null,null);
	}
	
	@Override
	public List<TeacherInterviewdetail> getTeacherInterviewdetail(String year, String month) {
		CsvUtils cu=new CsvUtils();
		String path=this.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));//获得服务器的根目录
		List<String[]> list=cu.readeCsv(path+"/csv/teacherInterview_"+year+"-"+month+".csv");//从csv文件中读取数据
		List<TeacherInterviewdetail> listwork=new ArrayList<TeacherInterviewdetail>();
		if(list==null){
			return null;
		}
		for(String[] str:list){
			TeacherInterviewdetail TId=new TeacherInterviewdetail();
			TId.setTeacherName(str[0]);
			TId.setNum(Integer.parseInt(str[1]));
			listwork.add(TId);
		}
		return listwork;
	}
	@Override
	public List<ExaminInterviewRecord> getTeacherInterviewByName(String teachername, int year, int month) {
		String sql="select * from examininterviewrecord where teacherName=:teacherName  and pdate>:startime and pdate<:endtime";
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
		String startime=staryear+"-0"+starmonth+"-25";
		String endtime=year+"-0"+month+"-26";
		map.put("teacherName", teachername);
		map.put("startime", startime);
		map.put("endtime", endtime);
		return this.baseDao.findDatabyhql(sql, map,new ExaminInterviewRecord() ,null, null);
	}
	@Override
	public List findCount(Integer className) {
		//String sql="select * from examinee where examinee.classId=:id ";
		String sql="select * from examineeclass where examineeclass.Id=:id ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", className);
		return this.baseDao.findDatabyhql(sql,map,null,null);
	}

}
