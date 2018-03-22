package com.yc.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.WritingPaperBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.dao.BaseDao;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.po.WritingPaperTemplate;
import com.yc.po.WritingQuestion;
import com.yc.vo.ChapterQuestionCount;
import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.Page;


@Service("writingPaperBiz")
public class WritingPaperBizImpl implements WritingPaperBiz {
	private BaseDao baseDao;
	private WritingQuestionBiz writingQuestionBiz;
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Resource(name="writingQuestionBiz")
	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz){
		this.writingQuestionBiz=writingQuestionBiz;
	}

	@Override
	public int addWritingPaper(WritingPaper wp) {
		baseDao.add(wp);
		return 0;
	}
	@Override
	public int addWritingPaperTemplate(WritingPaperTemplate wp) {
		baseDao.add(wp);
		return 0;
	}

	@Override
	public int updateCountOfQuestion(String paperId, int count) {
		return 0;
	}

	/**
	 * 更新试卷表信息
	 * 
	 * @author fanhaohao
	 */
	@Override
	public int updateWritingPaper(WritingPaper wp) {
		WritingPaper writingPaper = searchWritingPaper(wp.getId()).get(0);
		writingPaper.setPaperName(wp.getPaperName());
		writingPaper.setExamineeClass(wp.getExamineeClass());
		writingPaper.setPaperPwd(wp.getPaperPwd());
		writingPaper.setExamSubject(wp.getExamSubject());
		writingPaper.setExamDate(wp.getExamDate());
		writingPaper.setCountOfQuestion(wp.getCountOfQuestion());
		writingPaper.setTimesConsume(wp.getTimesConsume());
		writingPaper.setOperator(wp.getOperator());
		writingPaper.setRemark(wp.getRemark());
		writingPaper.setPaperStatus(wp.getPaperStatus());
		baseDao.update(writingPaper);
		return 0;
	}

	/**
	 * 根据id更新试卷表信息
	 * 
	 * @author fanhaohao
	 */
	@Override
	public int updateWritingPaperById(WritingPaper wp) {
		WritingPaper writingPaper = searchWritingPaper(wp.getId()).get(0);
		String strQuestionIds=wp.getQuestionId();
		
		writingPaper.setQuestionId(strQuestionIds);
		String correct=writingQuestionBiz.getAnswers(strQuestionIds);
		//试题个数
		String arr[] = strQuestionIds.split(",");
		writingPaper.setCountOfQuestion(arr.length);
		//设置对应题目的答案
		writingPaper.setQuestionCorrect(correct);
		baseDao.update(writingPaper);
		return 0;
	}

	@Override
	public int updatePaperInfo(WritingPaper wp, boolean clearScore) {
		return 0;
	}
	/**根据id更新试卷状态
	 * fanhaohao
	 */
	@Override
	public int updateWritingPaper(String paperId, int paperStatus) {
		/*WritingPaper wp = new WritingPaper();
		wp = (WritingPaper) this.baseDao.get(wp.getClass(), paperId);
		wp.setPaperStatus(paperStatus);*/
		String sql="update WritingPaper set paperStatus= :paperStatus where id= :id and paperStatus=4 ";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("paperStatus",paperStatus);
		params.put("id",paperId);
		try {
			baseDao.executeSql(sql, params);
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateWritingPaper(String paperId, String questionCorrect) {
		return 0;
	}

	@Override
	public int deleteWritingPaper(WritingPaper wp) {
		this.baseDao.del(wp);
		return 0;
	}

	@Override
	public List<WritingPaper> searchWritingPaper(String paperId) {
		String[] params = new String[] { paperId };
		String sql = "from WritingPaper where id=?";
		List<WritingPaper> list = this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<WritingPaperTemplate> searchWritingPaperTemplate(String paperId) {
		String[] params = new String[]{ paperId };
		String sql = "from WritingPaperTemplate where id=?";
		List<WritingPaperTemplate> list = this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}


	@Override
	public List searchWritingPaper(WritingPaper wp) {
		return null;
	}

	/**
	 * 判断试卷编号和密码是否正确
	 * 
	 * @author fpc
	 */
	@Override
	public boolean validateWritingPaperPassword(WritingPaper wp) {
		boolean flag = false;
		String[] params = new String[] { wp.getId(), wp.getPaperPwd() };
		String sql = "from WritingPaper wp where wp.id =? and wp.paperPwd =?";
		List list = this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	// @Override
	// public List<WritingQuestion> getWritingQuestionBean(int questionID) {
	// String[] params = new String[] { questionID+"" };
	// String sql = "from WritingQuestion where id=?";
	// List<WritingQuestion> list =
	// (List<WritingQuestion>)this.baseDao.search(sql, params);
	// return list;
	// }

	/**
	 * 根据多个id查试卷信息
	 * 
	 * @author tane
	 */
	@Override
	public List<WritingQuestion> findWritingQuestionByIds(List<Integer> ids) {
		String hql = "from WritingQuestion wq where wq.id in (:listName)";
		List<WritingQuestion> listWritingQuestion = baseDao.searchByList(hql,
				ids, "listName");
		return listWritingQuestion;
	}

	/**
	 * 通过试卷编号,获得试卷的所有题目编号,放在集合中
	 * 
	 * @param paperId
	 *            String 参数传递试卷编号
	 * @return List 返回一个集合
	 * @throws Exception
	 *             抛出系统异常
	 */
	public List<WritingQuestion> getWritingPaperQuestions(String questionId) {
		List<String> list = new ArrayList<String>(); // 放每道题的编号
		List<Integer> list1 = new ArrayList<Integer>();// 放每道题的编号
		List<WritingQuestion> arrQuestion = null; // 每道题对象
		int id = 0;
		/*String questionID = "";
		String[] params = new String[] { paperId };
		String sql = "from WritingPaper wp where wp.id =?";
		List<WritingPaper> list2 = (List<WritingPaper>) this.baseDao.search(
				sql, params);
		if (list2 != null && list2.size() > 0) {
			questionID = list2.get(0).getQuestionId();
		}*/
		// 用";"号拆分试题编号
		String array[] = questionId.split(",");
		for (int i = 0 ,len=array.length; i <len ; i++) {
			// 把拆分到的题目编号存到集合中
			list.add(array[i]);
		}

		for (int i = 0 ,len=array.length; i <len ; i++) {
			if ("".equals(array[i]))
				continue; // 如果题目编号为空，则跳过下面的语句
			id = Integer.parseInt(array[i]);
			list1.add(id);
		}
		// 把得到的Bean属性存入集合中，
		arrQuestion = this.findWritingQuestionByIds(list1);
		return arrQuestion;
	}

	@Override
	public int addInitAnswer(WritingAnswer wa) {
		return 0;
	}

	/**
	 * 通过试卷ID得到试卷对象
	 * 
	 * @author fpc
	 */

	@Override
	public WritingPaper getWritingPaper(String paperId) {
		//WritingPaper wp = null;
		//String[] params = new String[] { paperId };
		
		String sql="select * from WritingPaper wp where wp.id= :id ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", paperId);
		
		List<WritingPaper> result= this.baseDao.findDatabyhql(sql, map, new WritingPaper(), null, null);
		if(result.size()>0){
			return result.get(0);
		}
		
		return null;
		
		
		/*//String sql = "select wp.id,wp.examineeClass,wp.paperName,wp.countOfQuestion,wp.timesConsume,wp.questionCorrect from WritingPaper wp where wp.id=?";
		List<Object[]> list = this.baseDao.search(sql, params);
		List<WritingPaper> list2 = new ArrayList<WritingPaper>();
		// 将回调函数返回的Object[]，强转为：List<Object[]>（不能强转为
		// List<Category>，因为SQL语句是多表连接查询，执行后会报上述错误）
		// 然后再遍历，通过Category对象赋值（即将 Object[]的元素值赋予Category属性），再添加至List<Category>
		for (int i = 0; i < list.size(); i++) {
			Object[] obs = list.get(i);
			WritingPaper cate = new WritingPaper();
			cate.setId((String) obs[0]);
			cate.setExamineeClass((String) obs[1]);
			cate.setPaperName((String) obs[2]);
			cate.setCountOfQuestion((Integer) obs[3]);
			cate.setTimesConsume((Integer) obs[4]);
			cate.setQuestionCorrect((String) obs[5]);
			list2.add(cate);
		}
		if (list != null && list.size() > 0) {
			// System.out.println(list2.get(0).getExamineeClass());
			wp = new WritingPaper();
			wp.setId(list2.get(0).getId());
			wp.setExamineeClass(list2.get(0).getExamineeClass());
			wp.setPaperName(list2.get(0).getPaperName());
			wp.setCountOfQuestion(list2.get(0).getCountOfQuestion());
			wp.setTimesConsume(list2.get(0).getTimesConsume());
			wp.setQuestionCorrect(list2.get(0).getQuestionCorrect());
		}
		return wp;*/
	}

	@Override
	public List<Object> searchWritingPaperInfo(String paperId) {
		String[] params = new String[] { paperId };
		String hqlString = "from WritingPaper wp where wp.id=?";
				
		List<Object> list = (List<Object>) baseDao.search(hqlString, params);
		if (list != null && list.size() > 0) {

			return list;

		} else {

			return null;
		}

	}
	

	@Override
	public List searchWritingPaper(WritingPaper wp, int displayRows, int nextNum) {
		String hqlString = "from WritingPaper wp where wp.examineeClass='"
				+ wp.getExamineeClass() + "'";
		List<WritingPaper> list = (List<WritingPaper>) baseDao.searchByPage(
				nextNum, displayRows, hqlString);
		if (list != null && list.size() > 0) {

			return list;

		} else {

			return null;
		}

	}

	@Override
	public DataGaidModel searchWritingPaper(int paperStatus,DataarrayActionModel dm) {
		DataGaidModel dgm=new DataGaidModel();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("paperStatus", paperStatus);
		String hqlString = "from WritingPaper wp where paperStatus=:paperStatus order by examDate desc ";
		String queryCountHql = "select count(wp) from WritingPaper wp  where paperStatus=?";
		int page=dm.getPage();
		int rows=dm.getRows();
		int offset=(page-1)*rows;
		String order=dm.getOrder();
		String sort="wp."+dm.getSort();
		List<WritingPaper> list=baseDao.findByProperty(hqlString, map, offset, rows, sort, order);
		List<Long> count=baseDao.search(queryCountHql,paperStatus+"");
		for(int i=0,len=list.size();i<len;i++){
			list.get(i).setWritingAnswers(null);
		}
		Long total=(long) 0;
		if(count!=null&&count.size()>0){
			total=count.get(0);
		}
		dgm.setTotal(total);
		dgm.setRows((list!=null&&list.size()>0?list:null));
		return dgm;
	}


	@Override
	public List<WritingPaper> searchWritingPaper(int paperStatus,int paperStatusSec, String examClass) {
		String[] params = new String[] { examClass ,Integer.toString(paperStatus),Integer.toString(paperStatusSec) };
		String sql = "select wp.id,wp.paperName,wp.examineeClass,wp.examDate,wp.paperStatus from WritingPaper wp where examineeClass = ? and paperStatus in (?,?)";
		return this.baseDao.search(sql, params);
	}

	/**
	 * 判断所在班级是否有此编号的考卷
	 * 
	 * @author fpc
	 */
	@Override
	public boolean isExistsPaperByClass(String paperId, String examClass) {
		boolean flag = false;
		String[] params = new String[] { paperId, examClass };
		/*String sql = "from WritingPaper wp where wp.id =? and wp.examineeClass =?";
		List list = this.baseDao.search(sql, params);*/
		String sql = "select * from WritingPaper wp where wp.id = :id and wp.examineeClass = :examineeClass";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", paperId);
		map.put("examineeClass", examClass);
		List list = this.baseDao.findDatabyhql(sql, map, null, null);
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否存在指定编号和状态的试卷
	 * 
	 * @author fpc
	 */
	@Override
	public boolean isExistsPaperByState(String paperId, int paperStatus) {
		boolean flag = false;
		String[] params = new String[] { paperId, (paperStatus + "") };
		// Map<String,Object> params=new HashMap<String,Object>();
		String sql = "from WritingPaper wp where wp.id =? and wp.paperStatus =?";
		List list = this.baseDao.search(sql, params);
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public int getSearchWritingMaxRow(WritingPaper wp) {
		int result = 0;
		String hqlString = "from WritingPaper wp where wp.examineeClass='"
				+ wp.getExamineeClass() + "'";
		result = baseDao.search(hqlString).size();
		return result;
	}

	@Override
	public List<String> getGradationExamineeCount(String paperId) {
		return null;
	}

	@Override
	public List getcChapterCensus(String paperId) {
		return null;
	}

	@Override
	public List<List<ChapterQuestionCount>> getChaptersCount(String paperId) {
		return null;
	}

	@Override
	public List<String> getCorrectRateOfChapterOrSubject(String paperId) {
		return null;
	}

	@Override
	public List getExamineeCorrectRateOfChapterOrSubject(String paperId,
			String examineeName) {
		return null;
	}

	@Override
	public int getExamineeCorrect(String paperId, String rightAnswer,
			String examineeAnswer, int index, int count) {
		return 0;
	}

	/**
	 * 考生端根据考生姓名查询考生笔试成绩,如果没有指定试卷编号,查询所有考过的试卷
	 * 
	 * @param paperID
	 *            String
	 * @param examClass
	 *            String
	 * @param examineeName
	 *            String
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List searchExaminneWritAchievement(String paperID, String examClass,
			String examineeName, int paperStatus) {
		Map<String,Object> params=new HashMap<String,Object>();
		List arraylist = new ArrayList();
//		select  wa.paperId as 试卷编号,wp.paperName as 试卷名称,wp.examineeClass as 班级,"
//				+ "wa.examineeName as 姓名,wa.score as 成绩,wp.examDate as 考试日期
		
		String sql = "from WritingAnswer wa left join wa.writingPaper wp where wa.examineeName=:examineeName";
		params.put("examineeName", examineeName);
		if (examClass != null && !examClass.equals("")) {
			sql += " and wp.examineeClass=:examClass";
			params.put("examClass", examClass);
		}
//		if (paperID != null && !paperID.equals("")) {
//			sql += " and wa.paperId=:paperID";
//			params.put("paperID", paperID);
//		}
		if (paperStatus != 0) {
			sql += " and paperStatus=:paperStatus";
			params.put("paperStatus", paperStatus);
		}
		List<WritingAnswer> list=this.baseDao.findByProperty(sql, params, null, null);
		
		
		WritingPaper wp=new WritingPaper();
		// 检索此 ResultSet 对象的列的编号、类型和属性。
//		for (int i = 0; i <= list.size(); i++) {
//			list.get(i).setAnswer(null);
//			list.get(i).setCorrectRate(null);
//			list.get(i).setSpareTime(0);
//			wp.setPaperName(list.get(i).getWritingPaper().getPaperName());
//			wp.setExamineeClass(list.get(i).getWritingPaper().getExamineeClass());
//			wp.setExamDate(list.get(i).getWritingPaper().getExamDate());
//			wp.setAvgScore(null);
//			wp.setCorrectRate(null);
//			wp.setCountOfQuestion(0);
//			wp.setExamSubject(null);
//			wp.setMaxScore(null);
//			wp.setMinScore(null);
//			wp.setOperator(null);
//			wp.setQuestionId(null);
//			wp.setQuestionCorrect(null);
//			list.get(i).setWritingPaper(wp);
//		}
		return list;
	}

	@Override
	public int getExamineeWritAchievementMaxRow(WritingAnswer wa,
			String examineeName) {
		return 0;
	}

	@Override
	public List searchClassWritingScors(String paperId, String examClass) {
		return null;
	}

	@Override
	public int getSearchClassWritingScorsRowMax(WritingPaper wp,
			String examineeClass) {
		return 0;
	}

	@Override
	public int reExamWritingPaper(String paperId) throws Exception {
		WritingPaper wp = searchWritingPaper(paperId).get(0);
		wp.setId(paperId);
		wp.setPaperStatus(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		wp.setExamDate(sdf.parse(sdf.format(new Date())));
		wp.setScorePercent("");
		wp.setAvgScore(0.0f);
		wp.setMaxScore(0.0f);
		wp.setMinScore(0.0f);
		wp.setCorrectRate("");
		wp.setRemark("试卷重考");
		wp.setPaperStatus(1);
		baseDao.update(wp);
		// 删除试卷
		/*Map<String, Object> params=new HashMap<String, Object>();
		params.put("paperId", paperId);
		String hql="delete from WritingAnswer wa where wa.writingPaper.id=:paperId";
		baseDao.executeHql(hql, params);*/
		return 0;
		
	}

	@Override
	public List getQuestionIds(String examClass, String examSubject) {
		List alQuestions = new ArrayList();
		String sql="";
		String[] params=null;
		if(examSubject!="综合"){
			sql = "select questionId from WritingPaper where examineeClass=? and examSubject=?";
			params = new String[] { examClass, examSubject };
		}else{
			sql = "select questionId from WritingPaper where examineeClass=?";
			params = new String[] { examClass};
		}
		alQuestions = baseDao.search(sql, params);
		if (alQuestions != null && alQuestions.size() > 0) {
			return alQuestions;
		}
		return null;
	}

	@Override
	public void searchWritingPaperPage(Page<WritingPaper> page, WritingPaper wp) {
		if (wp.getExamineeClass() != null && !"".equals(wp.getExamineeClass())) {
			String[] params = new String[] { wp.getExamineeClass() };
			String hqlString = "from WritingPaper wp where wp.examineeClass=? order by examDate desc";
			String queryCountHql = "select count(wp) from WritingPaper wp where wp.examineeClass=?";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else {
			String hqlString = "from WritingPaper wp order by examDate desc  ";
			String queryCountHql = "select count(wp) from WritingPaper wp";
			baseDao.showPage(hqlString, queryCountHql, page);
		}
	}
	@Override
	public void searchWritingPaperPage(Page<WritingPaperTemplate> page, WritingPaperTemplate wp) {
		if (wp.getExamSubject()!= null && !"".equals(wp.getExamSubject())) {
			String[] params = new String[] { wp.getExamSubject() };
			String hqlString = "from WritingPaperTemplate wp where   wp.examSubject=?";
			String queryCountHql = "select count(wp) from WritingPaperTemplate wp where   wp.examSubject=?";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else {
			String hqlString = "from WritingPaperTemplate wp  ";
			String queryCountHql = "select count(wp) from WritingPaperTemplate wp";
			baseDao.showPage(hqlString, queryCountHql, page);
		}
		
	}


	@Override
	public void searchWritingPaperPageById(Page<WritingPaper> page,
			WritingPaper wp) {
		if (wp.getExamineeClass() != null && !"".equals(wp.getExamineeClass())&&wp.getId() != null && !"".equals(wp.getId())) {
			String[] params = new String[] { wp.getExamineeClass(),"%"+ wp.getId()+"%" };
			String hqlString = "from WritingPaper wp where wp.examineeClass=? and wp.paperStatus=3 and wp.id like ?";
			String queryCountHql = "select count(wp) from WritingPaper wp where wp.examineeClass=? and wp.paperStatus=3 "
					+ "and wp.id like ?";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else if(wp.getExamineeClass() != null && !"".equals(wp.getExamineeClass())){
			String[] params = new String[] { wp.getExamineeClass() };
			String hqlString = "from WritingPaper wp where wp.examineeClass=? and wp.paperStatus=3 ";
			String queryCountHql = "select count(wp) from WritingPaper wp where wp.examineeClass=? and wp.paperStatus=3";
			
			baseDao.showPage(hqlString, queryCountHql, page,params);
		}
	}

	/**
	 * 根据试卷ID查考试班级
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return
	 */
	@Override
	public String getExamClass(String paperId) {
		String examClass = "";
		String[] params = new String[] { paperId };
		String sql = "from WritingPaper wp where wp.id =?";
		List<WritingPaper> list = (List<WritingPaper>) this.baseDao.search(sql,
				params);
		examClass = list.get(0).getExamineeClass();
		return examClass;
	}

	@Override
	public int updatePaperStatusById(WritingPaper wp) {
		WritingPaper writingPaper = searchWritingPaper(wp.getId()).get(0);
		writingPaper.setPaperStatus(wp.getPaperStatus());
		baseDao.update(writingPaper);
		return 0;
		
	}


	@Override
	public WritingPaper findWritingPaperById(String id) {
		String sql="from WritingPaper where id= ? ";
		String[] params = new String[] { id };
		List<WritingPaper> list=baseDao.search(sql, params);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	@Override
	public WritingPaperTemplate findWritingPaperByIdTemlate(String id) {
		String sql="from WritingPaperTemplate where id= ? ";
		String[] params = new String[] { id };
		List<WritingPaperTemplate> list=baseDao.search(sql, params);
		return list!=null&&list.size()>0?list.get(0):null;
	}


	@Override
	public void updatePaper(WritingPaper wp) {
		baseDao.update(wp);
	}


	@Override
	public int updateWpByScore(WritingPaper wp) {
			WritingPaper writingPaper = searchWritingPaper(wp.getId()).get(0);
			writingPaper.setScorePercent(wp.getScorePercent());
			writingPaper.setAvgScore(wp.getAvgScore());
			writingPaper.setMaxScore(wp.getMaxScore());
			writingPaper.setMinScore(wp.getMinScore());
			writingPaper.setPaperStatus(wp.getPaperStatus());
			baseDao.update(writingPaper);
			return 0;
	}

	
	@Override
	public int updateWritingStatusPaperById(WritingPaper wp) {
		WritingPaper writingPaper = searchWritingPaper(wp.getId()).get(0);
		writingPaper.setPaperStatus(wp.getPaperStatus());
		baseDao.update(writingPaper);
		return 0;
		
	}


	@Override
	public List<WritingPaper>  searchTodayPaper() {
		//查出今天以前的试卷  的考试状态和未评状态的试卷
		String sql="select * from WritingPaper WHERE  examDate < now()  AND paperStatus in(2,4)";
		List<WritingPaper> list=baseDao.excuteSelectSql(sql, new WritingPaper());
		if(list.size()>0){
			return list;
		}else{
			return Collections.EMPTY_LIST;
		}
		
	}

	
	
	

}
