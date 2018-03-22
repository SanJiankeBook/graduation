package com.yc.batch;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.yc.vo.TeacherInterviewdetail;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;

//业务层
@Component("teacherInterviewProcessor")
public class TeacherInterviewProcessor implements ItemProcessor<TeacherInterviewdetail, TeacherInterviewdetail> {

	@Override
	public TeacherInterviewdetail process(TeacherInterviewdetail teacherInterviewdetail) throws Exception {
		 
		return teacherInterviewdetail;
	}
}