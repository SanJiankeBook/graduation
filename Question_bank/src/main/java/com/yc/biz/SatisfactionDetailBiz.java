package com.yc.biz;

import java.io.Serializable;
import java.util.List;

import com.yc.po.ADailyTalk;
import com.yc.po.SatisfactionDetail;

public interface SatisfactionDetailBiz {
	//插入细节调查表
	public Integer insertSatisfaction(SatisfactionDetail satisfactionDetail);
	//显示细节调查表
	public List<SatisfactionDetail> showSatisfactionDetail(Integer said);
	//获取一个调查表的细节调查表总计
	public Integer satisDetailCount(Integer said);
	//判断 是否已经填写过调查表
	public List<SatisfactionDetail> isWriteSatisfactionDetail(String useid,Integer said);
}
