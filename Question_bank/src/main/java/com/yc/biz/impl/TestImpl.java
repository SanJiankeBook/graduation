package com.yc.biz.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.po.Subject;
import com.yc.vo.TestJavaBen;
import com.yc.webexam.actions.AssessmentAction;

@Service("testBiz")
public class TestImpl  {
	private static final Logger logger = Logger.getLogger(AssessmentAction.class);
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			logger.error(e);
			return sessionFactory.openSession();
		}
	}

	
	public <T> void  insertInfo( T t){
		//这个t对象必须是与数据库表建立联系的类的对象
		/**
		 * eg:@Entity
			@Table(name="testjavaben")
		 */
		System.out.println("-------------------------------------------");
		this.getSession().save(t);
		System.out.println("-------------------------------------------");
	
	}
	public <T> void  delete( T t){
		//这个t对象必须是与数据库表建立联系的类的对象
		/**
		 * eg:@Entity
			@Table(name="testjavaben")
		 */
		System.out.println("-------------------------------------------");
		this.getSession().delete( t);
		System.out.println("-------------------------------------------");
	
	}
	public <T> void  update( T t){
		//这个t对象必须是与数据库表建立联系的类的对象
		/**
		 * eg:@Entity
			@Table(name="testjavaben")
		 */
		System.out.println("-------------------------------------------");
		this.getSession().update( t);
		System.out.println("-------------------------------------------");
	
	}
	
	public <T> void  get( Class<T> t,Serializable id){
		//这个t对象必须是与数据库表建立联系的类的对象
		/**
		 * eg:@Entity
			@Table(name="testjavaben")
		 */
		System.out.println("-------------------------------------------");
		System.out.println(this.getSession().get(t,id));
		System.out.println("-------------------------------------------");
	
	}
	
	//原生数据查询 hql hibernate拥有的数据库指令，跟sql差别不大
	public <T> void  hql( String hql,T t){
		//这个t对象必须是与数据库表建立联系的类的对象
		/**
		 * eg:@Entity
			@Table(name="testjavaben")
		 */
		System.out.println("-------------------------------------------");
		Query q=this.getSession().createQuery(hql);
		System.out.println(q.list());
		System.out.println("sqldata=====");
		System.out.println("-------------------------------------------");
	
	}
	
	//原生数据查询 hql hibernate拥有的数据库指令，跟sql差别不大
	public <T> void  sql( String sql,T t){
		//这个t对象必须是与数据库表建立联系的类的对象
		/**
		 * eg:@Entity
			@Table(name="testjavaben")
		 */
		System.out.println("-------------------------------------------");
		System.out.println(this.getSession().createSQLQuery(sql).addEntity(Subject.class).list());
		System.out.println("-------------------------------------------");
	}
}
