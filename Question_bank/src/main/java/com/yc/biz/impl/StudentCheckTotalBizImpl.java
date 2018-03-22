package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.yc.biz.StudentCheckTotal;
import com.yc.dao.BaseDao;
import com.yc.po.ADailyTalk;
import com.yc.po.Checking;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.Temp;
import com.yc.po.Work;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.utils.JedisUtils;

import redis.clients.jedis.Jedis;
@Repository("studentCheckTotalBizImpl")
public class StudentCheckTotalBizImpl implements StudentCheckTotal {
	private BaseDao baseDao;
	private Jedis jedis=new Jedis(JedisUtils.REDIS_URL,JedisUtils.REDIS_PORT);
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}
	
	@Override
	public List setTotalInfo() {
		setCheckTotal(true);
		setCheckTotal(false);
		setWorkTotal(true);
		setWorkTotal(false);
		setADailyTalk(true);
		setADailyTalk(false);
		setWritingAnswer(true);
		setWritingAnswer(false);
		return null;
	}
	
	/**
	 * 获取每人的考试情况
	 */
	public void setWritingAnswer(boolean bool){
		Map<String, Integer> map=new HashMap<String, Integer>();//存考勤记录,已到
		String sql="select * from ExamineeClass where semester in('S1','S2','S3')";//得到班级编号
		List<ExamineeClass> list=this.baseDao.searchsql(sql, new ExamineeClass());
		for( ExamineeClass ec:list){
			//根据班级编号得到学生名字
			//sql="select * from examinee where classId="+ec;
//			List<Examinee> listclassName=this.baseDao.searchsql(sql, new Examinee());
			//根据班级编号得到每个班的考卷情况
			String sqllist=null;
			if(bool){
				 sqllist="select * from writingpaper where examineeClass='"+ec.getClassName()+"'";
			}else{
				 sqllist="select * from writingpaper where examineeClass='"+ec.getClassName()+"' and date_format(examDate,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')";
			}
			List<WritingPaper> listClassInfo=this.baseDao.searchsql(sqllist, new WritingPaper());
			
				String sql2=null;
				String names=null;
				if(bool){//如果没有时间限制
					sql2="select wa.id,wa.paperId,wa.examineeName,wa.answer,wa.score,wa.correctRate,wa.spareTime from WritingAnswer wa ,writingPaper wp where wp.id=wa.paperId  and wp.examineeClass='"+ec.getClassName()+"'";
					names=ec.getId()+"_WritingPaper";
				}else{//有时间限制
					//得到上一月的数据
					sql2="select wa.id,wa.paperId,wa.examineeName,wa.answer,wa.score,wa.correctRate,wa.spareTime  from WritingAnswer wa ,writingPaper wp where wp.id=wa.paperId  and wp.examineeClass='"+ec.getClassName() +"' and date_format(examDate,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')";
					names=ec.getId()+"_LastMonth_WritingPaper";
				}
					List<WritingAnswer> listChecking=this.baseDao.searchsql(sql2, new WritingAnswer());
					
					if(listChecking!=null && !listChecking.isEmpty()){
						map.put(names, listClassInfo.size());
						for(WritingAnswer at:listChecking){
							String name=at.getExamineeName();
							Integer checkingcount=listChecking.size();
							String snames=null;
							if(bool){
								snames=ec.getId()+"_"+name+"_WritingAnswer";
							}else{
								snames=ec.getId()+"_"+name+"_LastMonth_WritingAnswer";
							}
							Integer value=map.get(snames);
							if(value==null){
								value=0;
								map.put(snames,++value);
							}else{
								map.put(snames,++value);
							}
							
						}
					}		
		}
		//更新的时候redis缓存
				for( Entry<String,Integer> m:map.entrySet()){
					String str=jedis.set( m.getKey(), m.getValue()+"");
				}
	}
	/**
	 * 获取每个人的每日一讲情况
	 */
	public void setADailyTalk(boolean bool){
		Map<String, Integer> map=new HashMap<String, Integer>();//存考勤记录,已到
		String sql="select * from ExamineeClass where semester in('S1','S2','S3')";//得到班级编号
		List<ExamineeClass> list=this.baseDao.searchsql(sql, new ExamineeClass());
		for( ExamineeClass ec:list){
			//根据班级编号得到学生名字
			sql="select * from examinee where classId="+ec.getId();
//			List<Examinee> listclassName=this.baseDao.searchsql(sql, new Examinee());
			//根据班级编号和学期编号得到考勤记录id
				String sql2=null;
				
				if(bool){//如果没有时间限制
					sql2="select * from ADailyTalk where cid="+ec.getId();
				}else{//有时间限制
					//得到上一周的数据
					sql2="select * from ADailyTalk where cid="+ec.getId() +" and date_format(speakdate,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')";
				}
					List<ADailyTalk> listChecking=this.baseDao.searchsql(sql2, new ADailyTalk());
					if(listChecking!=null && !listChecking.isEmpty()){
						for(ADailyTalk at:listChecking){
							String name=at.getName();
							Integer checkingcount=listChecking.size();
							String snames=null;
							if(bool){
								snames=ec.getId()+"_"+name+"_ADailyTalk";
							}else{
								snames=ec.getId()+"_"+name+"_LastMonth_ADailyTalk";
							}
							Integer value=map.get(snames);
							if(value==null){
								value=0;
								map.put(snames,++value);
							}else{
								map.put(snames,++value);
							}
							
						}
						
					}
	
		}
		//更新的时候redis缓存
		for( Entry<String,Integer> m:map.entrySet()){
			String str=jedis.set( m.getKey(), m.getValue()+"");
		}
	}
	/**
	 * 获取每个人的作业记录
	 */
	
	public void setWorkTotal(boolean bool){
		Map<String, Integer> map=new HashMap<String, Integer>();//存考勤记录,已到
		String sql="select * from ExamineeClass where semester in('S1','S2','S3')";//得到班级编号
		List<ExamineeClass> list=this.baseDao.searchsql(sql, new ExamineeClass());
		for( ExamineeClass ec:list){
			//根据班级编号得到学生名字
			sql="select * from examinee where classId="+ec.getId();
			//根据班级编号和学期编号得到考勤记录id
			for(int j=1;j<=3;j++){
				String sql2=null;
				if(bool){//如果没有时间限制
					sql2="select * from work where examineeclassid="+ec.getId()+" and semester='S"+j+"'";
				}else{//有时间限制
					//得到上一周的数据
					sql2="select * from work where examineeclassid="+ec.getId()+" and semester='S"+j+"' and YEARWEEK(date_format(checkDate,'%Y-%m-%d')) = YEARWEEK(now())-1 ";
				}
					List<Work> listChecking=this.baseDao.searchsql(sql2, new Work());
					if(listChecking!=null && !listChecking.isEmpty()){
						
						Integer checkingcount=listChecking.size();
						if(bool){
							map.put("S"+j+"_"+ec.getId()+"_checkOut_work", checkingcount);
						}else{
							map.put("S"+j+"_"+ec.getId()+"_checkOut_LastWeek_work", checkingcount);
						}
						for(Work ck:listChecking){
							int flags = 1;
							String checkResult=ck.getResult();
							String semester=ck.getSemester();
							String ClassId=ec.getId()+"";
							if(checkResult!="" && checkResult!=null){
								while (flags > 0) {
									String sname=checkResult.substring(0, checkResult.indexOf(",", 0));
									String snames=null;
									if(bool){
										 snames=semester+"_"+ClassId+"_"+sname+"_work";//设置考勤时已到的key
									}else{
										snames=semester+"_"+ClassId+"_"+sname+"_LastWeek_work";//设置考勤时已到的key
									}
									int status = Integer.parseInt(checkResult.substring(checkResult.indexOf(",", 0) + 1,
											checkResult.indexOf("-", 0)));
									if(status==1){
										Integer value= map.get(snames);
										if(value==null){
											value=0;
											map.put(snames, ++value);
										}else{
											
											map.put(snames, ++value);
										}
									}
									if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
											.indexOf("|", 0) > 1) {
										checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
												checkResult.length());
									} else {
										flags = 0;
									}
								}
							}
						}
					}
				}
		}
		//更新的时候redis缓存
		for( Entry<String,Integer> m:map.entrySet()){
			String str=jedis.set( m.getKey(), m.getValue()+"");
		}
	}
	/**
	 * 获取每个人的考勤记录
	 */
	public void setCheckTotal(boolean bool){
		Map<String, Integer> map=new HashMap<String, Integer>();//存考勤记录,已到
		String sql="select * from ExamineeClass where semester in('S1','S2','S3')";//得到班级编号
		List<ExamineeClass> list=this.baseDao.searchsql(sql, new ExamineeClass());
		for( ExamineeClass ec:list){
			//根据班级编号得到学生名字
			sql="select * from examinee where classId="+ec.getId();
			//根据班级编号和学期编号得到考勤记录id
			for(int j=1;j<=3;j++){
				String sql2=null;
				if(bool){//如果没有时间限制
					sql2="select * from Checking where cid="+ec.getId()+" and semester='S"+j+"'";
				}else{//有时间限制
					//得到上一周的数据
					sql2="select * from Checking where cid="+ec.getId()+" and semester='S"+j+"' and YEARWEEK(date_format(checkDate,'%Y-%m-%d')) = YEARWEEK(now())-1 ";
				}
					List<Checking> listChecking=this.baseDao.searchsql(sql2, new Checking());
					if(listChecking!=null && !listChecking.isEmpty()){
						String sql3="select * from Checking ck  where checkStatus=1 and checkId in (";
						for(int i=0,len=listChecking.size();i<len;i++){
							sql3+=listChecking.get(i).getCheckId()+",";
						}
						Integer checkingcount=listChecking.size();
						if(bool){
							map.put("S"+j+"_"+ec.getId()+"_checkOut", checkingcount);
						}else{
							map.put("S"+j+"_"+ec.getId()+"_checkOut_LastWeek", checkingcount);
						}
						sql3=sql3.substring(0,sql3.length()-1)+")";
						List<Checking> listChecks=this.baseDao.searchsql(sql3, new Checking());
						for(Checking ck:listChecks){
							int flags = 1;
							String checkResult=ck.getCheckResult();
							String semester=ck.getSemester();
							String ClassId=ec.getId()+"";
							while (flags > 0) {
								String sname=checkResult.substring(0, checkResult.indexOf(",", 0));
								String snames=null;
								if(bool){
									 snames=semester+"_"+ClassId+"_"+sname;//设置考勤时已到的key
								}else{
									snames=semester+"_"+ClassId+"_"+sname+"_LastWeek";//设置考勤时已到的key
								}
								int status = Integer.parseInt(checkResult.substring(checkResult.indexOf(",", 0) + 1,
										checkResult.indexOf("-", 0)));
								if(status==1){
									Integer value= map.get(snames);
									if(value==null){
										value=0;
										map.put(snames, ++value);
									}else{
										
										map.put(snames, ++value);
									}
								}
								if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
										.indexOf("|", 0) > 1) {
									checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
											checkResult.length());
								} else {
									flags = 0;
								}
							}
						}
					}
				}
		}
		//更新的时候redis缓存
		for( Entry<String,Integer> m:map.entrySet()){
			String str=jedis.set( m.getKey(), m.getValue()+"");
		}
	}
	
	
}
