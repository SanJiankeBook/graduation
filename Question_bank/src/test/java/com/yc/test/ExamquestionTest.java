package com.yc.test;
import static org.junit.Assert.assertNotNull;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.xml.internal.bind.v2.TODO;
import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ChapterBiz;
import com.yc.biz.EditionBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.PointAnswerBiz;
import com.yc.biz.PointInfoBiz;
import com.yc.biz.PointPaperBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.biz.impl.TestImpl;
import com.yc.po.ADailyTalk;
import com.yc.po.Chapter;
import com.yc.po.Edition;
import com.yc.po.ExamineeClass;
import com.yc.po.PointAnswer;
import com.yc.po.PointInfo;
import com.yc.po.PointPaper;
import com.yc.po.Subject;
import com.yc.po.TestJavaBen;
import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;
import com.yc.utils.ExamUtil;
import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.Page;
import com.yc.vo.PointInfoModel;
import com.yc.vo.PointModel;
import com.yc.vo.PointPaperModel;
import com.yc.vo.WritingQuestionPaper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@SuiteClasses(ExamquestionTest.class)
/**
 *  题库维护测试
 * @author pengtao
 */
public class ExamquestionTest {
	private ExamineeClassBiz examineeClassBiz;
	private WritingPaperBiz writingPaperBiz;
	private LabPaperBiz labPaperBiz;
	private ExamineeBiz examineeBiz;
	private PointAnswerBiz pointAnswerBiz;
	private SubjectBiz subjectBiz;
	private EditionBiz editionBiz;
	private PointInfoBiz pointInfoBiz;
	private ChapterBiz chapterBiz;
	private WritingQuestionBiz writingQuestionBiz;
	private PointPaperBiz pointPaperBiz;
	private PointInfoBiz pointInfobiz;
	
	@Autowired 
	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}
	@Autowired 
	public void setPointInfoBiz(PointInfoBiz pointInfoBiz) {
		this.pointInfoBiz = pointInfoBiz;
	}
	@Autowired 
	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}
	@Autowired 
	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}
	@Autowired 
	public void setPointPaperBiz(PointPaperBiz pointPaperBiz) {
		this.pointPaperBiz = pointPaperBiz;
	}
	@Autowired 
	public void setPointInfobiz(PointInfoBiz pointInfobiz) {
		this.pointInfobiz = pointInfobiz;
	}
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
	
	@Test //测试知识点管理中的显示所有版本号    
	public void testHibernateConn2() {//（一条语句）已优化
		List<Edition> editions = editionBiz.getAllEdition();
	}

	@Test //测试知识点管理的科目显示   initData_subject.action  变量不为空情况
	public void testHibernateConn3() {//已优化
		String jsonStr = "";
		List<Subject> subjects = new ArrayList<Subject>();
		String semester="S1";
		Integer editionId=1;

		if (semester != null && editionId != null) {//如果学期编号不为空并且版本id不为空
			subjects = subjectBiz.findSubject(semester, editionId);
		} else {
			//subjects = subjectBiz.getSubjects();
		}
	}

	@Test //测试知识点管理  查出科目 initData_subject.action  在变量为null时关联了5个
	public void testHibernateConn4() {//(优化---一条语句)
		String jsonStr = "";
		List<Subject> subjects = new ArrayList<Subject>();
		String semester=null;
		Integer editionId=null;
		if (semester != null && editionId != null) {//如果学期编号不为空并且版本id不为空
			//subjects = subjectBiz.findSubject(semester, editionId);
		} else {
			//subjects = subjectBiz.getSubjects();
		}
	}

	@Test //测试知识点管理中的题目显示     assessment_initPointInfo.action  在变量不为null时关联了6个
	public void testHibernateConn5() {//（一条语句）已优化
		PointInfoModel pointInfoModel = new PointInfoModel();
		DataGaidModel dgm = new DataGaidModel();
		pointInfoModel.setRows(10);
		pointInfoModel.setPage(1);
		dgm = pointInfoBiz.getPointInfo(pointInfoModel);
	}

	@Test //测试录入笔试题  initData_version.action 关联13个，initData_subject.action  在变量不为null时关联了1个(已优化)
	//当前测试的是 根据课程编号查出所有章节  initData_chapter.action 关联了1个
	public void testHibernateConn6() {//（三条语句）已优化
		Integer subjectId=0;
		List<Chapter> chapters = chapterBiz.findChapter(subjectId);
		for (int i = 0; i < chapters.size(); i++) {
			chapters.get(i).setSubject(null);
		}
	}

	@Test //测试录入笔试题界面中的“查找”功能   qusetion_findQuestbyText.action 关联了1个
	public void testHibernateConn6a() {//(一条语句)已优化
		String question="ni";
		List<WritingQuestion> list = writingQuestionBiz.searchQuesExist(question);
	}

	@Test //测试录入笔试题界面中的“添加题目”功能   qusetion_upload.action 关联了1个
	public void testHibernateConn6b() {//（一条语句）已优化
	}


	@Test//测试浏览笔试题的所有版本  initData_version.action 关联13个，initData_subject.action  在变量不为null时关联了1个
	//当前测试的是 查询所有题目信息  dataarray_searchAllPaper.action   已优化
	public void testHibernateConn71(){ //这个测试主要的查询语句是来自for循环（后面可以考虑优化for循环中的代码）
		List<WritingQuestionPaper> lists = new ArrayList<WritingQuestionPaper>();
		DataGaidModel dgm = null;
		DataarrayActionModel dataarrayActionModel=new DataarrayActionModel();
		dgm = writingQuestionBiz.searchAllWritingQuestionPage(dataarrayActionModel);
		DataGaidModel d = new DataGaidModel();
		List<WritingQuestion> list = dgm.getRows();
		for (int i = 0; i < list.size(); i++) {
			WritingQuestionPaper wqp = new WritingQuestionPaper();
			wqp.setId(list.get(i).getId());
			wqp.setQuestion("<xmp>" + list.get(i).getQuestion() + "</xmp>");
			wqp.setSubjectName(subjectBiz.findSubjectName(list.get(i).getSubjectId()));
			wqp.setChapterName(chapterBiz.getChapterName(list.get(i).getChapterId()));
			if (("1").equals(list.get(i).getQuestionType())) {
				wqp.setType("单选");
			} else if (("2").equals(list.get(i).getQuestionType())) {
				wqp.setType("多选");
			}
			lists.add(wqp);
		}
		d.setRows(lists);
		d.setTotal(dgm.getTotal());
	}
	@Test
	// 测试浏览笔试题中的“查询功能” dataarray_searchAllPaper.action 总共关联了7个   已优化
	public void testHibernateConn7() {//（for循环中的语句可以优化）

		DataarrayActionModel dataarrayActionModel = new DataarrayActionModel();//
		dataarrayActionModel.setOrder("asc");//设置排序是升序
		dataarrayActionModel.setPage(1);//设置第几页
		dataarrayActionModel.setRows(10);//设置每一页的数据条数
		dataarrayActionModel.setSort("id");//设置排序的属性
		List<WritingQuestionPaper> lists = new ArrayList<WritingQuestionPaper>();
		DataGaidModel dgm = null;
		dgm = writingQuestionBiz.searchAllWritingQuestionPage(dataarrayActionModel);//2个
		DataGaidModel d = new DataGaidModel();
		List<WritingQuestion> list = dgm.getRows();
		for (int i = 0,len= list.size(); i <len; i++) {// 循环出所有的课程ID
			WritingQuestionPaper wqp = new WritingQuestionPaper();
			wqp.setId(list.get(i).getId());
			wqp.setQuestion("<xmp>" + list.get(i).getQuestion() + "</xmp>");
			wqp.setSubjectName(subjectBiz.findSubjectName(list.get(i).getSubjectId())); //1个，根据课程id来查询
			wqp.setChapterName(chapterBiz.getChapterName(list.get(i).getChapterId())); //1个
		}
	}

	@Test //测试出笔试卷的所有版本  initData_version.action 关联13个，initData_subject.action  在变量不为null时关联了1个
	//当前测试的是查出所有班级信息  initData_examClass.action 
	public void testHibernateConn8() {//(优化)
		String semester=null;
		List examineeClass = new ArrayList<String>();
		examineeClass = examineeClassBiz.findExamineeClassAndClassIdBySemester(semester);//1个

	}
	@Test //测试出笔试卷 中的“确定出卷”功能  paper_addWritingPaper.action
	public void testHibernateConn8a() {//已经优化

		//下面这些测试由于参数原因都是通过debug测试的，所以这个单元测试只是把调用业务层的方法列出来了
		/**
		 * 根据版本,学期,班级,科目(综合科:0)和当前时间生成试卷编号  三条Basedao语句，三条查询（sql无需优化）
		 * 
		 * @param version
		 *            String 版本
		 * @param semester
		 *            String 学期
		 * @param examClass
		 *            String 班级
		 * @return String
		 */
		String subject=null;
		String vid = Integer.toString(editionBiz.getEditionId(""));
		String eid = Integer.toString(examineeClassBiz.getClassIdOfname(""));
		String sid;
		if (subject.endsWith("0")) {
			sid = 0 + "";
		} else {
			sid = Integer.toString(subjectBiz.getSubjectId(""));
		}
		/**
		 * // 连接生成试卷的题目编号 (已优化)
		 */
		String[] strQ=null;
		int subjID = subjectBiz.getSubjectId("");
		String chapterN[] = strQ[1].split(";");
		// String chapterN[] = strQ[1].split(",");
		@SuppressWarnings("unused")
		List chapterIDandCountList = new ArrayList();
		for (int i = 0,len=chapterN.length; i < len; i++) {
			int chapterId = chapterBiz.getChapterId(subjID,
					chapterN[i].split(",")[0]);
			int chapterCount = Integer.parseInt(chapterN[i].split(",")[1]);
			//				String ss = this.searchQuestionOfRandom("", subjID,
			//						chapterId, chapterCount, "", "", "");
			// 数据库中符合条件的题目不够数时
			// if (ss.equals("0")) {
			// return ss;
			// }
			String strQuestionIDs=null;
			//				strQuestionIDs += ss;
		}

		/**
		 * 添加试卷（将试卷信息插入数据库） 已优化
		 */
		//writingPaperBiz.addWritingPaper(wp);


	}

	@Test //测试出笔试卷  
	//当前测试的是查出章节  initData_findchapter.action
	public void testHibernateConn9() {//已优化

		Integer subjectId=0;
		List<Chapter> chapters = chapterBiz.findChapter(subjectId);
		List<Map<String, Object>> chapter = new ArrayList<Map<String, Object>>();//1个
		for (int i = 0,len=chapters.size(); i <len ; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("chapterName", chapters.get(i).getChapterName());
			map.put("chapterId", chapters.get(i).getId());
			int id = chapters.get(i).getId();
			int count = writingQuestionBiz.getCount(id);//1个
			map.put("questionNum", count);
			chapter.add(map);
		}
	}

	@Test //测试浏览笔试卷
	//当前测试的是查找班级的名字  writingPaper_getExamineeClassName.action 关联了1个
	public void testHibernateConn10() {//已优化
		String semester="S1";
		List<ExamineeClass> examineeClassList = examineeClassBiz.findExamineeClassBySemester(semester);
	}
	@Test //测试浏览笔试卷中的“搜索”  dataarraylist_showWritingPaperPages.action 关联了2个
	public void testHibernateConn10a() throws Exception {//已优化

		WritingPaper wp = new WritingPaper();
		wp.setExamineeClass("YC_46");//设置班名
		Page<WritingPaper> page = new Page<WritingPaper>();
		page.setPageSize(1);
		page.setCurrentPage(1);
		writingPaperBiz.searchWritingPaperPage(page, wp);
	}

	@Test //测试考试安排
	//当前测试的是查询未开考的试卷  dataarray_findAllWritingPaper.action 关联了2个
	public void testHibernateConn11() {//已优化
		DataarrayActionModel dataarrayActionModel = new DataarrayActionModel();
		dataarrayActionModel.setOrder("asc");
		dataarrayActionModel.setPage(1);
		dataarrayActionModel.setRows(10);
		dataarrayActionModel.setSort("id");
		DataGaidModel dgm = writingPaperBiz.searchWritingPaper(1, dataarrayActionModel);

	}
	@Test //测试考试安排中的“激活试卷” dataarray_updatePaperStatus.action 关联了1个
	public void testHibernateConn11a() {//已优化
		WritingPaper wp = writingPaperBiz.findWritingPaperById("1S149120170710103839");

	}
	//很多方法都引用的三个action
	@Test //initData_version.action 查出所有的版本 JSON   已优化 一条语句
	public void testHibernateConn13() {//已优化
		List<Edition> editions = editionBiz.getAllEdition();
	}
	
	//很多方法都引用的三个action
	@Test //initData_subject.action 通过条件查出科目   已优化 一条语句
	public void testHibernateConn13a() {//已优化
		List subjects = subjectBiz.getSubjects();
		//	subjects = subjectBiz.findSubject(semester, editionId); 已优化
	}
	
	//很多方法都引用的三个action
	@Test //initData_examClass.action 查出每一期的班级   已优化 一条语句
	public void testHibernateConn13b() {//已优化
		List examineeClass = examineeClassBiz.findExamineeClassAndClassIdBySemester("S1");
	}

	@Test //assessment_initPointInfos.action 查出每一门课程的知识   已优化 一条语句
	public void testHibernateConn13c() {//已优化
		List<PointInfo> pointInfos = pointInfoBiz.getPointInfo(1);	
	}

	@Test //assessmentpaper_newPointPaper.action 出自评卷（把自评卷的内容写进数据库）   已优化  三个basedao方法，三条语句
	public void testHibernateConn13d() {//已优化
		Subject sb = subjectBiz.findSubjectById(1);
		ExamineeClass ec = examineeClassBiz.findExamineeClassById(49);
		PointPaper po = new PointPaper();
		po.setPname("YC_45班的自评卷");
		po.setPdate(new Date());
		po.setPstatus(1);
		po.setPaperPwd("a");
		po.setPtitle("103,4,");
		po.setDescript("");
		po.setSubject(sb);
		po.setExamineeClass(ec);
		pointPaperBiz.addPointPaper(po);
	}
	/**
	 * 浏览自评卷   initData_subject.action  initData_examClass.action initData_examClass.action
	 * 这三个已经在上面被优化过了
	 */


	@Test //assessmentpaper_findAllPointPaper.action 查询所有试卷  
	public void testHibernateConn14() {//已优化    两个basedao发了两个语句
		List<PointPaper> pointPaper = new ArrayList<PointPaper>();
		List<PointPaperModel> list = new ArrayList<PointPaperModel>();
		DataGaidModel dgm = new DataGaidModel();
		PointPaperModel pointPaperModel = new PointPaperModel();
		pointPaperModel.setRows(5);
		pointPaperModel.setPage(1);
		dgm = pointPaperBiz.getAllPointPaper(pointPaperModel);
		pointPaper = dgm.getRows();//获取所有数据

		for (int i = 0; i < pointPaper.size(); i++) {
			PointPaperModel pm = new PointPaperModel();
			pm.setClassName(pointPaper.get(i).getExamineeClass().getClassName());
			pm.setExamDate(pointPaper.get(i).getPdate());
			pm.setPname(pointPaper.get(i).getPname());
			// pm.setPstatus(pointPaper.get(i).getPstatus());
			if (pointPaper.get(i).getPstatus() == 1) {
				pm.setPstatusname("未考");
			} else if (pointPaper.get(i).getPstatus() == 2) {
				pm.setPstatusname("开评");
			} else if (pointPaper.get(i).getPstatus() == 3) {
				pm.setPstatusname("已评");
			}
			pm.setPid(pointPaper.get(i).getPid());
			list.add(pm);
		}
		dgm.setRows(list);
	}

	//查看结果（考过后的结果）
	@Test //assessmentpaper_findPointPaper.action   // 按条件查询试卷
	public void testHibernateConn14a() {//已优化   

		PointPaperModel pointPaperModel = new PointPaperModel();
		pointPaperModel.setPid(7);
		String jsonStr = "";
		String date = "";
		List<PointPaper> pointPaper = new ArrayList<PointPaper>();
		List<PointPaperModel> list = new ArrayList<PointPaperModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (pointPaperModel.getExamDate() != null) {
			date = sdf.format(pointPaperModel.getExamDate());
		}
		if (pointPaperModel.getPid() != null) {
			pointPaper.add(pointPaperBiz.findPointPaperById(pointPaperModel.getPid()));
		} else {
			pointPaper = pointPaperBiz.findPointPaper(pointPaperModel.getSid(), pointPaperModel.getCid(), date);
		}
		
	}

	//查看结果（考过后的结果）
	@Test //assessmentpaper_studentCount.action   统计参加测评的人数
	public void testHibernateConn14b() {//已优化   
		ApplicationContext ac=new ClassPathXmlApplicationContext( new String[]{"applicationContext-dao.xml","applicationContext-actions.xml"});
		PointAnswerBiz pointAnswerBiz=(PointAnswerBiz) ac.getBean("pointAnswerBiz");
		int count = pointAnswerBiz.getStudentCount(7);
	}

	//查看结果（考过后的结果）
	@Test //pointPaper_findPointPaperInfoPid.action   根据班级名称pid来查测评试卷信息
	public void testHibernateConn14c() {//已优化   

		String pTilte = pointPaperBiz.getPointPaperTitleByPid(7);
		// 去掉pTilte的最后一个逗号
		if (pTilte != null && !"".equals(pTilte)) {
			String ptStringStr = "";
			if (pTilte.endsWith(",")) {
				ptStringStr = pTilte.substring(0, pTilte.lastIndexOf(","));
			} else {
				ptStringStr = pTilte;
			}
			List<Integer> pids = new ArrayList<Integer>();
			// 加【】便于alibaba json反序列化
			JSONArray jsonArray = JSON.parseArray("[" + ptStringStr + "]");
			for (Object o : jsonArray) {
				pids.add(Integer.parseInt(o.toString()));
			}
			// 根据id的集合来查询
			List<PointInfo> listPi = pointInfoBiz.findPointAllInfoByPids(pids);
			// 根据pid来查询pointanwer的信息
			List<PointAnswer> listPa = pointAnswerBiz.findAnswersByPid(7);
			// 创建一个pointmodel的集合
			List<PointModel> lpm = new ArrayList<PointModel>();
		}
	}
	
	
	//开考功能包含一个修改语句和上面一个列出的查询所有试卷语句 （已优化）
	
	
	// 查看试卷    assessmentpaper_findPointPaper.action (上面已有 )
	//assessmentpaper_showPointInfo.action  显示试卷题目
	@Test
	public void testHibernateConn15() {//已优化    两条语句
		String title = "103,4";
		String[] ptitles = title.split(",");
		List<String> list = new ArrayList<String>();
		String pointName = "";
		for (int i = 0; i < ptitles.length; i++) {
			pointName = pointInfobiz.findPointInfoById(Integer.parseInt(ptitles[i]));
			list.add(pointName);
		}

	}
	
	// 编辑试卷    initData_examClass.action  assessment_initPointInfos.action   assessmentpaper_findPointPaper.action(上面已有 ) 已优化
		



}
