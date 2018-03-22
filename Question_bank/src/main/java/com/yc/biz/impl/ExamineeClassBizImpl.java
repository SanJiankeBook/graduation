package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yc.biz.ExamineeClassBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.utils.ValueUtil;

@Service("examineeClassBiz")
public class ExamineeClassBizImpl implements ExamineeClassBiz {
	@Resource(name="baseDao")
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public int getClassIdOfname(String className) {
		String[] params=new String[]{className};
		int classID = 0;
		String sql = "select e.id from ExamineeClass e where e.className=?";
		List list=(List) this.baseDao.search(sql,params);
		if( list!=null&&list.size()>0 ){
			classID= (Integer)  (      list.get(0) );
		}		
		return classID;
	}

	@Override
	public int searchExamineeCount(String className) {
				int studentCount = 0;
				String sql="select studentcount from examineeclass where className =:className and studentStatus in(0,1,2,3)";
				//String[] params=new String[]{className};
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("className", className);
				List<Integer> list=(List<Integer>)this.baseDao.findDatabyhql(sql, map, null, null);
				if(list!=null&&list.size()>0){
					studentCount=list.get(0);
				}
				return studentCount;
	}

	@Override
	public List<String> searchExamineeClassName(String semester) {
		List<String> list = new ArrayList<String>();
		//按开班时间的降序排序，后开的班显示在前面
		String sql="select className from ExamineeClass where semester = ? and overDate > now()  order by createDate desc";
		String[] params=new String[]{semester};
		list=(List<String>) baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	public List<ExamineeClass> findExamineeClassBySemester(String semester) {
		String sql = "select * from ExamineeClass e  where 1=1 ";
		Map<String,Object> map=new HashMap<String,Object>();
		if(semester!=null&&!"".equals(semester)){
			sql+="and e.semester=:semester";
			map.put("semester", semester);
		}
		sql+=" and overDate > now() order by createDate desc";
		List<ExamineeClass> list=this.baseDao.findDatabyhql(sql, map,new ExamineeClass(), null, null);
		if( list!=null&&list.size()>0 ){	
			return list;
		}	
		return null;
	}
	public List<ExamineeClass> findExamineeClassAndClassIdBySemester(String semester) {
		String sql = "select * from ExamineeClass e  where 1=1 ";
		Map<String,Object> map=new HashMap<String,Object>();
		if(semester!=null&&!"".equals(semester)){
			sql+="and e.semester=:semester";
			map.put("semester", semester);
		}
		sql+=" and overDate > now() order by createDate desc";
		List<ExamineeClass> list= this.baseDao.findDatabyhql(sql, map,new ExamineeClass(), null, null);
		if( list!=null&&list.size()>0 ){	
			return list;
		}	
		return null;
	}

	@Override
	public List<String> searchAllExamineeClassName() {
		List<String> list = new ArrayList<String>();
		String sql="select className from ExamineeClass  where overDate > now() order by createDate desc";
		list=(List<String>) baseDao.search(sql);

		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	@Override
	public List<String> searchAllExamineeClassNameAndId() {
		List<String> list = new ArrayList<String>();
		//按开班时间的降序排序，后开的班显示在前面
		String sql="select e.id,e.className from ExamineeClass e where e. overDate > now() order by e.createDate desc";
		list=(List<String>) baseDao.search(sql);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public ExamineeClass findExamineeClassById(Integer cid) {
		String sql="from ExamineeClass where id=?";
		String[] params=new String[]{cid+""};
		List<ExamineeClass> list=baseDao.search(sql, params);
		if(list!=null&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ExamineeClass> findAllExamineeClass() {
		String sql="from ExamineeClass where overDate > now() ";
		String[] params=new String[]{};
		List<ExamineeClass> list=baseDao.search(sql, params);
		if(list!=null&list.size()>0){
			return list;
		}
		return null;
	}





	@Override
	public List<ExamineeClass> findAllClass() {
		String sql="from ExamineeClass where overDate > now() ";
		List<ExamineeClass> list=this.baseDao.findByProperty(sql, null, null, null);
		return list;
	}


	/**
	 * 通过ID找出对应的班级名称
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public String getClassNameById(int id) {
		String[] params=new String[]{id+""};
		String sql = "select className from ExamineeClass where id=?";
		List<String> list=(List<String>) this.baseDao.search(sql,params);
		return list.get(0);
	}



	@Override
	public void updateExamineeClass(ExamineeClass examineeClass) {
		baseDao.update(examineeClass);

	}

	@Override
	public void addExamineeClass(ExamineeClass examineeClass) {
		baseDao.add(examineeClass);

	}

	@Override
	public int searchExamineeCount(Integer classId) {
		if(classId!=null){
			String hql=" select count(*)  from Examinee  where classId =:classId and studentStatus<3";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("classId", classId);
			int count=baseDao.findcount(hql, map);

			return count;
		}

		return 0;
	}



	@Override
	public Integer deleteExaminee(Integer classId){
		String hql= "  delete  from  ExamineeClass where id=:classId";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("classId", classId);
		Integer result=baseDao.executeSql(hql, map);

		if(result>0){
			return 1;
		}else{
			return 0;
		}
	}

	public List<ExamineeClass> findExamineeClassByTime(String overDate) {
		//String[] params=new String[]{overDate};
		String sql = "select * from ExamineeClass e where e.overDate >:overDate";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("overDate", overDate);
		List<ExamineeClass> list=this.baseDao.findDatabyhql(sql, map,new ExamineeClass(), null, null);

		if( list!=null&&list.size()>0 ){	
			return list;
		}	
		return null;
	}

	/**
	 *根据班名查找数据
	 */
	public ExamineeClass findExamineeClassByName(String classname) {
		String sql="from ExamineeClass where className=?";
		String[] params=new String[]{classname+""};
		List<ExamineeClass> list=baseDao.search(sql, params);
		if(list!=null&list.size()>0){
			return list.get(0);
		}
		return null;
	}


	//原生update
	@Override
	public void updateClassSQL(Integer id, String className) {
		String sql="update examinee set classId=(select id from examineeclass where className=:className) where id=:id";
		/*List<String> params=new ArrayList();
		params.add(className);
		params.add(id+"");*/
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("className",className);
		map.put("id",id);
		baseDao.executeSql(sql, map);
	}

	//查询可以转班的班级
	public List<ExamineeClass> findExamineeClassByEdit(String edit) {
		edit=edit.substring(0,edit.length()-3);
		//String[] params=new String[]{};
		String sql = "select * from ExamineeClass e where e.editionId in(select id from Edition where editionName like :editionName ) and overDate> :overDate order by createDate desc";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("editionName", edit+"%");
		map.put("overDate", ValueUtil.getNowDate());
		List<ExamineeClass> list=(List<ExamineeClass>) this.baseDao.findDatabyhql(sql, map,new ExamineeClass(), null, null);
		if( list!=null&&list.size()>0 ){	
			return list;
		}	
		return null;
	}

	//更新班级注意事项
	@Override
	public Integer addClassNotice(Integer classid, String content) {
		String sql="update examineeclass set notice=:notice where id= :id";
		//String sql="update examineeclass set notice=? where id=? ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("notice", content);
		map.put("id", classid);
		/*List<Object> params=new ArrayList<Object>();
			params.add(content);
			params.add(classid);
			Integer result=this.baseDao.executeBatchSqlWithListParams(sql, params);*/
		Integer result=this.baseDao.executeSql(sql, map);

		return result;
	}
	//根据班级ID查询班级注意事项
	public String getClassNotice(Integer classid){
		String sql="select * from  ExamineeClass where id=:id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", classid);
		ExamineeClass examineeClass=new ExamineeClass();
		List result=this.baseDao.findDatabyhql(sql, map, null, null);
		Object[]  obj = (Object [])result.get(0);
		//obj中保存的是查询出的对象
		/*for(int i=0;i<obj.length;i++){
			System.out.println(obj[i]);
			
		}*/
		
		//ExamineeClass ec = (ExamineeClass)obj;
		//System.out.println(ec.getNotice());
		if(result!=null){
			//System.out.println(ec);
			return obj[9].toString();
		}
		return null;
	}

	//查询所有班级的注意事项
	public List<String> getAllClassNotice(){
		String sql="select id,className ,notice from examineeclass where overDate> :overDate" ;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("overDate", ValueUtil.getNowDate());

		List<String> result=this.baseDao.findDatabyhql(sql, map, null, null);
		return result;
	}


	public int searchSingleClassExamineeCount(Integer classId) {
		if(classId!=null){
			String hql=" select count(*)  from Examinee  where classId = :classId and studentStatus<3";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("classId", classId);

			int count=baseDao.findcount(hql, map);
			return count;
		}
		return 0;
	}

	public int searchSingleClassExamineeCountByClassName(String className) {
		if(className!=null){
			String hql=" select count(*)  from Examinee  where classId=(select id from ExamineeClass where className=:className) and studentStatus<3";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("className", className);
			int count=baseDao.findcount(hql, map);
			return count;
		}
		return 0;
	}

	@Override
	public ExamineeClass findExamineeClassByStuId(Integer studentId) {
		String sql="select * from ExamineeClass where Id=(select classId from Examinee where id=:id)";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", studentId);

		List<ExamineeClass>  list=this.baseDao.findDatabyhql(sql, map,new ExamineeClass(), null, null);
		if(list.size()>0){
			return list.get(0);
		}
		return (ExamineeClass) Collections.emptyList();
	}

	@Override
	public void updateclasscount(Integer classcount, Integer classid) {
		String sql="update examineeclass set studentcount=:studentcount where id=:id ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", classid);
		map.put("studentcount", classcount);
		this.baseDao.executeSql(sql, map);
	}

	@Override
	public void updateclasscount(Integer classcount, String className) {
		String sql="update examineeclass set studentcount=:studentcount where className=:className ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("className", className);
		map.put("studentcount", classcount);
		this.baseDao.executeSql(sql, map);
	}

	@Override
	public List getInfo(String examClass) {
		String sql="select * from ExamineeClass where className=:id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", examClass);
		List  list=this.baseDao.findDatabyhql(sql, map, null, null);
		return list ;
	}
}
