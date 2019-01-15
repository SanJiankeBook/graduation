package com.yc.task.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.jexl2.Main;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import com.yc.task.SetTimeTask;
import com.yc.utils.ReadProperties;
import com.yc.utils.ValueUtil;


@Service
public class SetTimeTaskImpl implements SetTimeTask {

	private static final Logger logger = Logger.getLogger(SetTimeTaskImpl.class);
	
	
	@Scheduled(cron= "0 30 20 * * ?") //每月晚上十点20触发
	@Override
	public   void  task() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		String startTime = ValueUtil.getDate(year, month - 1, 26);// 得到上上一个月
		String endTime = ValueUtil.getDate(year, month, 25);// 得到上一个月
		ReadProperties.writeData("connectionConfig.properties", "detail_startime",startTime);//设置起始时间
		ReadProperties. writeData("connectionConfig.properties", "detail_endtime",endTime);//设置结束时间
		ReadProperties. writeData("connectionConfig.properties", "detail_time",year+"-"+(month+1));//设置年月用来匹配文件名称

	}


}
