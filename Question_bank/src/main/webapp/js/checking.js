$(function() {
	semester = $("#semesterS1").attr('value');
	// 设置时间选中 上午、下午、晚上
	/*var myDate = new Date();
	var hours = myDate.getHours();
	if (hours < 12 && hours > 7) {
		$("#dateTime").find("option[value='上午']").attr("selected", true);
	} else if (hours >= 12 && hours < 18) {
		$("#dateTime").find("option[value='下午']").attr("selected", true);
	} else {
		$("#dateTime").find("option[value='晚上']").attr("selected", true);

	}*/
	var dateTime = $("#dateTime").find("option:selected").text().trim();

	$("#semesterS1").prop('checked', true);
	showClassName(semester);

	userName = localStorage.getItem("systemUser_userName");
	$("#systemUser_userName").html("考勤教员：" + userName);
	$("#checkDate").val(changeTime(new Date()));
	$("#checkDate2").val(changeTime(new Date()));
})
var userName = "";
var index = 0; // 考勤人数

function checkSemesters(semester) {
	showClassName(semester);
}

//显示讲解班级
function showClassName(semester) {
	$.post("/Examination2.0/examineeclass_showClassList.action", {
		semester : semester
	}, function(obj) {
		$("#examineeClass").html("<option>--请选择--</option>");
		if (obj == null) {
			return;
		}
		for (var i = 0; i < obj.length; i++) {
			$("#examineeClass").append(
					"<option value='" + obj[i].id + "'>" + obj[i].className
					+ "</option>");
		}
	});
}
var semester=1;
var className=1;
var checkTime=1;
var checkDate=1;
var userName=1;
var checkRemark=1;
var checkId=1;

function showAllCheckingInfo() {
	$("#change_check_info").html("")
	$("#findCheckingResultInfo").html("")
	var str='<tr height="30">';
	str+='<th bordercolor="#7EA6DC" width="15%">学员姓名</th>';
	str+='<th bordercolor="#7EA6DC" width="65%">到勤状态</th>';
	str+='<th bordercolor="#7EA6DC" width="10%">备注</th>';
	str+='<th bordercolor="#7EA6DC" width="10%">操作</th></tr>';
	$("#change_check_info").append(str)
	var cid = $("#examineeClass").find("option:selected").val(); // 班级编号
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var dateTime = $("#dateTime").find("option:selected").text().trim();

	if (cid == "--请选择--" || cid == "0") {
		alert("请选择班级");
		return;
	}

	if (dateTime == "--请选择--") {
		dateTime = null;
	}
	$.post(
			"/Examination2.0/checking_showAllCheckingInfo.action",
			{
				cid : cid,
				startDate : startDate,
				endDate : endDate,
				dateTime : dateTime
			},
			function(obj) {
				if (obj != null && obj.length > 0) {
					str = "<table id='mytable' style='float: left;' width='100%' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";

					for (var i = 0; i < obj.length; i++) {
						if (obj[i].checkRemark == ""
							|| obj[i].checkRemark == null) {
							obj[i].checkRemark = "无";
						}
						str += "<tr height='23' id='"
							+ (i + 1)
							+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
						str += "<td width=15% align='center'>"
							+ (i + 1) + "</td>";
						str += "<td width=15% align='center'>"
							+ obj[i].examineeClass.className
							+ "</td>";
						str += "<td width=15%>"
							+ obj[i].systemUser.userName + "</td>";
						str += "<td align='center' width='25%'>"
							+ changeTime(obj[i].checkDate)
							+ "</td>";
						str += "<td align='center' width='15%'>"
							+ obj[i].checkTime + "</td>";
						"semester=" + semester + "&className="
						+ obj[i].examineeClass.className
						+ "&userName="
						+ obj[i].systemUser.userName
						/*str += "<td align='center' width='15%'><font style='color:blue;font-size:12px'><a href=\"javascript:showcheckingresultinfos('"+obj[i].checkId+"')\">&nbsp;查看详情</a></font></td>"*/
						str += "<td align='center' width='15%'><font style='color:blue;font-size:12px'><a href=\"javascript:showcheckingresultinfos('"+obj[i].semester+"','"+obj[i].examineeClass.className+"'" +
										",'"+obj[i].checkTime+"','"+changeTime(obj[i].checkDate)+"'" +
												",'"+obj[i].systemUser.userName+"','"+obj[i].checkRemark+"','"+obj[i].checkId+"')\">&nbsp;查看详情</a></font></td>"
						str += "</td></tr>";
					}
					// utcToDate(new
					// Date(obj[i].checkDate)).substring(9)
					str += "</table>";
					$("#findCheckingResultInfo").html(str);
				} else {
					alert("该班级在该时段类没有考勤记录");
					$("#findCheckingResultInfo").html("");
				}
			});

}

//得到每个班的详细数据
function showcheckingresultinfos(semester1,className1,checkTime1,checkDate1,userName1,checkRemark1,checkId1) {
	semester=semester1;
	className=className1;
	checkTime=checkTime1;
	checkDate=checkDate1;
	userName=userName1;
	checkRemark=checkRemark1;
	checkId=checkId1;
	
	$("#change_check_info").html("")
	var str='<tr height="30">';
	str+='<th bordercolor="#7EA6DC" width="15%">学员姓名</th>';
	str+='<th bordercolor="#7EA6DC" width="65%">到勤状态</th>';
	str+='<th bordercolor="#7EA6DC" width="10%">备注</th>';
	str+='<th bordercolor="#7EA6DC" width="10%">操作</th></tr>';
	if (checkId == null) {
		$("#change_check_info").append(str+
						"<tr height='30'><td align='center' colspan='4'><span class='fontColor' style='color:red;font-weight:bold;font-size:20px;'>暂无考勤记录</span></td></tr>")
	} else {
		$.post(
						"/Examination2.0/checking_showcheckingresultinfos.action",
						{
							checkId : checkId
						},
						function(obj) {
							if (obj.responseCode == 0) {
								$("#change_check_info").append(str);
								var checkResults = obj.obj;
								var checkResult = checkResults.checkResult;
								crs = checkResult.split("|");
								for (var i = 0; i < crs.length - 1; i++) {
									index = i + 1;
									var stu = crs[i].split(",");
									var stuName = stu[0];
									var stuStatus = stu[1];
									var status = 0;
									var remark = '';
									status = stuStatus.split("-")[0];
									remark = stuStatus.split("-")[1];
									$("#change_check_info")
											.append(
													"<tr height='23' id='student"
															+ i
															+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'; align='left'>"
															+ "<td align='center' width='15%' id='stuName"
															+ (i + 1)
															+ "' >"
															+ stuName
															+ "</td>"
															+ "<td width='65%' align='center' id='radio"
															+ i
															+ "'>"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status1' value='"
															+ stuName
															+ ",1' />已到&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status2' value='"
															+ stuName
															+ ",2' />迟到&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status3' value='"
															+ stuName
															+ ",3' />病假&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status4' value='"
															+ stuName
															+ ",4' />请假&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status5' value='"
															+ stuName
															+ ",5' />旷课&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status6' value='"
															+ stuName
															+ ",6' />早退&nbsp;"
															+ "</td>"
															+ "<td width='10%'><input type='text' size='5' id='remarkInfo"
															+ (i + 1)
															+ "' value='"
															+ remark
															+ "' /></td>"
															+ "<td align='center' width='10%'>"
															+ "<a href=\"javascript:showTotalInfo('"+stuName+"')\">详情</a>"
															+ "</td>"
															+ "</tr>")

									$("#radio" + i + "  #status" + status)
											.attr("checked", true);
									if (status != 1) {
										$("#radio" + i).parent().css(
												'background-color', 'red');
									}
								}
								$("#change_check_info").append("<tr bgcolor='#EDECEB'> <td align='center' colspan='4'>备注 :<input type='text'id='remarkInfo' size='50' value-'"+checkRemark+"' id=''/></td></tr>");
								$("#change_check_info").append("<tr bgcolor='#EDECEB'><td align='center' colspan='4'><input type='button' value='修改' onclick='changeResultInfo()'/> </td></tr>");
							} else if (obj.responseCode == 1 || obj == null) {
								$("checkClassInfo table")
										.append(
												"<tr height='30'><td align='center' colspan='4'><span class='fontColor' style='color:red;font-weight:bold;font-size:20px;'>暂无考勤记录</span></td></tr>")
							}
						});
	}
}

function changeResultInfo() {
	var cid = className; // 考勤班级
	// alert(cid);
	var date = checkDate; // 考勤日期
	var time =checkTime; // 考勤时段
	var remark = $("#remarkInfo").val(); // 备注
	var semester = semester; // 学期

	var resultInfo = ""; // 考勤结果
	var count = 0; // 考勤总数
	var sturemark = ""; // 学生备注信息
	if (date == "" || date == null) {
		alert("考勤日期不能修改为空");
		return false;
	}

	$("#change_check_info input[type=radio]").each(function() {
		if (this.checked) {
			count += 1;
			sturemark = "#remarkInfo" + count;
			sturemark = $(sturemark).val();
			if (sturemark == null || sturemark == "") {
				resultInfo += $(this).val() + "- |";
			} else {
				resultInfo += $(this).val() + "-" + sturemark + "|";
			}
		}
	});

	$.post("/Examination2.0/checking_subchangeChecking.action", {
		id : checkId,
		checkDate : date,
		checkTime : time,
		checkRemark : remark,
		checkResult : resultInfo
	}, function(obj) {
		if (obj.responseCode == 1) {
			alert(obj.errorMessage);
		} else {
			alert("提交成功");
			$("#remarkInfo").val("");
		}
	});
}


function changeClazz() {
	$.ajaxSettings.async = false;
	var className = $("#examineeClass").find("option:selected").text().trim(); // 班级编号
	var mydate = $("#checkDate").val(); // 考勤日期
	var mytime = $("#dateTime").val(); // 考勤时段
	// $("#btnView").attr("disabled",true);
	$("#btnView").removeAttr("disabled");
	var str;
	if (className == "" || className == null) {
		return;
	}
	var statusInfo = [ "已到", "迟到", "病假", "请假", "旷课", "早退" ];
	$.post(
					"/Examination2.0/examineeclass_showExamineeList.action",
					{
						className : className
					},
					function(json) {
						if (json != null || json != "") {
							str = "<table width='100%' border='1' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
							for (var i = 0; i < json.length; i++) {
								index = i + 1;
								str += "<tr height='25px' id=\""
										+ index
										+ "\" bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
								str += "<td width=\"8%\" align=\"center\">"
										+ index + "</td>";
								str += "<td width=\"12%\" align=\"center\">"
										+ json[i].name
										+ "</td><td width=\"50%\" align=\"center\">";
								str += "<input type=\"radio\" name=\"status"
										+ index
										+ "\" id=\"status"
										+ index
										+ "\" value=\""
										+ json[i].name
										+ ",1\" checked=\"checked\"/>已到&nbsp;&nbsp;&nbsp;";
								str += "<input type=\"radio\" name=\"status"
										+ index + "\" id=\"status" + index
										+ "\" value=\"" + json[i].name
										+ ",2\"/>迟到&nbsp;&nbsp;&nbsp;";
								str += "<input type=\"radio\" name=\"status"
										+ index + "\" id=\"status" + index
										+ "\" value=\"" + json[i].name
										+ ",3\"/>病假&nbsp;&nbsp;&nbsp;";
								str += "<input type=\"radio\" name=\"status"
										+ index + "\" id=\"status" + index
										+ "\" value=\"" + json[i].name
										+ ",4\"/>请假&nbsp;&nbsp;&nbsp;";
								str += "<input type=\"radio\" name=\"status"
										+ index + "\" id=\"status" + index
										+ "\" value=\"" + json[i].name
										+ ",5\"/>旷课&nbsp;&nbsp;&nbsp;";
								str += "<input type=\"radio\" name=\"status"
										+ index + "\" id=\"status" + index
										+ "\" value=\"" + json[i].name
										+ ",6\"/>早退&nbsp;&nbsp;&nbsp;";
								str += "</td><td style='text-align:center'><input style='width:70px' type=\"text\" size=\"30\" id=\"remarkInfo"
										+ i + "\"/></td>";
								str+="<td> <a  href='javascript:void(0)' onClick=detailTotal(\'"
												+ json[i].name
												+ "\') title='查看详细内容'>[详细]</a> </td></tr>";
							}
							str += "</table>";
							$("#checkClassInfo").html(str);
						}

			});
}
//提交
function CheckingResultInfo() {
	var cid = $("#examineeClass").val(); // 考勤班级
	// alert(cid);
	var date = $("#checkDate").val(); // 考勤日期
	var time = $("#dateTime").val(); // 考勤时段
	var remark = $("#remarkInfo").val(); // 备注
	var semester = $("input[name=semester]:checked").val(); // 学期

	if (time == "0") {
		alert("请选择考勤时段！");
		//dateTime = null;
		return;
	}
	var resultInfo = ""; // 考勤结果
	var count = 0; // 考勤总数
	var sturemark = ""; // 学生备注信息

	if (cid <= 0) {
		alert("请选择考勤班级");
		return false;
	}

	if (date == "" || date == null) {
		alert("请选择考勤日期");
		return false;
	}

	$("#checkClassInfo input[type=radio]").each(function() {
		if (this.checked) {
			// count+=1;
			sturemark = "#remarkInfo" + count;
			sturemark = $(sturemark).val();
			if (sturemark == null || sturemark == "") {
				resultInfo += $(this).val() + "- |";
			} else {
				resultInfo += $(this).val() + "-" + sturemark + "|";
			}
			count += 1;
		}
	});
	if (index > count) {
		alert("您还有未考完的学生记录，请全部考完后再提交");
		return;
	}

	$.post("/Examination2.0/checking_subChecking.action", {
		className : cid,
		checkDate : date,
		checkTime : time,
		checkRemark : remark,
		semester : semester,
		checkResult : resultInfo,
		userName : userName
	}, function(obj) {
		if (obj.responseCode == 1) {
			alert(obj.errorMessage);
		} else {
			alert("提交成功");
			$("#remarkInfo").val("");
		}
	});
}

function changeClasses() {
	var cid=$("#examineeClass").find("option:selected").val();
	var className = $("#examineeClass").find("option:selected").text().trim(); // 班级编号
	var mydate = $("#checkDate").val(); // 考勤日期
	var mytime = $("#dateTime").val(); // 考勤时段
	var str;
	if (className == "" || className == null) {
		return;
	}
	var statusInfo = [ "已到", "迟到", "病假", "请假", "旷课", "早退" ];
	/*$.post(
			"/Examination2.0/checking_showcheckingClass.action",
			{
				cid : cid,
				mydate : mydate,
				mytime : mytime
			},
			function(data) {
				if (data.dataInfo == "" || data.dataInfo == null) {
					document.myform.btnView.disabled = true;
					str = "<span class=\"fontColor\" style=\"color:red;font-weight:bold;font-size:20px;\">对不起，本班您已经考勤，如需修改或查询请选择学员管理->考勤记录</span>";
				} else {
					document.myform.btnView.disabled = false;
					str = "<table width='90%' border='1' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
					if (data.dataInfo[0].name == "SBSBSB") {
						$("#remarkInfo").val("");
						document.myform.btnView.value = "提交";
						$
						.each(
								data.dataInfo,
								function(index, item) {
									if (index != 0
											&& index != "0") {
										str += "<tr height='25px' id=\""
											+ index
											+ "\" bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
										str += "<td width=\"8%\" align=\"center\">"
											+ index
											+ "</td>";
										str += "<td width=\"12%\" align=\"center\">"
											+ item.name
											+ "</td><td width=\"50%\" align=\"center\">";
										str += "<input type=\"radio\" name=\"status"
											+ index
											+ "\" id=\"status"
											+ index
											+ "\" value=\""
											+ item.name
											+ ",1\" checked=\"checked\"/>已到&nbsp;&nbsp;&nbsp;";
										str += "<input type=\"radio\" name=\"status"
											+ index
											+ "\" id=\"status"
											+ index
											+ "\" value=\""
											+ item.name
											+ ",2\"/>迟到&nbsp;&nbsp;&nbsp;";
										str += "<input type=\"radio\" name=\"status"
											+ index
											+ "\" id=\"status"
											+ index
											+ "\" value=\""
											+ item.name
											+ ",3\"/>病假&nbsp;&nbsp;&nbsp;";
										str += "<input type=\"radio\" name=\"status"
											+ index
											+ "\" id=\"status"
											+ index
											+ "\" value=\""
											+ item.name
											+ ",4\"/>请假&nbsp;&nbsp;&nbsp;";
										str += "<input type=\"radio\" name=\"status"
											+ index
											+ "\" id=\"status"
											+ index
											+ "\" value=\""
											+ item.name
											+ ",5\"/>旷课&nbsp;&nbsp;&nbsp;";
										str += "<input type=\"radio\" name=\"status"
											+ index
											+ "\" id=\"status"
											+ index
											+ "\" value=\""
											+ item.name
											+ ",6\"/>早退&nbsp;&nbsp;&nbsp;";
										str += "</td><td><input type=\"text\" size=\"35\" id=\"remarkInfo"
											+ index
											+ "\"/></td></tr>";
									}
								});
					} else {
						document.myform.btnView.value = "提交修改";
						$("#remarkInfo").val(data.dataInfo[0].name);
						$
						.each(
								data.dataInfo,
								function(index, item) {
									if (index != 0
											&& index != "0") {
										str += "<tr height='25px' id=\""
											+ index
											+ "\" bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
										str += "<td width=\"8%\" align=\"center\">"
											+ index
											+ "</td>";
										str += "<td width=\"12%\" align=\"center\">"
											+ item.sname
											+ "</td><td width=\"50%\" align=\"center\">";
										for (var i = 1; i <= 6; i += 1) {
											if (item.subid == i) {
												str += "<input type=\"radio\" name=\"status"
													+ index
													+ "\" id=\"status"
													+ index
													+ "\" value=\""
													+ item.sname
													+ ","
													+ i
													+ "\" checked=\"checked\"/>"
													+ statusInfo[i - 1]
												+ "&nbsp;&nbsp;&nbsp;";
											} else {
												str += "<input type=\"radio\" name=\"status"
													+ index
													+ "\" id=\"status"
													+ index
													+ "\" value=\""
													+ item.sname
													+ ","
													+ i
													+ "\"/>"
													+ statusInfo[i - 1]
												+ "&nbsp;&nbsp;&nbsp;";
											}
										}
										str += "</td><td><input type=\"text\" size=\"35\" id=\"remarkInfo"
											+ index
											+ "\" value=\""
											+ item.pcontent
											+ "\"/></td></tr>";
									}
								});
					}
					str += "</table>";
				}
				$("#checkClassInfo").html(str);
			});*/
	$.post(
					"/Examination2.0/checking_showcheckingClass.action",
					{
						cid : cid,
						mydate : mydate,
						mytime : mytime
					},
					function(data) {
						if (data.dataInfo == "" || data.dataInfo == null) {
							document.myform.btnView.disabled = true;
							str = "<span class=\"fontColor\" style=\"color:red;font-weight:bold;font-size:20px;\">对不起，本班您已经考勤，如需修改或查询请选择学员管理->考勤记录</span>";
						} else {
							document.myform.btnView.disabled = false;
							str = "<table width='100%' border='1' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
							if (data.dataInfo[0].name == "SBSBSB") {
								$("#remarkInfo").val("");
								document.myform.btnView.value = "提交";
								$
										.each(
												data.dataInfo,
												function(index, item) {
													if (index != 0
															&& index != "0") {
														str += "<tr height='25px' id=\""
																+ index
																+ "\" bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
														str += "<td width=\"8%\" align=\"center\">"
																+ index
																+ "</td>";
														str += "<td width=\"12%\" align=\"center\">"
																+ item.name
																+ "</td><td width=\"50%\" align=\"center\">";
														str += "<input type=\"radio\" name=\"status"
																+ index
																+ "\" id=\"status"
																+ index
																+ "\" value=\""
																+ item.name
																+ ",1\" checked=\"checked\"/>已到&nbsp;&nbsp;&nbsp;";
														str += "<input type=\"radio\" name=\"status"
																+ index
																+ "\" id=\"status"
																+ index
																+ "\" value=\""
																+ item.name
																+ ",2\"/>迟到&nbsp;&nbsp;&nbsp;";
														str += "<input type=\"radio\" name=\"status"
																+ index
																+ "\" id=\"status"
																+ index
																+ "\" value=\""
																+ item.name
																+ ",3\"/>病假&nbsp;&nbsp;&nbsp;";
														str += "<input type=\"radio\" name=\"status"
																+ index
																+ "\" id=\"status"
																+ index
																+ "\" value=\""
																+ item.name
																+ ",4\"/>请假&nbsp;&nbsp;&nbsp;";
														str += "<input type=\"radio\" name=\"status"
																+ index
																+ "\" id=\"status"
																+ index
																+ "\" value=\""
																+ item.name
																+ ",5\"/>旷课&nbsp;&nbsp;&nbsp;";
														str += "<input type=\"radio\" name=\"status"
																+ index
																+ "\" id=\"status"
																+ index
																+ "\" value=\""
																+ item.name
																+ ",6\"/>早退&nbsp;&nbsp;&nbsp;";
														str += "</td><td><input type=\"text\" size=\"35\" id=\"remarkInfo"
																+ index
																+ "\"/></td></tr>";
													}
												});
							} else {
								document.myform.btnView.value = "提交修改";
								$("#remarkInfo").val(data.dataInfo[0].name);
								$
										.each(
												data.dataInfo,
												function(index, item) {
													if (index != 0
															&& index != "0") {
														str += "<tr height='25px' id=\""
																+ index
																+ "\" bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
														str += "<td width=\"8%\" align=\"center\">"
																+ index
																+ "</td>";
														str += "<td width=\"12%\" align=\"center\">"
																+ item.sname
																+ "</td><td width=\"50%\" align=\"center\">";
														for (var i = 1; i <= 6; i += 1) {
															if (item.subid == i) {
																str += "<input type=\"radio\" name=\"status"
																		+ index
																		+ "\" id=\"status"
																		+ index
																		+ "\" value=\""
																		+ item.sname
																		+ ","
																		+ i
																		+ "\" checked=\"checked\"/>"
																		+ statusInfo[i - 1]
																		+ "&nbsp;&nbsp;&nbsp;";
															} else {
																str += "<input type=\"radio\" name=\"status"
																		+ index
																		+ "\" id=\"status"
																		+ index
																		+ "\" value=\""
																		+ item.sname
																		+ ","
																		+ i
																		+ "\"/>"
																		+ statusInfo[i - 1]
																		+ "&nbsp;&nbsp;&nbsp;";
															}
														}
														str += "</td><td><input type=\"text\" size=\"35\" id=\"remarkInfo"
																+ index
																+ "\" value=\""
																+ item.pcontent
																+ "\"/></td></tr>";
													}
												});
							}
							str += "</table>";
						}
						$("#checkClassInfo").html(str);
					});
}

function changeTime(time) {
	if (time == null) {
		var now = new Date();
	} else {
		var now = new Date(time);
	}

	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日

	// var hh = now.getHours(); //时
	// var mm = now.getMinutes(); //分

	var clock = year + "-";

	if (month < 10)
		clock += "0";

	clock += month + "-";

	if (day < 10)
		clock += "0";

	clock += day + " ";

	// if(hh < 10)
	// clock += "0";

	// clock += hh + ":";
	// if (mm < 10) clock += '0';
	// clock += mm;
	return (clock);
}
function detailTotal(name) {
	var cid=$("#examineeClass").find("option:selected").val();
	var semester=$("input[name='semester']:checked").val();
	/*console.log(cid,semester);*/
	$.post("/Examination2.0/chapter_getTotal.action", {
		name : name,
		cid : cid,
		semester : semester
	}, function(data) {
		var data = JSON.parse(data); 
		if (data.responseCode == 0) {
			$("#totalDate").html(data.obj);
		} else {
			//
			alert(data.errorMessage);
		}
	});
}
function showTotalInfo(name) {
	var semester=$("input[name='semester1']:checked").val();
	var cid = $("select[name='examineeClass1']").val();
	
	var browser_width = $(document).width(); 
	$("#div1").css("width",browser_width*0.7); 
	var browser_height = $(document).height(); 
	$("#div1").css("height",browser_height); 
	/*$("#div1").show(1000);
    $("#div2").show(1000);*/
	$("#div1").toggle();
	$("#div2").toggle();
	$.post("/Examination2.0/chapter_getTotal.action", {
		name : name,
		cid : cid,
		semester : semester
	}, function(data) {
		var data = JSON.parse(data); 
		if (data.responseCode == 0) {
			//var str="<br/><br/> <button type='button' onclick='closeInfo()' name=\"关闭\">关闭界面</button>";
			$("#div2").html(data.obj);
		} else {
			//
			alert(data.errorMessage);
		}
	});
}
function closeInfo() {
	/* $("#div1").hide();
     $("#div2").hide();*/
}