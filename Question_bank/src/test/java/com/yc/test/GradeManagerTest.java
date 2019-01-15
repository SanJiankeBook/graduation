package com.yc.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;



import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.PointAnswerBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.LabPaper;
import com.yc.po.PointAnswer;
import com.yc.po.PointPaper;
import com.yc.po.WritingPaper;
import com.yc.utils.JsonProtocol;
import com.yc.vo.Page;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(GradeManagerTest.class)
/**
 *  成绩管理测试
 * @author pt
 */
public class GradeManagerTest {
	private ExamineeClassBiz examineeClassBiz;
	private WritingPaperBiz writingPaperBiz;
	private LabPaperBiz labPaperBiz;
	private ExamineeBiz examineeBiz;
	private PointAnswerBiz pointAnswerBiz;
	private SubjectBiz subjectBiz;
	
	
	@Autowired 
	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}
	@Autowired 
	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}
	@Autowired 
	public void setLabPaperBiz(LabPaperBiz labPaperBiz) {
		this.labPaperBiz = labPaperBiz;
	}
	@Autowired 
	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}
	@Autowired 
	public void setPointAnswerBiz(PointAnswerBiz pointAnswerBiz) {
		this.pointAnswerBiz = pointAnswerBiz;
	}
	@Autowired 
	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}
	@Test //测试笔试卷查询
	//通过条件（学期）查找班级的名字   writingPaper_getExamineeClassName.action 关联了1个
	public void testHibernateConn2() {//已优化
		String semester="S1";
		List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassBySemester(semester);

	}
	@Test //测试笔试卷查询 中的“搜索”功能  dataarraylist_showWritingPaperPages.action 关联了2个
	public void testHibernateConn10a() throws Exception {//已优化
		WritingPaper wp = new WritingPaper();
		wp.setExamineeClass("YC_46");
		Page<WritingPaper> page = new Page<WritingPaper>();
			page.setPageSize(1);// 每页显示记录条数
			page.setCurrentPage(1);// 当前页数
			writingPaperBiz.searchWritingPaperPage(page, wp);
	}
	
	@Test //测试班级成绩查询  
	//通过条件（学期）查找班级的名字   writingPaper_getExamineeClassName.action 关联了1个
	public void testHibernateConn3() {//已优化
		String semester="S1";
		List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassBySemester(semester);
		
	}
	
	@Test //测试班级成绩查询中的“搜索”功能   dataarraylist_showPaperPages.action 分笔试和机试
	public void testHibernateConn3a() throws Exception {//已优化
		
		/**
		 * 这种笔试是关联2个
		 */
//		WritingPaper wp = new WritingPaper();
//		wp.setExamineeClass("YC_46");
//		wp.setId("");
//		Page<WritingPaper> page = new Page<WritingPaper>();
//		page.setPageSize(10);
//		page.setCurrentPage(1);
//		writingPaperBiz.searchWritingPaperPageById(page, wp);
		/**
		 * 机试是关联2个
		 */
		LabPaper lp = new LabPaper();
		lp.setExamineeClass("YC_46");
		lp.setId("");
		Page<LabPaper> page = new Page<LabPaper>();
		page.setPageSize(10);// 每页显示记录条数
		page.setCurrentPage(1);// 当前页数
			labPaperBiz.searchLabPaperPageById(page, lp);
		assert (page.getResult().size()!=0):"程序成功";
		
	}
	
	@Test //测试考生成绩查询 
	//通过条件（学期）查找班级的名字   writingPaper_getExamineeClassName.action 关联了1个
	public void testHibernateConn4() {//已优化
		String semester="S1";
		List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassBySemester(semester);
		assert (examineeClassList.size()!=0):"程序成功";
	}
	
	@Test //测试考生成绩查询“点击班级下划线，查找每个班对应的学生名”  dataarraylist_showExamineeNames1.action 关联了2个
	public void testHibernateConn4a() {//已优化
		List<Examinee> list = examineeBiz.findAllStuNameByClassName("YC_40");
		assert (list.size()!=0):"程序成功";

	}
	

	@Test //测试测评成绩查询  
	//通过条件（学期）查找班级的名字  dataarraylist_findAllExamineeClass.action 关联了1个
	public void testHibernateConn5() {//已优化
		List<String> list = examineeClassBiz.searchAllExamineeClassName();
		assert (list.size()!=0):"程序成功";

	}
	@Test // 测试测评成绩查询的查询功能  pointAnswer_findPoinAnswerInfos.action
	public void testHibernateConn5a() {//已优化
		int classId = examineeClassBiz.getClassIdOfname("YC_27");
		List<PointAnswer> list = pointAnswerBiz.findMessageInfo(0, classId, "");
		assert (list.size()!=0):"程序成功";
	}
	
	//dataarraylist_showExamineeNames1.action//已优化
	@Test //测试知识点统计  通过条件查找班级的名字  writingPaper_getExamineeClassName.action 关联了1个
	public void testHibernateConn6() {//已优化
		String semester="S1";
		List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassBySemester(semester);
		assert (examineeClassList.size()!=0):"程序成功";
	}
	
	@Test //测试知识点统计"点击班级，查询每个班级对应的课程" pointPaper_findSubjectInfo.action 关联了1个
	public void testHibernateConn6a() {//已优化
		
		List<String> list = subjectBiz.getSubjectNamesBySemesterAndClassName("S1", "YC_46");
		assert (list.size()!=0):"程序成功";

	}
	@Test // 测试知识统计中的查询按钮功能   pointPaper_findPointPaperInfo.action 
	public void testHibernateConn6b() {//已优化
		
		List<PointPaper> list = subjectBiz.getPointPaperByIdAndClassName(1, "YC_40");
		assert (list.size()!=0):"程序成功";
		if (list != null && list.size() > 0) {
			for (PointPaper l : list) {//将不要的参数设为空，不然会产生无关联的语句
				l.setPointAnswers(null);
				l.getSubject().setChapters(null);
				l.getSubject().setEdition(null);
				l.getSubject().setPointInfos(null);
				l.getExamineeClass().setaDailyTalks(null);
				l.getExamineeClass().setCheckings(null);
				l.getExamineeClass().setPointPapers(null);
			}
		}
	}
	
	//测试课程测评统计 发现和testHibernateConn6一样，就不写了//已优化
	// writingPaper_getId.action // 已优化
	// pointAnswer_findsubjectResult.action //已优化
	@Test // 通过条件测试查看测评留言的课程名  subject_findSubjectBySemester.action 关联了一张表
	public void testHibernateConn7() {//已优化
		String semester="S1";
		List<String> list = subjectBiz.findSubjectNameBySemester(semester);
		assert (list.size()!=0):"程序成功";

	}
	@Test // 测试查看测评留言的班级显示   writingPaper_getExamineeClassNameAndClassId.action 关联了一张表
	public void testHibernateConn8() {//已优化
		String semester="S1";
		List examineeClassList = examineeClassBiz.findExamineeClassAndClassIdBySemester(semester);
		assert (examineeClassList.size()!=0):"程序成功";
	}
	
	@Test // 测试查看测评留言中的通过班级编号获得学生姓名 (根据班级编号显示同一班级的学生信息)  examineeclass_showExamineeList.action
	public void testHibernateConn8b() {//已优化
		String semester="S1";
		List examineeClassList = examineeClassBiz.findExamineeClassAndClassIdBySemester(semester);
		assert (examineeClassList.size()!=0):"程序成功";

	}
	
	//dataarraylist_showExamineeNames1.action
	@Test // 测试查看测评留言中的"查询功能" pointAnswer_findPointAnswerInfo.action
	public void testHibernateConn8a() {//已优化
		int classId = -1;
		String examClassName="YC_40";
		if (examClassName != null && !"".equals(examClassName)) {
			classId = examineeClassBiz.getClassIdOfname(examClassName);
			assert (classId!=0):"程序成功";

		}
		List<PointAnswer> list = pointAnswerBiz.findPointAnswerInfo(1, classId, "文楚");
		assert (list.size()!=0):"程序成功";
		if (list != null && list.size() > 0) {//参数设空
			for (PointAnswer l : list) {
				l.getPointPaper().setPointAnswers(null);
				l.getPointPaper().setExamineeClass(null);
				l.getPointPaper().setSubject(null);
				l.getPointPaper().setPaperPwd(null);
				l.getPointPaper().setPtitle(null);
			}
		}
	 }
	
}
