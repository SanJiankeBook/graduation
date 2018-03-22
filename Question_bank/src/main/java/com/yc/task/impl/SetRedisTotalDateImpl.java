package com.yc.task.impl;

import javax.annotation.Resource;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yc.biz.impl.StudentCheckTotalBizImpl;
import com.yc.task.SetRedisTotalDate;
@Service
public class SetRedisTotalDateImpl implements SetRedisTotalDate {

	private StudentCheckTotalBizImpl sctbi;
	@Resource(name="studentCheckTotalBizImpl")
	public void setSctbi(StudentCheckTotalBizImpl sctbi) {
		this.sctbi = sctbi;
	}

	@Scheduled(cron= "0 10 21 * * ?") //每天晚九点一十运行
	@Override
	public void RedisTotalDate() {
		this.sctbi.setTotalInfo();
	}

}
