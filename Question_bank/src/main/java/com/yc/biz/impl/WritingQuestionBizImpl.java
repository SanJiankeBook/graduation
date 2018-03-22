package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.ChapterBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ConvertQuestionEdition;
import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;

import com.yc.vo.DataGaidModel;
import com.yc.vo.DataarrayActionModel;
import com.yc.vo.Page;
import com.yc.vo.WritingQuestionInfo;

@Service("writingQuestionBiz")
public class WritingQuestionBizImpl implements WritingQuestionBiz {
	private BaseDao baseDao;
	
	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	@Resource(name = "chapterBiz")
	private ChapterBiz chapterBiz;

	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}

	@Override
	public int addWritingQuestion(WritingQuestion wq) {
		try {
			baseDao.add(wq);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	
	
	@Override
	public List<WritingQuestion> searchQuesExist(String question) {
		String sql = "from WritingQuestion where question like ?";
		String[] params = new String[] { "%" + question + "%" };
		List<WritingQuestion> list = baseDao.search(sql, params);
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public int updateQuestion(WritingQuestion wq) {
		baseDao.update(wq);
		return 0;
	}

	@Override
	public int updateWritingQuestionFromPaper(WritingQuestionBiz wq) {
		return 0;
	}

	@Override
	public int deleteWritingQuestion(WritingQuestion wq) {
		baseDao.del(wq);
		return 0;
	}

	@Override
	public int deleteManyWritingQuestion(String quesiontIDs) {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql = "delete   from   WritingQuestion where   id   in ( :id ) ";
		params.put("id", quesiontIDs);
		return baseDao.executeHql(sql, params);
	}

	@Override
	public List<Object> searchWritingQuestion(WritingQuestionBiz wq,
			int displayRows, int nextNum, int sortBy) {
		return null;
	}

	@Override
	public int getSearchMaxRow(WritingQuestionBiz wq) {
		return 0;
	}

	@Override
	public int getQuestionCount(int chapterId) {
		return 0;
	}

	@Override
	public String getAnswerOfId(int id) {
		String sql = "select answer from WritingQuestion where id=?";
		String params[] = new String[] { id + "" };
		List<String> list = baseDao.search(sql, params);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int getChapterId(int id) {
		return 0;
	}

	/*原系统的方法
	 @Override
	public String getQuestionIdsOf(String sQuestionCount, String semester,
			int questionCount, String rate1, String rate2, String rate3,
			List alQuestions) {
		String strQuestionIDs = "";
		String strQ[] = sQuestionCount.split(":");
		int subjID = subjectBiz.getSubjectId(strQ[0]);
		String chapterN[] = strQ[1].split(";");
		// String chapterN[] = strQ[1].split(",");
		@SuppressWarnings("unused")
		List chapterIDandCountList = new ArrayList();
		for (int i = 0; i < chapterN.length; i++) {
			int chapterId = chapterBiz.getChapterId(subjID,
					chapterN[i].split(",")[0]);
			int chapterCount = Integer.parseInt(chapterN[i].split(",")[1]);
			String ss = this.searchQuestionOfRandom(semester, subjID,
					chapterId, chapterCount, rate1, rate2, rate3, alQuestions);
			// 数据库中符合条件的题目不够数时
			// if (ss.equals("0")) {
			// return ss;
			// }
			strQuestionIDs += ss;
		}

		return strQuestionIDs;
	}*/
	@SuppressWarnings("unused")
	@Override//修改后的，去除了查重的方法
	public String getQuestionIdsOf(String sQuestionCount, String semester,
			int questionCount, String rate1, String rate2, String rate3) {
		String strQuestionIDs = "";
		String strQ[] = sQuestionCount.split(":");
		if(strQ.length<2){
			return "";
		}
		int subjID = subjectBiz.getSubjectId(strQ[0]);
		String chapterN[] = strQ[1].split(";");
		// String chapterN[] = strQ[1].split(",");
		
		List chapterIDandCountList = new ArrayList();
		for (int i = 0; i < chapterN.length; i++) {
			int chapterId = chapterBiz.getChapterId(subjID,
					chapterN[i].split(",")[0]);
			int chapterCount = Integer.parseInt(chapterN[i].split(",")[1]);
			String ss = this.searchQuestionOfRandom(semester, subjID,
					chapterId, chapterCount, rate1, rate2, rate3);
			// 数据库中符合条件的题目不够数时
			// if (ss.equals("0")) {
			// return ss;
			// }
			strQuestionIDs += ss;
		}

		return strQuestionIDs;
	}

	/*@Override//原系统的方法
	public String searchQuestionOfRandom(String semester, int subjectId,
			int chapterId, int chapterCount, String rate1, String rate2,
			String rate3, List alQuestions) {
		long maxRow = 0;
		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		if (!rate1.equals("") && !rate2.equals("") && !rate3.equals("")) {
			r1 = Integer.parseInt(rate1.substring(0, 2)) * chapterCount / 100;
			r3 = Integer.parseInt(rate3.substring(0, 2)) * chapterCount / 100;
			r2 = chapterCount - r1 - r3;
		}
		String strQuestionId = ""; // 串连产生的题编号
		// 通过指定条件,得到查询到的记录条数
		String sql = "select count(*) from WritingQuestion where 1=1 ";
		if (!semester.equals("")) {
			sql += " and semester = '" + semester + "' ";
		}
		if (subjectId != 0) {
			sql += " and subjectId = " + subjectId;
		}
		if (chapterId != 0) {
			sql += " and chapterId = " + chapterId;
		}
		List list = baseDao.search(sql);
		if (list != null && list.size() > 0) {
			maxRow = (Long) list.get(0);
		}
		// 当题库中没有足够多的题时,返回null
		if (maxRow < chapterCount) {
			chapterCount = (int) maxRow; // 题目不够时，等于全部的题目
		}
		if (r1 == 0 && r2 == 0 && r3 == 0) {
//			strQuestionId = searchQuestionIDsOf(semester, subjectId, chapterId,
//					0, chapterCount, alQuestions);
		} else {
			List a1 = searchQuestionIDsOf(semester, subjectId, chapterId, 1);
			List a2 = searchQuestionIDsOf(semester, subjectId, chapterId, 2);
			List a3 = searchQuestionIDsOf(semester, subjectId, chapterId, 3);
			
			String ids1 = "";
			String ids2 = "";
			String ids3 = "";
			int count = r1+r2+r3;
			//如果a1、a2和a3都不为空，且有一个难度的题库数量不足出题数量，则先出满难度为1的，然后在出满难度为2的，剩下的题目为难度为3的
			if(a1!=null&&a1.size()>0&&a2!=null&&a2.size()>0&&a3!=null&&a3.size()>0){
				if(a1.size()<r1||a2.size()<r2||a3.size()<r3){
					if(a1.size()<=count){
						ids1=searchQuestionIds(a1,a1.size(),alQuestions);
						if(a2.size()<=count-a1.size()){
							ids2=searchQuestionIds(a2,a2.size(),alQuestions);
							ids3=searchQuestionIds(a3,count-a2.size()-a1.size(),alQuestions);
							return ids1+ids2+ids3;
						}else{
							ids2=searchQuestionIds(a2,count-a1.size(),alQuestions);
							return ids1+ids2;
						}
					}
				}
				if(a1.size()>=r1&&a2.size()>=r2&&a3.size()>=r3){
					ids1=searchQuestionIds(a1,r1,alQuestions);
					ids2=searchQuestionIds(a2,r2,alQuestions);
					ids3=searchQuestionIds(a3,r3,alQuestions);
					return ids1+ids2+ids3;
				}
			}
			
			if(a1==null||a1.size()<=0){
				if(a2==null||a2.size()<=0){
					ids3=searchQuestionIds(a3,count,alQuestions);
					return ids3;
				}else{
					if(a3==null||a3.size()<=0){
						ids2=searchQuestionIds(a2,count,alQuestions);
						return ids2;
					}else{
						if(a2.size()<=count){
							ids2=searchQuestionIds(a2,a2.size(),alQuestions);
							ids3=searchQuestionIds(a3,count-a2.size(),alQuestions);
							return ids2+ids3;
						}else{
							ids2=searchQuestionIds(a2,count,alQuestions);
							return ids2;
						}
					}
				}
			}
			
			if(a2==null||a2.size()<=0){
				if(a1==null||a1.size()<=0){
					ids3=searchQuestionIds(a3,count,alQuestions);
					return ids3;
				}else{
					if(a3==null||a3.size()<=0){
						ids1=searchQuestionIds(a1,count,alQuestions);
						return ids1;
					}else{
						if(a1.size()<=count){
							ids1=searchQuestionIds(a1,a1.size(),alQuestions);
							ids3=searchQuestionIds(a3,count-a1.size(),alQuestions);
							return ids1+ids3;
						}else{
							ids1=searchQuestionIds(a1,count,alQuestions);
							return ids1;
						}
					}
				}
			}
			
			if(a3==null||a3.size()<=0){
				if(a1==null||a1.size()<=0){
					ids2=searchQuestionIds(a2,count,alQuestions);
					return ids2;
				}else{
					if(a2==null||a2.size()<=0){
						ids1=searchQuestionIds(a1,count,alQuestions);
						return ids1;
					}else{
						if(a1.size()<=count){
							ids1=searchQuestionIds(a1,a1.size(),alQuestions);
							ids2=searchQuestionIds(a2,count-a1.size(),alQuestions);
							return ids1+ids2;
						}else{
							ids1=searchQuestionIds(a1,count,alQuestions);
							return ids1;
						}
					}
				}
			}
			
//			if(a2!=null&&a2.size()>0){
//				if(a2.size()>=r2){
//					ids2=searchQuestionIds(a2,r2,alQuestions);
//					r2=0;
//				}else{
//					ids2=searchQuestionIds(a2,a2.size(),alQuestions);
//					r2=r2-a2.size();
//				}
//			}
//			
//			if(a3!=null&&a3.size()>0){
//				if(a3.size()>=r3){
//					ids3=searchQuestionIds(a3,r3,alQuestions);
//					r3=0;
//				}else{
//					ids3=searchQuestionIds(a3,a3.size(),alQuestions);
//					r3=r3-a3.size();
//				}
//			}
//			String a1 = searchQuestionIDsOf(semester, subjectId, chapterId, 1,
//					r1, alQuestions);
//			String a2 = searchQuestionIDsOf(semester, subjectId, chapterId, 2,
//					r2, alQuestions);
//			String a3 = searchQuestionIDsOf(semester, subjectId, chapterId, 3,
//					r3, alQuestions);
//
			strQuestionId = ids1 + ids2 + ids3;
		}
		return strQuestionId;
	}*/
	
	@Override//自己写的方法，去除了查重的方法
	public String searchQuestionOfRandom(String semester, int subjectId,
			int chapterId, int chapterCount, String rate1, String rate2,
			String rate3) {
		long maxRow = 0;
		int r1 = 0;
		int r2 = 0;
		int r3 = 0;
		if (!rate1.equals("") && !rate2.equals("") && !rate3.equals("")) {
			if(!"0%".equals(rate1)){
				r1 = Integer.parseInt(rate1.substring(0, 2)) * chapterCount / 100;
			}
			if(!"0%".equals(rate3)){
				r3 = Integer.parseInt(rate3.substring(0, 2)) * chapterCount / 100;
			}
			r2 = chapterCount - r1 - r3;
		}
		String strQuestionId = ""; // 串连产生的题编号
		// 通过指定条件,得到查询到的记录条数
		Map<String,Object> params=new HashMap<String,Object>();
		String sql = "select count(*) from WritingQuestion where 1=1 ";
		if (!semester.equals("")) {
			sql += " and semester = :semester ";
			params.put("semester", semester);
		}
		if (subjectId != 0) {
			sql += " and subjectId = :subjectId" ;
			params.put("subjectId", subjectId);
		}
		if (chapterId != 0) {
			sql += " and chapterId = :chapterId";
			params.put("chapterId", chapterId);
		}
		
		maxRow=baseDao.findcount(sql, params);
		/*List list = baseDao.search(sql);
		if (list != null && list.size() > 0) {
			maxRow = (Long) list.get(0);
		}*/
		// 当题库中没有足够多的题时,返回null
		if (maxRow < chapterCount) {
			chapterCount = (int) maxRow; // 题目不够时，等于全部的题目
		}
		if (r1 == 0 && r2 == 0 && r3 == 0) {
//			strQuestionId = searchQuestionIDsOf(semester, subjectId, chapterId,
//					0, chapterCount, alQuestions);
		} else {
			List a1 = searchQuestionIDsOf(semester, subjectId, chapterId, 1);
			List a2 = searchQuestionIDsOf(semester, subjectId, chapterId, 2);
			List a3 = searchQuestionIDsOf(semester, subjectId, chapterId, 3);
			
			String ids1 = "";
			String ids2 = "";
			String ids3 = "";
			int count = r1+r2+r3;
			int a1_count = a1.size();
			int a2_count = a2.size();
			int a3_count = a3.size();
			//如果a1、a2和a3都不为空，且有一个难度的题库数量不足出题数量，则先出满难度为1的，然后在出满难度为2的，剩下的题目为难度为3的
			if(a1!=null&&a1_count>0&&a2!=null&&a2_count>0&&a3!=null&&a3_count>0){
				if(a1_count<r1||a2_count<r2||a3_count<r3){
					if(a1_count<=count){
						ids1=searchQuestionIds(a1,a1_count);
						if(a2_count<=count-a1_count){
							ids2=searchQuestionIds(a2,a2_count);
							ids3=searchQuestionIds(a3,count-a2_count-a1_count);
							return ids1+ids2+ids3;
						}else{
							ids2=searchQuestionIds(a2,count-a1_count);
							return ids1+ids2;
						}
					}
				}
				if(a1_count>=r1&&a2_count>=r2&&a3_count>=r3){
					ids1=searchQuestionIds(a1,r1);
					ids2=searchQuestionIds(a2,r2);
					ids3=searchQuestionIds(a3,r3);
					return ids1+ids2+ids3;
				}
			}
			
			if(a1==null||a1_count<=0){
				if(a2==null||a2_count<=0){
					ids3=searchQuestionIds(a3,count);
					return ids3;
				}else{
					if(a3==null||a3_count<=0){
						ids2=searchQuestionIds(a2,count);
						return ids2;
					}else{
						if(a2_count<=count){
							ids2=searchQuestionIds(a2,a2_count);
							ids3=searchQuestionIds(a3,count-a2_count);
							return ids2+ids3;
						}else{
							ids2=searchQuestionIds(a2,count);
							return ids2;
						}
					}
				}
			}
			
			if(a2==null||a2_count<=0){
				if(a1==null||a1_count<=0){
					ids3=searchQuestionIds(a3,count);
					return ids3;
				}else{
					if(a3==null||a3_count<=0){
						ids1=searchQuestionIds(a1,count);
						return ids1;
					}else{
						if(a1_count<=count){
							ids1=searchQuestionIds(a1,a1_count);
							ids3=searchQuestionIds(a3,count-a1_count);
							return ids1+ids3;
						}else{
							ids1=searchQuestionIds(a1,count);
							return ids1;
						}
					}
				}
			}
			
			if(a3==null||a3_count<=0){
				if(a1==null||a1_count<=0){
					ids2=searchQuestionIds(a2,count);
					return ids2;
				}else{
					if(a2==null||a2_count<=0){
						ids1=searchQuestionIds(a1,count);
						return ids1;
					}else{
						if(a1_count<=count){
							ids1=searchQuestionIds(a1,a1_count);
							ids2=searchQuestionIds(a2,count-a1_count);
							return ids1+ids2;
						}else{
							ids1=searchQuestionIds(a1,count);
							return ids1;
						}
					}
				}
			}
			
//			if(a2!=null&&a2.size()>0){
//				if(a2.size()>=r2){
//					ids2=searchQuestionIds(a2,r2,alQuestions);
//					r2=0;
//				}else{
//					ids2=searchQuestionIds(a2,a2.size(),alQuestions);
//					r2=r2-a2.size();
//				}
//			}
//			
//			if(a3!=null&&a3.size()>0){
//				if(a3.size()>=r3){
//					ids3=searchQuestionIds(a3,r3,alQuestions);
//					r3=0;
//				}else{
//					ids3=searchQuestionIds(a3,a3.size(),alQuestions);
//					r3=r3-a3.size();
//				}
//			}
//			String a1 = searchQuestionIDsOf(semester, subjectId, chapterId, 1,
//					r1, alQuestions);
//			String a2 = searchQuestionIDsOf(semester, subjectId, chapterId, 2,
//					r2, alQuestions);
//			String a3 = searchQuestionIDsOf(semester, subjectId, chapterId, 3,
//					r3, alQuestions);
//
			strQuestionId = ids1 + ids2 + ids3;
		}
		return strQuestionId;
	}

	/**
	 * @author Tane
	 * 根据题目的list、数量还有alQuestions得到一组题的题号
	 * @param a1
	 * @param r1
	 * @param alQuestions
	 *//*
	private String searchQuestionIds(List list, int count, List alQuestions) {
		List questionIds = new ArrayList();
		if (list != null && list.size() > 0) {
			questionIds = list;
		}
		// 获取原来的试卷蓝本(ID字符串,有可能系多套试卷的ID字符串)
		// 试卷蓝本(字符串)
		String currQuestionIds = "";
		if (alQuestions != null && alQuestions.size() > 0) {
			for (int i = 0; i < alQuestions.size(); i++) {
				String str = (String) alQuestions.get(i);
				currQuestionIds += str;
			}
		}
		String[] arrQuestions = currQuestionIds.split(",");

		String strId = ""; // 查询到的题目编号
		@SuppressWarnings("unused")
		List randNums = new ArrayList(); // 存放产生的随机数
		// 产生查询到的最大记录数的随机数
		Random ran = new Random();
		// 把产生不为0的随机数存入集合中
		for (int i = 0; i < count; i++) {
			int n = ran.nextInt(questionIds.size());
			int flag = 0;
			// 查看"蓝本"中是否有随机产生的题目ID
			for (int j = 0; j < arrQuestions.length; j++) {
				if (arrQuestions[j].equals(questionIds.get(n).toString())) {
					flag++;
				}
			}
			// 如果在"蓝本"中没有该题目ID就获取该题目ID
			if (flag == 0) {
				strId += questionIds.get(n) + ",";
				// 得到一个删除一个
				questionIds.remove(n);
			}
		}
		return strId;
	}*/
	
	/**
	 * @author Tane
	 * 根据题目的list、数量还有alQuestions得到一组题的题号
	 * @param a1
	 * @param r1
	 */
	private String searchQuestionIds(List list, int count) {
		List questionIds = new ArrayList();
		if (list != null && list.size() > 0) {
			questionIds = list;
		}

		String strId = ""; // 查询到的题目编号
		// 产生查询到的最大记录数的随机数
		Random ran = new Random();
		// 把产生不为0的随机数存入集合中
		for (int i = 0; i < count; i++) {
			int n = ran.nextInt(questionIds.size());
			strId += questionIds.get(n) + ",";
			// 得到一个删除一个
			questionIds.remove(n);
		}
		return strId;
	}

	/**
	 * 通过条件查询每一种类型题目的编号
	 * 
	 * @param semester
	 *            String 学期
	 * @param subjectId
	 *            int 科目ID
	 * @param difficulty
	 *            int 难易度:1,2,3
	 * @param count
	 *            int 多少题
	 * @param maxCount
	 *            int 随机数所在的最大题目编号
	 * @return String 串连后的题目ID字符串
	 * @throws Exception
	 */
	@Override
	public List searchQuestionIDsOf(String semester, int subjectId,
			int chapterId, int difficulty) {
		// 查询符合条件的所有题目编号
		Map<String,Object> map=new HashMap<String,Object>();
		String sql = "select id from WritingQuestion where 1=1 ";
		if (!semester.equals("")) {
			sql += " and semester =:semester" ;
			map.put("semester", semester);
		}
		if (subjectId != 0) {
			sql += " and subjectId = :subjectId ";
			map.put("subjectId", subjectId);
		}
		if (chapterId != 0) {
			sql += " and chapterId =:chapterId" ;
			map.put("chapterId", chapterId);
		}
		if (difficulty != 0) {
			sql += " and difficulty =:difficulty " ;
			map.put("difficulty", difficulty);
		}
		
		
		// if (editionId) 要考虑版本
		return baseDao.findDatabyhql(sql, map, null, null);
//		if (list != null && list.size() > 0) {
//			questionIds = list;
//		}
//		// 获取原来的试卷蓝本(ID字符串,有可能系多套试卷的ID字符串)
//		// 试卷蓝本(字符串)
//		String currQuestionIds = "";
//		if (alQuestions != null && alQuestions.size() > 0) {
//			for (int i = 0; i < alQuestions.size(); i++) {
//				String str = (String) alQuestions.get(i);
//				currQuestionIds += str;
//			}
//		}
//		String[] arrQuestions = currQuestionIds.split(",");
//
//		String strId = ""; // 查询到的题目编号
//		@SuppressWarnings("unused")
//		List randNums = new ArrayList(); // 存放产生的随机数
//		// 产生查询到的最大记录数的随机数
//		Random ran = new Random();
//		// 把产生不为0的随机数存入集合中
//		for (int i = 0; i < count; i++) {
//			int n = ran.nextInt(questionIds.size());
//			int flag = 0;
//			// 查看"蓝本"中是否有随机产生的题目ID
//			for (int j = 0; j < arrQuestions.length; j++) {
//				if (arrQuestions[j].equals(questionIds.get(n).toString())) {
//					flag++;
//				}
//			}
//			// 如果在"蓝本"中没有该题目ID就获取该题目ID
//			if (flag == 0) {
//				strId += questionIds.get(n) + ",";
//				// 得到一个删除一个
//				questionIds.remove(n);
//			}
//		}
//		return strId;
	}

	// 获得相应学期、科目以及章节的题目难度数量
	public int getDifficultyRate(String semester, int subjectId, int chapterId,
			int difficulty) {
		// 查询符合条件的所有题目编号
		Map<String,Object> map=new HashMap<String,Object>();
		//String sql = "select id from WritingQuestion where 1=1 ";
		String sql = "select count(0) from WritingQuestion where 1=1 ";
		if (!semester.equals("")) {
			sql += " and semester =:semester" ;
			map.put("semester", semester);
		}
		if (subjectId != 0) {
			sql += " and subjectId =:subjectId";
			map.put("subjectId", subjectId);
		}
		if (chapterId != 0) {
			sql += " and chapterId =:chapterId " ;
			map.put("chapterId", chapterId);
		}
		if (difficulty != 0) {
			sql += " and difficulty = :difficulty " ;
			map.put("difficulty", difficulty);
		}
		return baseDao.findcount(sql, map);
		//return baseDao.search(sql).size();
	}

	@Override
	public String getAnswers(String strQuestionIds) {
		String strAnswers = "";
		String arr[] = strQuestionIds.split(",");
		//升序排列
		int arrs[]=new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int n = Integer.parseInt(arr[i].trim());
			arrs[i]=n;
		}
		
		for(int i=0;i<arrs.length;i++){
			int temp;
			for(int j=0;j<arrs.length-i-1;j++){
				if(arrs[j]>arrs[j+1]){
					temp=arrs[j];
					arrs[j]=arrs[j+1];
					arrs[j+1]=temp;
				}
			}
		}
		for (int i = 0; i < arrs.length; i++) {
			//int n = Integer.parseInt(arr[i].trim());
			strAnswers += (i + 1) + "," + this.getAnswerOfId(arrs[i]) + ";";
		}
		return strAnswers;
	}

	@Override
	public List<Object> delQuestionToNext(int questionId) {
		return null;
	}

	@Override
	public List<Object> searchQuestion(int questionId) {
		return null;
	}

	@Override
	public List<Object> searchWritingQuestionByForward(int id, String forward) {
		return null;
	}

	@Override
	public int maxWQuestionId() {
		return 0;
	}

	@Override
	public WritingQuestion searchWQuestion(int id) {
		String sql = "from WritingQuestion where id=?";
		String[] params = new String[] { id + "" };
		List<WritingQuestion> list = baseDao.search(sql, params);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int getSubjectCount(int subjectId) {
		return 0;
	}

	@Override
	public int getNextIdentity() {
		return 0;
	}

	/**
	 * 根据生成试卷的条件,随机产生指定章节的一组题
	 * 
	 * @param wq
	 *            WritingQuestion
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
//	private String searchQuestionOfRandom(String semester, int subjectId,
//			int chapterId, int chapterCount, String rate1, String rate2,
//			String rate3, ArrayList alQuestions) throws Exception {
//		int maxRow = 0;
//		int r1 = 0;
//		int r2 = 0;
//		int r3 = 0;
//		// 当难易度系数不为空时,产生难易度题目数量
//		if (!rate1.equals("") && !rate2.equals("") && !rate3.equals("")) {
//			r1 = Integer.parseInt(rate1.substring(0, 2)) * chapterCount / 100;
//			r3 = Integer.parseInt(rate1.substring(0, 2)) * chapterCount / 100;
//			r2 = chapterCount - r1 - r3;
//		}
//		String strQuestionId = ""; // 串连产生的题编号
//		// 通过指定条件,得到查询到的记录条数
//		String sql = "select count(*) from Writingquestion where 1=1 ";
//		if (!semester.equals("")) {
//			sql += " and semester = '" + semester + "' ";
//		}
//		if (subjectId != 0) {
//			sql += " and subjectId = " + subjectId;
//		}
//		if (chapterId != 0) {
//			sql += " and chapterId = " + chapterId;
//		}
//		List<Integer> list = (List<Integer>) baseDao.search(sql);
//		if (list != null && list.size() > 0) {
//			maxRow = list.get(0);
//		}
//		// 当题库中没有足够多的题时,返回null
//		if (maxRow < chapterCount) {
//			chapterCount = maxRow; // 题目不够时，等于全部的题目
//		}
//		if (r1 == 0 || r2 == 0 || r3 == 0) {
//			strQuestionId = searchQuestionIDsOf(semester, subjectId, chapterId,
//					0, chapterCount, alQuestions);
//		} else {
//			String a1 = searchQuestionIDsOf(semester, subjectId, chapterId, 1,
//					r1, alQuestions);
//			String a2 = searchQuestionIDsOf(semester, subjectId, chapterId, 2,
//					r2, alQuestions);
//			String a3 = searchQuestionIDsOf(semester, subjectId, chapterId, 3,
//					r3, alQuestions);
//			strQuestionId = a1 + a2 + a3;
//		}
//		return strQuestionId;
//	}

	/**
	 * 根据多个id查试卷信息
	 * 
	 * @author fanhaohao
	 */
	@Override
	public List<WritingQuestion> findWritingQuestionByIds(List<Integer> ids) {
		String hql = "from WritingQuestion wq where wq.id in (:listName)";
		List<WritingQuestion> listWritingQuestion = baseDao.searchByList(hql,
				ids, "listName");
		return listWritingQuestion;
	}

	public int updateWritingQuestion(WritingQuestion wq) {
		WritingQuestion writingQuestion = (WritingQuestion) baseDao.get(
				WritingQuestion.class, wq.getId());
		writingQuestion.setQuestion(wq.getQuestion());
		writingQuestion.setOptionA(wq.getOptionA());
		writingQuestion.setOptionB(wq.getOptionB());
		writingQuestion.setOptionC(wq.getOptionC());
		writingQuestion.setOptionD(wq.getOptionD());
		writingQuestion.setAnswer(wq.getAnswer());
		int answerLength=wq.getAnswer().length();
	if(answerLength==1){
		writingQuestion.setQuestionType("1");
	}else{
		writingQuestion.setQuestionType("2");
	}
		baseDao.update(writingQuestion);
		return 0;
	}

	@Override
	public void searchAllWritingQuestionPage(Page<WritingQuestion> page) {
		String hqlString = "from WritingQuestion wp";
		String queryCountHql = "select count(wp) from WritingQuestion wp";
		baseDao.showPage(hqlString, queryCountHql, page);
	}

	@Override
	public int getNextId(int id) {
		String sql = "select id from WritingQuestion where id = (select min(id) from WritingQuestion where id > ? )";
		String[] params = new String[] { id + "" };
		List<Number> list = baseDao.search(sql, params);
		return (Integer) list.get(0);
	}

	@Override
	public int getPrevId(int id) {
		String sql = "select id from WritingQuestion where id = (select max(id) from WritingQuestion where id < ? )";
		String[] params = new String[] { id + "" };
		List<Number> list = baseDao.search(sql, params);
		return (Integer) list.get(0);
	}

	@Override
	public Integer getCount(int chapterId) {
		String sql = "select count(id) from WritingQuestion where chapterId=?";
		String[] params = new String[] { chapterId + "" };
		List list = baseDao.search(sql, params);
		Number count = (Number) list.get(0);
		return count.intValue();
	}

	@Override
	public DataGaidModel searchAllWritingQuestionPage(DataarrayActionModel da) {
		DataGaidModel dgm = new DataGaidModel();
		String sql = "from WritingQuestion wp where 1=1 ";
		String sqlcount = "select count(wp) from WritingQuestion wp where 1=1 ";
		Map<String, Object> param = new HashMap<String, Object>();
		int page = da.getPage();
		int rows = da.getRows();
		int offset = (page - 1) * rows;
		String order = da.getOrder();
		String s = da.getSort();
		if (s != null || !"".equals(s)) {
			if ("subjectName".equals(s)) {
				s = "subjectId";
			}
			if ("chapterName".equals(s)) {
				s = "chapterId";
			}
			if ("type".equals(s)) {
				s = "questionType";
			}
		}
		String sort = "wp." + s;
		if (da != null) {
			if (da.getQuestionType() != null && Integer.parseInt(da.getQuestionType()) != 3) {
				sql += " and wp.questionType = :questionType";
				sqlcount += " and wp.questionType = '" + da.getQuestionType()
				+ "'";
				param.put("questionType", da.getQuestionType());
			}
			if (da.getDifficult() != null && da.getDifficult() != 4) {
				sql += " and wp.difficulty = :difficulty" ;
				sqlcount += " and wp.difficulty = " + da.getDifficult();
				param.put("difficulty", da.getDifficult());
			}
			if (da.getSemester() != null && !"0".equals(da.getSemester())) {
				sql += " and wp.semester =:semester " ;
				sqlcount += " and wp.semester = '" + da.getSemester() + "'";
				param.put("semester", da.getSemester());
			}
			if (da.getSubject() != null && da.getSubject() != 0) {
				sql += " and wp.subjectId =:subjectId";
				sqlcount += " and wp.subjectId = " + da.getSubject();
				param.put("subjectId", da.getSubject());
			}
			if (da.getVersion() != null && !"".equals(da.getVersion())) {
				sql += " and wp.editionId =:editionId ";
				sqlcount += " and wp.editionId = " + da.getVersion();
				param.put("editionId", da.getVersion());
			}
			if (da.getTxtQuestion() != null && !"".equals(da.getTxtQuestion())) {
				try {
					Integer id= Integer.valueOf(da.getTxtQuestion());
					sql += " and wp.id =:id";
					sqlcount += " and wp.id =" + id;
					param.put("id", id);
				} catch (NumberFormatException e) {
					sql += " and wp.question like :question";
					sqlcount += " and wp.question like '%" + da.getTxtQuestion()+ "%'";
					 param.put("question", "%"+da.getTxtQuestion()+"%");
				}
				
			}
			
		}
 
		List<WritingQuestion> list = baseDao.findByProperty(sql, param, offset,rows, sort, order);
		List<Long> count = baseDao.search(sqlcount);
		Long total = (long) 0;
		if (count != null && count.size() > 0) {
			total = count.get(0);
		}
		dgm.setTotal(total);
		dgm.setRows((list != null && list.size() > 0 ? list : null));
		return dgm;
	}

	@Override
	public WritingQuestion sreachLastRecord() {
		String sql="select * from WritingQuestion  order by id desc ";
		List list=this.baseDao.excuteSelectSql(sql, new WritingQuestion());
		if(list.size()>0){
			return (WritingQuestion) list.get(0);
		}else{
			return null;
		}
		
	}
	

}
