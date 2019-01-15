package com.yc.biz;

import java.util.List;
import com.yc.po.ExaminInterviewRecord;
import com.yc.po.Work;
import com.yc.po.WritingAnswer;
import com.yc.vo.Page;
import com.yc.vo.Workdetail;
import com.yc.vo.TeacherInterviewdetail;

/**
 * 访谈记录业务层接口
 * @author pengtao
 *
 */
public interface ExaminInterviewRecordBiz {
	
	/**
	 * 将访问记录添加进数据库
	 * 
	 * @param er 存放着要插入的数据
	 */
public void addinfo(ExaminInterviewRecord er);

/**
 * 通过条件查询访问记录
 * @param page  存放着每页数据的条数和开始页数
 * @param er 存放着条件数据
 * @return 
 */
public Page<ExaminInterviewRecord> findinfo(Page<ExaminInterviewRecord> page,ExaminInterviewRecord er);

/**
 * 统计访谈记录
 * @param className 
 * @param page
 * @param er
 * @return
 */
public List findinfo(String className);

/**
 * 根据id查询访问记录
 * @param id
 * @return
 */
public  List showinfo(Integer id);

/**

 * 输入条件，显示访谈记录
 * @param year
 * @param month
 * @param path
 * @return
 */
public List<Workdetail> getclassWorkdetail(String year, String month, String path);
/**
 * 以班级和时间为条件显示数据
 * @param className
 * @param year
 * @param month
 * @return
 */
public List<ExaminInterviewRecord> findWorkdetail(int className, int year, int month);

	/**
	 * 获取所有的访谈记录
	 */
	public List<TeacherInterviewdetail> getTeacherInterviewdetail(String year,String month);
	
	/**
	 * 根据教师名字和日期查找老师
	 * @param teachername
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ExaminInterviewRecord> getTeacherInterviewByName(String teachername,int year,int month);
	/**
	 * 根据班级名查询班级人数
	 * @param integer
	 * @return
	 */
	public List findCount(Integer integer);
}
