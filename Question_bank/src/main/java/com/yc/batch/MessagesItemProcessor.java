package com.yc.batch;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yc.vo.Workdetail;


//业务层
@Component("messagesItemProcessor")
public class MessagesItemProcessor implements ItemProcessor<Workdetail, Workdetail> {
 
	

	public Workdetail process(Workdetail workdetail) throws Exception {
        return workdetail;
    }
 
}