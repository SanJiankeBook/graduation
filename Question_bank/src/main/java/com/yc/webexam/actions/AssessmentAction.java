package com.yc.webexam.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.PointInfoBiz;
import com.yc.biz.SubjectBiz;
import com.yc.po.PointInfo;
import com.yc.po.Subject;
import com.yc.po.XSubject;
import com.yc.utils.JsonUtil;
import com.yc.vo.DataGaidModel;
import com.yc.vo.PointInfoModel;

public class AssessmentAction extends BaseAction implements ModelDriven<PointInfoModel> {
	private PointInfoModel pointInfoModel = new PointInfoModel();

	private static final Logger logger = Logger.getLogger(AssessmentAction.class);

	@Resource(name = "pointInfoBiz")
	private PointInfoBiz pointInfoBiz;

	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;

	public void initPointInfos() {
		String jsonStr = "";
		try {
			List<PointInfo> pointInfos = pointInfoBiz.getPointInfo(pointInfoModel.getCid());
			if(pointInfos!=null){
				for (int i = 0,len =pointInfos.size(); i < len; i++) {
					pointInfos.get(i).setSubject(null);
				}
				jsonStr = super.writeJson(0, pointInfos);
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

	//获取所有章节    --毕设版
	public void initPointInfo() {
		String jsonStr = "";
		try {
			DataGaidModel dgm = new DataGaidModel();
			if(pointInfoModel.getCid()!=null&&pointInfoModel.getCid()!=0){
				dgm = pointInfoBiz.getPointInfo(pointInfoModel);
				if (dgm.getRows().size() > 0 && dgm.getRows() != null) {
					for (int i = 0,len= dgm.getRows().size(); i < len; i++) {
						((PointInfo) dgm.getRows().get(i)).setSubject(null);
					}
				}
				jsonStr = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd");
			}else{
				List<PointInfoModel> list = new ArrayList<PointInfoModel>();
				dgm.setRows(list);
				dgm.setTotal((long) 0);
				jsonStr = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd");
			}
		} catch (Exception e) {
			DataGaidModel dgm = new DataGaidModel();
			List<PointInfoModel> list = new ArrayList<PointInfoModel>();
			dgm.setRows(list);
			dgm.setTotal((long) 0);
			jsonStr = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	// 增加章节    毕设版
	public void addPointInfo() {
		String jsonStr = "";
		try {
			XSubject subject = subjectBiz.findSubjectById(pointInfoModel.getCid());
			String contnt=pointInfoModel.getPcontent();
			contnt=contnt.replaceAll("；", ";");
			String[] contnts=contnt.split(";");
			for(String str:contnts){
				if(str==null||"".equals(str)){
					continue;
				}
				PointInfo po = new PointInfo();
				po.setPcontent(str);
				po.setSubject(subject);
				po.setStatus(1);
				pointInfoBiz.addPointInfo(po);
			}
			jsonStr = super.writeJson(0, "添加成功");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "添加失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	public void delPointInfo() {
		String jsonStr = "";
		try {
			XSubject subject = subjectBiz.findSubjectById(pointInfoModel.getCid());
			PointInfo po = new PointInfo();
			po.setPid(pointInfoModel.getPid());
			po.setSubject(subject);
			pointInfoBiz.delPointInfoById(po);
			jsonStr = super.writeJson(0, "删除成功");
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

	public void updatePointInfo() {
		String jsonStr = "";
		try {
			XSubject subject = subjectBiz.findSubjectById(pointInfoModel.getCid());
			PointInfo po = new PointInfo();
			po.setPid(pointInfoModel.getPid());
			po.setPcontent(pointInfoModel.getPcontent());
			po.setSubject(subject);
			po.setStatus(1);
			pointInfoBiz.updatePointInfo(po);
			jsonStr = super.writeJson(0, "修改成功");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "修改失败！");
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
	public PointInfoModel getModel() {
		return this.pointInfoModel;
	}
	public void setPointInfoBiz(PointInfoBiz pointInfoBiz) {
		this.pointInfoBiz = pointInfoBiz;
	}
	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}
}
