package com.yc.biz.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.CheckingBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ADailyTalk;
import com.yc.po.Checking;

@Service("checkingBiz")
public class CheckingBizImpl implements CheckingBiz {
	
	private BaseDao baseDao;
	
	private Map<String, Object> param=new HashMap<String,Object>();

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public int addCheckingInfo(Checking ck) {
		String[] params=new String[]{ck.getSemester(),ck.getSystemUser().getId()+"",ck.getCheckDate()+"",ck.getCheckTime(),ck.getCheckResult(),ck.getCheckRemark()};
		int result= (Integer) baseDao.add(ck);
		return result;
	}

	@Override
	public int updateCheckingInfo(Checking ck) {
		try {
			this.baseDao.update(ck);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public Checking findNewCheckingId() {
		return null;
	}

	@Override
	public Checking findNewCheckingIdes(int checkId) {
		return null;
	}

	@Override
	public Checking findCheckingInfoById(int checkId) {
		Map<String,Object> map=new HashMap<String,Object>();
		String sql="select *  from Checking c  where c.checkId=:checkId ";
		map.put("checkId", checkId);
		List<Checking> list=this.baseDao.findDatabyhql(sql, map, new Checking(), null, null);
		if(list!=null &&  list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Checking> findCheckingByAll(String semester, int cid,
			String stuname, String startdate, String enddate, String datetime) {
		
		return null;
	}

	@Override
	public List<Integer> findCheckingAllCheckId(String semester, int cid,
			String stuname, String startdate, String enddate, String datetime)  {
		String sql="";
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			sql="select checkId from Checking where checkStatus=1";
			if(semester!=null&&!"".equalsIgnoreCase(semester)){
				sql+=" and semester like :semester";
				map.put("semester", "%"+semester+"%");
			}
			if(cid>0){
				sql+=" and cid=:cid";
				map.put("cid", cid);
			}
			if(stuname!=null&&!"".equalsIgnoreCase(stuname)&&!"0".equalsIgnoreCase(stuname)){
				sql+=" and checkResult like :stuname";
				map.put("stuname", "%"+stuname+"%");
			}
			if(startdate!=null&&!"".equalsIgnoreCase(startdate)){
//				Date startdates=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startdate);
				sql+=" and checkDate>= :startdate";  	// DATEDIFF('"+startdate+"',checkDate)>=0";
				map.put("startdate", startdate);
			
			}
			if(enddate!=null&&!"".equalsIgnoreCase(enddate)){
//				Date enddates=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(enddate);
				sql+=" and   checkDate <= :enddate";	 //DATEDIFF(checkDate,'"+enddate+"')>=0";
				map.put("enddate", enddate);
			}
			if(datetime!=null&&!"".equalsIgnoreCase(datetime)&&!"0".equalsIgnoreCase(datetime)){
				sql+=" and rtrim(checkTime) like :datetime";
				map.put("datetime", "%"+datetime+"%");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.baseDao.findDatabyhql(sql, map, null, null);
	}

	/**
	 * 根据跟定的条件查询考勤信息（学生端）
	 * @param semester：学期
	 * @param startDate：查询开始时间
	 * @param endDate：查询结束时间
	 * @param dateTime：查询时间段
	 * @param uname：学生姓名
	 * @return
	 */
	@Override
	public List<Checking> findCheckingResullByStudentName(String semester,
			String startDate, String endDate, String time, String uname) {
		String sql="";
		List<Checking> list=null;
		if(semester==null||"".equals(semester)){
			if(time==null  ||  "".equals(time)){
				if(startDate == null || "".equals(startDate) && endDate == null  || "".equals(endDate)){
					sql=" from Checking c where c.checkResult like ?  order by checkDate DESC";
					String	param[]={"%"+uname+"%"};
					list=this.baseDao.search(sql, param);
				} else {
					sql=" from Checking c where c.checkResult like ?   and  checkDate BETWEEN ?  AND ? order by checkDate DESC" ;
					String	param[]={"%"+uname+"%",startDate,endDate};
					list=this.baseDao.search(sql, param);
				}
			}else{
				if(startDate == null || "".equals(startDate) && endDate == null  || "".equals(endDate)){
					sql=" from Checking c where c.checkResult like ?   and  checkTime=? order by checkDate DESC";
					String	param[]={"%"+uname+"%",time};
					list=this.baseDao.search(sql, param);
				}else{
					sql=" from Checking c where c.checkResult like ? and checkTime=? and  checkDate BETWEEN ?  AND ? order by checkDate DESC";
					String	param[]={"%"+uname+"%",time,startDate,endDate};
					list=this.baseDao.search(sql, param);
				}
				
			}
		}else{
			if(time==null  ||  "".equals(time)){
				if(startDate == null || "".equals(startDate) && endDate == null  || "".equals(endDate)){
					sql=" from Checking c where c.checkResult like ? and c.semester=? order by checkDate DESC ";
					String	param[]={"%"+uname+"%",semester};
					list=this.baseDao.search(sql, param);
				} else {
					sql=" from Checking c where c.checkResult like ? and c.semester=?  and  checkDate BETWEEN ?  AND ?  order by checkDate DESC";
					String	param[]={"%"+uname+"%",semester,startDate,endDate};
					list=this.baseDao.search(sql, param);
				}
			}else{
				if(startDate == null || "".equals(startDate) && endDate == null  || "".equals(endDate)){
					sql=" from Checking c where c.checkResult like ?   and  checkTime=? and c.semester=?  order by checkDate DESC";
					String	param[]={"%"+uname+"%",time,semester};
					list=this.baseDao.search(sql, param);
				}else{
					sql=" from Checking c where c.checkResult like ? and checkTime=? and c.semester=? and  checkDate BETWEEN ?  AND ?  order by checkDate DESC";
					String	param[]={"%"+uname+"%",time,semester,startDate,endDate};
					list=this.baseDao.search(sql, param);
				}
				
			}
		}
		return list;
	}

	@Override
	public List<Checking> showCheckingResult(String semester, int cid,
			String startdate, String enddate, String datetime) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Checking findCheckingByClassIdAndDateTime(int cid, Date date,
			String time) {
		//String sql="select count(0) from Checking where cid=? and checkDate=? and checkTime=?";
		String sql=" from Checking c where c.examineeClass.id=? and c.checkDate=?  and c.checkTime=?";
		
		String param[]={cid+"",new SimpleDateFormat("yyyy-MM-dd").format(date),time};
//		param.put("id", cid);
//		param.put("checkDate", date);
//		param.put("checkTime", time);
		List<Checking> list=this.baseDao.searchs_sql(sql, param);
		if(list!=null  &&  list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Checking> findCheckingByClassIdAndUidAndTime(int cid,String time,
			String startDate,String endDate) {
		String sql="";
		List<Checking> list=null;
		
		if(time==null  ||  "".equals(time)){
			if(startDate == null || "".equals(startDate) && endDate == null  || "".equals(endDate)){
				sql=" from Checking c where c.examineeClass.id=?  order by checkDate DESC";
				String	param[]={cid+""};
				list=this.baseDao.search(sql, param);
			} else {
				sql=" from Checking c where c.examineeClass.id=?   and  checkDate BETWEEN ?  AND ? order by checkDate DESC";
				String	param[]={cid+"",startDate,endDate};
				list=this.baseDao.search(sql, param);
			}
		}else{
			if(startDate == null || "".equals(startDate) && endDate == null  || "".equals(endDate)){
				sql=" from Checking c where c.examineeClass.id=?   and  checkTime=?  order by checkDate DESC";
				String	param[]={cid+"",time};
				list=this.baseDao.search(sql, param);
			}else{
				sql=" from Checking c where c.examineeClass.id=? and checkTime=? and  checkDate BETWEEN ?  AND ? order by checkDate DESC";
				String	param[]={cid+"",time,startDate,endDate};
				list=this.baseDao.search(sql, param);
			}
			
		}
		

		return list;
	}

	@Override
	public List<Checking> findCheckByCheckid(int checkid) {
		String sql="from Checking ck  where checkStatus=1 and checkId =?";
		String param[]={checkid+""};
		List<Checking> list=this.baseDao.search(sql, param);
		if(list!=null  &&  list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	


	public List<Checking> findCheckByClassId(int cid,String startDate,String endDate,String datetime) {
		//String sql="select checkId as checkid ,semester,checkDate,checkTime,checkResult,checkRemark ,checkDescript as userName from Checking ck  where checkStatus=1 and checkId =(";
		String sql="select * from Checking   where checkStatus=1 and cid=:cid  ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cid", cid);
		if(datetime!=null&&!"".equalsIgnoreCase(datetime)&&!"0".equalsIgnoreCase(datetime)){
			sql+="  and checkTime=:datetime  ";
			map.put("datetime", datetime);
		}
		if(startDate!=null && !"".equals(startDate) && endDate!=null && !"".equals(endDate)){
			sql+=" and  checkDate BETWEEN :startDate  AND :endDate order by checkDate DESC  ";
			map.put("startDate", startDate);
			map.put("endDate", endDate);
		}
		List<Checking> list=this.baseDao.findDatabyhql(sql, map, new Checking(), null, null);
		//.search(sql, param);
		if(list!=null  &&  list.size()>0){
			return list;
		}else{
			return Collections.emptyList();
		}
	}

	public List<Checking> findCheckInCheckid(List<Integer> checkid) {
		//String sql="select checkId as checkid ,semester,checkDate,checkTime,checkResult,checkRemark ,checkDescript as userName from Checking ck  where checkStatus=1 and checkId =(";
		String sql="select * from Checking ck  where checkStatus=1 and checkId in (";

		for(int i=0,len=checkid.size();i<len;i++){
			sql+=checkid.get(i)+",";
		}
		
		sql=sql.substring(0,sql.length()-1)+")"+" order by checkDate DESC";
		List<Checking> list=this.baseDao.excuteSelectSql(sql, new Checking());
		//.search(sql, param);
		if(list!=null  &&  list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	// zx 原生 根据条件查id
	
		public List<Integer> findCheckingAllCheckIdExcuteSql(String semester, int cid,
				String stuname, String startdate, String enddate, String datetime)  {
			String sql="";
			Map<String,Object> map=new HashMap<String,Object>();
			
			try {
				sql="select checkId from Checking where checkStatus=1";
				/*if(semester!=null&&!"".equalsIgnoreCase(semester)){
					sql+=" and semester like '%"+semester+"%'";
				}*/
				
				if(semester!=null&&!"".equals(semester)){
					sql+=" and semester=:semester";
					map.put("semester", semester);
				}
				if(cid>0){
					
					sql+=" and cid=:cid";
					map.put("cid", cid);
				}
				if(stuname!=null&&!"".equalsIgnoreCase(stuname)&&!"0".equalsIgnoreCase(stuname)){
					sql+=" and checkResult like :stuname";
					map.put("stuname", "%"+stuname+"%");
				}
				if(startdate!=null&&!"".equalsIgnoreCase(startdate)){
//					Date startdates=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startdate);
					sql+=" and checkDate>=  :startdate";  	// DATEDIFF('"+startdate+"',checkDate)>=0";
					map.put("startdate", startdate);
				}
				if(enddate!=null&&!"".equalsIgnoreCase(enddate)){
//					Date enddates=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(enddate);
					sql+=" and   checkDate <= :enddate";	 //DATEDIFF(checkDate,'"+enddate+"')>=0";
					map.put("enddate", enddate);
				}
				if(datetime!=null&&!"".equalsIgnoreCase(datetime)&&!"0".equalsIgnoreCase(datetime)){
					sql+=" and rtrim(checkTime) like :datetime";
					map.put("datetime", "%"+datetime+"%");
				}// RTRIM(str) 返回字符串str与尾部(右边)的空格字符去掉。 
				sql+=" order by checkDate DESC ";
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return this.baseDao.findDatabyhql(sql, map, null, null);
		}
	
	
	

}
