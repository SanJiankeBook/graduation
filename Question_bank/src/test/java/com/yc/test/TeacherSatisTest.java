package com.yc.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SatisfactionBiz;
import com.yc.biz.SatisfactionDetailBiz;
import com.yc.biz.impl.ADailyTalkBizImpl;
import com.yc.po.ADailyTalk;
import com.yc.po.Satisfaction;
import com.yc.po.SatisfactionDetail;
import com.yc.po.SystemUser;

/**
 * 后台满意度测试
 * @author 郑笑
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext*.xml")
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(TeacherSatisTest.class)
public class TeacherSatisTest implements ApplicationContextAware {
	private ApplicationContext context ;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		 context = arg0; 
		
	}
	
	private SatisfactionBiz satisfactionBiz;
	private SatisfactionDetailBiz satisfactionDetailBiz;
	private ExamineeClassBiz examineeClassBiz;
	
	@Autowired 
	public void setSatisfactionBiz(SatisfactionBiz satisfactionBiz) {
		this.satisfactionBiz = satisfactionBiz;
	}

	@Autowired 
	public void setSatisfactionDetailBiz(SatisfactionDetailBiz satisfactionDetailBiz) {
		this.satisfactionDetailBiz = satisfactionDetailBiz;
	}

	@Autowired 
	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}



	@Test //开启月度课时调查表  原生
	public void testOpenSatis() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//System.out.println("test_ac:::::::"+ac);
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) ac.getBean("satisfactionBiz");
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) context.getBean("satisfactionBiz");
		String teacherid = "2";
		String teacherName = "周海军";
		String year = "2017";
		String month = "6";
		String startDate ="2017-06-07";
		String endDate = "2017-06-12";
		String openteacherName = "黄初云";
		String className = "YC_27";
		
		Integer openteacherid=12;
		Satisfaction satisfaction=new Satisfaction();
		satisfaction.setClassName(className);
		satisfaction.setTeacherName(teacherName);
		satisfaction.setTeacherid(Integer.parseInt(teacherid));
		satisfaction.setOpenYear(year);
		satisfaction.setOpenMonth(month);
		satisfaction.setStartTime(startDate);
		satisfaction.setEndTime(endDate);
		satisfaction.setOpenPersonId(openteacherid);
		satisfaction.setOpenPersonName(openteacherName);
		Integer result=satisfactionBiz.insertSatisfaction(satisfaction);
		assert (result!=0):"程序成功";
		
	}
	
	
	
	@Test ////显示老师满意调查表数据  原生
	public void testShowTeacherData() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) context.getBean("satisfactionBiz");
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) context.getBean("satisfactionBiz");
		Satisfaction satisfaction=new Satisfaction();
		satisfaction.setOpenYear("2017");
		satisfaction.setTeacherName("张影");
		satisfaction.setOpenMonth("7");
		List<Satisfaction> list=satisfactionBiz.showTeacherSatisfaction(satisfaction);
		assert (list.size()!=0):"程序成功";
		for(Satisfaction sat:list){
			System.out.println(sat.getTeacherName());
			System.out.println(sat.getClassName());
		}
		
		List<Satisfaction> list1=satisfactionBiz.showTeacherSatisfaction(satisfaction);
		System.out.println(list1);
		for(Satisfaction sat1:list1){
			System.out.println(sat1.getTeacherName());
			System.out.println(sat1.getClassName());
		}
	}
	
	@Test //根据satisid测试satisDetail的总数   原生sql
	public void testSatisDetailCount() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		
		//SatisfactionDetailBiz satisfactionDetailBiz=(SatisfactionDetailBiz) context.getBean("satisfactionDetailBiz");
		int id=23;
		Integer sampleCount =satisfactionDetailBiz.satisDetailCount(id);
		assert (sampleCount!=0):"程序成功";
	}
	
	@Test //测试班级总数  无关联查询
	public void testClassCount() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//ExamineeClassBiz examineeClassBiz=(ExamineeClassBiz) context.getBean("examineeClassBiz");
		
		Integer classPepoleNumber=examineeClassBiz.searchSingleClassExamineeCountByClassName("YC_27");
		assert (classPepoleNumber!=0):"程序成功";
	}
	
	@Test //测试班级满意度数据  原生缓存
	public void testClassSatis() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) context.getBean("satisfactionBiz");
		
		Satisfaction satisfaction=new Satisfaction();
		satisfaction.setOpenYear("2017");
		satisfaction.setClassName("YC_27");
		satisfaction.setOpenMonth("6");
		List<Satisfaction> list=satisfactionBiz.showClassSatisfaction(satisfaction);
		assert (list.size()!=0):"程序成功";
		
		List<Satisfaction> list1=satisfactionBiz.showClassSatisfaction(satisfaction);
		System.out.println(list1);
		
	}
	
	
	@Test //测试满意度调查表汇总  原生缓存
	public void testAllSatis() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) context.getBean("satisfactionBiz");
		
		String month="6";
		String year="2017";
		
		List<Satisfaction> list=satisfactionBiz.findSummaySatisInfo(year,month);
		assert (list.size()!=0):"程序成功";
		List<Satisfaction> list1=satisfactionBiz.findSummaySatisInfo(year,month);
		System.out.println(list1);
	}
	
	
	@Test //测试查找满意不满意的细节  原生缓存
	public void testSatisSatis() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//SatisfactionBiz satisfactionBiz=(SatisfactionBiz) context.getBean("satisfactionBiz");
		
		String month="7";
		String year="2017";
		Integer ids=23;
		
		Satisfaction list=satisfactionBiz.findSummaySatisInfoDetail(year, month, ids);
		assert (list.getClassName()!=null):"程序成功";
		Satisfaction list1=satisfactionBiz.findSummaySatisInfoDetail(year, month, ids);
		System.out.println(list1);

	}
	
	
	@Test //测试显示老师满意度细节表数据   原生缓存
	public void testTeacherSatisDetail() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		//SatisfactionDetailBiz satisfactionDetailBiz=(SatisfactionDetailBiz) context.getBean("satisfactionDetailBiz");
		
		String id="23";
		
		List<SatisfactionDetail> list=satisfactionDetailBiz.showSatisfactionDetail(Integer.parseInt(id));
		assert (list.size()!=0):"程序成功";

		List<SatisfactionDetail> list1=satisfactionDetailBiz.showSatisfactionDetail(Integer.parseInt(id));
		System.out.println(list1);
	}

	
	
}
