package com.yc.webexam.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.yc.biz.ChapterBiz;
import com.yc.biz.EditionBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.LabQuestionBiz;
import com.yc.biz.LabQuestionTypeBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.po.Chapter;
import com.yc.po.Edition;
import com.yc.po.ExamineeClass;
import com.yc.po.Subject;
import com.yc.po.XSubject;
import com.yc.utils.JsonUtil;


public class InitDataAction extends BaseAction implements ServletResponseAware {
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = -2373730480874665027L;
	@Resource(name = "editionBiz")
	private EditionBiz editionBiz;
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;
	@Resource(name = "chapterBiz")
	private ChapterBiz chapterBiz;
	@Resource(name = "labQuestionTypeBiz")
	private LabQuestionTypeBiz labQuestionTypeBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "labQuestionBiz")
	private LabQuestionBiz labQuestionBiz;
	@Resource(name = "writingQuestionBiz")
	private WritingQuestionBiz writingQuestionBiz;
	private String requestState;
	private String semester;
	private String version;
	private String subject;
	private String come;
	private String skillCode;
	private String questionId;
	private Integer editionId;
	private Integer subjectId;
	private HttpServletResponse response;
	private PrintWriter out;
	HttpServletRequest request = ServletActionContext.getRequest();
	private List<String> list = new ArrayList<String>();

	public String getRequestState() {
		return requestState;
	}

	public void setRequestState(String requestState) {
		this.requestState = requestState;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCome() {
		return come;
	}

	public void setCome(String come) {
		this.come = come;
	}

	public String getSkillCode() {
		return skillCode;
	}

	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Integer getEditionId() {
		return editionId;
	}

	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public void setServletResponse(HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		this.response = resp;
		try {
			this.out = this.response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 查出所有的版本 JSON
	public void version() {
		String jsonStr = "";
		try {
			List<Edition> editions = editionBiz.getAllEdition();
			for (int i = 0,len=editions.size(); i < len; i++) {
				editions.get(i).setLabQuestions(null);
				editions.get(i).setSubjects(null);
			}
			jsonStr = super.writeJson(0, editions);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	// 查出科目  修改版
	public void subject() {
		String jsonStr = "";
		List<XSubject> subjects = new ArrayList<XSubject>();
		try {
			subjects = subjectBiz.getSubjects();
			if(subjects!=null){
				jsonStr = super.writeJson(0, subjects);
			}else{
				jsonStr = super.writeJson(1, "暂无数据");
			}
			
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	// 查出所有班级
	public void examClass() {
		String jsonStr = "";
		List<ExamineeClass> examineeClass = new ArrayList<ExamineeClass>();
		try {
			examineeClass = examineeClassBiz.findExamineeClassBySemester(semester);
			if(examineeClass!=null){
				for(ExamineeClass e:examineeClass){
					e.setaDailyTalks(null);
					e.setCheckings(null);
					e.setCreateDate(null);
					e.setEditionId(null);
					e.setNotice(null);
					e.setOverDate(null);
					e.setPointPapers(null);
					e.setRemark(null);
					e.setSemester(null);
					e.setStudentcount(null);
				}
			}
			jsonStr = super.writeJson(0, examineeClass);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	// 根据课程编号查出所有章节
	public void chapter() {
		String jsonStr = "";
		try {
			List<Chapter> chapters = chapterBiz.findChapter(subjectId);
			if(chapters!=null){
			for (int i = 0,len=chapters.size(); i < len; i++) {
				chapters.get(i).setSubject(null);
			}
			jsonStr = super.writeJson(0, chapters);
			}else{
					jsonStr = super.writeJson(1, "暂无数据");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	// 查出章节
	public void findchapter() {
		String jsonStr = "";
		try {
			List<Chapter> chapters = chapterBiz.findChapter(subjectId);
			List<Map<String, Object>> chapter = new ArrayList<Map<String, Object>>();
			if(chapters!=null){
			for (int i = 0,len=chapters.size(); i < len; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("chapterName", chapters.get(i).getChapterName());
				map.put("chapterId", chapters.get(i).getId());
				int id = chapters.get(i).getId();
				int count = writingQuestionBiz.getCount(id);
				map.put("questionNum", count);
				chapter.add(map);
			}
			jsonStr = super.writeJson(0, chapter);
		}else{
				jsonStr = super.writeJson(1, "暂无数据");
		}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public void initData() throws UnsupportedEncodingException {
		// String requestState = request.getParameter("requestState");
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");

			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (requestState == null || requestState.equals("")) {
			return;
		}
		// 当请求的是版本时,返回学期所对应的版本
		if (requestState.equals("version")) {
			// 从版本数据操作类中得到版本
			list = editionBiz.searchEdition();
			for (int i = 0,len=list.size(); i < len; i++) {
				out.print(list.get(i).toString().trim());
			}
		}

		if (requestState.equals("subject")) {
			// String semester = request.getParameter("semester");
			// String version =
			// ValueUtil.formatRequestStr(request.getParameter("version"));
			version = new String(version.getBytes("ISO-8859-1"), "UTF-8");
			// 把 "点符号" 字符串转回 "."
			String s[] = version.split(".");
			if (s.length > 1) {
				version = s[0] + "." + s[1];
			}

			int ver = editionBiz.getEditionId(version);
			// 从科目数据操作类中得到科目
			list = subjectBiz.searchSubject(semester, ver);
			for (int i = 0,len=list.size(); i < len; i++) {
				out.println(list.get(i).toString().trim());
			}

		}

		// 当请求的是章节时,返回科目所对应的章节
		if (requestState.equals("chapter")) {
			// String subject = request.getParameter("subject");
			if (subject == null || subject.equals("")) {
				return;
			}

			subject = new String(subject.getBytes("ISO-8859-1"), "UTF-8");
			// 把 "与符号" 字符串转回 "&"
			String s[] = subject.split("与符号");
			if (s.length > 1) {
				subject = s[0] + "&" + s[1];
			}
			// 是否是从出笔试题来的，不为空则表示从出笔试卷来的
			// String come = request.getParameter("come");
			// 从科目数据操作类中得到科目名称
			int subjectID = subjectBiz.getSubjectId(subject);
			// 从章节数据操作类中得到章节
			list = chapterBiz.searchChapter(subjectID);
			// 得到章节的ID
			for (int i = 0,len=list.size(); i < len; i++) {
				String chapterName = list.get(i).toString().trim();
				if (come == null) {
					out.println(chapterName);
				} else if ("NEW_WRITING_PAPER".equals(come)) {
					// 通过章节ID和章节名称得到章节ID
					int chapterId = chapterBiz.getChapterId(subjectID, chapterName);
					// 通过章节ID得到本章有多少道题
					int count = chapterBiz.getQuestionAmount(chapterId);
					// 用||分开，在表示层再分
					out.println(chapterName + "||" + count);
				}

			}
		}

		// 当请求的是技术组合时,返回学期所对应的技术组合
		if (requestState.equals("skillCode")) {
			// String semester = request.getParameter("semester");
			list = labQuestionTypeBiz.getSkillbySemester(semester);
			for (int i = 0,len=list.size(); i < len; i++) {
				out.println(list.get(i).toString().trim());
			}
		}

		// 当请求的是班级时,返回学期所对应的班级
		if (requestState.equals("examClass")) {
			// String semester = request.getParameter("semester");
			// 从班级数据操作类中得到班级
			list = examineeClassBiz.searchExamineeClassName(semester);
			for (int i = 0,len=list.size(); i < len; i++) {
				out.println(list.get(i).toString().trim());
			}
		}
		// 当请求的是题目编号时,判断指定条件的题编号是否存在
		if (requestState.equals("questionId")) {
			// String skillCode = request.getParameter("skillCode");
			skillCode = new String(skillCode.getBytes("ISO-8859-1"), "UTF-8");
			skillCode = URLDecoder.decode(skillCode, "UTF-8");
			// String questionId = request.getParameter("questionId");

			// 通过技术组合类型得到ID
			skillCode = skillCode.trim().replace(' ', '+');
			int skillCodeID = labQuestionTypeBiz.getSkillCodeId(skillCode);
			// 通过技术组合类型编号,题目编号,判断是否存在此题
			if (questionId == null || questionId.equals("")) {
				return;
			}
			boolean flag = labQuestionBiz.isExistQuestionId(skillCodeID, Integer.parseInt(questionId));
			if (flag) {
				out.print("1");
			} else {
				out.print("0");
			}
		}
		out.close();
	}

	// JSON取出默认版本号
	public void editionName() {
		// 相应的默认版本号从数据库中得到，默认的版本名

		String currentUse = editionBiz.getEditionName();

		String jsonStr = super.writeJson(0, currentUse);
		try {
			JsonUtil.jsonOut(jsonStr);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}

	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}

	public void setLabQuestionTypeBiz(LabQuestionTypeBiz labQuestionTypeBiz) {
		this.labQuestionTypeBiz = labQuestionTypeBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setLabQuestionBiz(LabQuestionBiz labQuestionBiz) {
		this.labQuestionBiz = labQuestionBiz;
	}

	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}

}
