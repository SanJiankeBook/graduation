package com.yc.biz.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.TempBiz;
import com.yc.dao.BaseDao;
import com.yc.po.PointPaper;
import com.yc.po.Temp;
@Service("tempBiz")
public class TempBizImpl implements TempBiz{
	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}
	/**
	 * 清空temp表
	 */
	public int exectueDelTemp() {
		String hql=" delete from Temp";
		//int result=this.baseDao.executeHql(hql, null);
		int result=1;
		try {
			this.baseDao.del(new Temp());
		} catch (Exception e) {
			result=0;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 执行自评存储过程
	 * @param pid：自评试题编号
	 * @param cid：自评班级
	 * @param uname：自评学员姓名
	 */
	public void exectueProc(int pid, int cid, String uname) {
		try {
		String sql="{ Call pr_info(?,?,?) }";
		String[] params = new String[] { pid+"",cid+"",uname };
		this.baseDao.excutePro(sql,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行考勤模块存储过程
	 * @param checkId：考勤记录编号
	 */
	public void exectueCheckingProc(int checkId) {
		try {
			String hql="{ Call pr_checkInfo(?) }";
			String[] params = new String[] { checkId+"" };
			this.baseDao.excutePro(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统计临时表中数据的总数
	 * @return
	 */
	public int getTotalInfo() {
		String sql="select count(t) from Temp t";
		List list=this.baseDao.search(sql);
		if(list!=null&&list.size()>0){
			return (Integer) list.get(0);
		}else{
			return 0;
		}
	}

	/**
	 * 按知识点统计
	 * @return
	 */
	public List<PointPaper> getPointTotal() {
		String hql="select t.className,t.subname,t.pcontent,count(t),sum(t.grade) ,avg(t.grade)  from Temp t group by t.className,t.subname,t.pcontent";
		List<String> list=this.baseDao.search(hql);
		List<PointPaper> result=new ArrayList<PointPaper>();
		PointPaper PointPaper=null;
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				PointPaper=new PointPaper();
				PointPaper.setPname(list.get(i));  //测评班级
				PointPaper.setPtitle(list.get(++i));  //测评课程
				PointPaper.setDescript(list.get(++i)); //测评知识点
				PointPaper.setRemark(list.get(++i)); //参评人数
				PointPaper.setPaperPwd(list.get(++i));  //总分
				PointPaper.setFlag(list.get(++i));  //平均分
				result.add(PointPaper);
			}
		}
		return result;
	}

	/**
	 * 按课程统计人数
	 * @return
	 */
	public int findSubjectTotal() {
		
		int count =0;
		String hql="select count(t) from Temp t group by t.sname";
		List<Number> counts=this.baseDao.search(hql);
		
		if(counts!=null&&counts.size()>0){
			count=(counts.get(0)).intValue();
		}
		
		return count;
	}
	/**
	 * 按课程统计信息
	 * @return
	 */
	public List<String> findSubjectInfo() {
		String hql="select t.className,t.subname,count(t),sum(t.grade),avg(t.grade)  from Temp t group by t.className,t.subname";
		List<String> list=this.baseDao.search(hql);
		
		if(list!=null&&list.size()>0){
			return list;
		}
		
		return null;
	}
	/**
	 * 根据知识点统计，总分存放在sname中，平均分存放在grade中
	 * @param pid:试卷编号
	 * @return
	 */
	public List<Temp> getTotalByPoint(int pid) {
		String[] params = new String[] { pid+"" };
		String hql="select t.pcontent,sum(t.grade),avg(t.grade) from Temp t where t.ppid=? group by t.pcontent order by t.grade";
		return this.baseDao.search(hql, params);
	}

	/**
	 * 根据学生姓名统计，总分存放在className中，平均分存放在grade中，留言存放在pcontent中
	 * @param pid:试卷编号
	 * @return
	 */
	public List<Temp> getTotalBySname(int pid) {
		String[] params = new String[] { pid+"" };
		String sql="select tp.sname,SUM(tp.grade) as tp.className,avg(tp.grade),pa.idea as pcontent from Temp tp,PointAnswer pa where tp.sname=pa.name and tp.sname!='' and tp.sname!='null' and  tp.ppid=? group by tp.sname,tp.idea order by tp.grade";
		List<Temp> list=this.baseDao.search(sql);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 查询临时表中的所有数据
	 * @return
	 */
	public List<Temp> getAllTempInfo() {
		String sql="from Temp t where sname!='' and sname!='null'";
		List<Temp> list=this.baseDao.search(sql);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 查询有考勤记录的学员姓名
	 * @param cid：考勤班级
	 * @return
	 */
	public List<Temp> getAllStuname(int cid) {
		String[] params = new String[] { cid+"" };
		String sql="select t.sname from Temp t where t.sname!='' and t.sname!='null' and t.classid=? group by t.sname order by hex(subname)";
		List<Temp> list=this.baseDao.search(sql,params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 获取查询的考勤记录的考勤日期
	 * @param cid
	 * @return
	 */
	public List<Temp> getAllDateTime(int cid) {
		String[] params = new String[] { cid+"" };
		String sql="";
		sql="select ppid,pcontent,subid from Temp where rtrim(sname)!='' and rtrim(sname)!='null' and classid=? group by pcontent,subid";
		List<Temp> list=this.baseDao.search(sql, params);
		return list;
	}

	//获取所有的考勤记录
	public List<Temp> getAllCheckingInfo(int cid) {
		String[] params = new String[] { cid+"" };
		String sql=" select ppid,sname,	classid,className,subid,subname,pointid,pcontent,grade from Temp where rtrim(sname)!='' and rtrim(sname)!='null' and classid= ?  ";
		return this.baseDao.search(sql, params);
	}

	/**
	 * 查询个人记录
	 * @param uname:要查询的学生姓名
	 * @return
	 */
	public List<Temp> getSingleCheckingInfo(String uname) {
		String[] params = new String[] { uname };
		String sql="from Temp t where t.sname=?";
		List<Temp> list=this.baseDao.search(sql,params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 根据学生信息和考勤编号查询对于的信息
	 * @param ppid
	 * @param uname
	 * @return
	 */
	public Temp getSingleCheckingResultInfo(int ppid, String uname) {
		String[] params = new String[] {uname,ppid+"" };
		String sql="from Temp t where t.sname=? and t.ppid=?";
		List<Temp> tp=this.baseDao.search(sql,params);
		if(tp!=null&&tp.size()>0){
			return tp.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据学生姓名统计
	 * @param uname：要统计的学生姓名
	 * @return
	 */
	public List<Temp> getTotalByStatus(String uname) {
		String[] params = new String[] { uname };
		String sql="from Temp t where t.sname=? group by t.subid order by t.subid desc";
		List<Temp> list=this.baseDao.search(sql,params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * 根据学生姓名统计学生各自考勤状态
	 * @param uname：要统计的学生姓名
	 * @return
	 */
	public List<Temp> getTotalStatusBysname(String uname) {
		String[] params = new String[] { uname };
		String sql="select subid, count(t) as classid from Temp t where t.sname=? group by t.subid order by t.subid desc";
		List<Temp> list=this.baseDao.search(sql,params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}


	/**
	 * 根据学生姓名统计考勤记录
	 * @param uname：要统计的学生姓名
	 * @return
	 */
	public int getCheckingTotalInfo(String uname) {
		String[] params = new String[] { uname };
		String sql="select count(t)  from Temp t where t.sname=?";
		List list=this.baseDao.search(sql,params);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return 0;
		}
	}


	/**
	 * 获取警告信息
	 * @param cid
	 * @return
	 */
	public List<Temp> getWarnInfo(int cid) {
		String[] params = new String[] { cid+"" };
		String sql="SELECT COUNT(t) AS pcontent,pointid,sname FROM Temp t WHERE "+
  " classid=? AND pointid IN(2,5,6) GROUP BY sname,pointid  HAVING COUNT(t)>2 ORDER BY COUNT(t) DESC";
		List<Temp> list=this.baseDao.search(sql,params);
		if(list!=null  &&  list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<String> findTempInfoByClassNameAndSubjectId(String className,Integer subjectId) {
		String[] params = new String[] { className ,subjectId+""};
		String sql = "select avg(t.grade) ,sum(t.grade) ,count(t) ,t.pcontent from Temp t where t.className=? and t.subid=?";
		List list = this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	@Override
	public void addTemp(Temp t) {
//		String hql="";
//		Map<String, Object> param=new HashMap<String,Object>();
//		hql="insert into Temp (ppid,className, classid, grade, pcontent, pointid, sname, subid, subname) values(:ppid, :className,:classid, :grade, :pcontent, :pointid, :sname, :subid,:subname)";
//		param.put("ppid", t.getPpid());
//		param.put("className", t.getClassName());
//		param.put("classid", t.getClassid());
//		param.put("grade", t.getGrade());
//		param.put("pcontent", t.getPcontent());
//		param.put("pointid", t.getPointid());
//		param.put("sname", t.getSname());
//		param.put("subid", t.getSubid());
//		param.put("subname", t.getSubname());
//		this.baseDao.findByProperty(hql, param, null, null);
//		return 0;
		baseDao.add(t);
	}

}
