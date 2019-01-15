package com.yc.webexam.actions;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
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
import com.yc.biz.TempBiz;
import com.yc.po.ExamineeClass;
import com.yc.po.PointAnswer;
import com.yc.po.PointInfo;
import com.yc.po.PointPaper;
import com.yc.po.Subject;
import com.yc.po.Temp;
import com.yc.utils.JsonProtocol;
import com.yc.utils.JsonUtil;

public class PointAnswerAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PointAnswerAction.class);

	private static final long serialVersionUID = 1L;
	// 班级名称
	private String examClassName;
	// 学生名字
	private String examineeName;
	// 测评卷答卷id
	private Integer opid;
	// 学生名字
	private String stuName;
	// 课程id
	private Integer subjectid;
	// pointInfo pid 的所拼成的字符串数组
	private String ptitle;
	// 学期
	private String semseter;
	private String classId;

	@Resource(name = "pointAnswerBiz")
	private PointAnswerBiz pointAnswerBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "pointInfoBiz")
	private PointInfoBiz pointInfoBiz;
	@Resource(name = "tempBiz")
	private TempBiz tempBiz;
	@Resource(name = "pointPaperBiz")
	private PointPaperBiz pointPaperBiz;
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;

	/**
	 * 根据班级，和姓名来查找测评答卷信息 fanhaohao
	 */
	public void findPoinAnswerInfos() {
		String jsonStr = "";
		try {
			int classId = examineeClassBiz.getClassIdOfname(examClassName);
			List<PointAnswer> list = pointAnswerBiz.findMessageInfo(0, classId, examineeName);
			if (list != null && list.size() > 0) {
				for (PointAnswer l : list) {
					PointPaper pp = new PointPaper();
					ExamineeClass ec = new ExamineeClass();
					ec.setCheckings(null);
					ec.setClassName(l.getPointPaper().getExamineeClass().getClassName());
					ec.setId(l.getPointPaper().getExamineeClass().getId());
					ec.setSemester(l.getPointPaper().getExamineeClass().getSemester());
					ec.setCreateDate(l.getPointPaper().getExamineeClass().getCreateDate());
					pp.setDescript(l.getPointPaper().getDescript());
					pp.setExamineeClass(ec);
					pp.setPointAnswers(null);
					pp.setPid(l.getPointPaper().getPid());
					pp.setPname(l.getPointPaper().getPname());
					pp.setPtitle(l.getPointPaper().getPtitle());
					pp.setRemark(l.getPointPaper().getRemark());
					pp.setPdate(l.getPointPaper().getPdate());
					pp.setSubject(null);
					l.setPointPaper(pp);
				}
			}
			JsonProtocol jp = new JsonProtocol(0, null, list);
			jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd",
					SerializerFeature.DisableCircularReferenceDetect);
			// jsonStr=JSON.toJSONString(jp);
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
	 * 根据测评答案id来查询答卷信息 fanhaohao
	 */
	public void findPoinAnswerInfoByOpid() {
		String jsonStr = "";
		try {
			List<PointAnswer> list = pointAnswerBiz.findAnswerByPid(opid);
			if (list != null && list.size() > 0) {
				for (PointAnswer l : list) {
					PointPaper pp = new PointPaper();
					ExamineeClass ec = new ExamineeClass();
					Subject s = new Subject();
					s.setSubjectName(l.getPointPaper().getSubject().getSubjectName());
					ec.setCheckings(null);
					ec.setClassName(l.getPointPaper().getExamineeClass().getClassName());
					ec.setId(l.getPointPaper().getExamineeClass().getId());
					ec.setSemester(l.getPointPaper().getExamineeClass().getSemester());
					ec.setCreateDate(l.getPointPaper().getExamineeClass().getCreateDate());
					pp.setDescript(l.getPointPaper().getDescript());
					pp.setExamineeClass(ec);
					pp.setPointAnswers(null);
					pp.setPid(l.getPointPaper().getPid());
					pp.setPname(l.getPointPaper().getPname());
					pp.setPtitle(l.getPointPaper().getPtitle());
					pp.setRemark(l.getPointPaper().getRemark());
					pp.setPdate(l.getPointPaper().getPdate());
					pp.setSubject(s);
					l.setPointPaper(pp);
				}
			}
			List<PointAnswer> listinfo=new ArrayList<PointAnswer>();
			if(list!=null){
				listinfo.add(list.get(0));
			}
			JsonProtocol jp = new JsonProtocol(0, null, listinfo);
			jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd");
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
	 * 查出该套试卷的所有学生情况
	 */
	public void findPoinAnswerInfoByOpidAll() {
		List<PointAnswer> listTemp1=new ArrayList<PointAnswer>();//让有留言的在前面显示
		List<PointAnswer> listTemp2=new ArrayList<PointAnswer>();//让有留言的在前面显示
		String jsonStr = "";
		try {
			List<PointAnswer> list = pointAnswerBiz.findAnswerByPid(opid);
			if (list != null && list.size() > 0) {
				
				for (PointAnswer l : list) {
					PointPaper pp = new PointPaper();
					ExamineeClass ec = new ExamineeClass();
					Subject s = new Subject();
					s.setSubjectName(l.getPointPaper().getSubject().getSubjectName());
					ec.setCheckings(null);
					ec.setClassName(l.getPointPaper().getExamineeClass().getClassName());
					ec.setId(l.getPointPaper().getExamineeClass().getId());
					ec.setSemester(l.getPointPaper().getExamineeClass().getSemester());
					ec.setCreateDate(l.getPointPaper().getExamineeClass().getCreateDate());
					pp.setDescript(l.getPointPaper().getDescript());
					pp.setExamineeClass(ec);
					pp.setPointAnswers(null);
					pp.setPid(l.getPointPaper().getPid());
					pp.setPname(l.getPointPaper().getPname());
					pp.setPtitle(l.getPointPaper().getPtitle());
					pp.setRemark(l.getPointPaper().getRemark());
					pp.setPdate(l.getPointPaper().getPdate());
					pp.setSubject(s);
					l.setPointPaper(pp);
					if(l.getIdea()!=null&&l.getIdea()!=""&&!l.getIdea().equals("")){
						listTemp1.add(l);
					}else{
						listTemp2.add(l);
					}
				}
				
			}
			
			List<PointAnswer> listTemp3=new ArrayList<PointAnswer>();//让有留言的在前面显示
			for(PointAnswer p:listTemp1){
				listTemp3.add(p);
			}
			for(PointAnswer p:listTemp2){
				listTemp3.add(p);
			}
			JsonProtocol jp = new JsonProtocol(0, null, listTemp3);
			jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd");
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
	 * 根据测评答卷opid来删除答卷信息 fanhaohao
	 */
	public void deletePoinAnswerInfoByOpid() {
		String jsonStr = "";
		try {
			PointAnswer pa = new PointAnswer();
			pa.setOpid(opid);
			pointAnswerBiz.delPaperAnswer(pa);
			jsonStr = super.writeJson(0, "删除成功！");
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
	 * 根据多个pid来查pointinfo表 fanhaohao
	 */
	public void findAllPoinInfoByPids() {
		String jsonStr = "";
		try {
			List<Integer> pids = new ArrayList<Integer>();
			JSONArray jsonArray = JSON.parseArray(ptitle);
			for (Object o : jsonArray) {
				pids.add(Integer.parseInt(o.toString()));
			}
			List<PointInfo> list = pointInfoBiz.findPointAllInfoByPids(pids);
			for (PointInfo l : list) {
				l.setSubject(null);
			}
			jsonStr = super.writeJson(0, list);
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
	 * 根据学生名称，课程id,班级班级名称来显示显示测评留言信息 fanhaohao
	 */
	public void findPointAnswerInfo() {
		String jsonStr = "";
		try {
			int classId = -1;
			if (examClassName != null && !"".equals(examClassName)) {
				classId = examineeClassBiz.getClassIdOfname(examClassName);
			}
			List<PointAnswer> list = pointAnswerBiz.findPointAnswerInfo(subjectid, classId, stuName);
			if (list != null && list.size() > 0) {
				for (PointAnswer l : list) {
					l.getPointPaper().setPointAnswers(null);
					l.getPointPaper().setExamineeClass(null);
					l.getPointPaper().setSubject(null);
					l.getPointPaper().setPaperPwd(null);
					l.getPointPaper().setPtitle(null);
				}
			}
			JsonProtocol jp = new JsonProtocol(0, null, list);
			jsonStr = JSON.toJSONStringWithDateFormat(jp, "yyyy-MM-dd",
					SerializerFeature.DisableCircularReferenceDetect);
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

	// 根据学期，班级统计课程测评
	public void findsubjectResult() {
		String jsonStr = "";
		try {
			if (semseter != null && !"".equals(semseter) && classId != null && !"".equals(classId)) {
				List<PointAnswer> list = pointAnswerBiz.findSubjectTotal(semseter, Integer.parseInt(classId));
				if (list != null && list.size() > 0) {
					// 先清空temp表
					int result = tempBiz.exectueDelTemp();
					int sizeoflist = list.size();
					if (result >= 0) {
						for (PointAnswer pa : list) {
							int flag = 1;
							String Answer = pointAnswerBiz.findPointAnswer(pa.getName(), pa.getCladdid() + "",
									pa.getPointPaper().getPid() + "");
							String answer = Answer.substring(0, Answer.length());
							int subid = pointPaperBiz.findSid(pa.getPointPaper().getPid(), pa.getCladdid());
							String cname = examineeClassBiz.getClassNameById(pa.getCladdid());
							String subname = subjectBiz.findSubjectName(pa.getPointPaper().getPid());
							while (flag > 0) {
								int pointid = Integer.parseInt(answer.substring(0, answer.indexOf("-", 0)));
								int grade = Integer
										.parseInt(answer.substring(answer.indexOf("-", 0) + 1, answer.indexOf(",", 0)));
								if (pointid > 0) {
									String pcontent = pointInfoBiz.findPointInfoById(pa.getPointPaper().getPid());
									Temp t = new Temp();
									t.setPpid(pa.getPointPaper().getPid());
									t.setSname(pa.getName());
									t.setClassid(pa.getCladdid());
									t.setClassName(cname);
									t.setSubid(subid);
									t.setSubname(subname);
									t.setPointid(pointid);
									t.setPcontent(pcontent);
									t.setGrade(Float.parseFloat(grade + ""));
									tempBiz.addTemp(t);
								}
								if (answer.substring(answer.indexOf(",", 0) + 1, answer.length()).indexOf(",", 0) > 1) {
									answer = answer.substring(answer.indexOf(",", 0) + 1, answer.length());
								} else {
									flag = 0;
								}
							}
							// tempBiz.exectueProc(pa.getPointPaper().getPid(),pa.getCladdid(),pa.getName());

						}

						// 按课程统计
						List<String> totalInfo = tempBiz.findSubjectInfo();
						int totalCount = tempBiz.findSubjectTotal();
						totalInfo.add(totalCount + "");
						if (totalInfo != null && totalInfo.size() > 0) {
							jsonStr = super.writeJson(0, totalInfo);
						} else {
							jsonStr = super.writeJson(1, "没查到值！");
						}
					} else {
						jsonStr = super.writeJson(1, "没查到值！");
					}
				} else {
					jsonStr = super.writeJson(1, "没查到值！");
				}
			} else {
				jsonStr = super.writeJson(1, "没查到值！");
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

	public String getExamClassName() {
		return examClassName;
	}

	public void setExamClassName(String examClassName) {
		this.examClassName = examClassName;
	}

	public String getExamineeName() {
		return examineeName;
	}

	public void setExamineeName(String examineeName) {
		this.examineeName = examineeName;
	}

	public Integer getOpid() {
		return opid;
	}

	public void setOpid(Integer opid) {
		this.opid = opid;
	}

	public String getPtitle() {
		return ptitle;
	}

	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public String getSemseter() {
		return semseter;
	}

	public void setSemseter(String semseter) {
		this.semseter = semseter;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void setPointAnswerBiz(PointAnswerBiz pointAnswerBiz) {
		this.pointAnswerBiz = pointAnswerBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setPointInfoBiz(PointInfoBiz pointInfoBiz) {
		this.pointInfoBiz = pointInfoBiz;
	}

	public void setTempBiz(TempBiz tempBiz) {
		this.tempBiz = tempBiz;
	}

	public void setPointPaperBiz(PointPaperBiz pointPaperBiz) {
		this.pointPaperBiz = pointPaperBiz;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

}
