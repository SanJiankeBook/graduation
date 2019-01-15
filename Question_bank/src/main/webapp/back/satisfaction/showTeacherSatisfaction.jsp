<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc" %>	 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
!window.jQuery && document.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" language="javascript"
	src="../../js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/demo.css">
<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../jslib/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/dateChoose.js"></script>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

<style>
#showTable table {
	float: left;
	margin-top: 50px;
	margin-left: 120px;
	margin-top: 10px;
	border: 1px solid #000000;
	text-align: center;
	margin-bottom: 10px;
}

#showTable table tr {
	border: 1px solid #000000;
	width: 80px;
}

#showTable table td {
	border: 1px solid #000000;
	width: 100px;
	height: 20px;
	font-size: 13px;
}

#teacher_table_left {
	float: left;
	margin-left:30px;
	width: 45%;
}

#teacher_table_rigth {
	float: left;
	width: 45%;
	padding-left: 30px;
}

#teacher_count {
	display: inline-block;
	margin-left: 20px;
}

#showTable table td p {
	text-align: center;
}
</style>

</head>
<body >

	<div>
		老师名：<select id="showTeacherSatis_teacherName">
			<option value="-1" selected="selected">--请选择--</option>
		</select>&nbsp;&nbsp; 年份：<input type="text" id="showTeacherSatis_year">&nbsp;&nbsp;
		月份：<yc:monthSelect id="showTeacherSatis_month" name="月" ></yc:monthSelect>
		<input type="button" id="showTeacherSatis_show"
			onClick="showTeacherSatisfaction()" value="查看">
	</div>

	<div id="showTable">
		<table id="teacherSatisShow"  width="80%" cellpadding='0' cellspacing='0'>
			<tr>
				<td>老师名</td>
				<td>班级名</td>
				<td>开启时间</td>
				<td>班级人数</td>
				<td>样本数</td>
				<td>平均分</td>
				<td>操作</td>
			</tr>
		</table>
	</div>
	<div id="teacher_show_satisfactiondetail" align="center" style="display: none;margin:0 auto;width: 90%;">
		<div id="teacher_table_left">
			<table>
				<tr>
					<td >平均分:</td>
					<td id="teacher_count" style="font-size: 22px;"></td>
				</tr>
				<tr>
					<td>不满意：</td>
					<td ><textarea rows="6" cols="50" id="unsatis"
							style="overflow: scroll"></textarea></td>
				</tr>
				<tr>
					<td>满意：</td>
					<td ><textarea rows="6" cols="50" id="satis"
							style="overflow: scroll"></textarea></td>
				</tr>

			</table>
		</div>
		<div id="teacher_table_rigth">
			<table border="1">
				<thead>
					<tr>
						<th colspan="3" id="head"></th>
						<th>得分（满分10分）</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td rowspan="12">理论教学</td>
						<td rowspan="10">课堂教学</td>
						<td>备课充分，重难点突出</td>
						<td id="0"></td>
					</tr>

					<tr>
						<td>讲课认真，内容熟练</td>
						<td id="1"></td>
					</tr>
					<tr>
						<td>语言表达能力</td>
						<td id="2"></td>
					</tr>
					<tr>
						<td>案例丰富，切合主题</td>
						<td id="3"></td>
					</tr>
					<tr>
						<td>讲题有启发性，调动学生主动思考</td>
						<td id="4"></td>
					</tr>
					<tr>
						<td>教学形式灵活、与学员有互动</td>
						<td id="5"></td>
					</tr>
					<tr>
						<td>及时总结，前后知识衔接</td>
						<td id="6"></td>
					</tr>
					<tr>
						<td>方法得当，板书规范</td>
						<td id="7"></td>
					</tr>
					<tr>
						<td>知识点讲解完后，有足够的时间上机操练</td>
						<td id="8"></td>
					</tr>

					<tr>
						<td>授课有条理，思路清晰</td>
						<td id="9"></td>
					</tr>

					<tr>
						<td rowspan="2">作业辅导</td>
						<td>作业适量，批改及时，讲评认真</td>
						<td id="10"></td>
					</tr>

					<tr>
						<td>坚持课后辅导且耐心细致</td>
						<td id="11"></td>
					</tr>

					<tr>
						<td rowspan="7" colspan="2">实践教学</td>
						<td>认真负责，准备充分</td>
						<td id="12"></td>
					</tr>
					<tr>
						<td>上机内容明确清楚</td>
						<td id="13"></td>
					</tr>
					<tr>
						<td>巡回指导主动性</td>
						<td id="14"></td>
					</tr>
					<tr>
						<td>指导耐心细致</td>
						<td id="15"></td>
					</tr>
					<tr>
						<td>组织严谨，以身作则</td>
						<td id="16"></td>
					</tr>
					<tr>
						<td>完成实践实训任务</td>
						<td id="17"></td>
					</tr>
					<tr>
						<td>老师解答问题的能力</td>
						<td id="18"></td>
					</tr>

					<tr>
						<td rowspan="7" colspan="2">综合素质</td>
						<td>老师的专业知识技能</td>
						<td id="19"></td>
					</tr>
					<tr>
						<td>与学生交流沟通</td>
						<td id="20"></td>
					</tr>
					<tr>
						<td>是否按时上下课、课程进度完成</td>
						<td id="21"></td>
					</tr>
					<tr>
						<td>老师的责任心与耐心</td>
						<td id="22"></td>
					</tr>
					<tr>
						<td>严格课堂纪律，严格考勤</td>
						<td id="23"></td>
					</tr>
					<tr>
						<td>言行文明，为人师表</td>
						<td id="24"></td>
					</tr>
					<tr>
						<td>敬业爱岗，关心学生，教书育人</td>
						<td id="25"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

</body>
<script type="text/javascript">
 $(function(){
	 
		var now = new Date();
		var startyear = getNum(now.getFullYear());
		
		$("#showTeacherSatis_year").val(startyear);

		function getNum(num){
				if(num<10)
			{
				return "0"+num;
			}
				else
			{
				return num;
			}
				}
	 
	//获取老师信息
	
		$.post("findAllTeacher.action", function(data) {
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
	//显示老师满意度信息
	function showTeacherSatisfaction(){
		$("#teacher_show_satisfactiondetail").css("display","none")
		var teacherName=$("#showTeacherSatis_teacherName").find("option:selected").text();;
		var year=$("#showTeacherSatis_year").val();
		var month=$("#showTeacherSatis_month").val();
		if(teacherName==-1){
			alert("请选择老师！");
			return;
		}
		$.post("showTeacherSatisfactionAction.action", {
			year:year,
			month:month,
			teacherName:teacherName
		}, function(data) {
			$("#teacherSatisShow  tr:not(:first)").empty("");
			if (data.responseCode == 1) {
				alert("当前选择的时间没有满意度调查表数据!");
				return;
			} else {
				if(data.obj.length==0){
					alert("当前选择的时间没有满意度调查表数据!");
					return;
				}
				
				for(var i=0;i<data.obj.length;i++){
					if(data.obj[i].summaryGrade==undefined){
						data.obj[i].summaryGrade=0;	
					}
					$("#teacherSatisShow").append("<tr><td>"+data.obj[i].teacherName+
						"</td><td>"+data.obj[i].className+"</td><td>"+data.obj[i].startTime+"</td><td>"+data.obj[i].teacherid+"</td><td>"+data.obj[i].openPersonId+"</td><td>"+data.obj[i].summaryGrade+"</td><td><a href='javascript:find_satisteacher("
						+ data.obj[i].id
						+ ",\""
						+ data.obj[i].className
						+ "\")'>详情</a></td></tr>");
				}
				
			}
		});
	}

	function find_satisteacher(id, classname) {
		$.post("showClassSatisfactionDetailAction.action", {
			id : id,
			className : classname,
		}, function(data) {
			if (data.responseCode == 1) {
				alert("查看失败，请与管理员联系!");
				return;
			}
			$("#teacher_show_satisfactiondetail").css("display","block")
			//a1-a26 26项评分细则的打分
			var a1 = 0;
			var a2 = 0;
			var a3 = 0;
			var a4 = 0;
			var a5 = 0;
			var a6 = 0;
			var a7 = 0;
			var a8 = 0;
			var a9 = 0;
			var a10 = 0;
			var a11 = 0;
			var a12 = 0;
			var a13 = 0;
			var a14 = 0;
			var a15 = 0;
			var a16 = 0;
			var a17 = 0;
			var a18 = 0;
			var a19 = 0;
			var a20 = 0;
			var a21 = 0;
			var a22 = 0;
			var a23 = 0;
			var a24 = 0;
			var a25 = 0;
			var a26 = 0;
			var count = 0;
			var satis = [];
			var unsatis = [];
			var sampleCount = 0;
			sampleCount = data.obj.length;
			if (sampleCount == 0) {
				sampleCount = 1;
			}
			$("#head").text("     老师："+data.obj[0].teacherName);
			for (var i = 0; i < data.obj.length; i++) {
				
				a1 += data.obj[i].a1;
				a2 += data.obj[i].a2;
				a3 += data.obj[i].a3;
				a4 += data.obj[i].a4;
				a5 += data.obj[i].a5;
				a6 += data.obj[i].a6;
				a7 += data.obj[i].a7;
				a8 += data.obj[i].a8;
				a9 += data.obj[i].a9;
				a10 += data.obj[i].a10;
				a11 += data.obj[i].a11;
				a12 += data.obj[i].a12;
				a13 += data.obj[i].a13;
				a14 += data.obj[i].a14;
				a15 += data.obj[i].a15;
				a16 += data.obj[i].a16;
				a17 += data.obj[i].a17;
				a18 += data.obj[i].a18;
				a19 += data.obj[i].a19;
				a20 += data.obj[i].a20;
				a21 += data.obj[i].a21;
				a22 += data.obj[i].a22;
				a23 += data.obj[i].a23;
				a24 += data.obj[i].a24;
				a25 += data.obj[i].a25;
				a26 += data.obj[i].a26;
				count += data.obj[i].totalScore;

				satis += data.obj[i].satisfaction + ",";
				unsatis += data.obj[i].unsatisfaction + ",";
				$("#classSatisDetail_sampleNumber").text(
						data.obj[i].sampleCount);
				$("#classSatisDetail_classPeopleNumber").text(
						data.obj[i].classPeopleCount);

			}

			if (count != 0) {
				count = (count / 26);
				count = count / sampleCount;
				//count=count.toFixed(2);
				//count=count.substr(0,count.indexOf(".")+3);
			}

			if ($("#classSatisDetail_sampleNumber").text() == '') {
				$("#classSatisDetail_sampleNumber").text('0');
			}

			$("#0").text((a1 / sampleCount).toFixed(1));
			$("#1").text((a2 / sampleCount).toFixed(1));
			$("#2").text((a3 / sampleCount).toFixed(1));
			$("#3").text((a4 / sampleCount).toFixed(1));
			$("#4").text((a5 / sampleCount).toFixed(1));
			$("#5").text((a6 / sampleCount).toFixed(1));
			$("#6").text((a7 / sampleCount).toFixed(1));
			$("#7").text((a8 / sampleCount).toFixed(1));
			$("#8").text((a9 / sampleCount).toFixed(1));
			$("#9").text((a10 / sampleCount).toFixed(1));
			$("#10").text((a11 / sampleCount).toFixed(1));
			$("#11").text((a12 / sampleCount).toFixed(1));
			$("#12").text((a13 / sampleCount).toFixed(1));
			$("#13").text((a14 / sampleCount).toFixed(1));
			$("#14").text((a15 / sampleCount).toFixed(1));
			$("#15").text((a16 / sampleCount).toFixed(1));
			$("#16").text((a17 / sampleCount).toFixed(1));
			$("#17").text((a18 / sampleCount).toFixed(1));
			$("#18").text((a19 / sampleCount).toFixed(1));
			$("#19").text((a20 / sampleCount).toFixed(1));
			$("#20").text((a21 / sampleCount).toFixed(1));
			$("#21").text((a22 / sampleCount).toFixed(1));
			$("#22").text((a23 / sampleCount).toFixed(1));
			$("#23").text((a24 / sampleCount).toFixed(1));
			$("#24").text((a25 / sampleCount).toFixed(1));
			$("#25").text((a26 / sampleCount).toFixed(1));
			$("#teacher_count").text((count).toFixed(1));
			$("#satis").text(satis);
			$("#unsatis").text(unsatis);

		}, 'json')
	}
	
</script>

</html>