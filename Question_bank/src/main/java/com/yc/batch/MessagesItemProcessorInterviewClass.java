package com.yc.batch;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yc.vo.TeacherWorkdetailInterview;
import com.yc.vo.Workdetail;


//业务层
@Component("itemProcessorInterview")
public class MessagesItemProcessorInterviewClass implements ItemProcessor<TeacherWorkdetailInterview, TeacherWorkdetailInterview> {
 
	

	public TeacherWorkdetailInterview process(TeacherWorkdetailInterview workdetail) throws Exception {
    	/*System.out.println("==================");
    	Workdetail work=new Workdetail();
    	System.out.println(workdetail);
    	work.setCheckcount(100);
    	work.setClasscount(50);
    	work.setWorkcount(10);;
    	work.setClassName("ssss");
    	System.out.println(work);*/
    /*	System.out.println("==================");
    	System.out.println(workdetail);
    	System.out.println("==================");*/
        return workdetail;
    }
 
}