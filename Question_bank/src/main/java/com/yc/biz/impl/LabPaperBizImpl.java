package com.yc.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.LabPaperBiz;
import com.yc.dao.BaseDao;
import com.yc.po.LabAnswer;
import com.yc.po.LabPaper;
import com.yc.po.LabQuestion;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.vo.Page;

@Service("labPaperBiz")
public class LabPaperBizImpl implements LabPaperBiz {

	@Resource(name = "baseDao")
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public int addLabPaper(LabPaper lp) {
		return 0;
	}

	@Override
	public int updateLabPaperOfId(LabPaper lp) {
		return 0;
	}

	@Override
	public int updateLabPaper(String paperId, int paperStatus) {
		return 0;
	}

	@Override
	public int deleteLabPaper(LabPaper lp) {
		this.baseDao.del(lp);
		return 0;
	}

	@Override
	public List searchLabPaper(LabPaper lp) {
		String examineeClass = lp.getExamineeClass();
		String[] params = new String[] { examineeClass };
		String sql = "from LabPaper where 1=1";
		if (lp.getExamineeClass() != null) {
			sql += " and examineeClass=?";
		}
		List list = this.baseDao.search(sql, params);
		return list;
	}

	@Override
	public boolean validateLabPaperPassword(LabPaper lp) {
		return false;
	}

	@Override
	public int getQuestionId(String paperId) {
		return 0;
	}

	@Override
	public LabQuestion getLabQuestionBean(String paperId) {
		return null;
	}

	@Override
	public LabPaper getLabPaper(String paperId) {
		return null;
	}

	@Override
	public List searchLabPaper(LabPaper lp, int displayRows, int nextNum) {
		return null;
	}

	@Override
	public boolean isExistsPaper(String paperId, String examClass) {
		return false;
	}

	@Override
	public boolean isExistsPaper(String paperId, int paperStatus) {
		return false;
	}

	@Override
	public List searchLabPaper(int paperStatus, int displayRows, int nextNum) {
		return null;
	}

	@Override
	public List searchLabPaper(int paperStatus, String examClass) {
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String strTime = sf.format(now);
		String[] params = new String[] { examClass,strTime };
		String sql = "select lp.id,lp.paperName,lp.examineeClass,lp.examDate from LabPaper lp where examineeClass =? and examDate > ?";
		if (paperStatus != 0) {
			sql += " and paperStatus = ?";
			params = new String[] { examClass,strTime,paperStatus+"" };
		}
		List list = this.baseDao.search(sql, params);
		return list;
	}

	@Override
	public List searchLabPaperInfo(String paperId) {
		return null;
	}

	@Override
	public int getSearchLabMaxRow(LabPaper lp) {
		return 0;
	}

	@Override
	public int updateLabPaper(LabPaper lp) {
		return 0;
	}

	@Override
	public List getGradationexamineeCount(String paperId) {
		return null;
	}

	@Override
	public List searchExaminneLabAchievement(String paperId, String examClass,
			String examineeName) {
		Map<String,Object> params=new HashMap<String,Object>();
		List arraylist = new ArrayList();
		String sql = "select  wa.paperId as 试卷编号,wp.paperName as 试卷名称,wp.examineeClass as 班级,"
				+ "wa.examineeName as 姓名,wa.score as 成绩,wp.examDate as 考试日期 from WritingAnswer wa "
				+ "inner join WritingPaper wp on wa.paperId=wp.id where wa.examineeName=:examineeName";
		params.put("examineeName", examineeName);
		if (examClass != null && !examClass.equals("")) {
			sql += " and wp.examineeClass=:examClass";
			params.put("examClass", examClass);
		}
		if (paperId != null && !paperId.equals("")) {
			sql += " and wa.paperId=:paperID";
			params.put("paperID", paperId);
		}
		List<WritingAnswer> list=this.baseDao.findByProperty(sql, params, null, null);
		List<Object> list2=new ArrayList<Object>();
		// 检索此 ResultSet 对象的列的编号、类型和属性。
		for (int i = 0; i <= list.size(); i++) {
			list2.add(list.get(i).getWritingPaper().getId());
			list2.add(list.get(i).getWritingPaper().getPaperName());
			list2.add(list.get(i).getWritingPaper().getExamineeClass());
			list2.add(list.get(i).getExamineeName());
			list2.add(list.get(i).getScore());
			list2.add(list.get(i).getWritingPaper().getExamDate());
			arraylist.add(list2);
		}
		return arraylist;
	}

	@Override
	public int getExamineeLabAchievementMaxRow(LabAnswer wa, String examineeName) {
		return 0;
	}

	@Override
	public List searchClassLabScore(String paperId, String examClass) {
		return null;
	}

	@Override
	public int getSearchClassLabScorsRowMax(LabPaper lp, String examineeClass) {
		return 0;
	}

	@Override
	public void searchLabPaperPageById(Page<LabPaper> page, LabPaper lp) {
		if (lp.getExamineeClass() != null && !"".equals( lp.getExamineeClass())) {
			String[] params = new String[] {  lp.getExamineeClass() };
			String hqlString = "from LabPaper lp where lp.examineeClass=? and lp.paperStatus=3 and lp.id like '%"+lp.getId()+"%'";
			String queryCountHql = "select count(lp) from LabPaper lp where lp.examineeClass=? and lp.paperStatus=3 and lp.id like '%"+lp.getId()+"%'";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else {
			String hqlString = "from LabPaper lp where lp.paperStatus=3 and lp.id like '%"+lp.getId()+"%'";
			String queryCountHql = "select count(lp) from LabPaper lp where lp.paperStatus=3 and lp.id like '%"+lp.getId()+"%'";
			baseDao.showPage(hqlString, queryCountHql, page);
		}
		
	}

}
