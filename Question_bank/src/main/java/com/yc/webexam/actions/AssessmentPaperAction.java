package com.yc.webexam.actions;

import java.awt.Point;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ChapterBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.PointAnswerBiz;
import com.yc.biz.PointInfoBiz;
import com.yc.biz.PointPaperBiz;
import com.yc.biz.SubjectBiz;
import com.yc.po.Chapter;
import com.yc.po.ExamineeClass;
import com.yc.po.PointPaper;
import com.yc.po.PointPaperTemplate;
import com.yc.po.Subject;
import com.yc.po.WritingPaper;
import com.yc.po.WritingPaperTemplate;
import com.yc.utils.JsonProtocol;
import com.yc.utils.JsonUtil;
import com.yc.vo.DataGaidModel;
import com.yc.vo.Page;
import com.yc.vo.PointPaperModel;
import com.yc.vo.WritingQuestionPaper;

public class AssessmentPaperAction extends BaseAction implements ModelDriven<PointPaperModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368770898654923760L;
	private static final Logger logger = Logger.getLogger(AssessmentPaperAction.class);
	private static final PointPaper writingPaperModel = null;
	private PointPaperModel pointPaperModel = new PointPaperModel();
	private int displayRows; // 要显示的页数
	private int pageNume; // 当前请求的第几页数
	private int sid;//课程编号

	public int getDisplayRows() {
		return displayRows;
	}
	public void setDisplayRows(int displayRows) {
		this.displayRows = displayRows;
	}
	public int getPageNume() {
		return pageNume;
	}
	public void setPageNume(int pageNume) {
		this.pageNume = pageNume;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Resource(name = "pointInfoBiz")
	private PointInfoBiz pointInfobiz;

	@Resource(name = "pointPaperBiz")
	private PointPaperBiz pointPaperBiz;

	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;

	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;

	@Resource(name = "pointAnswerBiz")
	private PointAnswerBiz pointAnswerBiz;

	// 添加试卷
	public void newPointPaper() {
		String jsonStr = "";
//		try {
//			//Subject sb = subjectBiz.findSubjectById(pointPaperModel.getSid());
//			ExamineeClass ec = examineeClassBiz.findExamineeClassById(pointPaperModel.getCid());
//			PointPaper po = new PointPaper();
//			po.setPname(pointPaperModel.getPname());
//			po.setPdate(pointPaperModel.getExamDate());
//			po.setPstatus(pointPaperModel.getPstatus());
//			po.setPaperPwd(pointPaperModel.getPaperPwd());
//			po.setPtitle(pointPaperModel.getPtitle());
//			po.setDescript(pointPaperModel.getDescript());
//			po.setSubject(sb);
//			po.setExamineeClass(ec);
//			pointPaperBiz.addPointPaper(po);
//			jsonStr = super.writeJson(0, "出卷成功！");
//		} catch (Exception e) {
//			jsonStr = super.writeJson(1, "出卷失败！");
//			logger.error(e);
//		} finally {
//			try {
//				JsonUtil.jsonOut(jsonStr);
//			} catch (IOException e) {
//				logger.error(e);
//			}
//		}
	}
	// 添加模板试卷
	public void newPointPapertemplate() {
//		String jsonStr = "";
//		try {
//			//Subject sb = subjectBiz.findSubjectById(pointPaperModel.getSid());
//			//ExamineeClass ec = examineeClassBiz.findExamineeClassById(pointPaperModel.getCid());
//			PointPaperTemplate po = new PointPaperTemplate();
//			po.setPname(pointPaperModel.getPname());
//			po.setPdate(pointPaperModel.getExamDate());
//			po.setPstatus(pointPaperModel.getPstatus());
//			po.setPaperPwd(pointPaperModel.getPaperPwd());
//			po.setPtitle(pointPaperModel.getPtitle());
//			po.setDescript(pointPaperModel.getDescript());
//			po.setSid(sb.getId());
//			pointPaperBiz.addPointPaper(po);
//			jsonStr = super.writeJson(0, "出模板卷成功！");
//		} catch (Exception e) {
//			jsonStr = super.writeJson(1, "出卷失败！");
//			logger.error(e);
//		} finally {
//			try {
//				JsonUtil.jsonOut(jsonStr);
//			} catch (IOException e) {
//				logger.error(e);
//			}
//		}
	}
	
	/**
	 * 将试卷指定一个班级
	 */
	public void examPaperToAnotherClassTemplate(){
		String jsonStr=null;
		Page<PointPaperTemplate> page =new Page<PointPaperTemplate>();
		page.setPageSize(50);
		page.setCurrentPage(1);
		PointPaperTemplate wp=new PointPaperTemplate();
		wp.setPid(pointPaperModel.getPid());
		pointPaperBiz.getAllPointPaperTemplate(page, wp);
		PointPaperTemplate pint=page.getResult().get(0);
		Subject sb = new Subject();
		sb.setId(pint.getSid());
		ExamineeClass ec = new ExamineeClass();
		ec.setId(pointPaperModel.getCid());
		try {
			PointPaper paper=new PointPaper();
			paper.setDescript(pint.getDescript());
			paper.setExamineeClass(ec);
			paper.setSubject(sb);
			paper.setFlag(pint.getFlag());
			paper.setPaperPwd("a");
			paper.setPdate(pointPaperModel.getExamDate());
			paper.setPname(pint.getPname());
			paper.setPstatus(pint.getPstatus());
			paper.setPtitle(pint.getPtitle());
			paper.setRemark(pint.getRemark());
			pointPaperBiz.addPointPaper(paper);
			jsonStr = super.writeJson(0, "试卷生成成功!");
			//wp.setExamineeClass(examineeClass);
			//writingQuestionBiz.updateWritingQuestion(writingQuestion);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "该试卷指定失败！！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	// 更新试卷
	public void updatePointPaper() {
		String jsonStr = "";
//		try {
//			Subject sb = subjectBiz.findSubjectById(pointPaperModel.getSid());
//			ExamineeClass ec = examineeClassBiz.findExamineeClassById(pointPaperModel.getCid());
//			PointPaper po = new PointPaper();
//			po = pointPaperBiz.findPointPaperById(pointPaperModel.getPid());
//			po.setPname(pointPaperModel.getPname());
//			po.setPdate(pointPaperModel.getExamDate());
//			po.setPstatus(pointPaperModel.getPstatus());
//			po.setPaperPwd(pointPaperModel.getPaperPwd());
//			po.setPtitle(pointPaperModel.getPtitle());
//			po.setDescript(pointPaperModel.getDescript());
//			po.setSubject(sb);
//			po.setExamineeClass(ec);
//			pointPaperBiz.updatePointPaper(po);
//			jsonStr = super.writeJson(0, "编辑成功！");
//		} catch (Exception e) {
//			jsonStr = super.writeJson(1, "编辑失败！请稍后再试");
//			logger.error(e);
//		} finally {
//			try {
//				JsonUtil.jsonOut(jsonStr);
//			} catch (IOException e) {
//				logger.error(e);
//			}
//		}
	}

	// 按条件查询试卷
	public void findPointPaper() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String jsonStr = "";
		String date = "";
		List<PointPaper> pointPaper = new ArrayList<PointPaper>();
		List<PointPaperModel> list = new ArrayList<PointPaperModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (pointPaperModel.getExamDate() != null) {
			date = sdf.format(pointPaperModel.getExamDate());
		}
		try {
			if (pointPaperModel.getPid() != null) {
				pointPaper.add(pointPaperBiz.findPointPaperById(pointPaperModel.getPid()));
			} else {
				pointPaper = pointPaperBiz.findPointPaper(pointPaperModel.getSid(), pointPaperModel.getCid(), date);
			}
			for (int i = 0,len=pointPaper.size(); i < len; i++) {
				PointPaperModel pm = new PointPaperModel();
				pm.setClassName(pointPaper.get(i).getExamineeClass().getClassName());
				pm.setExamDate(pointPaper.get(i).getPdate());
				pm.setPname(pointPaper.get(i).getPname());
				pm.setPstatus(pointPaper.get(i).getPstatus());
				pm.setPid(pointPaper.get(i).getPid());
				pm.setSubjectName(pointPaper.get(i).getSubject().getSubjectName());
				pm.setPaperPwd(pointPaper.get(i).getPaperPwd());
				pm.setCid(pointPaper.get(i).getExamineeClass().getId());
				pm.setPtitle(pointPaper.get(i).getPtitle());
				pm.setSid(pointPaper.get(i).getSubject().getId());
				pm.setDescript(pointPaper.get(i).getDescript());
				pm.setDate(sdf.format(pointPaper.get(i).getPdate()));
				list.add(pm);
			}
			JsonProtocol jsonProtocol = null;
			jsonProtocol = new JsonProtocol(0, null, list);
			jsonStr = JSON.toJSONString(jsonProtocol, SerializerFeature.DisableCircularReferenceDetect);
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
	public void findPointPapertemplate() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String jsonStr = "";
		String date = "";
		List<PointPaperTemplate> pointPaper = new ArrayList<PointPaperTemplate>();
		List<PointPaperModel> list = new ArrayList<PointPaperModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (pointPaperModel.getExamDate() != null) {
			date = sdf.format(pointPaperModel.getExamDate());
		}
		try {
			if (pointPaperModel.getPid() != null) {
				pointPaper.add(pointPaperBiz.findPointPaperByIdTemplate(pointPaperModel.getPid()));
			} /*else {
				pointPaper = pointPaperBiz.findPointPaper(pointPaperModel.getSid(), pointPaperModel.getCid(), date);
			}*/
			for (int i = 0,len=pointPaper.size(); i < len; i++) {
				PointPaperModel pm = new PointPaperModel();
				pm.setPtitle(pointPaper.get(i).getPtitle());
				list.add(pm);
			}
			JsonProtocol jsonProtocol = null;
			jsonProtocol = new JsonProtocol(0, null, list);
			jsonStr = JSON.toJSONString(jsonProtocol, SerializerFeature.DisableCircularReferenceDetect);
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
	
	// 查询所有试卷
	public void findAllPointPaper() {
		String jsonStr = "";
		
		try {
			List<PointPaper> pointPaper = new ArrayList<PointPaper>();
			List<PointPaperModel> list = new ArrayList<PointPaperModel>();
			DataGaidModel dgm = new DataGaidModel();
			dgm = pointPaperBiz.getAllPointPaper(pointPaperModel);
			pointPaper = dgm.getRows();
			//2个
			for (int i = 0,len=pointPaper.size(); i < len; i++) {
				PointPaperModel pm = new PointPaperModel();
				pm.setClassName(pointPaper.get(i).getExamineeClass().getClassName());//TODO:这里的这个for循环每循环一次，Examineeclass表就会查询一次
				//pointPaper.get(i).setExamineeClass(null);
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
			jsonStr = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd",
					SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			DataGaidModel dgm = new DataGaidModel();
			List<PointPaperModel> list = new ArrayList<PointPaperModel>();
			dgm.setRows(list);
			dgm.setTotal((long) 0);
			jsonStr = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd",
					SerializerFeature.DisableCircularReferenceDetect);
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	// 根据条件查询模板试卷
	public void findAllPointPapertemplate() {
		String jsonStr = "";
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			Page<PointPaperTemplate> page = new Page<PointPaperTemplate>();
			page.setPageSize(displayRows);
			page.setCurrentPage(pageNume);
			PointPaperTemplate wp=new PointPaperTemplate();
			Subject subject=new Subject();
			wp.setSid(Integer.valueOf(req.getParameter("sid")));
			
			pointPaperBiz.getAllPointPaperTemplate(page, wp);
			Integer len=page.getResult().size();
			//2个
			/*for (int i = 0; i < len; i++) {
				page.getResult().get(i).getSubject().setChapterCount(null);
				page.getResult().get(i).getSubject().setChapters(null);
				page.getResult().get(i).getSubject().setEdition(null);
				page.getResult().get(i).getSubject().setPointInfos(null);
				page.getResult().get(i).getSubject().setPointPapers(null);
				page.getResult().get(i).getSubject().setSeq(null);
			}*/
			
			jsonStr = super.writeJson(0, page);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "出错");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	// 修改试题状态
	public void changePointPaperStatus() {
		String jsonStr = "";
		try {
			PointPaper po = new PointPaper();
			po = pointPaperBiz.findPointPaperById(pointPaperModel.getPid());
			po.setPstatus(pointPaperModel.getPstatus());
			pointPaperBiz.updatePointPaperStatus(po);
			jsonStr = super.writeJson(0, "开考成功！");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "开考失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	// 删除试卷
	public void delPointPaper() {
		String jsonStr = "";
		try {
			PointPaper po = new PointPaper();
			po.setPid(pointPaperModel.getPid());

			pointPaperBiz.delPointPaperById(po);
			jsonStr = super.writeJson(0, "删除成功！");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "删除失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	// 显示试卷题目
	public void showPointInfo() {
		String jsonStr = "";
		try {
			String title = pointPaperModel.getPtitle().substring(0, pointPaperModel.getPtitle().length() - 1);
			String[] ptitles = title.split(",");
			List<String> list = new ArrayList<String>();
			String pointName = "";
			for (int i = 0; i < ptitles.length; i++) {
				pointName = pointInfobiz.findPointInfoById(Integer.parseInt(ptitles[i]));
				list.add(pointName);
			}
			jsonStr = super.writeJson(0, list);
		} catch (NumberFormatException e) {
			jsonStr = super.writeJson(1, "失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	// 统计参加测评的人数
	public void studentCount() {
		String jsonStr = "";
		try {
			int count = 0;
			count = pointAnswerBiz.getStudentCount(pointPaperModel.getPid());
			jsonStr = super.writeJson(0, count);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public PointPaperModel getModel() {
		return this.pointPaperModel;
	}

	public void setPointInfobiz(PointInfoBiz pointInfobiz) {
		this.pointInfobiz = pointInfobiz;
	}

	public void setPointPaperBiz(PointPaperBiz pointPaperBiz) {
		this.pointPaperBiz = pointPaperBiz;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setPointAnswerBiz(PointAnswerBiz pointAnswerBiz) {
		this.pointAnswerBiz = pointAnswerBiz;
	}

}
