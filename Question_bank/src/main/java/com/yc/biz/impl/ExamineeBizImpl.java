package com.yc.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.ExamineeBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.vo.ExamineePage;

@Service("examineeBiz")
public class ExamineeBizImpl implements ExamineeBiz {

	private BaseDao baseDao;
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	

	@Override
	public boolean isClassExaminee(String name, int classId) {
		boolean isExists = false;
		String[] params = new String[] { (classId + ""), name };
		String sql = "from Examinee e where e.examineeClass.id =? and e.name =?";
		List<Examinee> list = (List<Examinee>) this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			isExists = true;
		}
		return isExists;
	}

	@Override
	public boolean isClassExaminee(String name, int classId, String password) {
		boolean isExists = false;
		String[] params = new String[] { (classId + ""), name };
		// 去除bug，先根据name和id找password，再比较password
		String sql = "select password from Examinee e where e.classid=? and e.name=?";
		String pass = "xxxxxxxx";
		List list = (List) this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			pass = (String) list.get(0);
		}
		if (password.equals(pass)) {
			isExists = true;
		}
		return isExists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Examinee getExaminee(String name, String password, String className) {
		List<Examinee> list = null;
		
		if (password == null) {
			String hql = "from Examinee e where e.name=?  and e.examineeClass.className=?";
			String params[] = { name, className };
			list = (List<Examinee>) baseDao.search(hql, params);
		} else {
			String hql = "from Examinee e where e.name=? and e.password=? and e.examineeClass.className=?";
			String params[] = { name, password, className };
			list = (List<Examinee>) baseDao.search(hql, params);
		}

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Examinee> findAllStudent(int classId) {
		String hql = "select * from examinee where  classid= :id  ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", classId);
		List<Examinee> list = baseDao.findDatabyhql(hql, map, new Examinee(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<Examinee> findAllStudentByClassName(String className) {
		String hql = "from Examinee e where  e.examineeClass.className=?  ";
		String params[] = { className };
		List<Examinee> list = (List<Examinee>) baseDao.search(hql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List searchExaminee(int classId) {
		// String hql="from Examinee e where e.classId=?";
		// String params[]={classId+"" };
		// baseDao.search(hql, params);

		return null;
	}
	
	public List searchExamineeByExamineeName(String name) {
		String hql = "from Examinee e where  e.name=? ";
		String params[] = {name };
		List<Examinee> list = (List<Examinee>) baseDao.search(hql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public int updateExaminee(Examinee examinee) {
		try {

			baseDao.update(examinee);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public boolean isExists(Examinee examinee) {
		return false;
	}

	@Override
	public int updateClassPwd(String className, String pwd) {
		return 0;
	}

	@Override
	public int searchClassid(String classname) {
		String hql = "from ExamineeClass ec where  ec.className=?";
		String params[] = { classname };
		List<ExamineeClass> list = (List<ExamineeClass>) baseDao.search(hql, params);
		if (list != null && list.size() > 0) {
			return list.get(0).getId();
		}
		return 0;
	}

	@Override
	public int getExamineeCount(int classId) {
		String sql="select count(*) from Examinee where classid=:classid and  studentStatus in(0,1,2)"; 
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("classid", classId);
		return this.baseDao.findcount(sql, params);
	}

	@Override
	public List searchClassExaminee(int clasid) {
		return null;
	}

	@Override
	public int addExaminee(String examName, String password, int classId, String idCard, String tel) {
		Examinee examinee = new Examinee();

		examinee.setIdCard(idCard);
		examinee.setTel(tel);
		examinee.setName(examName);
		examinee.setPassword(password);
		ExamineeClass examineeClass = new ExamineeClass();
		examineeClass.setId(classId);
		examinee.setExamineeClass(examineeClass);
		System.out.println(examinee);
		try {
			baseDao.saveOrUpdate(examinee);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean updatePassword(int clasid, String name, String newPassword) {
		String sql = "update Examinee e set e.password=:password where e.name=:name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", newPassword);
		params.put("name", name);
		int result = this.baseDao.executeSql(sql, params);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateTeacherPassword(String name, String newPassword) {
		String sql = "update systemuser s set s.password=:password where s.userName=:userName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", newPassword);
		params.put("userName", name);
		int result = this.baseDao.executeSql(sql, params);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List searchExamineelist(Examinee examinee, int displayRows, int nextNum) {
		return null;
	}

	@Override
	public int getSearchMaxRow(Examinee examinee) {
		return 0;
	}

	@Override
	public int deletelistexam(int classId, String name) {
		String sql = "delete from Examinee e where e.examineeClass.id=:classId and e.name=:name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("classId", classId);
		params.put("name", name);
		try {
			baseDao.executeHql(sql, params);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<String> searchExamineeNames(String className) {
		return null;
	}

	@Override
	public List<Examinee> findAllStuNameByClassName(String className) {
		String hql = "select * from Examinee e INNER JOIN ExamineeClass ec ON ec.Id=e.classId where ec.className=:className and studentStatus<3";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("className", className);
		List<Examinee> list = baseDao.findDatabyhql(hql, map, new Examinee(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<String> findAllStuNameByClassId(Integer classId) {
		String hql = "select e.name from Examinee e where  e.examineeClass.id=? and studentStatus<3";
		String params[] = { classId + "" };
		List<String> list = (List<String>) baseDao.search(hql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<Examinee> findAllStudentAndExaminee(int classId) {
		return null;
	}

	@Override
	public List<Examinee> getAllExaminee(String className) {
		String hql = "from Examinee e where  e.examineeClass.className=?";
		String params[] = { className };
		List<Examinee> list = (List<Examinee>) baseDao.search(hql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public String getPwd(String name, int classid) {
		String hql = "select e.password from Examinee e where e.name=? and  e.examineeClass.id=?";
		String params[] = { name, classid + "" };
		List<String> pwd = baseDao.search(hql, params);
		return pwd.get(0);
	}
	
	@Override
	public String getTeacherPwd(String name) {
		String hql = "select s.password from  SystemUser s where s.userName=?";
		String params[] = { name };
		List<String> pwd = baseDao.search(hql, params);
		return pwd.get(0);
	}
	@Override
	public int getStudentCount(String className, int studentStatus) {
		int count = 0;
		String hql = "select count(*) from Examinee e where  e.examineeClass.className=? and e.studentStatus= ?";
		String params[] = { className ,""+studentStatus};
		List<Long> list = (List<Long>) baseDao.search(hql, params);
		if (list != null && list.size() > 0) {
			count = list.get(0).intValue();
		}
		return count;
	}


	@Override
	public List searchExamineeByExamineeName(String name, String className) {
		String hql = "select * from examinee where  name=:name and classId=:classId ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("classId", className);
		List<Examinee> list = baseDao.findDatabyhql(hql, map, new Examinee(), null, null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}


	@Override
	public List<Examinee> getExamineeDetail(int Examineeid) {
		String sql="select * from examinee where id=:id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", Examineeid);
		return  baseDao.findDatabyhql(sql, map, new Examinee(), null, null);
	}



}