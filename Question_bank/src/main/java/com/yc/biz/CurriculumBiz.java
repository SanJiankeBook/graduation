package com.yc.biz;

import java.util.List;

import com.yc.po.Curriculum;
import com.yc.po.TeacherCurriModel;

public interface CurriculumBiz {
	/**
	 * 插入课表
	 * @param list
	 * @return
	 */

	public Integer insertIntoCurriculum (List<Curriculum> list);

	/**
	 * 查询已经上的有效数据
	 * @param cid ：班级ID
	 * @param date：日期
	 * @param timeperiods：时间
	 * @param num：数量
	 * @return
	 */
	public List<Curriculum> getCurriculum(Integer cid,String date,String timeperiods,Integer num);
	
	 
	
	/**
	 * 获取老师/班级/教室 的课表信息
	 * @param startDate：起始日期
	 * @param endDate：终止日期
	 * @param teacherid：老师id
	 * @param status： 0 老师  1 班级  2 教室
	 * @return
	 */
	public List<String> getCurriInfo(String startDate,String endDate,String id,Integer status); 
	
	/**
	 * 获取老师的课表信息
	 * @param startDate：起始日期
	 * @param endDate：终止日期
	 * @param name：老师名称
	 * @return
	 */
	public List<String> getAllTeacherCurriInfo(String startDate,String endDate,String name);

	/**
	 * 得到月的课时总和
	 * @param startTime ：起始日期
	 * @param endTime：终止日期
	 * @return
	 */
	public int getTotalCount(String startTime, String endTime); 

	
	/**
	 * 更新课表信息 
	 */
	public Integer updateCurri(Curriculum curriculum);
	/**
	 * 课表删除
	 * @param currId ：课表ID
	 * @return
	 */
	public Integer deleteCurri(Integer currId);
	/**
	 * 判断是否已经插入过课程了
	 * @param curriculum
	 * @return
	 */
	public List<Curriculum> isHaveCurris(Curriculum curriculum);
	
}
