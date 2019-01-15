package com.yc.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yc.biz.JobBiz;
import com.yc.po.Job;
import com.yc.task.JobTask;
import com.yc.utils.JedisUtils;
import com.yc.utils.MapConfig;
import com.yc.vo.JobPool;

@Service("jobTaskImpl")
public class JobTaskImpl implements JobTask{

	@Resource(name="jobBiz")
	private JobBiz jobBiz;

	public JobBiz getJobBiz() {
		return jobBiz;
	}

	public void setJobBiz(JobBiz jobBiz) {
		this.jobBiz = jobBiz;
	}

	@Scheduled(cron= "00 10 11 * * ?") //每月晚上十点20触发
	@Override
	public void jobtask() {
		try {
			Map<String,Integer> map =MapConfig.getInstance();
			JedisUtils ju=JedisUtils.getInstance();
			List<Job> list=this.jobBiz.findJobPool();
			Set<String> keys=map.keySet();
			for(String key:keys){
				JobPool jobPool=new JobPool(); 
				jobPool.setName(key);
				jobPool.setId(map.get(key));
				jobPool.setWorknum(0);
				jobPool.setSalary(0);
				ju.set(jobPool, key);
			}
			for(Job job:list){
				JobPool jobPool=new JobPool(); 
				Integer id=map.get(job.getProvince());
				jobPool.setId(id);
				jobPool.setWorknum(job.getId());
				jobPool.setName(job.getProvince());
				if(job.getId()!=0){
					jobPool.setSalary(job.getSalary()/job.getId());
				}
				ju.set(jobPool, job.getProvince());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
