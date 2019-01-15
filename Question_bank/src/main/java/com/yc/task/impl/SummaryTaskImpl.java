package com.yc.task.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yc.dao.BaseDao;
import com.yc.po.Satisfaction;
import com.yc.po.SatisfactionDetail;
import com.yc.task.SummayTask;

@Service
public class SummaryTaskImpl implements SummayTask{

	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Scheduled(cron= "0 0 22 * * ?")   //每天晚上十点秒执行一次   

	@Transactional
	@Override
	public void task() {
		try {  
			Calendar calerdar=Calendar.getInstance();
			int year=calerdar.get(Calendar.YEAR);
			//更新当年的调查表
			String sql="select * from satisfaction where openYear=:year";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("year",String.valueOf(year));
			List<Satisfaction> allSaid=this.baseDao.findDatabyhql(sql, map, new Satisfaction(), null,null);
			//根据调查表id查询对应的细节调查表
			for(Satisfaction satisid:allSaid){
				Integer id=satisid.getId();
				Double summaryCount=0.0;
				String summarySatis="";
				String summaryUnsatis="";
				String sql1="select * from satisfactionDetail where said=:said ";
				Map<String, Object> param=new HashMap<String, Object>();
				param.put("said", id);
				List<SatisfactionDetail> satisDetailList=this.baseDao.findDatabyhql(sql1, param, new SatisfactionDetail(), null, null);
				if(satisDetailList.size()>0&&satisDetailList!=null){
					for(SatisfactionDetail satisDetails:satisDetailList){
						summaryCount+=satisDetails.getTotalScore();
						summarySatis+=satisDetails.getSatisfaction()+",";
						summaryUnsatis+=satisDetails.getUnsatisfaction()+",";
					} 
				}
				System.out.println(summarySatis);
				if("".equals(summarySatis) || summarySatis==null){
					summarySatis="无";
				}
				if("".equals(summaryUnsatis) || summaryUnsatis==null){
					summaryUnsatis="无";
				}
				Integer len=satisDetailList.size();
				if(summaryCount!=0 && len!=0){
					summaryCount=(summaryCount/26)/len;
				}else{
					summaryCount=0.0;
				}

				Satisfaction sati=new Satisfaction();
				sati.setSummaryGrade(summaryCount);
				sati.setSatisfactionSummary(summarySatis);
				sati.setUnsatisfactedSummary(summaryUnsatis);
				sati.setId(id);
				//this.baseDao.update(sati);
				String sql2="update satisfaction set summaryGrade=:summaryGrade ,satisfactionSummary=:satisfactionSummary ,unsatisfactedSummary=:unsatisfactedSummary where id=:id";
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("summaryGrade", summaryCount);
				params.put("satisfactionSummary", summarySatis);
				params.put("unsatisfactedSummary", summaryUnsatis);
				params.put("id", id);
				this.baseDao.executeSql(sql2, params);

			}

		} catch (Exception e) {  
			e.printStackTrace();  
		}  

	}

}
