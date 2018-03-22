<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
!window.jQuery && document.write('<script src=js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" language="javascript"
	src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="jslib/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="jslib/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/demo.css">
<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="jslib/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/dateChoose.js"></script>
<script type="text/javascript" src="js/studentSatisfaction.js"></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

<title>满意度调查表</title>
</head>
<body>
<table style="margin-top: 30px" id="table_list_satisfaction" width="600px" cellspacing="0" cellpadding="0" border="1" align="center">
<thead>
	<tr>
		<td align="center">编号</td>
		<td align="center">评价老师</td>
		<td align="center">评价开始时间</td>
		<td align="center">评价结束时间</td>
		<td align="center">操作</td>
	</tr>
</thead>
</table>
	<script type="text/javascript">
	var allTeacherName=new Array();
	var said=new Array();
	var wid="";
	var teacherName="";
$(function(){
	//获取当前年月
	var date=new Date();
	var mon = date.getMonth()  + 1;        //getMonth()返回的是0-11，则需要加1
	if(mon <=9){                                     //如果小于9的话，则需要加上0
	mon = "0" + mon;
	}
	var day = date.getDate();                   //getdate()返回的是1-31，则不需要加1
	if(day <=9){                                     //如果小于9的话，则需要加上0
	day = "0" + day;
	}
	
	date=date.getFullYear()+ "-" + mon + "-" +  day;
	
	
	var userName = localStorage.getItem("student_userName");
	var userid = localStorage.getItem("student_userid");
	//开启满意度请求
	 $.post("/Examination2.0/isOpenSatisfactionAction.action",
				{
					date:date,
					userName:userName,
					userid:userid
				},
				function(data) {
					
					if(data.responseCode == 1) {
						alert("你已填写过调查表，新的调查表还未开启!");
						return;
					} else {
						if(data.obj==null || data.obj==""){
							
							alert("你已填写过调查表，新的调查表还未开启!");
							return;
						}
						
						var str="";
						for(var i=0,len=data.obj.length;i<len;i++){
							str+="<tr><td align='center' id='id"+data.obj[i].id+"'>"+data.obj[i].id+"</td><td align='center'>"
							+data.obj[i].teacherName+"</td><td align='center'>"
							+data.obj[i].startTime+"</td><td align='center'>"
							+data.obj[i].endTime+"</td><td align='center'><a href='javascript:drowTable("+data.obj[i].id+",\""+data.obj[i].teacherName+"\")'>填写</a></td><tr>"
						}
						$("#table_list_satisfaction").append(str)
						
						/* $("#satisDiv").append("<label style='font-size:15px;'>开启时间：</label><label style='font-size:15px;'>"+data.obj[0].startTime+"</label>&nbsp;&nbsp;&nbsp;&nbsp;<label style='font-size:15px;'>结束时间：</label><label style='font-size:15px;'>"+data.obj[0].endTime+"</label><br><br>");
						for(var i=0;i<data.obj.length;i++){
							drowTable(data.obj[i].teacherName);
							$("#head"+data.obj[i].teacherName+"").text("   班级：" +data.obj[i].className + "     老师：" + data.obj[i].teacherName);
							teacherName=data.obj[i].teacherName;
							said[i]=data.obj[i].id;
						}
						 */
					}
						
				}); 
	
	
	
	
	
});



function satisfactionSub(){
	var flag=0;//判断是否所有的满意度表都插入成功
	
		var count=0;var a1=0;var a2=0;var a3=0;var a4=0;var a5=0;var a6=0;var a7=0;var a8=0;var a9=0;var a10=0;
		var a11=0;var a12=0;var a13=0;var a14=0;var a15=0;var a16=0;var a17=0;var a18=0;var a19=0;var a20=0;
		var a21=0;var a22=0;var a23=0;var a24=0;var a25=0;var a26=0;
		// 评分细则26条  a1-a26
		 a1 = $("#s1"+teacherName).val(); a2 = $("#s2"+teacherName).val(); a3 = $("#s3"+teacherName).val();
		 a4 = $("#s4"+teacherName).val(); a5 = $("#s5"+teacherName).val(); a6 = $("#s6"+teacherName).val();
		 a7 = $("#s7"+teacherName).val(); a8 = $("#s8"+teacherName).val(); a9 = $("#s9"+teacherName).val();
		 a10 = $("#s10"+teacherName).val(); a11 = $("#s11"+teacherName).val(); a12 = $("#s12"+teacherName).val();
		 a13 = $("#s13"+teacherName).val(); a14 = $("#s14"+teacherName).val(); a15 = $("#s15"+teacherName).val();
		 a16 = $("#s16"+teacherName).val(); a17 = $("#s17"+teacherName).val(); a18 = $("#s18"+teacherName).val();
		 a19 = $("#s19"+teacherName).val(); a20 = $("#s20"+teacherName).val(); a21 = $("#s21"+teacherName).val();
		 a22 = $("#s22"+teacherName).val(); a23 = $("#s23"+teacherName).val(); a24 = $("#s24"+teacherName).val();
		 a25 = $("#s25"+teacherName).val(); a26 = $("#s26"+teacherName).val(); //count = $("#count"+teacherName).val();
		var satis = $("#satis"+teacherName).val();var unsatis = $("#unsatis"+teacherName).val();
		var userName = localStorage.getItem("student_userName");
		var saids=said[i];//满意度id
		
		if(a1=='' || a2==''||a3=='' || a4==''||a5=='' || a6==''||a7=='' || a8==''||a9=='' || a10==''||
			a11=='' || a12==''||a13=='' || a14==''||a15=='' || a16==''||a17=='' || a18==''||a19=='' || a20==''||
			a21=='' || a22==''||a23=='' || a24==''||a25=='' || a26==''||unsatis=='' || satis=='' ){
			alert("请填写完整！");
			return;
		}
		count=parseInt(a1)+parseInt(a2)+parseInt(a3)+parseInt(a4)+parseInt(a5)+parseInt(a6)+parseInt(a7)
		count+=parseInt(a8)+parseInt(a9)+parseInt(a10)+parseInt(a11)+parseInt(a12)+parseInt(a13)+parseInt(a14)+parseInt(a15)+parseInt(a16)+parseInt(a17)
		count+=parseInt(a18)+parseInt(a19)+parseInt(a20)+parseInt(a21)+parseInt(a22)+parseInt(a23)+parseInt(a24)+parseInt(a25)+parseInt(a26);
		$.post("/Examination2.0/insertSatisfactionAction.action",
				{
					a1:a1,a2:a2,a3:a3,a4:a4,a5:a5,a6:a6,a7:a7,a8:a8,a9:a9,a10:a10,
					a11:a11,a12:a12,a13:a13,a14:a14,a15:a15,a16:a16,a17:a17,a18:a18,a19:a19,a20:a20,
					a21:a21,a22:a22,a23:a23,a24:a24,a25:a25,a26:a26,satisfaction:satis,
					unsatisfaction:unsatis,className:userName,totalScore:count,said:wid,teacherName:teacherName
				},
				function(data) {
					if(data.responseCode == 1) {
						return;
					} else {
						alert("填写成功!");
						var a=$("#id"+wid).parent()
						a.remove();
						$('#satisDiv').html("");
						}
						 
						// $("#satisDiv").text("");
				});
	//window.location.reload(); 
	/*  console.info("flag:"+flag+"allTeacherName.length:"+allTeacherName.length);
	/* console.info("flag:"+flag+"allTeacherName.length:"+allTeacherName.length);
	if(flag==allTeacherName.length){
		//alert("填写成功!");
		//window.history.go(-1);
		//window.location.reload(); 
	}else{
		//alert("填写失败  请联系管理员！");
	}  */
	
	
}
//绘制满意度表
function drowTable(said,id){
	$('#satisDiv').html("");
	teacherName=id;
	wid=said;
	var table="<br><table align='center' border='1' id='satisWriteTble'> <thead><tr><th colspan='3' id='head"+id+"'></th><th style='width:400px' >得分（满分10分）</th></tr></thead> " ;
		table+="<tbody><tr><td style='width:120px' rowspan='12'>理论教学</td><td style='width:120px' rowspan='10'>课堂教学</td> <td>备课充分，重难点突出</td><td id='0"+id+"'><select  style='width:360px;' id='s1"+id+"'></select> </td></tr>  ";
		table+="<tr><td>讲课认真，内容熟练</td><td id='1"+id+"'><select name='satis_4'   style='width:360px;'  id='s2"+id+"'></select></td></tr> ";
		table+="<tr><td>语言表达能力</td><td id='2"+id+"'><select style='width:360px;'  id='s3"+id+"'></select></td></tr>";
		table+="<tr><td>案例丰富，切合主题</td><td id='3"+id+"'><select style='width:360px;'  id='s4"+id+"'></select></td></tr>";
		table+="<tr><td>讲题有启发性，调动学生主动思考</td><td id='4"+id+"'><select style='width:360px;'  id='s5"+id+"'></select></td></tr>";
		
		table+="<tr><td>教学形式灵活、与学员有互动</td><td id='5"+id+"'><select style='width:360px;' id='s6"+id+"'></select></td></tr>";
		table+="<tr><td>及时总结，前后知识衔接</td><td id='6"+id+"'><select style='width:360px;'  id='s7"+id+"'></select></td></tr>";
		table+="<tr><td>方法得当，板书规范</td><td id='7"+id+"'><select style='width:360px;'  id='s8"+id+"'></select></td></tr>";
		table+="<tr><td>知识点讲解完后，有足够的时间上机操练</td><td id='8"+id+"'><select style='width:360px;'  id='s9"+id+"'></select></td></tr>";
		
		table+="<tr><td>授课有条理，思路清晰</td><td id='9"+id+"'><select style='width:360px;'  id='s10"+id+"'></select></td></tr>";
		table+="<tr><td rowspan='2'>作业辅导</td><td>作业适量，批改及时，讲评认真</td><td id='10"+id+"'><select style='width:360px;'  id='s11"+id+"'></select></td></tr> ";
		table+="<tr><td>坚持课后辅导且耐心细致</td><td id='11"+id+"'><select style='width:360px;'  id='s12"+id+"'></select></td></tr>";
		table+="<tr><td rowspan='7' colspan='2'>实践教学</td><td>认真负责，准备充分</td><td id='12"+id+"'><select style='width:360px;'  id='s13"+id+"'></select></td></tr>";
		table+="<tr><td>上机内容明确清楚</td><td id='13"+id+"'><select style='width:360px;'  id='s14"+id+"'></select></td></tr>";
		table+="<tr><td>巡回指导主动性</td><td id='14"+id+"'><select style='width:360px;'  id='s15"+id+"'></select></td></tr>";
		
		table+="<tr><td>指导耐心细致</td><td id='15"+id+"'><select style='width:360px;'  id='s16"+id+"'></select></td></tr>";
		table+="<tr><td>组织严谨，以身作则</td><td id='16"+id+"'><select style='width:360px;'  id='s17"+id+"'></select></td></tr>";
		table+="<tr><td>完成实践实训任务</td><td id='17"+id+"'><select style='width:360px;' id='s18"+id+"'></select></td></tr>";
		table+="<tr><td>老师解答问题的能力</td><td id='18"+id+"'><select style='width:360px;'  id='s19"+id+"'></select></td></tr>";
		table+="<tr><td rowspan='7' colspan='2'>综合素质</td><td>老师的专业知识技能</td><td id='19"+id+"'><select style='width:360px;'  id='s20"+id+"'></select></td> ";
		table+="<tr><td>与学生交流沟通</td><td id='20"+id+"'><select style='width:360px;' id='s21"+id+"'></select></td></tr>";
		table+="<tr><td>是否按时上下课、课程进度完成</td><td id='21"+id+"'><select style='width:360px;'  id='s22"+id+"'></select></td></tr>";
		
		table+="<tr><td>老师的责任心与耐心</td><td id='22"+id+"'><select style='width:360px;'  id='s23"+id+"'></select></td></tr>";
		table+="<tr><td>严格课堂纪律，严格考勤</td><td id='23"+id+"'><select style='width:360px;'  id='s24"+id+"'></select></td></tr>";
		table+="<tr><td>言行文明，为人师表</td><td id='24"+id+"'><select style='width:360px;'  id='s25"+id+"'></select></td></tr>";
		table+="<tr><td>敬业爱岗，关心学生，教书育人</td><td id='25"+id+"'><select style='width:360px;'  id='s26"+id+"'></select></td></tr>";
		//table+="<tr><td colspan='3'>总分</td><td ><select style='width:360px;' id='count"+id+"'></td></tr>";
		table+="<tr><td  >满意：</td><td colspan='3' ><textarea style='width:700px' id='satis"+id+"' >无</textarea></td></tr> ";
		table+="<tr><td   >不满意：</td><td colspan='3'  ><textarea style='width:700px' id='unsatis"+id+"'>无</textarea></td></tr>";
		table+="</tbody></table>";
		//选择下拉框
		$("#satisDiv").append(table);
		var option="<option value='1'>1</option><option value='2'>2</option><option value='3'>3</option>";
		option+="<option value='4'>4</option><option value='5'>5</option><option value='6'>6</option>";
		option+="<option value='7'>7</option><option value='8'>8</option><option selected='selected' value='9'>9</option>";
		option+="<option value='10'>10</option>";
		$('#satisWriteTble select').append(option);
		$("#satisDiv").append("<br><br><input type='button' style='margin-left:200px;' value='提交' onclick='satisfactionSub()'> ");
}



</script>
	<div id="satisDiv" ">
		
	</div>
</body>

</html>