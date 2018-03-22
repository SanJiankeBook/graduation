package com.yc.test;

import static org.junit.Assert.assertNotNull;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Query;
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

import com.alibaba.fastjson.JSON;
import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ChapterBiz;
import com.yc.biz.EditionBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.PointInfoBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.biz.impl.TestImpl;
import com.yc.po.ADailyTalk;
import com.yc.po.Chapter;
import com.yc.po.Edition;
import com.yc.po.ExamineeClass;
import com.yc.po.PointInfo;
import com.yc.po.Subject;
import com.yc.po.TestJavaBen;
import com.yc.po.WritingQuestion;
import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.PointInfoModel;
import com.yc.vo.SubjectPage;
import com.yc.vo.WritingQuestionPaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(EditControlTest.class)
/**
 * 版本控制测试
 * @author pengtao
 *
 */

public class EditControlTest {
	private EditionBiz editionBiz;
	private SubjectBiz subjectBiz;
	
	
	@Autowired 
	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}
	@Autowired 
	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}
	
	@Test //测试版本维护中的版本显示  course_edition.action 关联了1个 
	public void testHibernateConn2() {//已优化
		List<Edition> currentUse = editionBiz.searchAllEdition();
		assert (currentUse.size()!=0):"程序成功";
	}
	@Test //测试版本维护的“修改功能” course_updateCurrent.action 关联1个
	public void testHibernateConn2a() {//已优化
		 Edition edition = new Edition();
		edition.setId(4);
		edition.setEditionName("前端v3.01"); //设置版本名
		edition.setCurrentUse(1);
		editionBiz.updateEdition(edition);
	}
	@Test //测试版本维护的“增加版本功能” course_addEdition.action//1个关联
	public void testHibernateConn2b() {//已优化
		 Edition edition = new Edition();
		String editionName = "java";
		edition.setEditionName(editionName);
		edition.setCurrentUse(0);
		int results = (Integer) editionBiz.addEdition(edition);
		assert (results!=0):"程序成功";
	}
	
	
	@Test //测试课程维护中的通过条件显示课程  course_edition.action 关联了1个     subject_showSubject.action
	public void testHibernateConn3() {//已优化
		SubjectPage sb = new SubjectPage();
		sb.setSemester("S1");
		sb.setEditionName("J2EE7.0");
		sb.setPage(1);
		sb.setRows(50);
		List<Subject> allSubject = subjectBiz.getAllSubject(sb, sb.getPage(), sb.getRows());//2个
		Map<String, Object> map = new HashMap<String, Object>();//将数据存进map中进行交互
		List<SubjectPage> subjects  = new ArrayList<SubjectPage>();
		if (allSubject != null && allSubject.size() > 0) {
			for (int i = 0,len=allSubject.size(); i < len; i++) {
				SubjectPage subjectPage = new SubjectPage();
				subjectPage.setId(allSubject.get(i).getId());
				subjectPage.setEditionName(allSubject.get(i).getEdition().getEditionName());
				subjectPage.setSemester(allSubject.get(i).getSemester());
				subjectPage.setSubjectName(allSubject.get(i).getSubjectName());
				subjectPage.setChapterCount(allSubject.get(i).getChapterCount());
				subjectPage.setSeq(allSubject.get(i).getSeq() );
				subjects.add(subjectPage);
			}
			int total = subjectBiz.getAllSubject(sb, null, null).size();
			assert (total!=0):"程序成功";
			map.put("total", total);//将数据的总数添加进去
			map.put("rows", subjects);//将所有的课程添加进map映射中
		} else {
			map.put("total", 0);
			map.put("rows", allSubject);
		}
	
	}
	
	@Test //测试章节维护 
	//course_edition.action 查询所有的版本 上面已经有了，已优化
	//chapter_subjectName.action 查询所有的课程  关联了5个 已优化
	public void testHibernateConn4() {
		ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		SubjectBiz subjectBiz=(SubjectBiz) ac.getBean("subjectBiz");
		List<XSubject> subject = (List<XSubject>) subjectBiz.getSubjects();
		assert (subject.size()>0):"程序成功";
	}
}
