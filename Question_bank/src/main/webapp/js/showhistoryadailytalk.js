var val;
var result;
$(function() {
	result = getQueryString();
	if(result!=null || result!='' ){
		var arr = result.toString().split(",");
		
		var cid = arr[0].split("=")[1];
		//alert(cid);
		if(arr[1]!=undefined){
		var semester = arr[1].split("=")[1];
		}
		if(arr[2]!=undefined){
			var className = arr[2].split("=")[1];
			showClassName(semester, cid);
		}
		
		
		
		
		$("#" + semester).attr("checked", true);
	}
	

	// $("#examineeClass").val()=cid;
	// $('input:radio[name="examineeClass"]:checked').val()=cid;
	// alert($("#examineeClass").length);
	// $("#examineeClass").find("option:selected").text(className);

	$("#semesterS1").click(function() {
		val = $('input:radio[name="semester"]:checked').val();
	});

	$("#semesterS2").click(function() {
		val = $('input:radio[name="semester"]:checked').val();
	});

	$("#semesterS3").click(function() {
		val = $('input:radio[name="semester"]:checked').val();
	});
})

function checkSemesters(semester) {
	showClassName(semester, null);
}

function initDate() {

}

// 显示讲解班级
function showClassName(semester, cid) {
	$.post("/Examination2.0/examineeclass_showClassList.action", {
		semester : semester
	}, function(json) {
		//var obj = $.parseJSON(json);
		var obj=json;
		$("#examineeClass").html("<option>--请选择--</option>");
		if (obj == null) {
			$("#examineeClass").append("<option>--请选择--</option>");
		}
		for (var i = 0; i < obj.length; i++) {
			$("#examineeClass").append(
					"<option value='" + obj[i].id + "'>" + obj[i].className
							+ "</option>");

			if (obj[i].id == cid) {
				$("#examineeClass option[value='" + cid + "']").attr(
						"selected", "selected");
			}
		}
		changeExamineeClasses();
		showAdailyTalk();
	});
}

// 改变讲解班级显示学员姓名
function changeExamineeClasses() {
	var className = $("#examineeClass").find("option:selected").text().trim();
	if (className == null || className == "--请选择--") {
		$("#historyInfobtn").attr("disabled", true);
		return;
	}
	$("#historyInfobtn").removeAttr("disabled");
	$.post("/Examination2.0/examineeclass_showExamineeList.action", {
		className : className
	}, function(json) {
		//var obj = $.parseJSON(json);
		var obj=json;
		$("#examineeName").html("<option>--请选择--</option>");
		for (var i = 0; i < obj.length; i++) {
			$("#examineeName").append(
					"<option value='" + i + "'>" + obj[i].name
							+ "&nbsp;&nbsp;&nbsp;&nbsp;</option>");
		}
	});

}
// 显示历史记录
function showAdailyTalk(btn) {
	if (btn != null) {
		$("#showResultListInfoTeacher").html("正在查询,请稍后...");
	}
	var str = "";
	$.ajaxSettings.async = false;

	var className = $("#examineeClass").find("option:selected").text().trim(); // 班级编号
	var name = $("#examineeName").find("option:selected").text().trim(); // 学生编号
	var startdate = $("#startDate").val(); // 查询开始时间
	var enddate = $("#endDate").val(); // 查询结束时间

	if (className == "" || className == "--请选择--") {
		return;
	}
	var name = $("#examineeName").find("option:selected").text().trim(); // 考生名
	if (name == "--请选择--" || name == "0") {
		name = null;
	}
	str = "<table id='mytable' width='90%' align='center' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";
	var status = 1;
	$
			.getJSON(
					"/Examination2.0/checking_showHistory.action",
					{
						status : status,
						className : className,
						name : name,
						startdate : startdate,
						enddate : enddate,
					},
					function(data) {
						if (data.responseCode == 0) {

							for (var i = 0; i < data.obj.length; i++) {
								str += "<tr height='23' id='"
										+ (i + 1)
										+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
								str += "<td width=10% align='center'>"
										+ (i + 1) + "</td>";
								str += "<td width=12% align='center'>"
										+ data.obj[i].name + "</td>";
								str += "<td width=45%>" + data.obj[i].knowledge
										+ "</td>";
								str += "<td align='center' width='15%'>"
										+ data.obj[i].speakdate.substr(0, 10)
										+ "</td>";
								if (data.obj[i].status == 2) {
									str += "<td align='center' width='17%'><font style='color:red;font-size:12px'>[未传]</font><a href='javascript:delKnowledgeInfo("
											+ data.obj[i].id
											+ ")'>[删除]</a><a href ='detailAdailytalk.html?name="
											+ data.obj[i].name
											+ "&id="
											+ data.obj[i].id
											+ "'style='font-size:12px' title='查看详细内容'>[详细]</a></td>"
								} else if (data.obj[i].status == 3) {
									str += "<td align='center' width='17%'><font style='color:blue;font-size:12px'>[下载]</font><a href='javascript:delKnowledgeInfo("
											+ data.obj[i].id
											+ ")'>[删除]</a><a style='font-size:12px' title='"
											+ data.obj[i].assessment
											+ "'>[详细]</a></td>"
								}

								str += "</td></tr>";
							}

						} else {
							str += data.errorMessage;
						}
					});
	str += "</table>";
	$("#showResultListInfoTeacher").html(str);
	$("#knowledgeInfo").val("");
}

function delKnowledgeInfo(id) {
	if (confirm("您确定要删除吗?")) {
		$.post("/Examination2.0/checking_delSpeak.action", {
			id : id
		}, function(data) {
			if (data.responseCode == 0) {
				showAdailyTalk();
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

// 重置按钮
function reset() {
	$("#startDate").val("");
	$("#endDate").val("");
	$("#examineeName").val("--请选择--");
	$("#examineeClass").val("0");
}