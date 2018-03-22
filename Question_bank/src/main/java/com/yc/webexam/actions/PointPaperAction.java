package com.yc.webexam.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.PointAnswerBiz;
import com.yc.biz.PointInfoBiz;
import com.yc.biz.PointPaperBiz;
import com.yc.biz.SubjectBiz;
import com.yc.po.ExamineeClass;
import com.yc.po.PointAnswer;
import com.yc.po.PointInfo;
import com.yc.po.PointPaper;
import com.yc.utils.JsonProtocol;
import com.yc.utils.JsonUtil;
import com.yc.vo.PointModel;
import com.yc.vo.PointPaperModel;

public class PointPaperAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PointPaperAction.class);

	private String className;
	private String semester;
	private Integer subjectId;
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;
	@Resource(name = "pointPaperBiz")
	private PointPaperBiz pointPaperBiz;
	@Resource(name = "pointInfoBiz")
	private PointInfoBiz pointInfoBiz;
	@Resource(name = "pointAnswerBiz")
	private PointAnswerBiz pointAnswerBiz;
	private Integer pid;

	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	/**
	 * 根据学期（semester）和班级名称（className）来查询课程名称及课程编号
	 * 
	 * @author fanhaohao
	 */
	public void findSubjectInfo() {
		String jsonStr = "";
		try {
			List<String> list = subjectBiz.getSubjectNamesBySemesterAndClassName(semester, className);
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(0, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	/**
	 * 根据班级名称（className）和课程编号（subjectId）来查测评试卷信息
	 * 
	 * @author fanhaohao
	 */
	public void findPointPaperInfo() {
		String jsonStr = "";
		try {
			List<PointPaper> list = subjectBiz.getPointPaperByIdAndClassName(subjectId, className);
			List<PointPaperModel> listInfo=new ArrayList<PointPaperModel>();
			if (list != null && list.size() > 0) {
				for (PointPaper l : list) {
					/*l.setPointAnswers(null);
					l.getSubject().setChapters(null);
					l.getSubject().setEdition(null);
					l.getSubject().setPointInfos(null);
					l.getExamineeClass().setaDailyTalks(null);
					l.getExamineeClass().setCheckings(null);
					l.getExamineeClass().setPointPapers(null);*/
					PointPaperModel paperModel=new PointPaperModel();
					paperModel.setSubjectName(l.getSubject().getSubjectName());
					paperModel.setPname(l.getPname());
					paperModel.setExamDate(l.getPdate());
					paperModel.setPid(l.getPid());
					paperModel.setClassName(l.getExamineeClass().getClassName());
					paperModel.setPstatus(l.getPstatus());
					paperModel.setDescript(l.getDescript());
					paperModel.setPtitle(l.getPtitle());
					/*paperModel.setPtitle(l.getPtitle());*/
					listInfo.add(paperModel);
				}
			}
			//JsonProtocol jp = new JsonProtocol(0, null, list);
			//jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd",
			//		SerializerFeature.DisableCircularReferenceDetect);
			logger.info("test========>"+list);
			 jsonStr=super.writeJson(0, listInfo);
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

	/**
	 * 根据班级名称pid来查测评试卷信息
	 * 
	 * @author fanhaohao
	 */
	public void findPointPaperInfoPid() {
		String jsonStr = "";
		try {
			// 查出对应的PointInfo的id字符串
			String pTilte = pointPaperBiz.getPointPaperTitleByPid(pid);
			// 去掉pTilte的最后一个逗号
			if (pTilte != null && !"".equals(pTilte)) {
				String ptStringStr = "";
				if (pTilte.endsWith(",")) {
					ptStringStr = pTilte.substring(0, pTilte.lastIndexOf(","));
				} else {
					ptStringStr = pTilte;
				}
				// 把PointInfo的id字符串转化成list集合，（list集合用于hibernate的查询中）
				List<Integer> pids = new ArrayList<Integer>();
				// 加【】便于alibaba json反序列化
				JSONArray jsonArray = JSON.parseArray("[" + ptStringStr + "]");
				for (Object o : jsonArray) {
					pids.add(Integer.parseInt(o.toString()));
				}
				// 根据id的集合来查询
				List<PointInfo> listPi = pointInfoBiz.findPointAllInfoByPids(pids);
				// 根据pid来查询pointanwer的信息
				List<PointAnswer> listPa = pointAnswerBiz.findAnswersByPid(pid);
				// 创建一个pointmodel的集合
				List<PointModel> lpm = new ArrayList<PointModel>();
				// 算平均分和总分
				if (listPi != null && listPi.size() > 0 && listPa!=null && listPa.size()>0) {
					for (PointInfo li : listPi) {
						PointModel pm = new PointModel();
						pm.setPcontent(li.getPcontent());
						pm.setPointInfoId(li.getPid());
						float sumScore = 0;
						float avgScore = 0;
						int answerCount = 0;
						if (listPa != null && listPa.size() > 0) {
							for (PointAnswer la : listPa) {
								String answers = la.getAnswer();
								if (answers != null && !"".equals(answers)) {
									String answersStr = "";
									if (answers.endsWith(",")) {
										answersStr = answers.substring(0, answers.lastIndexOf(","));
									} else {
										answersStr = answers;
									}
									String[] answersStrArr = answersStr.split(",");
									if (answersStrArr != null && answersStrArr.length > 0) {
										for (int i = 0,len=answersStrArr.length; i < len; i++) {
											if (Integer.parseInt(answersStrArr[i].split("-")[0]) == li.getPid()) {
												sumScore += Integer.parseInt(answersStrArr[i].split("-")[1]);
											}
										}
									}

								}
							}
						}
						answerCount = listPa.size();
						if (answerCount != 0) {
							avgScore = (float) (Math.round((sumScore * 100)) / answerCount) / 100;
						}
						pm.setAvgScore(avgScore);
						pm.setSumScore(sumScore);
						pm.setAnswerCount(answerCount);
						lpm.add(pm);
					}
				}
				jsonStr = super.writeJson(0, lpm);
			} else {
				jsonStr = super.writeJson(1, "没查询到数据！");
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
	
	/**
	 * 重新指派班级
	 */
	public void AnotherPaper(){
		String jsonStr = "";
		try {
			PointPaper listPi=pointPaperBiz.findPointPaperById(pid);
			PointPaper po=new PointPaper();
			//System.out.println(subjectId);
			//subjectId  新的班级id
			ExamineeClass ec = examineeClassBiz.findExamineeClassById(subjectId);
			//listPi.setExamineeClass(ec);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			//semester 
		    Date date = sdf.parse(semester);  
			
			po.setPname(listPi.getPname());
			po.setPdate(date);
			po.setPstatus(1);
			po.setPaperPwd(listPi.getPaperPwd());
			po.setPtitle(listPi.getPtitle());
			po.setDescript(listPi.getDescript());
			po.setPtitle(listPi.getPtitle());
			po.setExamineeClass(ec);
			po.setSubject(listPi.getSubject());
			//pointPaperBiz.addPointPaper(po);
			Integer result=pointPaperBiz.addPointPaper(po);
			
			//System.out.println(result);
			jsonStr = super.writeJson(0, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonStr = super.writeJson(1, "指派失败！");
			logger.error(e);
			
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	public void setPointPaperBiz(PointPaperBiz pointPaperBiz) {
		this.pointPaperBiz = pointPaperBiz;
	}

	public void setPointInfoBiz(PointInfoBiz pointInfoBiz) {
		this.pointInfoBiz = pointInfoBiz;
	}

	public void setPointAnswerBiz(PointAnswerBiz pointAnswerBiz) {
		this.pointAnswerBiz = pointAnswerBiz;
	}

}
