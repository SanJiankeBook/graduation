package com.yc.biz;

import java.io.Serializable;
import java.util.List;

import com.yc.po.ADailyTalk;
import com.yc.po.Satisfaction;
/**
 * 
 * @author 郑笑
 *
 */
public interface SatisfactionBiz {

	//插入满意度表
	public Integer insertSatisfaction(Satisfaction satisfaction);
	//显示老师满意度表
	public List<Satisfaction> showTeacherSatisfaction(Satisfaction satisfaction);
	//显示班级满意度表
	public List<Satisfaction> showClassSatisfaction(Satisfaction satisfaction);
	//根据时间和用户id 判断满意度表是否开启
	public List<Satisfaction> isOpenSatisfaction(String time,String userid);
	//查询满意度表汇总的数据
	public List<Satisfaction> findSummaySatisInfo(String year,String month);
	//查询满意度表汇总的细节数据（满意 不满意）
	public Satisfaction findSummaySatisInfoDetail(String year,String month,Integer id) ;
	
}
