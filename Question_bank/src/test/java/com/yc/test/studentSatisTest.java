package com.yc.test;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.SatisfactionBiz;
import com.yc.biz.SatisfactionDetailBiz;
import com.yc.po.ADailyTalk;
import com.yc.po.ExamineeClass;
import com.yc.po.Satisfaction;
import com.yc.po.SatisfactionDetail;

/**
 * 满意度测试
 * @author 郑笑
 * @param <T>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(studentSatisTest.class)
public class studentSatisTest {
	
	private SatisfactionBiz satisfactionBiz;
	private SatisfactionDetailBiz satisfactionDetailBiz;
	
	@Autowired 
	public void setSatisfactionBiz(SatisfactionBiz satisfactionBiz) {
		this.satisfactionBiz = satisfactionBiz;
	}
	
	@Autowired 
	public void setSatisfactionDetailBiz(SatisfactionDetailBiz satisfactionDetailBiz) {
		this.satisfactionDetailBiz = satisfactionDetailBiz;
	}



	@Test //测试是否开启了满意度调查表 （原生） null则为没有开启   不然为开启
	public void testIsOpenSatis() {
		/*ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		SatisfactionBiz satisfactionBiz=(SatisfactionBiz) ac.getBean("satisfactionBiz");*/
		//测试用例 ：
		String time="2017-07-20";
		String  userid="165";
		List<Satisfaction> list=satisfactionBiz.isOpenSatisfaction(time,userid);
		assert (list.size()!=0):"程序成功";
	}
	
	@Test //测试是否填写过满意度调查表 （原生）
	public void testIsEverWriteSatis() {
	/*	ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		SatisfactionDetailBiz satisfactionDetailBiz=(SatisfactionDetailBiz) ac.getBean("satisfactionDetailBiz");*/
		//测试用例 ：
		//String time="2017-07-01";
		String  userid="304";
		Integer said=23;
		List<SatisfactionDetail> detailList=satisfactionDetailBiz.isWriteSatisfactionDetail(userid, said);
		assert (detailList.size()!=0):"程序成功";
		System.out.println(  detailList ); 
	}
	
	@Test //测试插入满意度调查表 （原生） 
	public void testInsertSatis() {
		/*ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		SatisfactionDetailBiz satisfactionDetailBiz=(SatisfactionDetailBiz) ac.getBean("satisfactionDetailBiz");*/
		//测试用例 ：
		Integer count=9;Integer a1=9;Integer a2=9;Integer a3=9;Integer a4=9;Integer a5=9;Integer a6=9;Integer a7=9;Integer a8=9;Integer a9=9;Integer a10=9;
		Integer a11=9;Integer a12=9;Integer a13=9;Integer a14=9;Integer a15=9;Integer a16=9;Integer a17=9;Integer a18=9;Integer a19=9;Integer a20=9;
		Integer a21=9;Integer a22=9;Integer a23=9;Integer a24=9;Integer a25=9;Integer a26=9;
		SatisfactionDetail satisfactionDetail=new SatisfactionDetail();
		satisfactionDetail.setA1(a1);satisfactionDetail.setA2(a2);satisfactionDetail.setA3(a3);
		satisfactionDetail.setA4(a4);satisfactionDetail.setA5(a5);satisfactionDetail.setA6(a6);
		satisfactionDetail.setA7(a7);satisfactionDetail.setA8(a8);satisfactionDetail.setA9(a9);
		satisfactionDetail.setA10(a10);
		satisfactionDetail.setA1(a11);satisfactionDetail.setA2(a12);satisfactionDetail.setA3(a13);
		satisfactionDetail.setA4(a14);satisfactionDetail.setA5(a15);satisfactionDetail.setA6(a16);
		satisfactionDetail.setA7(a17);satisfactionDetail.setA8(a18);satisfactionDetail.setA9(a19);
		satisfactionDetail.setA10(a20);
		satisfactionDetail.setA1(a21);satisfactionDetail.setA2(a22);satisfactionDetail.setA3(a23);
		satisfactionDetail.setA4(a24);satisfactionDetail.setA5(a25);satisfactionDetail.setA6(a26);
		satisfactionDetail.setUnsatisfaction("无");
		satisfactionDetail.setSatisfaction("无");
		
		Integer n=satisfactionDetailBiz.insertSatisfaction(satisfactionDetail);
		assert (n==1):"程序成功";
	}
	
	

}
