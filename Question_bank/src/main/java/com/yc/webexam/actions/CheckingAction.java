package com.yc.webexam.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.CheckingBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.biz.TempBiz;
import com.yc.po.ADailyTalk;
import com.yc.po.Checking;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.SystemUser;
import com.yc.po.Temp;
import com.yc.utils.JsonUtil;
import com.yc.utils.PageUtil;
import com.yc.utils.UTFUtil;
import com.yc.utils.YcConstants;
import com.yc.vo.AdailyTalkPage;
import com.yc.vo.DataGaidModel;
import com.yc.vo.ListClassPage;

@SuppressWarnings("serial")
@Scope("prototype")
public class CheckingAction extends BaseAction
implements ServletRequestAware, ServletResponseAware, ModelDriven<AdailyTalkPage> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckingAction.class);

	private PrintWriter out;
	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;


	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	private PageUtil pageUtil = new PageUtil();
	private int totalInfo = 0;
	private int countInfo = 10;
	private int tempsum = 0;
	private String resultListInfo = "";
	private static List<ADailyTalk> aDailyTalkInfoHistory = null;

	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型

	private ListClassPage listClassPage = new ListClassPage();

	public ListClassPage getListClassPage() {
		return listClassPage;
	}

	public void setListClassPage(ListClassPage listClassPage) {
		this.listClassPage = listClassPage;
	}

	public ExamineeBiz getExamineeBiz() {
		return examineeBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	private List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
	private Map<String, Object> map = new HashMap<String, Object>();

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	private HttpSession mysession = request.getSession();

	@Resource(name = "checkingBiz")
	private CheckingBiz checkingBiz;
	// @Autowired
	// private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "aDailyTalkBiz")
	private ADailyTalkBiz aDailyTalkBiz;
	@Resource(name = "systemUserBiz")
	private SystemUserBiz systemUserBiz;
	@Resource(name = "tempBiz")
	private TempBiz tempBiz;

	private AdailyTalkPage adailyTalkPage = new AdailyTalkPage();

	public AdailyTalkPage getAdailyTalkPage() {
		return adailyTalkPage;
	}

	JSONArray json = null;
	JSONObject jb = null;
	String jsonStr = null;

	public void setServletResponse(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		this.response = response;
		try {
			this.out = this.response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public void setServletRequest(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			this.request = request;
			this.mysession = this.request.getSession();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 查看
	 */
	@SuppressWarnings("static-access")
	public void showcheckingresultinfos() {
		String checkId = request.getParameter("checkId").toString().trim();
		if (checkId != null && !"".equals(checkId)) {

			try {
				Checking checking = checkingBiz.findCheckingInfoById(Integer.parseInt(checkId));
				if (checking != null) {
					checking.setExamineeClass(null);
					checking.setSystemUser(null);
					jsonStr = super.writeJson(0, checking);
				} else {
					jsonStr = super.writeJson(1, "查询出错");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error(e);
				jsonStr = super.writeJson(1, "查询出现异常");
			} finally {
				try {
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 提交修改结果
	 */
	public void subchangeChecking() {
		int checkId = adailyTalkPage.getId();
		Date checkDate = adailyTalkPage.getCheckDate();
		String checkTime = adailyTalkPage.getCheckTime();
		String checkRemark = adailyTalkPage.getCheckRemark();
		String checkResult = adailyTalkPage.getCheckResult();

		try {
			Checking checking = checkingBiz.findCheckingInfoById(checkId);

			checking.setCheckId(checkId);
			checking.setCheckDate(checkDate);
			checking.setCheckTime(checkTime);
			checking.setCheckRemark(checkRemark);
			checking.setCheckResult(checkResult);

			int result = checkingBiz.updateCheckingInfo(checking);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "更新失败");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "更新出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 提交考勤结果
	 * 
	 * @throws IOException
	 */
	public void subChecking() throws IOException {
		int className = Integer.parseInt(adailyTalkPage.getClassName());
		Date checkDate = adailyTalkPage.getCheckDate();
		String checkTime = adailyTalkPage.getCheckTime();
		String checkRemark = adailyTalkPage.getCheckRemark();
		String semester = adailyTalkPage.getSemester();
		String userName = adailyTalkPage.getUserName();
		String checkResult = adailyTalkPage.getCheckResult();
		Checking ck = checkingBiz.findCheckingByClassIdAndDateTime(className, checkDate, checkTime);
		try {
			if (ck != null) {
				jsonStr = super.writeJson(1, "该时间段考勤已经提交，请不要重复考勤");
				return;
			} else {

				List<SystemUser> user = systemUserBiz.findSystemUserByName(userName);
				int uid = user.get(0).getId();

				Checking checking = new Checking();
				checking.setSemester(semester);
				checking.setCheckDate(checkDate);
				checking.setCheckTime(checkTime);
				checking.setCheckRemark(checkRemark);
				checking.setCheckResult(checkResult);
				checking.setCheckFlag(null);
				checking.setCheckStatus(1);
				checking.setCheckFlag(null);
				checking.setCheckDescript(null);
				SystemUser systemUser = new SystemUser();
				systemUser.setId(uid);
				checking.setSystemUser(systemUser);
				ExamineeClass examineeClass = new ExamineeClass();
				examineeClass.setId(className);
				checking.setExamineeClass(examineeClass);

				int result = checkingBiz.addCheckingInfo(checking);
				if (result > 0) {
					jsonStr = super.writeJson(0, null);
				} else {
					jsonStr = super.writeJson(1, "提交考勤结果失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonStr = super.writeJson(1, "添加出现异常，请联系管理员");
			logger.error(e);
		} finally {
			JsonUtil.jsonOut(jsonStr);
		}

	}

	public void showAllCheckingInfo() throws Exception {
		String cid = request.getParameter("cid").toString().trim(); // 班级
		String startDate = request.getParameter("startDate").toString().trim(); // 查询开始时间
		String endDate = request.getParameter("endDate").toString().trim(); // 查询结束时间
		String dateTime = request.getParameter("dateTime").toString().trim(); // 查询时间段

		if (startDate == null || "".equals(startDate)) {
			startDate = "1911-1-1";
		}
		if (endDate == null || "".equals(endDate)) {
			endDate = "2050-12-30";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date a = sdf.parse(startDate);
		Date b = sdf.parse(endDate);
		boolean flag = a.before(b);
		if (!flag) {
			String c = "";
			c = startDate;
			startDate = endDate;
			endDate = c;
		}

		// String userName = request.getParameter("userName").toString().trim();
		// List<SystemUser> user = systemUserBiz.findSystemUserByName(userName);
		// System.out.println(user.get(0).getId());
		List<Checking> checking = null;

		checking = checkingBiz.findCheckingByClassIdAndUidAndTime(Integer.parseInt(cid), dateTime, startDate, endDate);

		try {
			if (checking != null && checking.size() > 0) {
				for (int i = 0,len=checking.size(); i < len ; i++) {
					SystemUser systemUser = new SystemUser();
					systemUser.setUserName(checking.get(i).getSystemUser().getUserName());
					checking.get(i).setSystemUser(systemUser);
					ExamineeClass examineeClass = new ExamineeClass();
					examineeClass.setClassName(checking.get(i).getExamineeClass().getClassName());
					checking.get(i).setExamineeClass(examineeClass);
					checking.get(i).setCheckDate(
							new SimpleDateFormat("yyyy-MM-dd").parse(checking.get(i).getCheckDate().toString()));
				}
				jsonStr = JSON.toJSONString(checking, SerializerFeature.DisableCircularReferenceDetect);
			} else {
				jsonStr = super.writeJson(1, "查询失败");
			}
		} catch (Exception e) {
			logger.error(e);
			jsonStr = super.writeJson(1, "查询出现的异常");
			e.printStackTrace();
		} finally {
			try {
				
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 学生端查询考勤
	 */
	public void findResultStu() throws Exception {
		String semester = request.getParameter("semester").toString().trim(); // 学期编号
		String startDate = request.getParameter("startDate").toString().trim(); // 查询开始时间
		String endDate = request.getParameter("endDate").toString().trim(); // 查询结束时间
		String dateTime = request.getParameter("dateTime").toString().trim(); // 查询时间段
		if (semester == "0" || "0".equals(semester)) {
			semester = "";
		}
		if (dateTime == "0" || "0".equals(dateTime)) {
			dateTime = "";
		}
		if (startDate == null || "".equals(startDate)) {
			startDate = "1911-1-1";
		}
		if (endDate == null || "".equals(endDate)) {
			endDate = "2050-12-30";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date a = sdf.parse(startDate);
		Date b = sdf.parse(endDate);
		boolean flag = a.before(b);
		if (!flag) {
			String c = "";
			c = startDate;
			startDate = endDate;
			endDate = c;
		}

		String uname = mysession.getAttribute("userName").toString().trim(); // 当前学生姓名

		// 查出满足条件的考勤记录
		List<Checking> list = checkingBiz.findCheckingResullByStudentName(semester, startDate, endDate, dateTime,
				uname);
		
		// 准备结果页面
		StringBuffer stf = new StringBuffer(
				"<table width=\"860\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" bordercolor=\"#FFFFFF\" >");
		StringBuffer line = new StringBuffer();
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		int sum = 0;
		String result = "";

		// 如果有数据，则拼接
		if (list != null && list.size() > 0) {
			// 拼接表格的th部分
			if (list.size() >= 4) {
				stf.append(
						"<tr><th bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th><th bordercolor=\"#7EA6DC\">状&nbsp;&nbsp;态</th>");
				stf.append(
						"<th bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th><th bordercolor=\"#7EA6DC\">状&nbsp;&nbsp;态</th>");
				stf.append(
						"<th bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th><th bordercolor=\"#7EA6DC\">状&nbsp;&nbsp;态</th>");
				stf.append(
						"<th bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th><th bordercolor=\"#7EA6DC\">状&nbsp;&nbsp;态</th></tr>");
			} else {
				stf.append("<tr>");
				for (int i = 0,len=list.size(); i < len; i++) {
					stf.append(
							"<th bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th><th bordercolor=\"#7EA6DC\">状&nbsp;&nbsp;态</th>");
				}
				stf.append("</tr>");
			}
			// 拼接表格的td部分
			stf.append(
					"<tr align=\"center\" onmouseover=\"this.bgColor='#93BBDF'\" onmouseout=\"this.bgColor='#EDECEB';\" height=\"25\">");
			for (Checking ck : list) {
				sum++;
				line.append("<td align=\"center\" bordercolor=\"#7EA6DC\">" + ck.getCheckDate() + " "
						+ ck.getCheckTime() + "</td>");
				// 获取该学生的考勤状态
				result = ck.getCheckResult();
				result = result.substring(result.indexOf("|" + uname + ",") + uname.length() + 2,
						result.indexOf("|" + uname + ",") + uname.length() + 3);

				if (Integer.parseInt(result) == 2) {
					count2++;
					line.append("<td align=\"center\" bordercolor=\"#7EA6DC\" style=\"color:red\">迟到</td>");
				} else if (Integer.parseInt(result) == 3) {
					count3++;
					line.append("<td align=\"center\" bordercolor=\"#7EA6DC\" style=\"color:green\">病假</td>");
				} else if (Integer.parseInt(result) == 4) {
					count4++;
					line.append("<td align=\"center\" bordercolor=\"#7EA6DC\" style=\"color:red\">请假</td>");
				} else if (Integer.parseInt(result) == 5) {
					count5++;
					line.append("<td  align=\"center\" bordercolor=\"#7EA6DC\" style=\"color:red\">旷课</td>");
				} else if (Integer.parseInt(result) == 6) {
					count6++;
					line.append("<td  align=\"center\" bordercolor=\"#7EA6DC\" style=\"color:red\">早退</td>");
				} else {
					count1++;
					line.append("<td align=\"center\" bordercolor=\"#7EA6DC\">已到</td>");
				}
				if (sum % 4 == 0) {
					stf.append(line).append("</tr>");
					line = new StringBuffer();
					stf.append(
							"<tr align=\"center\" onmouseover=\"this.bgColor='#93BBDF'\" onmouseout=\"this.bgColor='#EDECEB';\" height=\"25\">");
				}
			}
			stf.append(line).append("</tr>");
			if (list.size() >= 4) {
				stf.append("<tr eight=\"40\" style=\"color:red;font-weight:bold;font-size:18px;\"><td colspan=\"8\">");
			} else {
				stf.append("<tr height=\"40\" style=\"font-weight:bold;font-size:18px;\"><td colspan=\"" + (sum * 2)
						+ "\">");
			}
			stf.append("考勤总数：" + sum + "次&nbsp;&nbsp;已到：" + count1 + "次&nbsp;&nbsp迟到：<font color=\"red\">" + count2
					+ "次</font>&nbsp;&nbsp" + "病假：<font color=\"green\">" + count3
					+ "次</font>&nbsp;&nbsp;请假：<font color=\"green\">" + count4 + "次</font>&nbsp;&nbsp;"
					+ "旷课：<font color=\"red\">" + count5 + "次</font>&nbsp;&nbsp;早退：<font color=\"red\">" + count6
					+ "次</font>&nbsp;&nbsp;</td></tr>");
		} else { // 如果没有记录，提示
			stf.append(
					"<tr><td><br/><br/><span class=\"fontColor\" style=\"color:red;font-weight:bold;font-size:18px;\">提示：对不起,没有找到您要的数据!</span></td></tr>");
		}
		stf.append("</table>");
		// this.writeJson(0, stf.toString());
		out.print(stf.toString());
		out.flush();
	}
	// 根据查询组合条件统计(教师端) 的弹出层处理

	// 根据学生姓名统计（弹层显示）
	public void totalResultByStuname() {
		String sname = request.getParameter("sname").toString().trim(); // 学生姓名

		try {
			if (sname != null && !"".equals(sname)) {
				List<Temp> list = tempBiz.getTotalStatusBysname(sname);
				int suminfo = tempBiz.getCheckingTotalInfo(sname);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("suminfo", suminfo);
				jsonStr = super.writeJson(0, map);
			} else {
				jsonStr = super.writeJson(1, "对不起,信息统计失败!");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "对不起,信息统计异常!");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	// 日期弹层
	public void totalResultByDate() {
		String ppid = request.getParameter("ppid").toString().trim(); // 考勤编号
		try {
			if (ppid != null && !"".equals(ppid)) {
				StringBuffer stf = new StringBuffer("<b>备注：</b><br /><br />");
				Checking ck = checkingBiz.findCheckingInfoById(Integer.parseInt(ppid));
				if (ck != null && ck.getCheckRemark() != null && !"".equalsIgnoreCase(ck.getCheckRemark().trim())) {
					stf.append(ck.getCheckRemark());
				} else {
					stf.append("暂无");
				}

				jsonStr = super.writeJson(0, stf);
			} else {
				jsonStr = super.writeJson(1, "对不起，显示备注失败");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error(e);
			jsonStr = super.writeJson(1, "对不起，显示备注异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	}

	// 状态的备注信息（弹层）
	public void totalResultByStatus() {
		String ppid = request.getParameter("ppid").toString().trim(); // 考勤编号
		String sname = request.getParameter("sname").toString().trim(); // 学生姓名

		try {
			if (ppid != null && !"".equals(ppid) && sname != null && !"".equals(sname) && !"".equals(sname)) {
				StringBuffer stf = new StringBuffer("<b>备注：</b><br /><br />");
				Temp tp = tempBiz.getSingleCheckingResultInfo(Integer.parseInt(ppid), sname);

				if (tp != null && tp.getPcontent().trim() != null && !"".equalsIgnoreCase(tp.getPcontent().trim())) {
					stf.append(tp.getPcontent());
				} else {
					stf.append("暂无");
				}

				jsonStr = super.writeJson(0, stf);
			} else {
				jsonStr = super.writeJson(1, "对不起，显示备注失败");
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
				
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	//查询班级统计情况（教师端）
	public void findClassResult() throws ParseException {
		String semester = request.getParameter("semester").toString().trim(); // 学期编号
		String cid = request.getParameter("cid").toString().trim(); // 班级编号
		String startdate = request.getParameter("startdate").toString().trim(); // 查询开始时间
		String enddate = request.getParameter("enddate").toString().trim(); // 查询结束时间
		String datetime = request.getParameter("datetime").toString().trim(); // 查询时间段

		if (startdate == null || "".equals(startdate)) {
			startdate = "1911-01-01";
		}
		if (enddate == null || "".equals(enddate)) {
			enddate = "2050-12-30";
		}

		List<String> listName=examineeBiz.findAllStuNameByClassId(Integer.parseInt(cid));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date a = sdf.parse(startdate);
		Date b = sdf.parse(enddate);
		boolean flag = a.before(b);
		if (!flag) {
			String c = "";
			c = startdate;
			startdate = enddate;
			enddate = c;
		}

		List<Integer> checkides = null;
		List<Temp> listTemp = new ArrayList<Temp>();
		//queqName 缺勤  subname 迟到  jianame 请假  ppid 考勤总数   kuangkCount 旷课人数  lateCount 迟到人数    qingjCount 请假人数 className
		String sname="";
		String queqName="";//缺勤人名
		String subname="";String jianame="";Integer count=0;
		Integer kuangkCount=0;Integer lateCount=0;Integer qingjCount=0;
		Integer status=0;
		Integer normalCount=0;

		try {
			int result = tempBiz.exectueDelTemp(); // 清空临时表中的记录
			List<Checking> rightList=checkingBiz.findCheckByClassId(Integer.parseInt(cid), startdate, enddate,datetime);
			if (result >= 0) {
				for (Checking list : rightList) {
					Date checkDate=list.getCheckDate();
					sname="";
					queqName="";//缺勤人名
					subname=""; jianame=""; count=0;
					kuangkCount=0; lateCount=0; qingjCount=0;
					normalCount=0;
					String checkTime = list.getCheckTime();
					String checkResult = list.getCheckResult();

					int flags = 1;
					while (flags > 0) {
						int tempNum=0;//排除退学的学生
						sname = checkResult.substring(0, checkResult.indexOf(",", 0));
						for(int i=0,len=listName.size();i<len;i++){
							if(sname.equals(listName.get(i))){
								tempNum=1;
								break;
							}
						}

						if(tempNum!=1){
							if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
									.indexOf("|", 0) > 1) {
								checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
										checkResult.length());
							} else {
								flags = 0;
							}
						}

						status = Integer.parseInt(checkResult.substring(checkResult.indexOf(",", 0) + 1,
								checkResult.indexOf("-", 0)));
						// 1 已到   2 迟到 3 病假 4 请假 5 旷课 6 早退 
						if(status==1){
							normalCount++;
						}

						if(status==2){
							subname+=" "+sname;
							lateCount++;
						}

						if(status==4){
							jianame+=" "+sname;
							qingjCount++;
						}

						if(status==5){
							queqName+=" "+sname;
							kuangkCount++;
						}
						count++;

						if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
								.indexOf("|", 0) > 1) {
							checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
									checkResult.length());
						} else {
							flags = 0;
						}
					}

					Temp t = new Temp();
					t.setPpid(count);
					t.setSname(queqName);
					t.setClassid(qingjCount);
					t.setClassName(checkDate.toString()+" "+checkTime);
					t.setSubid(lateCount);
					t.setSubname(subname);
					t.setPointid(kuangkCount);
					t.setPcontent(jianame);
					t.setGrade(Float.parseFloat(normalCount + ""));
					listTemp.add(t);

				}

				if (listTemp.isEmpty()) {
					jsonStr = super.writeJson(1, "暂无您要查询的考勤记录");
				} else {
					jsonStr = super.writeJson(0, listTemp);
				}
			} else {
				jsonStr = super.writeJson(1, "暂无您要查询的考勤记录");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "查询出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


	// 根据查询组合条件统计(教师端)
	public void findResult() throws ParseException {
		String semester = request.getParameter("semester").toString().trim(); // 学期编号
		String cid = request.getParameter("cid").toString().trim(); // 班级编号
		String stuname = request.getParameter("stuname").toString().trim(); // 学生姓名
		String startdate = request.getParameter("startdate").toString().trim(); // 查询开始时间
		String enddate = request.getParameter("enddate").toString().trim(); // 查询结束时间
		String datetime = request.getParameter("datetime").toString().trim(); // 查询时间段

		
		if (startdate == null || "".equals(startdate)) {
			startdate = "1911-1-1";
		}
		if (enddate == null || "".equals(enddate)) {
			enddate = "2050-12-30";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date a = sdf.parse(startdate);
		Date b = sdf.parse(enddate);
		boolean flag = a.before(b);
		if (!flag) {
			String c = "";
			c = startdate;
			startdate = enddate;
			enddate = c;
		}

		List<Integer> checkides = null;
		List<Temp> listTemp = new ArrayList<Temp>();
		try {
			/*checkides = checkingBiz.findCheckingAllCheckId(semester, Integer.parseInt(cid), stuname, startdate, enddate,
						datetime);*/

			checkides = checkingBiz.findCheckingAllCheckIdExcuteSql(semester, Integer.parseInt(cid), stuname, startdate, enddate,
					datetime);

			if (checkides != null && checkides.size() > 0) {
				List<Checking> lists = checkingBiz.findCheckInCheckid(checkides);
				int result = tempBiz.exectueDelTemp(); // 清空临时表中的记录
				if (result >= 0) {
					for (Checking list : lists) {
						int ctime = 0; // 考勤时间段 1.上午 2.下午 3.晚上
						String sname = ""; // 学生姓名
						int status = 0; // 考勤状态
						String remark = ""; // 备注
						//List<Checking> list = checkingBiz.findCheckByCheckid(str);

						Date checkDate = list.getCheckDate();
						String checkTime = list.getCheckTime();
						String checkResult = list.getCheckResult();
						Integer ccid = list.getExamineeClass().getId();
						// list.get(0).getExamineeClass().getClassName();
						String className = list.getExamineeClass().getClassName();
						int flags = 1;
						while (flags > 0) {
							if ("上午".equals(checkTime.trim())) {
								ctime = 1;
							} else if ("下午".equals(checkTime.trim())) {
								ctime = 2;
							} else {
								ctime = 3;
							}
							sname = checkResult.substring(0, checkResult.indexOf(",", 0));
							if (!sname.equals(stuname)) {
								if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
										.indexOf("|", 0) > 1) {
									checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
											checkResult.length());
								} else {
									flags = 0;
								}
								continue;
							}
							status = Integer.parseInt(checkResult.substring(checkResult.indexOf(",", 0) + 1,
									checkResult.indexOf("-", 0)));
							remark = checkResult.substring(checkResult.indexOf("-", 0) + 1,
									checkResult.indexOf("|", 0));
							Temp t = new Temp();
							t.setPpid(list.getCheckId());
							t.setSname(sname);
							t.setClassid(ccid);
							t.setClassName(className);
							t.setSubid(ctime);
							t.setSubname(remark);
							t.setPointid(status);
							t.setPcontent(sdf.format(checkDate));
							t.setGrade(Float.parseFloat(0 + ""));

							listTemp.add(t);
							if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
									.indexOf("|", 0) > 1) {
								checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
										checkResult.length());
							} else {
								flags = 0;
							}
						}

					}
					//System.out.println("listTemp:"+listTemp);
					if (listTemp.isEmpty()) {
						jsonStr = super.writeJson(1, "暂无您要查询的考勤记录");
					} else {
						jsonStr = super.writeJson(0, listTemp);
					}
				} else {
					jsonStr = super.writeJson(1, "暂无您要查询的考勤记录");
				}
			} else {
				jsonStr = super.writeJson(1, "暂无您要查询的考勤记录");
			}
			// 如果查到了记录，则执行存储过程

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "查询出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	}

	private String readyCheckingJson(int cid, String stuname) {
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;
		try {
			if (stuname != null && !"".equalsIgnoreCase(stuname) && !"0".equals(stuname)) {

				// 根据学生姓名，查询该学生的考勤信息
				List<Temp> singleCheckingInfo = tempBiz.getSingleCheckingInfo(stuname);
				if (singleCheckingInfo != null && singleCheckingInfo.size() > 0) {
					jsonStr = super.writeJson(0, singleCheckingInfo);
				} else {
					jsonStr = super.writeJson(1, "暂无您要查询的考勤记录");
				}
			} else {
				// 根据班级查询这个班的考勤学生姓名
				List<Temp> checkingStuInfo = tempBiz.getAllStuname(cid);
				list = new ArrayList<Map<String, Object>>();
				map = new HashMap<String, Object>();
				if (checkingStuInfo != null && checkingStuInfo.size() > 0) {
					map.put("checkingStuInfo", checkingStuInfo);
				}
				// 获取考勤日期
				List<Temp> checkingDateTime = tempBiz.getAllDateTime(cid);
				if (checkingDateTime != null && checkingDateTime.size() > 0) {
					map.put("checkingDateTime", checkingDateTime);
				}

				// 获取考勤记录 已到 请假 病假 等标记
				List<Temp> allInfo = tempBiz.getAllCheckingInfo(cid);
				if (allInfo != null && allInfo.size() > 0) {
					map.put("allInfo", allInfo);
				}
				// 获取警告信息
				List<Temp> warnInfo = tempBiz.getWarnInfo(cid);
				if (warnInfo != null && warnInfo.size() > 0) {
					map.put("warnInfo", warnInfo);
				}

				jsonStr = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
				// jsonStr=super.writeJson(0, allInfo);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "查询出现异常");
		}

		return jsonStr;

	}

	/**
	 * 每日一讲后台
	 * @throws IOException
	 */ 
	public void dailyTalk() throws Exception {
		String name = adailyTalkPage.getName();
		String className = adailyTalkPage.getClassName();
		String knowledge = adailyTalkPage.getKnowledge();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		//knowledge = new String(knowledge.getBytes("iso-8859-1"), "UTF-8");
		int cid = aDailyTalkBiz.getCid(className);

		ADailyTalk adilyTalk = new ADailyTalk();
		adilyTalk.setName(name);
		adilyTalk.setKnowledge(knowledge);
		adilyTalk.setStatus(1);
		adilyTalk.setSpeakdate(df.parse(time));
		adilyTalk.setAssessment( YcConstants.ADILYTALK_ACCESSMENT_START);
		ExamineeClass examineeClass = new ExamineeClass();
		examineeClass.setId(cid);
		adilyTalk.setExamineeClass(examineeClass);
		try {
			int result = (Integer) aDailyTalkBiz.addADailyTalk(adilyTalk);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "添加演讲内容失败");
			}
		} catch (Exception e) {
			logger.error(e);
			//e.printStackTrace();
			YcConstants.logger.error(  e );
			jsonStr = super.writeJson(1, "添加出现异常，请联系管理员");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	}

	/**每日一讲特制的姓名显示
	 * @author 彭涛
	 */
	// 根据班级编号显示同一班级的学生信息
			public void showExamineeList() {
				HttpServletRequest request1 = ServletActionContext.getRequest();
				String className = request1.getParameter("className");
				try {
					int cid = aDailyTalkBiz.getCid(className);
					List<ADailyTalk> adailytalk=this.aDailyTalkBiz.findClassName(String.valueOf(cid));
					Map<String,Integer> map=new HashMap<String,Integer>();
					if(adailytalk!=null && !adailytalk.isEmpty() && adailytalk.size()>0){
						for(ADailyTalk at:adailytalk){
							String key=at.getName();
							if(map.containsKey(key)){
								Integer value=map.get(key);
								map.put(key, ++value);
							}else{
								map.put(key, 1);
							}
						}
					}
					List<Examinee> examinee = examineeBiz.findAllStuNameByClassName(className);
					List<ListClassPage> list = new ArrayList<ListClassPage>();
					if (examinee != null && examinee.size() > 0) {
						for (int i = 0,len=examinee.size(); i < len; i++) {
							ListClassPage listClassPage = new ListClassPage();
							if(map.containsKey(examinee.get(i).getName())){
								String name=examinee.get(i).getName()+"_"+map.get(examinee.get(i).getName());
								listClassPage.setName(name);
							}else{
								String name=examinee.get(i).getName()+"_"+0;
								listClassPage.setName(name);
							}
							listClassPage.setClassName(examinee.get(i).getExamineeClass().getEditionId().toString());
							//传回去的实际是学生状态  （修改结果）
							listClassPage.setIdCard(examinee.get(i).getStudentStatus().toString());
							listClassPage.setId(examinee.get(i).getId());
							listClassPage.setTel(examinee.get(i).getTel());
							list.add(i, listClassPage);
						}
					}
					
					jsonStr = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
					JsonUtil.jsonOut(jsonStr);
				} catch (IOException e) {
					jsonStr = super.writeJson(0, "查询出现异常" + e.toString());
					logger.error(e);
					try {
						JsonUtil.jsonOut(jsonStr);
					} catch (IOException e1) {
						logger.error(e);
						e1.printStackTrace();
					}
				}
			}
	
	/**
	 * 删除
	 * 
	 * @throws IOException
	 */

	public void delSpeak() throws IOException {
		int id = adailyTalkPage.getId();
		 id = Integer.parseInt(request.getParameter("id"));

		try {
			int result = aDailyTalkBiz.delADailyTalk(id);

			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "该操作不成功");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示历史的每日一讲
	 * 
	 * @throws IOException
	 */

	public void showHistory() throws Exception {
		int status = adailyTalkPage.getStatus();
		String className = adailyTalkPage.getClassName().trim();
		String name = adailyTalkPage.getName().trim();

		String startdate = request.getParameter("startdate").toString().trim(); // 查询开始时间
		String enddate = request.getParameter("enddate").toString().trim(); // 查询结束时间

		if (startdate == null || "".equals(startdate)) {
			startdate = "1911-1-1";
		}
		if (enddate == null || "".equals(enddate)) {
			enddate = "2050-12-30";
		}

		// 判断日期是否正确
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date a = sdf.parse(startdate);
		Date b = sdf.parse(enddate);
		boolean flag = a.before(b);
		if (!flag) {
			String c = "";
			c = startdate;
			startdate = enddate;
			enddate = c;
		}

		List<ADailyTalk> aDailyTalk = null;
		try {
			int cid = aDailyTalkBiz.getCid(className);
			if (name == null || "".equals(name)) {
				aDailyTalk = aDailyTalkBiz.findHistoryADailyTalkByCid(cid, status, null, null);
			} else {
				aDailyTalk = aDailyTalkBiz.findADailyTalkByAllToTeacher(cid, name, startdate, enddate);
			}

			if (aDailyTalk != null && aDailyTalk.size() > 0) {
				for (int i = 0,len=aDailyTalk.size(); i < len; i++) {
					aDailyTalk.get(i).setExamineeClass(null);
				}
				jsonStr = super.writeJson(0, aDailyTalk);
			} else {
				jsonStr = super.writeJson(1, "暂无相关每日一讲信息");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示还未开讲的
	 * 
	 * @throws IOException
	 */
	public void showDailyTalk() throws IOException {
		int status = adailyTalkPage.getStatus();
		String className = adailyTalkPage.getClassName().trim();
		try {
			int cid = aDailyTalkBiz.getCid(className);

			List<ADailyTalk> aDailyTalk = aDailyTalkBiz.findADailyTalkByCid(cid, status);

			if (aDailyTalk != null && aDailyTalk.size() > 0) {
				for (int i = 0,len=aDailyTalk.size(); i < len; i++) {
					aDailyTalk.get(i).setExamineeClass(null);
				}
				jsonStr = super.writeJson(0, aDailyTalk);
			} else {
				jsonStr = super.writeJson(1, null);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	}

	/**
	 * 开讲
	 * 
	 * @throws IOException
	 */
	public void startSpeak() throws IOException {
		int id = adailyTalkPage.getId();
		String assessment = adailyTalkPage.getAssessment();
		try {
			int result = aDailyTalkBiz.updateADailyTalkStatus(id, assessment, 2);
			if (result > 0) {
				jsonStr = super.writeJson(0, null);
			} else {
				jsonStr = super.writeJson(1, "该操作不成功");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 每日一讲
	 * 
	 * @return
	 */
	public void talk() {
		String uname = mysession.getAttribute("userName").toString().trim(); // 当前学生姓名
		// String
		// cid=mysession.getAttribute("loginUserclassId").toString().trim();
		// //班级编号
		String examName = mysession.getAttribute("examClass").toString().trim(); // 班级编号
		int cid = aDailyTalkBiz.getCid(examName);
		int pageNo = super.getPage();
		int row = super.getRows();
		AdailyTalkPage at = new AdailyTalkPage();
		DataGaidModel dgm = new DataGaidModel();

		List<AdailyTalkPage> AdailyTalkModel = new ArrayList<AdailyTalkPage>();

		List<ADailyTalk> aDailyTalkInfoHistory = aDailyTalkBiz.findSize(cid);

		totalInfo = aDailyTalkInfoHistory.size();
		dgm.setTotal(Long.parseLong(totalInfo + ""));

		List<ADailyTalk> list = aDailyTalkBiz.findADailyTalkBypages(cid, uname, pageNo, row);
		for (int i = 0,len=list.size(); i < len; i++) {
			AdailyTalkPage atp = new AdailyTalkPage();

			String a = list.get(i).getSpeakdate().toString();
			String b = a.split(" ")[0];
			atp.setId(list.get(i).getId());
			atp.setName(list.get(i).getName());
			atp.setKnowledge(list.get(i).getKnowledge());
			atp.setAssessment(list.get(i).getAssessment());
			atp.setSpeakdate(java.sql.Date.valueOf(b));
			atp.setStatus(list.get(i).getStatus());
			atp.setUname(uname);
			if ("1".equals(list.get(i).getStatus() + "") || list.get(i).getStatus() == 1) {
				atp.setSta("资料准备中");
			} else if ("2".equals(list.get(i).getStatus() + "") || list.get(i).getStatus() == 2) {
				atp.setSta("资料未上传");
			} else if ("3".equals(list.get(i).getStatus() + "") || list.get(i).getStatus() == 3) {
				atp.setSta("可以下载");
			}
			AdailyTalkModel.add(atp);
			// dgm.setRows(AdailyTalkModel);
		}
		dgm.setRows(AdailyTalkModel);

		String json = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd");
		// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd
		// HH:mm:ss", SerializerFeature.PrettyFormat);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void talkByFpc() {
		String uname = mysession.getAttribute("userName").toString().trim(); // 当前学生姓名
		// String
		// cid=mysession.getAttribute("loginUserclassId").toString().trim();
		// //班级编号
		String examName = mysession.getAttribute("examClass").toString().trim(); // 班级编号
		int cid = aDailyTalkBiz.getCid(examName);
		List<ADailyTalk> stuNameList = aDailyTalkBiz.findADailyTalkStudentName(cid);
		map.put("stuNameList", stuNameList);
		listMap.add(map);
		
		String json = this.writeJson(0, listMap);
		out.print(json);
		out.flush();
	}

	// 查询
	public void find() throws ParseException {
		String uname = mysession.getAttribute("userName").toString().trim(); // 当前学生姓名
		String examName = mysession.getAttribute("examClass").toString().trim(); // 班级编号

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		if (startDate == null || "".equals(startDate)) {
			startDate = "1911-1-1";
		}
		if (endDate == null || "".equals(endDate)) {
			endDate = "2050-12-30";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date aa = sdf.parse(startDate);
		Date bb = sdf.parse(endDate);
		boolean flag = aa.before(bb);
		if (!flag) {
			String c = "";
			c = startDate;
			startDate = endDate;
			endDate = c;
		}

		String stuname = request.getParameter("stuname");
		if ("0".equals(stuname)) {
			stuname = null;
		}
		int cid = aDailyTalkBiz.getCid(examName);
		
		int pageNo = super.getPage();
		int row = super.getRows();
		AdailyTalkPage at = new AdailyTalkPage();
		DataGaidModel dgm = new DataGaidModel();

		List<AdailyTalkPage> AdailyTalkModel = new ArrayList<AdailyTalkPage>();
		//
		// List<ADailyTalk> aDailyTalkInfoHistory = aDailyTalkBiz.findSize(cid);
		//
		// totalInfo = aDailyTalkInfoHistory.size();
		// dgm.setTotal(Long.parseLong(totalInfo+""));

		List<ADailyTalk> list = aDailyTalkBiz.findADailyTalkBytime(cid, uname, startDate, endDate, stuname, pageNo,
				row);
		totalInfo = aDailyTalkBiz.findADailyTalkSize(cid, uname, startDate, endDate, stuname).size();
		dgm.setTotal(Long.parseLong(totalInfo + ""));
		for (int i = 0,len=list.size(); i < len; i++) {
			AdailyTalkPage atp = new AdailyTalkPage();

			String a = list.get(i).getSpeakdate().toString();
			String b = a.split(" ")[0];
			atp.setId(list.get(i).getId());
			atp.setName(list.get(i).getName());
			atp.setKnowledge(list.get(i).getKnowledge());
			atp.setAssessment(list.get(i).getAssessment());
			atp.setSpeakdate(java.sql.Date.valueOf(b));
			atp.setStatus(list.get(i).getStatus());
			atp.setUname(uname);
			if ("1".equals(list.get(i).getStatus() + "") || list.get(i).getStatus() == 1) {
				atp.setSta("资料准备中");
			} else if ("2".equals(list.get(i).getStatus() + "") || list.get(i).getStatus() == 2) {
				atp.setSta("资料未上传");
			} else if ("3".equals(list.get(i).getStatus() + "") || list.get(i).getStatus() == 3) {
				atp.setSta("可以下载");
			}
			AdailyTalkModel.add(atp);
			// dgm.setRows(AdailyTalkModel);
		}
		dgm.setRows(AdailyTalkModel);

		String json = JSON.toJSONStringWithDateFormat(dgm, "yyyy-MM-dd");
		// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd
		// HH:mm:ss", SerializerFeature.PrettyFormat);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	// 下载
	public void download() {
		String id = request.getParameter("id").toString(); // 新技术编号
		// String path=request.getParameter("path").toString(); //文件路径
		List<ADailyTalk> list = null;

		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		list = aDailyTalkBiz.downloadKnowledgeInfos(Integer.parseInt(id));
		File downFile;
		if (list != null && list.size() > 0) {
			// String moduleDir =
			// this.getServletContext().getRealPath(this.filepath);
			String moduleDir = request.getScheme() + "://" + request.getLocalAddr() + ":" + request.getServerPort()
			+ request.getContextPath();
			//org.newboy.io.FileUtils.newFolder(moduleDir);
			// http://127.0.0.1:80/WebExamination/upload/fpc201405112053.rar
			String local = "http://" + request.getLocalAddr();
			local += ":" + request.getLocalPort() + request.getContextPath().substring(0,request.getContextPath().lastIndexOf("/")) + "/upload/" + list.get(0).getRemark();
			// C:/Program Files
			// (x86)/apache-tomcat-7.0.42/webapps/WebExamination/upload
			downFile = new File(local);
			map.put("local", local);
			list1.add(map);
			String json = this.writeJson(0, list1);
			out.println(json);
			out.flush();
			out.close();
		}
	}

	/**
	 * 获取每日一讲的知识点的名字
	 * @throws IOException
	 */
	public void getKnow() throws IOException {
		String id = request.getParameter("id").toString();
		ADailyTalk ad = aDailyTalkBiz.findADailyTalkDetail(Integer.parseInt(id));
		PropertyFilter propertyFilter = new PropertyFilter() {
			boolean flag = true;
			@Override
			public boolean apply(Object obj, String name, Object value) {
				if (name.equalsIgnoreCase("examineeClass")) {
					flag = false;
				} else {
					flag = true;
				}
				return flag;
			}

		};
		String jsonString=JSON.toJSONString(  ad ,propertyFilter );
		JsonUtil.jsonOut( jsonString  );
	}

	// 上传
	// public void upload() {
	// String id = null; // 新技术编号
	// byte[] bt = null;
	// String fileName = null;
	// int result = 0;
	//
	//
	// // 处理表单其它数据
	// String path=request.getParameter("filename");
	// id=request.getParameter("id");
	// fileName = request.getParameter("filename");
	// // 得到上传文件的相关信息，可省
	// fileName = UrlUtils.formatParam(fileName);
	// // 只取后面一段
	// fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
	// fileName.length());
	// String filetype = fileName.substring(fileName.lastIndexOf("."));
	// fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
	// fileName.lastIndexOf("."))
	// + filetype;
	//
	// // String moduleDir =
	// // HttpServlet.getServletContext().getRealPath(this.filepath);
	// File uploadedFile = new File(path);
	// try {
	// FileInputStream fis = new FileInputStream(path);
	// bt = new byte[fis.available()];
	// fis.read(bt);
	// fis.close();
	// result = aDailyTalkBiz.uploadKnowledgeInfos(bt,
	// Integer.parseInt(id),fileName);
	// if (result > 0) {
	// List<ADailyTalk> listResult = (List<ADailyTalk>) mysession
	// .getAttribute("saDailyTalkListInfo");
	// if (listResult != null && listResult.size() > 0) {
	// for (ADailyTalk at : listResult) {
	// if (at.getId() == Integer.parseInt(id)) {
	// at.setStatus(3);
	// }
	// }
	// }
	// mysession.setAttribute("saDailyTalkListInfo", listResult);
	// out.println("<script>alert('上传成功!');window.close(); </script>");
	// } else {
	// out.println("<script>alert('上传失败!');window.close();</script>");
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// out.flush();
	// out.close();
	// try {
	// String realpath =
	// ServletActionContext.getServletContext().getRealPath("/upload");
	// //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
	// System.out.println("realpath: "+realpath);
	// if (image != null) {
	// File savefile = new File(new File(realpath), imageFileName);
	// if (!savefile.getParentFile().exists())
	// savefile.getParentFile().mkdirs();
	// FileUtils.copyFile(image, savefile);
	// ActionContext.getContext().put("message", "文件上传成功");
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	@Override
	public AdailyTalkPage getModel() {
		return adailyTalkPage;
	}

	public void setCheckingBiz(CheckingBiz checkingBiz) {
		this.checkingBiz = checkingBiz;
	}

	public void setaDailyTalkBiz(ADailyTalkBiz aDailyTalkBiz) {
		this.aDailyTalkBiz = aDailyTalkBiz;
	}

	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}

	public void setTempBiz(TempBiz tempBiz) {
		this.tempBiz = tempBiz;
	}

}
