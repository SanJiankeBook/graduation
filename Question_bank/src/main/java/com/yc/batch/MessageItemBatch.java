package com.yc.batch;

import javax.annotation.Resource;


import org.apache.commons.jexl2.Main;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageItemBatch {

	private Job job;
	private JobLauncher launcher;

	@Resource(name="writerclassJobInterview")
	public void setJob(Job job) {
		this.job = job;
	}

	@Autowired 
	public void setLauncher(JobLauncher launcher) {
		this.launcher = launcher;
	}


	public void test() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{

		JobParameters jobParameters =
				new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis()).toJobParameters();

		JobExecution result = launcher.run(job, jobParameters);
	}

}
