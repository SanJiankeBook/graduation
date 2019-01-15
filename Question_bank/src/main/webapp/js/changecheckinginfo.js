$(function() {
	var result = null;
	result = getQueryString();
	$.ajaxSettings.async = false;
	var arr = result.toString().split(",");
	var semester = arr[0].split("=")[1].trim();
	var className = arr[1].split("=")[1].trim();
	var userName = arr[2].split("=")[1].trim();
	var checkDate = arr[3].split("=")[1].trim();
	var checkTime = arr[4].split("=")[1].trim();
	var checkRemark = arr[5].split("=")[1].trim();
	var checkId = arr[6].split("=")[1].trim();
	id = checkId;
	checkTime = decodeURIComponent(checkTime);
	document.getElementById('remarkInfo').value = decodeURIComponent(checkRemark);
	$("#semester").text(semester);
	$("#className").text("考勤班级：" + className);
	$("#userName").text("考勤教员：" + decodeURIComponent(userName));
	$("#checkDate").val(checkDate);
	var info='<option value='+checkTime+'>'+checkTime+'&nbsp;</option>'
	$("#dateTime").html(info);
	/*$("#dateTime option").each(function() {
		if ($(this).text().trim() == checkTime) {
			$(this).attr("selected", true);
			return;
		}
	});*/
	showcheckingresultinfos(checkId);
});
var id = 0;// 考勤id
var index = 0; // 考勤人数
function changeResultInfo() {
	var cid = $("#className").text().trim(); // 考勤班级
	// alert(cid);
	var date = $("#checkDate").val(); // 考勤日期
	var time = $("#dateTime").val(); // 考勤时段
	var remark = $("#remarkInfo").val(); // 备注
	var semester = $("input[name=semester]:checked").val(); // 学期

	var resultInfo = ""; // 考勤结果
	var count = 0; // 考勤总数
	var sturemark = ""; // 学生备注信息

	if (date == "" || date == null) {
		alert("考勤日期不能修改为空");
		return false;
	}

	$("#checkClassInfo input[type=radio]").each(function() {
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

	/*
	 * if(index>count){ alert("学生记录数不正确，请全部考完后再提交修改"); return; }
	 */
	$.post("/Examination2.0/checking_subchangeChecking.action", {
		id : id,
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

function showcheckingresultinfos(checkId) {
	if (checkId == null) {
		$("checkClassInfo table")
				.append(
						"<tr height='30'><td align='center' colspan='4'><span class='fontColor' style='color:red;font-weight:bold;font-size:20px;'>暂无考勤记录</span></td></tr>")
	} else {
		$
				.post(
						"/Examination2.0/checking_showcheckingresultinfos.action",
						{
							checkId : checkId
						},
						function(obj) {
							if (obj.responseCode == 0) {
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
									$("#checkClassInfo table")
											.append(
													"<tr height='23' id='student"
															+ i
															+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'; align='left'>"
															+ "<td align='center' width='8%'>"
															+ (i + 1)
															+ "</td>"
															+ "<td align='center' width='10%' id='stuName"
															+ (i + 1)
															+ "' >"
															+ stuName
															+ "</td>"
															+ "<td width='40%' align='center' id='radio"
															+ i
															+ "'>"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status1' value='"
															+ stuName
															+ ",1' />已到&nbsp;&nbsp;&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status2' value='"
															+ stuName
															+ ",2' />迟到&nbsp;&nbsp;&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status3' value='"
															+ stuName
															+ ",3' />病假&nbsp;&nbsp;&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status4' value='"
															+ stuName
															+ ",4' />请假&nbsp;&nbsp;&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status5' value='"
															+ stuName
															+ ",5' />旷课&nbsp;&nbsp;&nbsp;"
															+ "<input type='radio' name='status"
															+ i
															+ "'  id='status6' value='"
															+ stuName
															+ ",6' />早退&nbsp;&nbsp;&nbsp;"
															+ "</td>"
															+ "<td width='38%'><input type='text' size='56' id='remarkInfo"
															+ (i + 1)
															+ "' value='"
															+ remark
															+ "' /></td>"
															+ "</tr>")

									$("#radio" + i + "  #status" + status)
											.attr("checked", true);
									if (status != 1) {
										$("#radio" + i).parent().css(
												'background-color', 'red');
									}
								}
							} else if (obj.responseCode == 1 || obj == null) {
								$("checkClassInfo table")
										.append(
												"<tr height='30'><td align='center' colspan='4'><span class='fontColor' style='color:red;font-weight:bold;font-size:20px;'>暂无考勤记录</span></td></tr>")
							}
						});
	}
}

function getQueryString() {
	var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+",
			"g"));
	if (result == null) {
		return "";
	}
	for (var i = 0; i < result.length; i++) {
		result[i] = result[i].substring(1);
	}
	return result;
}

// 后退
function back() {
	history.go(-1);
}