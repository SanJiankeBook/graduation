package com.yc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yc.biz.ExamineeBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.po.Examinee;
/**
 * 登录测试
 * @author zx
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(studentTest.class)
public class studentTest {
	
	private SystemUserBiz systemUserBiz;
	private ExamineeBiz examineeBiz;
	
	@Autowired 
	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}
	
	@Autowired 
	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}



	@Test //测试学生登录(无关联查询)
	public void testStudentLogin() {
	/*	ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		ExamineeBiz examineeBiz= (ExamineeBiz) ac.getBean("examineeBiz");*/
		String uname="方潇颖";
		String password="e6ee9813edf81b9e95a92fb5c66871dda80a0b2f";
		String examClass="YC_22";
		Examinee e = examineeBiz.getExaminee(uname, password, examClass);
		assert (e.getId()!=0):"程序成功";
	}
	
	@Test //测试教师登录(无关联查询)
	public void testTeacherIsLogin() {
		/*ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		SystemUserBiz systemUserBiz= (SystemUserBiz) ac.getBean("systemUserBiz");*/
		String uname="admin";
		String password="4cf2e96a35110daf7d0e997d1679ae5bc628f156";
		
		boolean e = systemUserBiz.isExist(uname, password);
		assert e:"程序成功";
		System.out.println(  e );
	}
	
	
	

}
