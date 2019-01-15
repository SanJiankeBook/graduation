package com.yc.biz;

import java.util.List;

import com.yc.po.PointInfo;
import com.yc.vo.DataGaidModel;
import com.yc.vo.PointInfoModel;

public interface PointInfoBiz {
	/**
	 * 根据课程编号，查询本课程的知识点信息
	 * @param sid：学期编号
	 * @param cid：课程编号
	 * @return
	 */
	public DataGaidModel getPointInfo(PointInfoModel pm);
	/**
	 * 根据课程编号，查询本课程的知识点信息
	 * @param sid：学期编号
	 * @param cid：课程编号
	 * @return
	 */
	public List<PointInfo> getPointInfo(int cid);
	/**
	 * 根据试卷中试题的编号字符串，查询题目
	 * @param cid：试卷中试题的编号字符串
	 * @return
	 */
	public List<PointInfo> findPointInfo(String cid);
	
	/**
	 * 根据知识点编号，删除知识点信息
	 * @param pid
	 * @return
	 */
	public int delPointInfoById(PointInfo p);
	
	
	/**
	 * 根据知识点编号，修改知识点信息
	 * @param p：知识点对象
	 * @return
	 */
	public int updatePointInfo(PointInfo p);
	
	/**
	 * 根据知识点编号，修改知识点内容
	 * @param cid:课程编号
	 * @param pcontent：知识点内容
	 * @param pid：知识点编号
	 * @return
	 */
	public int updatePointInfoById(String cid,String pcontent,String pid);
	
	/**
	 * 添加知识点
	 * @param sid：课程编号
	 * @param pcontent：知识点内容
	 * @return
	 */
	public int addPointInfo(PointInfo p);
	
	
	/**
	 * 根据知识点编号查知识点内容
	 * @param pid
	 * @return
	 */
	public String findPointInfoById(int pid);
	
	/**
	 * 根据知识点编号查询知识点信息，remark中存放的是学期,flag中存放课程名称
	 * @param pid：知识点编号
	 * @return
	 */
	public PointInfo findPointAllInfoById(int pid);
	/**
	 * 根据多个pid来查pointinfo的集合
	 * @param pids 一个pid的list集合
	 * @author fanhaohao
	 */
	public List<PointInfo> findPointAllInfoByPids(List<Integer> pids);
}
