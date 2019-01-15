package com.yc.test;


import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.yc.biz.ChapterBiz;
import com.yc.biz.ClassRoomBiz;
import com.yc.biz.CurriculumBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.PointAnswerBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ADailyTalk;
import com.yc.po.Chapter;
import com.yc.po.ClassRoom;
import com.yc.po.Curriculum;
import com.yc.po.ExamineeClass;
import com.yc.po.SystemUser;
import com.yc.po.TeacherCurriModel;
import com.yc.utils.JsonUtil;
import com.yc.utils.ValueUtil;
import com.yc.vo.CurriculumPage;

/**
 * 
 * @author lyh
 * @version 创建时间：2017年7月8日 上午8:57:56
 * @ClassName CurriculumTest
 * @Description 主要进行排课模块的测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" }) 
@SuiteClasses(CurriculumTest.class)
public class CurriculumTest {
	
	private ExamineeClassBiz examineeClassBiz;
	private WritingPaperBiz writingPaperBiz;
	private LabPaperBiz labPaperBiz;
	private ExamineeBiz examineeBiz;
	private PointAnswerBiz pointAnswerBiz;
	private SubjectBiz subjectBiz;
	private SystemUserBiz sysUser;
	private ClassRoomBiz classRoom;
	private ChapterBiz chapterBiz;
	private CurriculumBiz curriculumBiz;
	
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
	@Autowired 
	public void setSysUser(SystemUserBiz sysUser) {
		this.sysUser = sysUser;
	}
	@Autowired 
	public void setClassRoom(ClassRoomBiz classRoom) {
		this.classRoom = classRoom;
	}
	@Autowired 
	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}
	@Autowired 
	public void setCurriculumBiz(CurriculumBiz curriculumBiz) {
		this.curriculumBiz = curriculumBiz;
	}

	@Test // 测试排课管理(查询所有班级)
	public void testClass() {// (无关联查询)（一条语句）
		List<ExamineeClass> eList = examineeClassBiz.findExamineeClassByTime(ValueUtil.getNowDate());
		for (ExamineeClass e : eList) {
			System.out.println(e);
		}
		assert (eList.size()!=0):"程序成功";
	}

	@Test // 测试排课管理(查询所有的老师)
	public void testTeacher() {// (无关联查询)（一条语句）
		List<SystemUser> sList = sysUser.searchUser(1);// 查询状态为1（教师）的所有用户
		for (SystemUser s : sList) {
			System.out.println(s);
		}
		assert (sList.size()!=0):"程序成功";

	}

	@Test // 测试排课管理(查询所有的教室)
	public void testclassRoom() {// (无关联查询)（一条语句）
		List<ClassRoom> sList = classRoom.getAllClassRoom(1);// 查询所有在用的教室
		for (ClassRoom s : sList) {
			System.out.println(s);
		}
		assert (sList.size()!=0):"程序成功";
	}

	@Test // 测试排课管理(查询前二后八课程)（前五后五也一样）
	public void testCurri() {// (三条语句)（已优化）
		List<Object> list3 = new ArrayList<Object>();// 存放未上的八节课
		String bid = "28";// 班级编号
		List<Curriculum> cList = curriculumBiz.getCurriculum(Integer.parseInt(bid), null, null, 4);// 查询已经上的两节课
		List<Chapter> chapters = null;
		if (cList == null) {// 表示还没开始上过课
			chapters = chapterBiz.getEightChapter(0, Integer.parseInt(bid), 8);
			assert (chapters.size()!=0):"程序成功";
		} else {
			// 输出下前二课表信息
			for (Curriculum c : cList) {
				System.out.println(c);
			}
			Chapter chapter = chapterBiz.findChapterById(cList.get(0).getChapterid());// 得到最近的seq
			chapters = chapterBiz.getEightChapter(chapter.getSeq(), Integer.parseInt(bid), 8);
			assert (chapters.size()!=0):"程序成功";

		}
		if (chapters != null) {// 防止空指针异常
			for (Chapter c : chapters) {
				Chapter chapter = new Chapter();
				chapter.setId(c.getId());
				chapter.setChapterName(c.getChapterName());
				list3.add(chapter);
			}
		}
		assert (list3.size()!=0):"程序成功";
		for (Object c : list3) {// 输出后八课程信息
			System.out.println(c);
		}
	}

	@Test // 测试排课管理(课表的插入)
	public void testInsertCurri() {// (无关联查询)（一条语句）
		List<Curriculum> list = new ArrayList<Curriculum>();
		Curriculum curriculum = new Curriculum();
		curriculum.setChapterid(92);
		curriculum.setClassid(26);
		curriculum.setClassroomid(5);
		curriculum.setDate("2017-7-14");
		curriculum.setTeacherid(2);
		curriculum.setTimeperiods("20:30-22:00");
		list.add(curriculum);
		curriculumBiz.insertIntoCurriculum(list);// 课表的插入
	}

	// 月度课时统计test
	@Test
	public void testCurriCount() {// (无关联查询)（2条语句）
		String tid = "3";// 得到指定老师的id
		int month = 5;// 得到月份
		int year = 2017;// 得到年份
		String startTime = getDate(year, month - 2, 26);// 得到上上一个月
		String endTime = getDate(year, month - 1, 25);// 得到上一个月
		List list = new ArrayList<>();
		int totalSeduce = curriculumBiz.getTotalCount(startTime, endTime);// 查询出月份总的课时
		list = curriculumBiz.getAllTeacherCurriInfo(startTime, endTime, tid);// 查询出指定老师的课程情况
		assert (list.size()!=0):"程序成功";
		System.out.println("月度课时总计：" + totalSeduce);
		for (int i = 0, len = list.size(); i < len; i++) {
			TeacherCurriModel obj = (TeacherCurriModel) list.get(i);// 映射到实体类
			System.out.println(obj);// 输出课程信息
		}

	}

	public String getDate(int year, int month, int day) {
		// 制作日期 26-25 eg:3.26-4.25
		Calendar ca = Calendar.getInstance();
		ca.set(year, month, day);// 制作日期
		Date resultDate = ca.getTime(); // 结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println(sdf.format(resultDate));
		return sdf.format(resultDate);
	}

	@Test // 测试排课管理(班级课表查询)
	public void testclassSearch() {// (无关联查询)(优化)（一条语句）
		String startDate = "2017-06-12";
		String endDate = "2017-06-18";
		String classid = "44";
		int status = 1;// 0 老师 1 班级 2 教室
		// 获取正常数据
		List list = curriculumBiz.getCurriInfo(startDate, endDate, classid, status);
		assert (list.size()!=0):"程序成功";
		for (int i = 0; i < list.size(); i++) {// 输出班级课程信息
			System.out.println(list.get(i));
		}
	}

	@Test // 测试排课管理(班级课表更正)
	public void testclassUpdate() {// (无关联查询)(一条语句)
		Curriculum curriculum = new Curriculum();
		curriculum.setId(1399);
		curriculum.setChapterid(2);
		curriculum.setTeacherid(1);
		curriculum.setClassroomid(1);
		int result = curriculumBiz.updateCurri(curriculum);
		assert (result!=0):"程序成功";
		System.out.println(result);//输出最后的结果
	}

	@Test // 测试排课管理(班级注意事项查询)
	public void testclassSearchNotice() {// (无关联查询)（一条语句）
		List<String> list = examineeClassBiz.getAllClassNotice();
		assert (list.size()!=0):"程序成功";
		System.out.println(list);//输出班级信息
	}

	@Test // 测试排课管理(更多课程的查询)
	public void moreClass() {// (已优化)
		List<Chapter> list = null;
		list = chapterBiz.getAllChapter1(28);//28表示班级编号
		assert (list.size()!=0):"程序成功";
		System.out.println(list);//输出课程信息
		List<Chapter> list2 = null;
		list2 = chapterBiz.getAllChapter1(28);
		assert (list2.size()!=0):"程序成功";
		System.out.println(list2);//测试二级缓存
	}
}
