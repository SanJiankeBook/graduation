package com.yc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.biz.impl.StudentCheckTotalBizImpl;
import com.yc.po.ExamineeClass;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;
import com.yc.task.SummayTask;
import com.yc.task.impl.JobTaskImpl;
import com.yc.utils.ExamUtil;
import com.yc.utils.JedisUtils;
import com.yc.utils.ValueUtil;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml", "classpath*:batch.xml"})  
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@Transactional
public class DBConnTest_hwh_student {
	private SummayTask task;
	private Job job1;
	private Job job2;
	private Job job3;
	private Job job4;
	private WritingPaperBiz writingPaperBiz;
	private WritingQuestionBiz writingQuestionBiz;
	private JobLauncher launcher;
	private Jedis jedis=new Jedis(JedisUtils.REDIS_URL,JedisUtils.REDIS_PORT);
	
	private JobTaskImpl jobTaskImpl;

	
	public JobTaskImpl getJobTaskImpl() {
		return jobTaskImpl;
	}
	@Resource(name="jobTaskImpl")
	public void setJobTaskImpl(JobTaskImpl jobTaskImpl) {
		this.jobTaskImpl = jobTaskImpl;
	}

	private StudentCheckTotalBizImpl scbi;
	
	@Resource(name="studentCheckTotalBizImpl")
	public void setScbi(StudentCheckTotalBizImpl scbi) {
		this.scbi = scbi;
	}
/**
 * writerclassJob
 * writerclassJobInterview
 * writerteacherJob
 * writerteacherInterview
 * @param job
 */
	@Resource(name="writerclassJob")
	public void setJob1(Job job1) {
		this.job1 = job1;
	}
	@Resource(name="writerclassJobInterview")
	public void setJob2(Job job2) {
		this.job2 = job2;
	}
	@Resource(name="writerteacherJob")
	public void setJob3(Job job3) {
		this.job3 = job3;
	}
	@Resource(name="writerteacherInterview")
	public void setJob4(Job job4) {
		this.job4 = job4;
	}
	
	@Autowired 
	public void setLauncher(JobLauncher launcher) {
		this.launcher = launcher;
	}
	
	@Autowired 
	public void setTask(SummayTask task) {
		this.task = task;
	}

	@Autowired 
	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}



	public WritingPaperBiz getWritingPaperBiz() {
		return writingPaperBiz;
	}


	@Autowired 
	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

	@Test //测试spring batch
	public void test_springbatch1() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobParameters jobParameters =
				new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis()).toJobParameters();
		JobExecution result = launcher.run(job1, jobParameters);
		System.out.println("---------");
		System.out.println(result.toString());
	}
	@Test //测试spring batch
	public void test_springbatch2() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobParameters jobParameters =
				new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis()).toJobParameters();
		JobExecution result = launcher.run(job2, jobParameters);
		System.out.println("---------");
		System.out.println(result.toString());
	}
	@Test //测试spring batch
	public void test_springbatch3() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobParameters jobParameters =
				new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis()).toJobParameters();
		JobExecution result = launcher.run(job3, jobParameters);
		System.out.println("---------");
		System.out.println(result.toString());
	}
	@Test //测试spring batch
	public void test_springbatch4() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobParameters jobParameters =
				new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis()).toJobParameters();
		JobExecution result = launcher.run(job4, jobParameters);
		System.out.println("---------");
		System.out.println(result.toString());
	}
	
	@Test //测试spring batch
	public void test_springjob() {
		jobTaskImpl.jobtask();
	}
	@Test //测试spring batch
	public void test_redis() {
		jedis.connect();
		System.out.println(jedis.isConnected());
	}
	
	/*@Test
	public void test_redis(){
		this.scbi.setTotalInfo();
	}*/

/*	@Test //测试定时器  --满意度表
	public void test_task(){
		task.task();
		 String summarySatis=null;
		 summarySatis+="aaa";
		 System.out.println(summarySatis);
	}
	*/
	
	
/*	@Test //测试hibernate框架   测试查询学生成绩
	public void testClass() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String userName = "李文文";
		String examClass ="YC_30";
		String paperType = "writ";
		ArrayList arr = new ArrayList();
		arr =  (ArrayList) writingPaperBiz.searchExaminneWritAchievement("", examClass, userName,
				ExamUtil.PAPER_STATUS_VIEWED);
		assert arr.size()>0:"程序出错";
	}
	*/
	/*@Test //测试hibernate框架   测试学生
	public void testfind() {
		List<Integer> listid = new ArrayList<Integer>();
		String questionId="[1190,1191,305,291,320,340,532,348,321,323,654,653,1234,154,655,1235,1232,1233,290,299,289,309,326,1508,1494,1498,318,342,1499,1492,327,1497,237,1500,1495,159,1496,530,531,1493,157,1484,1482,1481,1477,1479,1480,1475,1483,1476,1478,325,316,296,1189,1187,242,1188,158,546]";
		JSONArray jsonArray = JSON.parseArray(questionId);
		for (Object o : jsonArray) {
			listid.add(Integer.parseInt(o.toString()));
		}
		List<WritingQuestion> list = writingQuestionBiz.findWritingQuestionByIds(listid);
		assert list.size()>0:"程序出错";
	}*/

}
