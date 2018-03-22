package com.yc.webexam.actions;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.CheckingBiz;
import com.yc.biz.TempBiz;
import com.yc.po.Temp;
import com.yc.utils.JsonUtil;
import com.yc.vo.AdailyTalkPage;

public class TotalCheckingInfoAction extends BaseAction
		implements ServletRequestAware, ServletResponseAware, ModelDriven<AdailyTalkPage> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2594238533293725648L;

	private static final Logger logger = Logger.getLogger(CheckingAction.class);

	@Resource(name = "checkingBiz")
	private CheckingBiz checkingBiz;
	@Resource(name = "tempBiz")
	private TempBiz tempBiz;
	HttpSession session = null;

	// 根据查询组合条件统计(教师端)
	private void findCheckingInfoByAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		classTopInfo(request, response);

		String semester = request.getParameter("semester").toString().trim(); // 学期编号
		String cid = request.getParameter("cid").toString().trim(); // 班级编号
		String stuname = request.getParameter("stuname").toString().trim(); // 学生姓名
		String startdate = request.getParameter("startdate").toString().trim(); // 查询开始时间
		String enddate = request.getParameter("enddate").toString().trim(); // 查询结束时间
		String datetime = request.getParameter("datetime").toString().trim(); // 查询时间段
		List<Integer> checkides = null;
		checkides = checkingBiz.findCheckingAllCheckId(semester, Integer.parseInt(cid), stuname, startdate, enddate,
				datetime);

		// 如果查到了记录，则执行存储过程
		if (checkides != null && checkides.size() > 0) {
			int result = tempBiz.exectueDelTemp(); // 清空临时表中的记录
			if (result > 0) {
				for (Integer str : checkides) {
					// 执行存储过程
					tempBiz.exectueCheckingProc(str);
				}

				// 根据条件获取考勤信息
				String htmlstr = readyCheckingHtml(Integer.parseInt(cid), stuname);
				JsonUtil.jsonOut(htmlstr);
			} else {
				JsonUtil.jsonOut("0");
			}
		} else {
			JsonUtil.jsonOut("0");
		}
	}

	// 准备查询结果界面
	private String readyCheckingHtml(int cid, String uname) {
		// StringBuffer stf=new StringBuffer("<%@ page language=\"java\"
		// contentType=\"text/html; charset=UTF-8\" import=\"java.util.*\"
		// pageEncoding=\"UTF-8\"%>" +
		// "<%String path = request.getContextPath();String basePath =
		// request.getScheme() +\"://\"+ request.getServerName() + \":\" +
		// request.getServerPort()+ path + \"/\";%>" +
		// "<html><head><base href=\"<%=basePath%>\"/><link
		// href=\"/ExamModule/Jsp/js/szindex.css\" type=\"text/css\"
		// rel=\"stylesheet\"></head>" +
		// "<body><table border=\"1\" cellpadding=\"1\" bordercolor=\"#FFFFFF\"
		// cellspacing=\"1\" >");
		StringBuffer stf = new StringBuffer(
				"<table width=\"1000px\" border=\"1\" cellpadding=\"1\" bordercolor=\"#FFFFFF\" cellspacing=\"1\" >");
		StringBuffer line = new StringBuffer();

		// StringBuffer stf1=new StringBuffer();

		// 查询个人
		if (uname != null && !"".equalsIgnoreCase(uname) && !"0".equals(uname)) {
			List<Temp> singleCheckingInfo = tempBiz.getSingleCheckingInfo(uname);
			if (singleCheckingInfo != null && singleCheckingInfo.size() > 0) {
				stf.append(
						"<tr><th bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th><th bordercolor=\"#7EA6DC\">状&nbsp;&nbsp;态</th><th bordercolor=\"#7EA6DC\">备&nbsp;&nbsp;注</th></tr>");
				for (Temp tp : singleCheckingInfo) {
					line.append(
							"<tr align=\"center\" onmouseover=\"this.bgColor='#93BBDF'\" onmouseout=\"this.bgColor='#EDECEB';\" height=\"25\">");
					line.append("<td bordercolor=\"#7EA6DC\">" + tp.getSubname() + "&nbsp;&nbsp;" + tp.getPointidTime()
							+ "</td>");
					if (tp.getSubid() == 3 || tp.getSubid() == 4) {
						line.append("<td bordercolor=\"#7EA6DC\" style='color:green'>" + tp.getSubidStr() + "</td>");
					} else if (tp.getSubid() == 2 || tp.getSubid() == 5 || tp.getSubid() == 6) {
						line.append("<td bordercolor=\"#7EA6DC\" style='color:red'>" + tp.getSubidStr() + "</td>");
					} else {
						line.append("<td bordercolor=\"#7EA6DC\">" + tp.getSubidStr() + "</td>");
					}
					if (tp.getPcontent() == null || "".equals(tp.getPcontent().toString().trim())) {
						line.append("<td bordercolor=\"#7EA6DC\">&nbsp;</td></tr>");
					} else {
						line.append("<td bordercolor=\"#7EA6DC\">" + tp.getPcontent() + "</td></tr>");
					}
				}
				stf.append(line);

				// 统计信息
				int sum = tempBiz.getCheckingTotalInfo(uname);
				List<Temp> list = tempBiz.getTotalByStatus(uname);
				stf.append(
						"<tr align=\"center\" height=\"40\"><td colspan=\"3\" style=\"font-weight:bold;font-size:18px;\">");
				if (list != null && list.size() > 0) {
					stf.append(uname + "&nbsp;&nbsp;考勤总数为：" + sum + "次&nbsp;&nbsp;&nbsp;&nbsp;");
					for (Temp tp : list) {
						if (tp.getSubid() == 3 || tp.getSubid() == 4) {
							stf.append(
									tp.getSubidStr() + "：<span style=\"color:green;font-weight:bold;font-size:18px;\">"
											+ tp.getClassid() + "次</span>&nbsp;&nbsp;&nbsp;&nbsp;");
						} else if (tp.getSubid() == 2 || tp.getSubid() == 5 || tp.getSubid() == 6) {
							stf.append(
									tp.getSubidStr() + "：<span style=\"color:green;font-weight:bold;font-size:18px;\">"
											+ tp.getClassid() + "次</span>&nbsp;&nbsp;&nbsp;&nbsp;");
						} else {
							stf.append(tp.getSubidStr() + "：<span style=\"font-weight:bold;font-size:18px;\">"
									+ tp.getClassid() + "次</span>&nbsp;&nbsp;&nbsp;&nbsp;");
						}
					}
				}
				stf.append("</td></tr>");
			} else {
				stf.append(
						"<tr><td><span style=\"color:red;font-weight:bold;font-size:18px;\">暂无您要查询的考勤记录</span></td></tr>");
			}
			stf.append("</table>");
		} else {
			// 获取考勤学生的姓名
			stf.append("<tr height=\"25\"><th width=\"8%\" bordercolor=\"#7EA6DC\">日&nbsp;&nbsp;期</th>");
			List<Temp> checkingStuInfo = tempBiz.getAllStuname(cid);
			if (checkingStuInfo != null && checkingStuInfo.size() > 0) {
				int count = 0;
				for (Temp tp : checkingStuInfo) {
					count++;
					stf.append("<th width=\"" + (900 / checkingStuInfo.size()) + "px\" id=\"student" + count
							+ "\" bordercolor=\"#7EA6DC\" onmouseover=\"totalMyCheckingInfo('" + tp.getSname()
							+ "','student" + count + "')\" onmouseout=\"totalMyCheckingout('student" + count + "')\">"
							+ tp.getSname() + "</th>");

					// 统计
				}
				stf.append("</tr></table>");
			}
			// 获取考勤日期
			stf.append(
					"<div id=\"findCheckingResultInfoes\"><table width=\"1000px\" border=\"1\" cellpadding=\"1\" bordercolor=\"#FFFFFF\" cellspacing=\"1\" >");
			List<Temp> checkingDateTime = tempBiz.getAllDateTime(cid);

			// 考勤记录
			List<Temp> allInfo = tempBiz.getAllCheckingInfo(cid);
			if (checkingDateTime != null && checkingDateTime.size() > 0) {
				int count = 0;
				int count1 = 0;
				boolean flag;
				for (Temp tp : checkingDateTime) {
					count++;
					line.append("<tr height=\"25\" align=\"center\"><td width=\"8%\" id=\"stuBydateInfo" + count
							+ "\" bordercolor=\"#7EA6DC\" onmouseover=\"totalDateCheckingInfo('stuBydateInfo" + count
							+ "','" + tp.getPpid() + "')\" onmouseout=\"totalDateCheckingout('stuBydateInfo" + count
							+ "')\">" + tp.getSubname() + " " + tp.getPointidTime() + "</td>");
					if (allInfo != null && allInfo.size() > 0) {
						for (Temp tp1 : checkingStuInfo) {
							flag = true;
							for (Temp tp2 : allInfo) {
								count1++;
								if (tp1.getSname().equals(tp2.getSname()) && tp2.getSubname().equals(tp.getSubname())
										&& tp2.getPointid() == tp.getPointid()) {
									flag = false;
									if (tp2.getSubid() == 3 || tp2.getSubid() == 4) {
										line.append("<td id=\"stuInfo" + count1
												+ "\" onmouseover=\"checkingInfoByPointOver('stuInfo" + count1 + "','"
												+ tp2.getPpid() + "','" + tp1.getSname()
												+ "')\" onmouseout=\"checkingInfoByPointOut('stuInfo" + count1
												+ "')\" bordercolor=\"#7EA6DC\" style='color:green'>"
												+ tp2.getSubidStr() + "</td>");
									} else if (tp2.getSubid() == 2 || tp2.getSubid() == 5 || tp2.getSubid() == 6) {
										line.append("<td id=\"stuInfo" + count1
												+ "\" onmouseover=\"checkingInfoByPointOver('stuInfo" + count1 + "','"
												+ tp2.getPpid() + "','" + tp1.getSname()
												+ "')\" onmouseout=\"checkingInfoByPointOut('stuInfo" + count1
												+ "')\" bordercolor=\"#7EA6DC\" style='color:red'>" + tp2.getSubidStr()
												+ "</td>");
									} else {
										line.append("<td id=\"stuInfo" + count1
												+ "\" onmouseover=\"checkingInfoByPointOver('stuInfo" + count1 + "','"
												+ tp2.getPpid() + "','" + tp1.getSname()
												+ "')\" onmouseout=\"checkingInfoByPointOut('stuInfo" + count1
												+ "')\" bordercolor=\"#7EA6DC\">" + tp2.getSubidStr() + "</td>");
									}
									break;
								}
							}
							if (flag) {
								line.append("<td bordercolor=\"#7EA6DC\">&nbsp;</td>");
							}
						}
					}
					line.append("</tr>");
				}
				stf.append(line + "</table></div>");
				// 获取警告信息
				List<Temp> warnInfo = tempBiz.getWarnInfo(cid);
				if (warnInfo != null && warnInfo.size() > 0) {
					String status2 = "<br /><font color=\"red\">以下人员迟到次数已达3次：";
					boolean flag1 = false;
					String status5 = "<br /><font color=\"red\">以下人员旷课次数已达3次：";
					boolean flag2 = false;
					String status6 = "<br /><font color=\"red\">以下人员早退次数已达3次：";
					boolean flag3 = false;
					stf.append("<br/><br /><font color=\"red\"><b>警告：</b></font><br />");
					for (Temp tp : warnInfo) {
						if (tp.getSubid() == 2) {
							flag1 = true;
							status2 += tp.getSname() + "  ";
						} else if (tp.getSubid() == 5) {
							flag2 = true;
							status5 += tp.getSname() + "  ";
						} else {
							flag3 = true;
							status6 += tp.getSname() + "  ";
						}
					}
					if (flag1) {
						stf.append(status2).append("</font>");
						flag1 = false;
					}
					if (flag2) {
						stf.append(status5).append("</font>");
						flag2 = false;
					}
					if (flag3) {
						stf.append(status6).append("</font>");
						flag3 = false;
					}
				}
				// else{
				// stf.append("<br /><br /><font
				// color=\"red\"><b>警告：</b></font><br /><font
				// size=\"4\">无</font>");
				// }

			} else {
				stf.append(
						"<tr><td><span style=\"color:red;font-weight:bold;font-size:18px;\">暂无您要查询的考勤记录</span></td></tr></table>");
			}
		}
		stf.append("<br/><br/><br/><br/>");
		return stf.toString();
	}

	private void classTopInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			session = request.getSession();
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	@Override
	public AdailyTalkPage getModel() {
		return null;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {

	}

	public void setCheckingBiz(CheckingBiz checkingBiz) {
		this.checkingBiz = checkingBiz;
	}

	public void setTempBiz(TempBiz tempBiz) {
		this.tempBiz = tempBiz;
	}

}
