package com.yc.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yc.dao.BaseDao;
import com.yc.po.ExaminInterviewRecord;
import com.yc.po.Examinee;
import com.yc.po.TeacherCurriModel;
import com.yc.vo.Page;
import com.yc.webexam.actions.AssessmentAction;

/**
 * 利用的是原生Hibernate API操作数据库
 * 
 * @author dsp
 */

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {

	private static final Logger logger = Logger.getLogger(AssessmentAction.class);
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	@Autowired
	private SessionFactory sessionFactory;

	/*public Session getSession() {
		Session session =  threadLocal.get();
		  //判断Session是否为空，如果为空，将创建一个session，并付给线程变量tLocalsess
		try {
			session = sessionFactory.getCurrentSession();
			 threadLocal.set(session);
			return session;
		} catch (HibernateException e) {
			logger.error(e);
			session = sessionFactory.openSession();
			 threadLocal.set(session);
			return session;
		}
	}*/
	
	public Session getSession() {
		Session session =  threadLocal.get();
		  //判断Session是否为空，如果为空，将创建一个session，并付给线程变量tLocalsess
		try {
			 session = sessionFactory.getCurrentSession();
			 threadLocal.set(session);
			return session;
		} catch (HibernateException e) {
			logger.error(e);
			 session = sessionFactory.openSession();
			threadLocal.set(session);
			return session;
		}
	}
	public static void closeSession(){
		Session s = (Session) threadLocal.get();
		if (s!=null)
		   s.close();
		threadLocal.set(null);
		}

	public Serializable add(T t) {
		Serializable serial = getSession().save(t);
		return serial;
	}



	public void del(T t) {
		getSession().delete(t);
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}

	public T get(Class<T> clss, Serializable id) {
		T t = (T) getSession().get(clss, id);
		return t;
	}

	public List<T> search(String hql, String... params) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0,len=params.length; i < len; ++i) {
				query.setString(i, params[i]);//可以看出是给hql语句中的未知量赋值，类似DBHelper中的PreparedStatment
			}
		}
		return query.list();
	}
	//使用原生态语句查询
	public List<T> searchsql(String hql,T t) {
		SQLQuery query=getSession().createSQLQuery(hql).addEntity(t.getClass());
		query.setCacheable(true);
		//		if (params.length > 0) {
		//			for (int i = 0; i < params.length; ++i) {
		//				query.setString(i, params[i]);//可以看出是给hql语句中的未知量赋值，类似DBHelper中的PreparedStatment
		//			}
		//		}
		return query.list();
	}


	public List<T> searchByPro(String hql, Integer offset, Integer length, Map<String, Object> params,T t) {

		SQLQuery	query = getSession().createSQLQuery(hql).addEntity(t.getClass());
		query.setCacheable(true);
		return query.list();
	}

	public List<T> searchByfpc(String hql, Integer offset, Integer length, String... params) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0 ,len= params.length; i <len ; ++i) {
				query.setString(i, params[i]);
			}
		}
		if (offset != null) {
			query.setFirstResult(offset).setMaxResults(length);
		}
		return query.list();
	}


	@Override
	public List<T> searchs_sql(String hql, String... params) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0 ,len= params.length; i <len ; ++i) {
				query.setString(i, params[i]);
			}
		}
		return query.list();
	}



	@Override
	public List<T> searchs(String hql, int... params) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0 ,len= params.length; i <len ; ++i) {
				query.setInteger(i, params[i]);
			}
		}
		return query.list();
	}



	public List<T> searchByList(String hql, List list, String listName) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		if (list.size() > 0) {
			query.setParameterList(listName, list);

		}
		return query.list();
	}

	@Override
	public List<T> searchByPage(int pageStart, int pageSize, String hqlString) {
		Query query = getSession().createQuery(hqlString);
		query.setCacheable(true);
		query.setFirstResult(pageStart);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public void showPage(String queryHql, String queryCountHql, Page<T> page, String... params) {
		Query queryAll = getSession().createQuery(queryHql);
		queryAll.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0 ,len= params.length; i <len ; ++i) {
				String param = params[i];
				queryAll.setString(i, param);
			}
		}
		page.setResult(queryAll.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize()).setMaxResults(page.getPageSize()).list());
		Query queryAllCount = getSession().createQuery(queryCountHql);
		queryAllCount.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0 ,len= params.length; i <len ; ++i) {
				String param = params[i];
				queryAllCount.setString(i, param);
			}
		}
		page.setTotalsCount(Integer.parseInt(queryAllCount.setMaxResults(1).uniqueResult().toString()));
		page.setTotalsPage(page.getTotalsCount()/page.getPageSize()+1);
	}

	public Integer executeHql(String hql, Map<String, Object> params) {
		Query q = this.getSession().createQuery(hql);
		q.setCacheable(true);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		int a = q.executeUpdate();
		return a;
	}

	//原生update sql语句执行  命名参数
	public Integer executeSql(String sql, Map<String, Object> params) {
		Query q = this.getSession().createSQLQuery(sql);
		q.setCacheable(true);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		int a = q.executeUpdate();

		return a;
	}
	//原生sql语句 执行    序号参数  
	public Integer executeBatchSqlWithListParams(String sql,List<Object> params) {
		Query q = this.getSession().createSQLQuery(sql);
		q.setCacheable(true);
		if(  params!=null&&params.size()>0  ){
			for( int i=0,len=params.size();i<len;i++){
				q.setParameter(i, params.get(0));
			}
		}
		int a = q.executeUpdate();
		return a;
	}

	@Override
	public int searchCount(String hql) {
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		int count = ((Long) query.iterate().next()).intValue();
		return count;
	}

	public List<T> findByProperty(String hql, Map<String, Object> param, Integer offset, Integer length) {
		Query queryObject = getSession().createQuery(hql);
		queryObject.setCacheable(true);
		if (param != null && param.size() > 0) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue().toString());
			} 
		}
		if (offset != null && length != null) {
			queryObject.setFirstResult(offset).setMaxResults(length);
		}

		return queryObject.list();
	}

	@Override
	public int updateploadKnowledgeInfo(byte[] bt, int id, String filetype) {
		String sql = "update ADailyTalk set remark=:filetype where id=:id";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("filetype", filetype);
		param.put("id", id);
		Query q = this.getSession().createQuery(sql);
		q.setCacheable(true);
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			q.setParameter(entry.getKey(), entry.getValue());
		}
		return q.executeUpdate();
	}

	@Override
	public List<T> excutePro(String hql, Object... params) {
		SQLQuery query = getSession().createSQLQuery(hql);
		query.setCacheable(true);
		if (params.length > 0) {
			for (int i = 0 ,len=params.length; i <len ; ++i) {
				Object param = params[i];
				query.setString(i, (String) param);
			}
		}
		query.executeUpdate();
		return null;
	}

	public List<T> findByProperty(String hql, Map<String, Object> param, Integer offset, Integer length, String sort,
			String order) {
		Query queryObject = getSession().createQuery(hql);
		queryObject.setCacheable(true);
		if (param != null && param.size() > 0) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				queryObject.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if (offset != null) {
			queryObject.setFirstResult(offset).setMaxResults(length);
		}
		if (sort != null && order != null) {
			hql += " order by " + sort + " " + order;
		}
		List list = queryObject.list();
		return list;
	}

	//原生 实体查询  
	public List excuteSelectSql(String sql,T t){
		SQLQuery query=getSession().createSQLQuery(sql).addEntity(t.getClass());
		query.setCacheable(true);
		return query.list();
	}
	
	
	//原生查询sql语句 执行    序号参数  
	public List excuteSelectSqlWithListParams(String sql,T t,List<Object> params) {
		SQLQuery query=getSession().createSQLQuery(sql).addEntity(t.getClass());
		query.setCacheable(true);
		
		if(  params!=null&&params.size()>0  ){
			for( int i=0,len=params.size();i<len;i++){

				query.setParameter(i, params.get(0));
			}
		}
		return query.list();
	}
	

	//原生标量查询
	public List excuteSelectSqlWithoutBean(String sql){
		SQLQuery query=getSession().createSQLQuery(sql);
		List list=query.list();

		return list;
	}

	@Override
	public List findData(String sql, Map<String, Object> params,String page,String rows) {
		Query query=getSession().createQuery(sql);
		if(page!=null&&!page.equals("")&&rows!=null&&!rows.equals("")){
			query.setFirstResult((Integer.valueOf(page)-1)*(Integer.valueOf(rows)));
			query.setMaxResults(Integer.valueOf(rows));
		}
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}
		
		List list=query.list();
		return list;
	}

	@Override
	public int findcount(String sql, Map<String, Object> params) {
		SQLQuery query=getSession().createSQLQuery(sql);
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return Integer.parseInt((query.uniqueResult()).toString());  
	}

	@Override
	public List findDatabyhql(String sql, Map<String, Object> params, String page, String rows) {
		SQLQuery query=getSession().createSQLQuery(sql);
		if(page!=null&&!page.equals("")&&rows!=null&&!rows.equals("")){
			query.setFirstResult((Integer.valueOf(page)-1)*(Integer.valueOf(rows)));
			query.setMaxResults(Integer.valueOf(rows));
		}
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		
		List list=query.list();
		return list;
	}

	@Override
	public List findDatabyhql(String sql, Map<String, Object> params, T t, String page, String rows) {
		SQLQuery query=getSession().createSQLQuery(sql).addEntity(t.getClass());;
		if(page!=null&&!page.equals("")&&rows!=null&&!rows.equals("")){
			query.setFirstResult((Integer.valueOf(page)-1)*(Integer.valueOf(rows)));
			query.setMaxResults(Integer.valueOf(rows));
		}
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		//TODO:加上二级缓存
		//query.setCacheable(true);

		List list=query.list();
		return list;
	}
	
}
