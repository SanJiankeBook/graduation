package com.yc.biz;

import java.util.List;

import com.yc.po.PointPaper;
import com.yc.po.PointPaperTemplate;
import com.yc.vo.DataGaidModel;
import com.yc.vo.Page;
import com.yc.vo.PointPaperModel;

public interface PointPaperBiz {
	/**
	 * 添加测评试卷
	 * @param params：参数列表
	 * @return
	 */
	public int addPointPaper(PointPaper po);

	/**
	 * 查询所有测评试卷信息
	 * @return
	 */
	public DataGaidModel getAllPointPaper(PointPaperModel pm);


	/**修改试卷的状态
	 * @param pid：试卷编号
	 * @param status：状态码
	 * @return
	 */
	public int updatePointPaperStatus(PointPaper po);


	/**
	 * 根据试卷编号删除试卷信息
	 * @param pid：要删除的试卷编号
	 * @return
	 */
	public int delPointPaperById(PointPaper po);


	/**
	 * 根据课程号、测试班级、测试日期查询测试试卷
	 * @param sid
	 * @param cid
	 * @param date
	 * @return
	 */
	public List<PointPaper> findPointPaper(int sid,int cid,String date);

	/**
	 * 根据试卷查询试卷信息
	 * @param pid
	 * @return
	 */
	public PointPaper findPointPaperById(Integer pid);


	/**
	 * 通过试卷编号，修改试卷信息
	 * @param params
	 * @return
	 */
	public int updatePointPaper(PointPaper po);


	/**
	 * 根据考试姓名和班级，查询出此考生没有测评的试卷信息
	 * @param name
	 * @param classid
	 * @return
	 */
	public List<PointPaper> findOpenPointPaper(String name,String classid);


	/**
	 * 根据考试姓名和班级，查询出此考生已经测评的试卷信息
	 * @return
	 */
	public List<PointPaper> findEndPointPaper(String name,String classid);
	
	/**
	 * 根据课程名和考试班级查询考卷信息
	 * @param subjectid：课程编号
	 * @param classid：考试班级
	 * @return
	 */
	public List<PointPaper> findPointPaperBySidAndCid(int subjectid,int classid);
	
	/**
	 * 通过试卷编号、考生姓名和班级查询是否已考
	 * @param pid：试卷编号
	 * @param name：考生姓名
	 * @param cid：考试班级
	 * @return
	 */
	public int findMyPointPaper(int pid,String name,int cid);
	
	/**
	 * 根据试卷编号，查询试卷信息。remark中存放该试卷的考试班级,flag中存放的是课程名称
	 * @param pid
	 * @return
	 */
	public PointPaper getPointPaperInfo(int pid);

	/**
	 * 根据pid来取title
	 * @param pid
	 * @return
	 */
	public String getPointPaperTitleByPid(int pid);
	
	/**
	 * 存储过程要用的方法
	 * 查找sid
	 */
	public int findSid(int pid,int cid);
	/**
	 * 添加模板卷
	 * @param po
	 */
	public void addPointPaper(PointPaperTemplate po);
	
	/**
	 * 获取所有符合条件的模板卷
	 * @param pointPaperModel
	 * @return
	 */
	public void getAllPointPaperTemplate(Page<PointPaperTemplate> page, PointPaperTemplate wp);
	/**
	 * 根据试卷编号得到试卷内容
	 * @param pid
	 * @return
	 */
	public PointPaperTemplate findPointPaperByIdTemplate(Integer pid);
}
