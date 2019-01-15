package com.yc.task.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;


import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yc.batch.ClassBatch;
import com.yc.batch.MessageItemBatch;
import com.yc.batch.TeacherInterviewBatch;
import com.yc.batch.TearcherBatch;
import com.yc.po.Work;
import com.yc.task.WorkTask;
import com.yc.vo.Workdetail;
@Service
public class WorkTaskImpl implements WorkTask{

	@Resource(name="classBatch")
	private ClassBatch classbatch;
	
	@Autowired
	private TearcherBatch tearcherBatch;
	
	@Autowired
	private TeacherInterviewBatch teacherInterviewBatch;//教师访谈记录
	
	@Autowired
	private MessageItemBatch mBatch;

	
	


	public ClassBatch getClassbatch() {
		return classbatch;
	}



	public void setClassbatch(ClassBatch classbatch) {
		this.classbatch = classbatch;
	}



	public TearcherBatch getTearcherBatch() {
		return tearcherBatch;
	}



	public void setTearcherBatch(TearcherBatch tearcherBatch) {
		this.tearcherBatch = tearcherBatch;
	}
	
	public TeacherInterviewBatch getTeacherInterviewBatch() {
		return teacherInterviewBatch;
	}

	public void setTeacherInterviewBatch(TeacherInterviewBatch teacherInterviewBatch) {
		this.teacherInterviewBatch = teacherInterviewBatch;
	}
	
	public MessageItemBatch getmBatch() {
		return mBatch;
	}



	public void setmBatch(MessageItemBatch mBatch) {
		this.mBatch = mBatch;
	}


	@Scheduled(cron= "0 30 22 * * ?")   //每天晚上十点30执行一次   
	@Override
	public void task() {
		try {
			classbatch.test();	//班级作业汇总
			tearcherBatch.test(); //教师作业汇总
			teacherInterviewBatch.test();//教师访谈
			mBatch.test();
			//老师访谈记录定时
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



}
