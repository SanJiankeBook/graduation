<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="../../jslib/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/icon.css">
<script type="text/javascript">
!window.jQuery && document.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../jslib/jeasyui.common.js"></script>
<script type="text/javascript"
	src="../../jslib/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->


<script type="text/javascript" src="../../js/dateChoose.js"></script>
<!-- <script type="text/javascript" src="../../js/satisfactionDetail.js"></script> -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

<body>

<div>
	班级:<label  id="classSatisDetail_className"></label>&nbsp;&nbsp;&nbsp;&nbsp;
	教师：<label id="classSatisDetail_teacherName"></label><br>
	年份：<label id="classSatisDetail_year"></label>&nbsp;&nbsp;&nbsp;&nbsp;
	月份：<label id="classSatisDetail_month"></label><br>
	样本数：<label id="classSatisDetail_sampleNumber"></label>&nbsp;&nbsp;&nbsp;&nbsp;
	班级人数：<label id="classSatisDetail_classPeopleNumber"></label><br>
	
</div>

<div>
	<table  border="1" >
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
			<tr>
				<td colspan="3">平均分</td>
				<td id="count"></td>
			</tr>
			<tr>
				<td  >满意：</td>
				<td colspan="3" ><textarea rows="1" cols="50" id="satis" style="overflow:scroll"></textarea></td>
			</tr>
			<tr>
				<td >不满意：</td>
				<td colspan="3" ><textarea rows="1" cols="50" id="unsatis" style="overflow:scroll"></textarea></td>
			</tr>
			
		</tbody>
	</table>

<a href="back/satisfaction/showClassSatisfaction.jsp" style=" text-decoration:none;font-size:15px;padding-left:360px; ">回    退</a>
</div>

</body>
<script type="text/JavaScript">
Request = {
QueryString : function(item){
var svalue = location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));
return svalue ? svalue[1] : svalue;
}
}

var id=Request.QueryString("id");
//id=id.substring(1,id.length-1);

var className=Request.QueryString("className");
className=className.substring(0,className.length-1);
var teacherName=decodeURI(Request.QueryString("teacherName"));

teacherName=teacherName.substring(0,teacherName.length-1);
var openYear=Request.QueryString("openYear");
openYear=openYear.substring(0,openYear.length-1);
var openMonth=Request.QueryString("openMonth");
openMonth=openMonth.substring(0,openMonth.length-4);
$("#classSatisDetail_className").text(className);
$("#classSatisDetail_teacherName").text(teacherName);
$("#head").text("   班级：" +className+"     老师："+teacherName);
$("#classSatisDetail_year").text(openYear);
$("#classSatisDetail_month").text(openMonth);

$.post("showClassSatisfactionDetailAction.action", {
	id:id,
	className:className,
}, function(data) {
	if (data.responseCode == 1) {
		alert("查看失败，请与管理员联系!");
		return;
	} else {
		
		//a1-a26 26项评分细则的打分
		var a1=0;var a2=0;var a3=0;var a4=0;var a5=0;var a6=0;var a7=0;var a8=0;var a9=0;var a10=0;
		var a11=0;var a12=0;var a13=0;var a14=0;var a15=0;var a16=0;var a17=0;var a18=0;var a19=0;var a20=0;
		var a21=0;var a22=0;var a23=0;var a24=0;var a25=0;var a26=0;var count=0;var satis=[];var unsatis=[];
		var sampleCount=0;
		sampleCount=data.obj.length;
		if(sampleCount==0){
			sampleCount=1;
		}
		
		for(var i=0;i<data.obj.length;i++){
			
			a1+=data.obj[i].a1;a2+=data.obj[i].a2;a3+=data.obj[i].a3;
			a4+=data.obj[i].a4;a5+=data.obj[i].a5;a6+=data.obj[i].a6;
			a7+=data.obj[i].a7;a8+=data.obj[i].a8;a9+=data.obj[i].a9;
			a10+=data.obj[i].a10;a11+=data.obj[i].a11;a12+=data.obj[i].a12;
			a13+=data.obj[i].a13;a14+=data.obj[i].a14;a15+=data.obj[i].a15;
			a16+=data.obj[i].a16;a17+=data.obj[i].a17;a18+=data.obj[i].a18;
			a19+=data.obj[i].a19;a20+=data.obj[i].a20;a21+=data.obj[i].a21;
			a22+=data.obj[i].a22;a23+=data.obj[i].a23;a24+=data.obj[i].a24;
			a25+=data.obj[i].a25;a26+=data.obj[i].a26;count+=data.obj[i].totalScore;
			
			satis+=data.obj[i].satisfaction+",";
			unsatis+=data.obj[i].unsatisfaction+",";
			$("#classSatisDetail_sampleNumber").text(data.obj[i].sampleCount);
			$("#classSatisDetail_classPeopleNumber").text(data.obj[i].classPeopleCount);
			
		} 
		
		if(count!=0){
			count = (count/26);
			count=count/sampleCount;
			//count=count.toFixed(2);
			//count=count.substr(0,count.indexOf(".")+3);
		}
		
		if($("#classSatisDetail_sampleNumber").text()==''){
			$("#classSatisDetail_sampleNumber").text('0');
		}
		
		$("#0").text((a1/sampleCount).toFixed(1));
		$("#1").text((a2/sampleCount).toFixed(1));
		$("#2").text((a3/sampleCount).toFixed(1));
		$("#3").text((a4/sampleCount).toFixed(1));
		$("#4").text((a5/sampleCount).toFixed(1));
		$("#5").text((a6/sampleCount).toFixed(1));
		$("#6").text((a7/sampleCount).toFixed(1));
		$("#7").text((a8/sampleCount).toFixed(1));
		$("#8").text((a9/sampleCount).toFixed(1));
		$("#9").text((a10/sampleCount).toFixed(1));
		$("#10").text((a11/sampleCount).toFixed(1));
		$("#11").text((a12/sampleCount).toFixed(1));
		$("#12").text((a13/sampleCount).toFixed(1));
		$("#13").text((a14/sampleCount).toFixed(1));
		$("#14").text((a15/sampleCount).toFixed(1));
		$("#15").text((a16/sampleCount).toFixed(1));
		$("#16").text((a17/sampleCount).toFixed(1));
		$("#17").text((a18/sampleCount).toFixed(1));
		$("#18").text((a19/sampleCount).toFixed(1));
		$("#19").text((a20/sampleCount).toFixed(1));
		$("#20").text((a21/sampleCount).toFixed(1));
		$("#21").text((a22/sampleCount).toFixed(1));
		$("#22").text((a23/sampleCount).toFixed(1));
		$("#23").text((a24/sampleCount).toFixed(1));
		$("#24").text((a25/sampleCount).toFixed(1));
		$("#25").text((a26/sampleCount).toFixed(1));
		$("#count").text((count).toFixed(1));
		 $("#satis").text(satis);$("#unsatis").text(unsatis);
		 
	}
});

if($("#classSatisDetail_classPeopleNumber").text()==''){
	//$("#teacherSatisDetail_sampleNumber").text('0');
 	$.post("getClassPeopleCountAction.action", {
			className : className,
			}, function(data) {
			if (data.responseCode == 1) {
				//alert("查看失败，请与管理员联系!");
				return;
			} else {
				
				$("#classSatisDetail_classPeopleNumber").text(
						data.obj);
			} 
	});
} 

</script>
</html>