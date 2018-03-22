package com.yc.test;

import java.util.ArrayList;
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

import com.yc.biz.ExamineeBiz;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.po.Examinee;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;
import com.yc.utils.ExamUtil;
/**
 * 
 * @author zx
 * 学生端考试部分测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(studentExamTest.class)
public class studentExamTest implements ApplicationContextAware{
	private ApplicationContext context ;
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		 context = arg0; 
		
	}
	
	private WritingPaperBiz writingPaperBiz;
	private LabPaperBiz labPaperBiz;
	private WritingAnswerBiz writingAnswerBiz;
	
	
	@Autowired 
	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}
	
	@Autowired 
	public void setLabPaperBiz(LabPaperBiz labPaperBiz) {
		this.labPaperBiz = labPaperBiz;
	}

	@Autowired 
	public void setWritingAnswerBiz(WritingAnswerBiz writingAnswerBiz) {
		this.writingAnswerBiz = writingAnswerBiz;
	}



	@Test //测试学生端是否一考过试(无关联查询)当请求是笔试卷时
	public void testHaveExam1() {
	
		//WritingPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		List arr = new ArrayList<WritingPaper>();
		arr = writingPaperBiz.searchWritingPaper(ExamUtil.PAPER_STATUS_IS_TESTING, ExamUtil.PAPER_STATUS_NOT_VIEW,
				"YC_27");
		assert (arr.size()!=0):"程序成功";
		System.out.println(  arr );
	}
	
	@Test //测试学生端是否考过(无关联查询) 当请求是机试卷时
	public void testHaveExam2() {
		 //labPaperBiz= (LabPaperBiz) context.getBean("labPaperBiz");
		List arr = new ArrayList<WritingPaper>();
		
		arr = labPaperBiz.searchLabPaper(ExamUtil.PAPER_STATUS_IS_TESTING, "YC_27");
		assert (arr.size()==0):"程序成功";
		System.out.println(  arr );
	}
	
	
	@Test //测试学生端考试是否存在(无关联查询) 缓
	public void testLoginPassWord() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		 //writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		
		boolean boo = writingPaperBiz.isExistsPaperByClass("123", "YC_27");
		assert !boo:"程序成功";
		System.out.println(  boo );
	}
	
	@Test // 测试判断此考生是否已参加了此次考试。“您已参加了本次考试，并已交了卷，不可以再次进入考试!”(无关联查询) 
	public void testIsGetTest() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		 writingAnswerBiz= (WritingAnswerBiz) context.getBean("writingAnswerBiz");
		
		boolean flag = writingAnswerBiz.isHandInPaper("123", "YC_27");
		assert !flag:"程序成功";
		System.out.println(  flag );
	}
	
	@Test // 判断是否存在此编号和状态的试卷，此编号的试卷还没到考试时间，不可以考试！ 
	public void testIsGetTime() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		
		boolean boo1 = writingPaperBiz.isExistsPaperByState("123", ExamUtil.PAPER_STATUS_IS_TESTING);
		assert !boo1:"程序成功";
		System.out.println(  boo1 );
	}
	
	
	
	
	@Test //判断试卷编号和密码是否正确  缓
	public void testPwdIsRight() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		WritingPaper wp = new WritingPaper();
		wp.setId("1S131220160710120907");
		wp.setPaperPwd("a");
		boolean number=writingPaperBiz.validateWritingPaperPassword(wp);
		assert number:"程序成功";
		System.out.println(  number );
	}
	
	@Test //显示试卷  缓
	public void testShowTest() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		WritingPaper wp = new WritingPaper();
		
		 wp=writingPaperBiz.getWritingPaper("1S131220160710120907");
		 assert (wp.getId()!=null):"程序成功";
		System.out.println(  wp );//null
	}
	
	@Test //查询考生答卷
	public void testAnswerPaper() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingAnswerBiz writingAnswerBiz= (WritingAnswerBiz) context.getBean("writingAnswerBiz");
		//pram:paperId  examineeName
		WritingAnswer wa = writingAnswerBiz.searchWritingAnswer("1S131220160723082751", "李善玲");
		assert (wa.getId()!=null):"程序成功";
		System.out.println(  wa.getId());
	}
	

	//提交答卷
	
	@Test // 通过试卷编号得到试卷的正确答案        空指针异常
	public void testGetRightAnswer() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		//pram:paperId  examineeName
		List<WritingPaper>	wa=writingPaperBiz.searchWritingPaper("1S131220160710120907");
		assert (wa.size()>0):"程序成功";
	}
	
	@Test // 通过试卷编号得到试卷的正确答案     
	public void testGetRightAnswer2() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		//pram:paperId  examineeName
		List<WritingPaper>	wa=writingPaperBiz.searchWritingPaper("1S131220160710120907");
		assert (wa.size()>0):"程序成功";
	}
	
	@Test // 计算出考生成绩，考生姓名从session中得到 要开redis
	public void testScoreOfStudent() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingAnswerBiz writingAnswerBiz= (WritingAnswerBiz) context.getBean("writingAnswerBiz");
		//paperId, userName, rightAnswer, countQuestion
		int score = writingAnswerBiz.computeScore("123", "a", "a", 1);
		assert (score==0):"程序成功";
		System.out.println(  score);
	}

	
	@Test // 试卷状态变为未评状态
	public void testUpdateTestStatus() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritPaperBiz writingPaperBiz= (WritingPaperBiz) context.getBean("writingPaperBiz");
		//paperId, ExamUtil.PAPER_STATUS_NOT_VIEW
		int n = writingPaperBiz.updateWritingPaper("3S3333720170703094914", ExamUtil.PAPER_STATUS_NOT_VIEW);
		assert (n!=0):"程序成功";
		System.out.println(n );//0
	}
	
	@Test //后台进入还剩余多少时间
	public void testNextTime() {
	//	ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingAnswerBiz writingAnswerBiz= (WritingAnswerBiz) context.getBean("writingAnswerBiz");
		String paperId = "1S3281120170706045116";
		String examineeName = "郑笑";
		String spareTime = "15";
		// String examineeClass = request.getParameter("examineeClass");
		Integer n=writingAnswerBiz.updateSpareTime(paperId, examineeName, spareTime);
		assert (n==0):"程序成功";
		System.out.println(n );//0
	}
	
	
	//功能：笔试答题时,每答一题保存一题 选从答卷表中答案找出，拆分出已答过的题目答案， 如果有现答的题目编号和已答的相同就更新答案，否则添加
	
	@Test //找现有的答案
	public void testNowHaveAnswer() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingAnswerBiz writingAnswerBiz= (WritingAnswerBiz) context.getBean("writingAnswerBiz");
		String paperId ="1S3281120170706045116";
		
		String examineeName = "郑笑";
		// 找现有的答案
		String strA = writingAnswerBiz.searchAnswer(paperId, examineeName);
		assert (strA!=null):"程序失败";
		System.out.println(strA);//0
	}
	
	
	@Test //更新答案
	public void testUpdateAnswers() {
		//ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
	
		//WritingAnswerBiz writingAnswerBiz= (WritingAnswerBiz) context.getBean("writingAnswerBiz");
		String paperId ="1S3281120170706045116";
		String strAnswer = "1-A";
		String examineeName = "郑笑";
		
		int n = writingAnswerBiz.updateAnswer(strAnswer, paperId, examineeName);
		assert (n!=0):"程序失败";
		System.out.println(n);//0
	}
	
}
