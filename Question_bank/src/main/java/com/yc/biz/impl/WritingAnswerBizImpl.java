package com.yc.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yc.biz.WritingAnswerBiz;
import com.yc.dao.BaseDao;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.utils.ExamUtil;
import com.yc.utils.JedisUtils;
import com.yc.vo.Page;
import redis.clients.jedis.Jedis;

@Service("writingAnswerBiz")
public class WritingAnswerBizImpl implements WritingAnswerBiz
{

	private Jedis jedis=new Jedis(JedisUtils.REDIS_URL,JedisUtils.REDIS_PORT);
	
	private BaseDao baseDao;
	
	private WritingAnswer wa=new WritingAnswer();

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}

	/**
	 * 添加笔试答卷
	 * 
	 * @param writingAnswer
	 *            WritingAnswer //笔试答卷Bean实体对像
	 * @return int //返回成功添加的行数
	 */
	@Override
	public int addWritingAnswer(WritingAnswer writingAnswer)
	{
		int addResult = 1;
		try {
			this.baseDao.add(writingAnswer);
		} catch (Exception e) {
			addResult=2;
		}
		return addResult;
	}

	@Override
	public String searchAnswer(String paperId, String examineeName)
	{
		String answer =jedis.get(paperId+":"+examineeName);
		if(answer==null){
			answer="";
		}
		return answer;
		
		/*String[] params=new String[]{paperId,examineeName };
		String sql = "select wa.answer from WritingAnswer wa where wa.writingPaper.id = ? and wa.examineeName=?";
		List<String> list=this.baseDao.search(sql,params);

		if( list!=null&&list.size()>0 ){
			return list.get(0);
		}	
		return "";*/

	}

	@Override
	public List<String> searchAnswers(String paperId)
	{	
		String[] params=new String[]{paperId };
		String hql = "select  wa.answer from WritingAnswer wa where wa.writingPaper.id = ?";
		List<String> list=this.baseDao.search(hql,params);
		if( list!=null&&list.size()>0 ){
			return list;
		}	
		return null;
	}

	@Override
	public int updateAnswer(String answer, String paperId, String examineeName)
	{
		//更新的时候redis缓存
		String str=jedis.set(paperId+":"+examineeName, answer);
		if(str.equals("OK")){
			return 1;
		}
		return 0;
		/*Map<String,Object> params=new HashMap<String,Object>();
		params.put("answer", answer);
		params.put("paperId", paperId);
		params.put("examineeName", examineeName);
		String hql="update WritingAnswer wa set wa.answer =:answer where wa.writingPaper.id=:paperId and wa.examineeName=:examineeName";
		int result=this.baseDao.executeHql(hql, params);*/
		
	}

	@Override
	public int deleteWritingAnswerByExamineeName(String examineeName)
	{
		return 0;
	}

	@Override
	public int deleteWritingAnswer(WritingAnswer writingAnswer)
	{
		this.baseDao.del(writingAnswer);
		return 0;
	}

	/**
	 * 根据考卷ID、考生名更新分数
	 * 
	 * @param writingAnswer
	 *            WritingAnswer 笔试答卷Bean
	 * @return row int 更新行数
	 * @throws Exception
	 */
	@Override
	public int exeUpdateGrade(Float score,int id,String paperId,String userName)
	{
		String answer=jedis.get(paperId+":"+userName);
		jedis.del(paperId+":"+userName);
		/*WritingAnswer wa=new WritingAnswer();
		wa=(WritingAnswer) this.baseDao.get(wa.getClass(),id);
		wa.setScore(score);*/
		String sql="update writinganswer set score =:score , answer=:answer where id=:id";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("score", score);
		map.put("answer", answer);
		map.put("id", id);
		return baseDao.executeSql(sql, map);
	}
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									
	@Override
	public float searchMaxScore(String paperId)
	{
		String[] params=new String[]{paperId };
		String sql = "select max(wa.score) from WritingAnswer wa where wa.writingPaper.id=?";
		List list=this.baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			double maxScore=(Float) list.get(0);
			return  (float) maxScore;
		}
		return 0;
	}

	@Override
	public float searchMinScore(String paperId)
	{
		String[] params=new String[]{paperId };
		String sql = "select min(wa.score) from WritingAnswer wa where wa.writingPaper.id=?";
		List list=this.baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			double minScore=(Float) list.get(0);
			return  (float) minScore;
		}
		return 0;
	}

	@Override
	public float searchAvgScore(String paperId)
	{	
		
		String[] params=new String[]{paperId };
		String sql = "select avg(wa.score) from WritingAnswer wa where wa.writingPaper.id=?";
		List list=this.baseDao.search(sql, params);
		if(list!=null&&list.size()>0){
			String avgScore= list.get(0)+"";
			
			return  Float.parseFloat( avgScore.substring(0));
		}
		return 0;
	}

	@Override
	public int searchExamineeCount(String paperId)
	{
		return 0;
	}

	@Override
	public String searchScorePercent(String paperId)
	{
		String str = ""; // 用";"连接每一分数段的考生数
		String[] params=new String[]{paperId};
		String sql = "select wa.score from WritingAnswer wa where  wa.writingPaper.id =?";
		List<Float> scores = this.baseDao.search(sql,params);
		// 考虑没有成绩的情况
		if (scores.size() == 0) {
			return "0;0;0;0;0;";
		}
		int[] grade = new int[] { 0, 0, 0, 0, 0 };
		for (Float score : scores) {
			//int s = score.intValue();
			float s=score;
			if (s < 60) {
				grade[0]++;
			} else if (s >= 60 && s < 70) {
				grade[1]++;
			} else if (s >= 70 && s < 80) {
				grade[2]++;
			} else if (s >= 80 && s < 90) {
				grade[3]++;
			} else if (s >= 90) {
				grade[4]++;
			}
		}
		for (int i = 0; i < grade.length; i++) {
			str += grade[i] + ";";
		}
		return str;
	}

	/**
	 * 通过试卷编号,考生姓名和正确答案,计算出此考生的成绩
	 * 
	 * @param paperId
	 *            String 试卷编号
	 * @param examineeName
	 *            String 考生姓名
	 * @param rightAnswer
	 *            String 正确答案
	 * @return int
	 */
	@Override
	public int computeScore(String paperId, String examineeName, String rightAnswer, int countQuestion)
	{
		// 每道题多少分
				double perScore = 100.0 / countQuestion;
				double score = 0;
				// 通过考生姓名和试卷ID得到考生的答案
				String str = this.searchAnswer(paperId, examineeName);
				if (str == null || rightAnswer == null) {
					return 0; // 正确答案得到错误，学生分数也为0,可以再次由老师来评卷
				}
				// 得到考生答案数组
				str = ExamUtil.fillSpaceAnswer(str, countQuestion);
				String examineeAns[] = str.split(";");
				// 正确答案
				String rightAns[] = rightAnswer.split(";");
				// 定义一个Map,键存放试卷中的题目编号,值存放所答答案
				HashMap<String, String> numbers = new HashMap<String, String>();
				// 数组从0开始
				for (int i = 0 ,len=examineeAns.length; i <len ; i++) {
					String oneQuestion[] = examineeAns[i].split(",");
					if (oneQuestion.length == 1) {
						numbers.put(oneQuestion[0], " ");
					} else {
						numbers.put(oneQuestion[0], oneQuestion[1]);
					}
				}
				// 定义一个哈希table,键存放试卷中的题目编号,值存放正确答案
				HashMap<String, String> rNumbers = new HashMap<String, String>();
				for (int i = 0 ,len=examineeAns.length; i <len ; i++) {
					// 如果正确答案没有，可能也会出现异常
					String oneQuestion[] = rightAns[i].split(",");
					if (oneQuestion.length == 1) {
						rNumbers.put(oneQuestion[0], " ");
					} else {
						// 去掉正确答案前后的空格，并转成大写再比
						rNumbers.put(oneQuestion[0], oneQuestion[1].trim()
								.toUpperCase());
					}
				}
				// 开始比较，题号从1开始
				for (int i = 1; i <= countQuestion; i++) {
					String num = numbers.get(String.valueOf(i));
					String rNum = rNumbers.get(String.valueOf(i));
					// 忽略大小写比较
					if (num != null && num.equalsIgnoreCase(rNum)) {
						score += perScore;
					}
				}
				return (int) (Math.round(score));
	}

	@Override
	public boolean isWritingAnswerExist(String paperId, String examineeName)
	{
		return false;
	}

	/**
	 * 学生正常的按了提交按钮，而不是退出的
	 * @author fpc
	 */
	@Override
	public boolean isHandInPaper(String paperId, String examineeName)
	{
		boolean flag=false;
		String sql = "select wa.* from WritingAnswer wa where wa.paperId =:paperId and wa.examineeName =:examineeName and wa.score is not null";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("paperId", paperId);
		map.put("examineeName", examineeName);
		
		List list=this.baseDao.findDatabyhql(sql, map, null, null);
		if( list!=null&&list.size()>0 ){
			flag =  true;
		}	
		return flag;
	}

	@Override
	public List<WritingAnswer> searchWritingAnswer(String paperId)
	{
		return null;
	}

	@Override
	public List<String> getScoreIsNullExamineeName(String paperId)
	{	
		String[] params=new String[]{paperId};
		String sql = "select wa.examineeName from WritingAnswer wa where wa.score is null and wa.writingPaper.id =?";
		List<String> arrEname=this.baseDao.search(sql,params);
		if(arrEname!=null&&arrEname.size()>0){
			return arrEname;
		}
		return null;
		
	}

	/**
	 * 根据试卷ID和考生姓名找到此考生的答卷
	 * @author fpc
	 */
	@Override
	
	public WritingAnswer searchWritingAnswer(String paperId, String examineeName)
	{
		
		WritingAnswer wa = null;
		String[] params=new String[]{paperId,examineeName };
		String sql = "from WritingAnswer wa where wa.writingPaper.id =? and wa.examineeName =?";
		//String sql = "from WritingAnswer wa where wa.writingPaper.id =? and wa.examineeName =? and wa.score is not null";
		List<WritingAnswer> list=(List<WritingAnswer>) this.baseDao.search(sql,params);
		//System.out.println(list.size());
		if( list!=null&&list.size()>0 ){
			wa = new WritingAnswer();
			wa.setId(list.get(0).getId());
			wa.setWritingPaper(list.get(0).getWritingPaper());
			wa.setExamineeName(list.get(0).getExamineeName());
			wa.setAnswer(list.get(0).getAnswer());
			wa.setScore(list.get(0).getScore());
			wa.setCorrectRate(list.get(0).getCorrectRate());
			wa.setSpareTime(list.get(0).getSpareTime());
		}else{
			wa = new WritingAnswer();
		}
		return wa;
		/*if(list.size()>0){
			return list.get(0);
		}
		else
			return null; */
	}

	/**
	 * 更新考试时间
	 */
	@Override
	public int updateSpareTime(String paperId, String examineeName, String spareTime)
	{
		int mySpareTime = Integer.parseInt(spareTime);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("spareTime", mySpareTime);
		params.put("paperId", paperId);
		params.put("examineeName", examineeName);
		String hql="update WritingAnswer wa set wa.spareTime =:spareTime where wa.writingPaper.id=:paperId and wa.examineeName=:examineeName";
		int result=this.baseDao.executeHql(hql, params);
		return result;
	}

	@Override
	public void searchWritingPaperPageByName(Page<WritingAnswer> page,
			WritingPaper wp, WritingAnswer wa) {
		if (wp.getExamineeClass() != null && !"".equals(wp.getExamineeClass())) {
			String[] params = new String[] { wp.getExamineeClass(),wa.getExamineeName() };
			String hqlString = "select wa from WritingAnswer wa left join  wa.writingPaper wp where wp.examineeClass=?  and wp.id like '%"+wp.getId()+"%' and wa.examineeName=?";
			String queryCountHql = "select count(wa) from WritingAnswer wa left join  wa.writingPaper wp where  wp.examineeClass=?  and wp.id like '%"+wp.getId()+"%' and wa.examineeName=?";
			baseDao.showPage(hqlString, queryCountHql, page, params);
		} else {
			String hqlString = "select wa from WritingAnswer wa left join  wa.writingPaper wp where wp.id=wa.paperId  and wp.id like '%"+wp.getId()+"%' and wa.examineeName=?";
			String queryCountHql = "select count(wa)  from WritingAnswer wa left join  wa.writingPaper wp wa where wp.id=wa.paperId  and wp.id like '%"+wp.getId()+"%' and wa.examineeName=?";
			baseDao.showPage(hqlString, queryCountHql, page);
		}
		
	}

	@Override
	public WritingAnswer searchPaperById(WritingPaper wp, WritingAnswer wa) {
		String[] params = new String[] { wp.getId(),wa.getId()+""};
		
		String hqlString = "select wa from WritingAnswer wa left join  wa.writingPaper wp where  wp.id=? and wa.id= ?";
		List<WritingAnswer> list=this.baseDao.search(hqlString, params);
		//System.out.println(list);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<WritingAnswer> searchExamineeInfoByPid(String wpid) {
		String[] params=new String[]{wpid };
		String sql = "from WritingAnswer wa where wa.writingPaper.id=? ";
		List list=this.baseDao.search(sql,params);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public int searchComputeScore(String paperId, String examineeName,
			String rightAnswer, int countQuestion) {
		// 每道题多少分
		double perScore = 100.0 / countQuestion;
		double score = 0;
		// 通过考生姓名和试卷ID得到考生的答案
		String str = this.searchAnswer(paperId, examineeName);
		if (str == null || rightAnswer == null) {
			return 0; // 正确答案得到错误，学生分数也为0,可以再次由老师来评卷
		}
		// 得到考生答案数组
		str = ExamUtil.fillSpaceAnswer(str, countQuestion);
		String examineeAns[] = str.split(";");
		// 正确答案
		String rightAns[] = rightAnswer.split(";");
		// 定义一个Map,键存放试卷中的题目编号,值存放所答答案
		HashMap<String, String> numbers = new HashMap<String, String>();
		// 数组从0开始
		for (int i = 0; i < examineeAns.length; i++) {
			String oneQuestion[] = examineeAns[i].split(",");
			if (oneQuestion.length == 1) {
				numbers.put(oneQuestion[0], " ");
			} else {
				numbers.put(oneQuestion[0], oneQuestion[1]);
			}
		}
		// 定义一个哈希table,键存放试卷中的题目编号,值存放正确答案
		HashMap<String, String> rNumbers = new HashMap<String, String>();
		for (int i = 0; i < rightAns.length; i++) {
			// 如果正确答案没有，可能也会出现异常
			String oneQuestion[] = rightAns[i].split(",");
			if (oneQuestion.length == 1) {
				rNumbers.put(oneQuestion[0], " ");
			} else {
				// 去掉正确答案前后的空格，并转成大写再比
				rNumbers.put(oneQuestion[0], oneQuestion[1].trim()
						.toUpperCase());
			}
		}
		// 开始比较，题号从1开始
		for (int i = 1; i <= countQuestion; i++) {
			String num = numbers.get(String.valueOf(i));
			String rNum = rNumbers.get(String.valueOf(i));
			// 忽略大小写比较
			if (num != null && num.equalsIgnoreCase(rNum)) {
				score += perScore;
			}
		}
		return (int) (Math.round(score));
	}

	@Override
	public int updateGrade(String eName, String wpid, float score) {
//		WritingAnswer wa=new WritingAnswer();
//		String[] params=new String[]{wpid,eName };
//		String sql = "from WritingAnswer wa where wa.writingPaper.id=? and wa.examineeName=?";
//		List<WritingAnswer> listWa=this.baseDao.search(sql,params);
//		if(listWa!=null&&listWa.size()>0){
//			wa=listWa.get(0);
//		}else{
//			wa=null;
//		}
//		wa.setScore(Float.parseFloat(score+""));
//		this.baseDao.update(wa);
//		return 0;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("score", score);
		params.put("paperId", wpid);
		params.put("examineeName", eName);
		String hql="update WritingAnswer wa set wa.score =:score where wa.writingPaper.id=:paperId and wa.examineeName=:examineeName";
		int result=this.baseDao.executeHql(hql, params);
		return result;
	}

	@Override
	public String getAnswer(String paperId, String examineeName){
		
		return jedis.get(paperId+":"+examineeName);
	};
	
}
