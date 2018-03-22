<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc"%>	 -->
<link rel="stylesheet" href="../../css/extreme.css" type="text/css"></link>
<link href="../../css/szindex.css" type=text/css rel=stylesheet>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style type="text/css">
#firstPage, #upPage, #downPage, #lastPage {
	background-image: url(../../images/bg_x.jpg);
	background-repeat: no-repeat;
	BORDER-TOP-WIDTH: 0px;
	BACKGROUND-POSITION: -4px -4px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	COLOR: #464646;
	WIDTH: 68px;
	LINE-HEIGHT: 23px;
	HEIGHT: 23px;
	BORDER-RIGHT-WIDTH: 0px
}

#firstPage:hover, #upPage:hover, #downPage:hover, #lastPage:hover {
	background-image: url(../../images/bg_x.jpg);
	background-repeat: no-repeat;
	BORDER-TOP-WIDTH: 0px;
	BACKGROUND-POSITION: -4px -30px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	COLOR: #464646;
	WIDTH: 68px;
	LINE-HEIGHT: 23px;
	HEIGHT: 23px;
	BORDER-RIGHT-WIDTH: 0px
}
#changeClass{
	margin-left:130px;
	margin-top:60px;

}
</style>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript">
!window.jQuery && document.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="../../js/creatXML.js"></script>
<script type="text/javascript" src="../../js/jsmathN.js"></script>
<script type="text/javascript" src="../../js/jscharts.js"></script>
<script type="text/javascript" src="../../js/listwritingpaperN.js"></script>
<script type="text/javascript" src="../../js/searchexamineeN.js"></script>

<!-- <script type="text/javascript"
	src="http://lib.sinaapp.com/js/jquery/2.0/jquery.min.js"></script> -->
<script type="text/javascript" src="../../jslib/jquery.min.js"></script>
<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../jslib/jeasyui.common.js"></script>


<script type="text/javascript" src="../../js/dateChoose.js"></script>

<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<body>
<div  style="text-align: center;margin: 0 auto;width:1000px;height:500px">
	<form name="frmList">
		<table width="1000" border="0" cellpadding="0" cellspacing="0">
				<td>
				<div style="background-color: #E8F2FE;width: 1000px;border: 1;border-radius:15px ">
					<table width="100%" border="0" align="center" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0">
									<tr>
										<td width="5%" height="27">
											<div align="right">
												<label class="fontColor1"> 学期编号: </label>
											</div>
										</td>
										<td width="11%"><input name="semester" type="radio"
											value="S1" checked="checked"
											onClick="createSelectOption('S1')">S1 &nbsp;&nbsp; <input
											type="radio" name="semester" value="S2"
											onClick="createSelectOption('S2')">S2 &nbsp; <input
											type="radio" name="semester" value="S3"
											onClick="createSelectOption('S3')">S3</td>
										<td width="11%" height="41" class="fontColor1">班级名称 : <select
											name="examClass" id="examClassName" class="text4"
											onChange="showExamineeNames(this.value)">
												<option>--请选择--</option>
										</select>
										</td>
										<td width="10%" height="27" class="fontColor1">学生姓名 : <span
											class="prompt"> <select name="examineeName"
												id="examineeName" class="text4">
													<option selected>--请选择--</option>
											</select>
										</span>
										</td>
										

										<td height=27 " width="12%" class="fontColor1">教师姓名 : <!--  <input type="text" id="teachername"
											name="teachername" />--> <select
											id="showTeacherSatis_teacherName">
												<option value="-1" selected="selected">--请选择--</option>
										</select>&nbsp;&nbsp;
										</td>
									</tr>

									<tr>
										<td><div style="text-align: right;">访谈日期 ：</div></td>
										<td height=27 " colspan="2" width="20%" class="fontColor1"><!-- <input
											type="text" id="checkDate" name="checkDate"
											readonly="readonly" style="width: 90px;"
											onclick="fPopCalendar(event,this,this)"
											onfocus="this.select()" /> —— <input type="text" value=""
											id="checkDate2" style="width: 90px;" name="checkDate2"
											onclick="fPopCalendar(event,this,this)"
											onblur="changeClasses()" readonly="readonly" /> -->
											<input type="text" value=""
											id="0p" name="checkDate" style="width: 90px;"
											onclick="fPopCalendar(event,this,this)" />——<input type="text" value=""
											id="checkDate2" name="checkDate2" style="width: 90px;"
											onclick="fPopCalendar(event,this,this)" /></td>
										<td><input name="searchInterviewRecord"
											id="searchInterviewRecord" type="button" class="inp_L1"
											value="搜索" onMouseOver="this.className='inp_L2'"
											onMouseOut="this.className='inp_L1'"
											onclick="showInterviewRecord()" /></td>
									</tr>

									<tr>
										<td height="16">&nbsp;</td>
										<td colspan="2"><font color="red"> <span
												id="strPrompt"></span>
										</font></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
		</table>
		<hr>
		<div style="width: 1000px; margin-top: 30px">

			<div style="width: 500px; float: left;">
				<table width="500px" border="0" align="center" cellspacing="0"
					id="tab_showWritingPaper">
					<tr>
					</tr>
					<tr>
						<td>
							<table width="500px" border="1" align="center" cellpadding="1"
								bordercolor="#E8F2FE" cellspacing="1" id="tbPapershowInfo">

							</table>
						</td>
					</tr>
				</table>
				<table width="500px" height="40" border="0" cellpadding="0"
					cellspacing="0">
					<tr height="40">
						<td>
							<table width="500px" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><br> 第 <span id="tabCurrentPage"></span> 页 &nbsp;
										共<span id="tabTotalPage"></span> 页 &nbsp; <input
										id="firstPage" type="button" value="首页"
										onClick="skipToPageNum('first')"></input> &nbsp; <input
										id="upPage" type="button" value="上一页"
										onClick="skipToPageNum('up')"></input> &nbsp; <input
										id="downPage" type="button" value="下一页"
										onClick="skipToPageNum('down')"></input> &nbsp;<input
										value="末页" type="button" id="lastPage"
										onClick="skipToPageNum('last')"></input> &nbsp;<input
										name="pageNum" type="hidden" class="text4" id="pageNume"
										onkeyup="" size="5"><input type="hidden" value=""
										name="sumPage" /></td>

								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr align="center">
									<td><font color="red"> <span id="gradePrompt"></span>
									</font></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center"><font color="red"> <span
								id="gradePaperPrompt">&nbsp;</span>
						</font></td>
					</tr>
				</table>
			</div>

			<div
				style="width: 450px; float: left; margin-left: 30px; margin-top: -20px">
				<div id="showInterviewRecordTo" name="showInterviewRecordTo">
					<div id="showInterviewRecord" name="showInterviewRecord"
						style="display: none;">
						<div style="background-color: #E8F2FE;border-radius: 20px;text-align: center;">访谈记录详细信息显示</div>
						<table width="460px" border="1" cellspacing="0"
							style=" text-align: center;border-radius: 15px">
							<tr height="43">
								<td width="15%" class="titlestyle" style="border-left: none;border-top: none">班级：<span id="className2"></span></td>
								<td width="20%" class="titlestyle">学生名：<span
									id="studentName2"></span></td>
								<td width="20%" class="titlestyle">教师名：<span
									id="teacherName2"></span></td>
								<td width="20%" class="titlestyle"  style="border-right: none;border-top: none">日期：<span id="pdate2"></span></td>
							</tr>

							<tr height="43">
								<td class="titlestyle">地址：<span id="interviewAddress2"></span></td>
								<td colspan="3" class="titlestyle">备注：<span id="remark2"></span></td>

							</tr>
							<tr>
								<td colspan="4"  style="border-bottom: none;border-left: none;border-right: none"><span style="width: 200px;height: 30px;font-size: 20px">访谈记录内容显示</span>
								<hr style="width: 300px"/>
								
								<div name='content2'  class="content" id="content2" style="border: none"></div><br/><br/><br/>
								 <!-- <input type="text" name='question'  class="content" onBlur="resetInfo()"
							id="question"></input> --></td>
							</tr>
						</table>
					</div>
				</div>
			</div>

		</div>


	</form>

</div>
</body>
<script type="text/javascript">

	$(function(){ 
		//从session中取出用户名
		//$("#teachername").val(user)
		$.post("findAllTeacher2.action", function(data) {
			if (data.responseCode == 1) {
				alert("老师查询错误,请与管理员联系");
				return;
			} else {
				for (var i = 0; i < data.obj.length; i++) {
					$("#showTeacherSatis_teacherName").append(
							"<option value="+data.obj[i].id+">"
									+ data.obj[i].userName + "</option>");
				}
			}
		});

	});
	//比较开始时间和结束时间之间是否合理
	function checkEndTime() {
		var startTime = $("#checkDate").val();
		var start = new Date(startTime.replace("-", "/"));
		var endTime = $("#checkDate2").val();
		var end = new Date(endTime.replace("-", "/"));
		if (end < start) {
			return false;
		}
		return true;
	}
	function showInterviewRecord() {
		$("#showInterviewRecord").hide();
		//总页数
		var totalPage = parseInt($.trim($("#tabTotalPage").text()));
		if (totalPage == "") {
			displayRows = 0;
		}
		//每页显示几条
		var displayRows = $.trim($("#displayRows").val());
		if (displayRows == "") {
			displayRows = 10;
			$("#displayRows").val(displayRows);
		}
		//第几页
		var pageNume = $.trim($("#pageNume").val());
		if (pageNume == "") {
			pageNume = 1;
			$("#pageNume").val(pageNume);
		} else if (pageNume == 0) {
			pageNume = 1;
		} else if (pageNume >= totalPage) {
			pageNume = totalPage;
			$("#pageNume").val(totalPage);
		}
		//学生名
		var stuName = $.trim($("#examineeName").val());
		if (stuName == "--请选择--") {
			stuName = "";
		}
		//班级名称	
		var examClassName = $.trim($("#examClassName").val());
		if (examClassName == "--请选择--") {
			examClassName = "";
		}
		//取教师名
		//	var teacherName = $.trim($("#teachername").val());
		var teacherName = $("#showTeacherSatis_teacherName").find(
				"option:selected").text().trim();
		if (teacherName == "--请选择--") {
			teacherName = null;
		}
		//日期
		var startpdate = $.trim($("#checkDate").val());
		var endpdate = $.trim($("#checkDate2").val());
	/* 	if (checkEndTime()) {

		} else {
			alert("结束时间不能小于开始时间，请从新选择");
			return;
		} */
		$.ajax({
			url : "examinInterviewRecord_showInfo.action",
			type : "post",
			datatype : "json",
			data : {
				"pageNume" : pageNume,
				"displayRows" : displayRows,
				"className" : examClassName,
				"studentName" : stuName,
				"teacherName" : teacherName,
				"startpdate" : startpdate,
				"endpdate" : endpdate
			},
			success : showPageInfo
		});
	}
	function change(data) {
		//2017-07-29 00:00:00
		time = data.replace(/\s(\w|:)*/, "");
		//time=time.replace(/\s$/,"");
		return time;
	}

	//拼页面
	function showPageInfo(data) {
		if (data.responseCode == 0) {
			var pageStr = "";
			pageStr = pageStr
					+ '<tr><th bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">学生名</th><th bordercolor="#7EA6DC">教师名</th><th bordercolor="#7EA6DC">班级名</th><th bordercolor="#7EA6DC">日期</th><th bordercolor="#7EA6DC">操作</th></tr>';
			var pageSize = data.obj.pageSize;
			var currentPage = data.obj.currentPage;
			var totalsCount = data.obj.totalsCount;
			var totalsPage = data.obj.totalsPage;
			//alert("pagesize:"+pageSize+"\t"+"totalspage:"+totalsPage);				
			$.each(data.obj.result,function(i, pageinfo) {
								//alert(pageinfo.examSubject);
								pageStr += '<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" id="tr_'+pageinfo.id+'">';
								pageStr += '<td align="left">' + pageinfo.id
										+ '</td>';
								pageStr += '<td align="center">'
										+ pageinfo.studentName + '</td>';
								pageStr += '<td align="center">'
										+ pageinfo.teacherName + '</td>';
								pageStr += '<td align="center">'
										+ pageinfo.className + '</td>';
								pageStr += '<td align="center">'
										+ change(pageinfo.pdate) + '</td>';
								pageStr += '<td align="center"> <a  href="javascript:void(0)" onClick="gradeThisPaper(\''
										+ pageinfo.id
										+ '\')" title="查看详情">查看详情</a>&nbsp; </td></tr>';
							});
			$("#tabCurrentPage").html(currentPage);
			$("#tabTotalPage").html(totalsPage);
			if(data.obj.result==null||data.obj.result==""){
				pageStr += '<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" >';
				pageStr+='<td align="center" colspan="6">暂无数据</td>';
			}
			$("#tbPapershowInfo").html(pageStr);
		} else {
			alert("查询失败")
		}
	}

	//首页，上一页，下一页，尾页，点击时触发该函数
	function skipToPageNum(status) {
		$("#showInterviewRecord").hide();
		//总页数
		var totalPage = parseInt($.trim($("#tabTotalPage").text()));
		//alert(totalPage);
		if (totalPage == "") {
			displayRows = 0;
		}
		//每页显示几条	
		var displayRows = $.trim($("#displayRows").val());
		if (displayRows == "") {
			displayRows = 10;
			$("#displayRows").val(displayRows);
		}
		//第几页		
		var pageNume = $.trim($("#pageNume").val());
		if (pageNume == "") {
			pageNume = 1;
			$("#pageNume").val(pageNume);
		} else if (pageNume == 0) {
			pageNume = 1;
		}
		//当前第几页	
		var currentNume = $.trim($("#tabCurrentPage").text());
		if (currentNume == "") {
			currentNume = 1;
			$("#tabCurrentPage").val(currentNume);
		} else if (currentNume == 0) {
			currentNume = 1;
		}
		//班级名称	
		var examClassName = $.trim($("#examClassName").val());
		if (examClassName == "--请选择--") {
			examClassName = "";
		}
		if (status == "up") { //上一页
			if (currentNume == 1) {
				pageNume = currentNume;
				return;
			} else {
				//$("#upPage").animate({color:'red'});
				pageNume--;
				$("#pageNume").val(pageNume);
			}
		} else if (status == "down") {
			if (currentNume == totalPage || currentNume + "" == totalPage + "") {
				pageNume = currentNume;
				return;
			} else {
				//$("#downPage").animate({color:'red'});
				pageNume++;
				$("#pageNume").val(pageNume);
			}
		} else if (status == "first") {
			if (currentNume == 1) {
				pageNume = currentNume;
				return;
			} else {
				//overChangeColor("firstPage","blue")
				pageNume = 1;
				$("#pageNume").val(pageNume);
			}
		} else if (status == "last") {
			if (currentNume == totalPage) {
				pageNume = currentNume;
				return;
			} else {
				//overChangeColor("lastPage","blue")
				pageNume = totalPage;
				$("#pageNume").val(pageNume);
			}
		}
		//学生名
		var stuName = $.trim($("#examineeName").val());
		if (stuName == "--请选择--") {
			stuName = "";
		}
		//班级名称	
		var examClassName = $.trim($("#examClassName").val());
		if (examClassName == "--请选择--") {
			examClassName = "";
		}
		//取教师名
		var teacherName = $.trim($("#teachername").val());
		//日期
		var pdate = $.trim($("#checkDate").val());

		if (pageNume >= 1 && pageNume <= totalPage) {
			$.ajax({
				url : "examinInterviewRecord_showInfo.action",
				type : "post",
				datatype : "json",
				data : {
					"pageNume" : pageNume,
					"displayRows" : displayRows,
					"className" : examClassName,
					"studentName" : stuName,
					"teacherName" : teacherName,
					"pdate" : pdate
				},
				success : showPageInfo
			});
		}
	}
	function gradeThisPaper(opid) {
		if (opid != "") {
			/*		if (window.localStorage) {
						localStorage.setItem("id", opid);
					} else {
						Cookie.write("id", opid);
					}*/
			$.ajax({
						url : "examinInterviewRecord_showInfoById.action",
						type : "post",
						datatype : "json",
						data : {
							"id" : opid
						},
						success : showPointAnswerInfo
					});
			//window.open("/Examination2.0/back/teachSeduce/showInterviewRecord.html");
		}
	}
	function showPointAnswerInfo(data) {
		$("#showInterviewRecord").show();
		var paperinfos = data;
		if (paperinfos.responseCode == 0) {

			var className = $.trim(data.obj[0].className);
			var interviewAddress = $.trim(data.obj[0].interviewAddress);
			var content = $.trim(data.obj[0].content);
			var pdate = $.trim(change(data.obj[0].pdate));
			var pname = $.trim(data.obj[0].pname);
			var remark = $.trim(data.obj[0].remark);
			var studentName = $.trim(data.obj[0].studentName);
			var teacherName = $.trim(data.obj[0].teacherName);

			$("#className2").text(className);
			$("#interviewAddress2").text(interviewAddress);
			$("#pdate2").text(pdate);
			$("#teacherName2").text(teacherName);
			$("#remark2").text(remark);
			$("#studentName2").text(studentName);
			$("#content2").html(content);

		} else {
			alert("失败");
		}
	}
</script>
</html>