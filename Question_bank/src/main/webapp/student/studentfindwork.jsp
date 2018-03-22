<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- <script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script> -->
<script type="text/javascript" language="javascript"
	src="../../js/jquery-1.9.1.js"></script>
<style type="text/css">

body{
background: red;
}
</style>


</head>

<body>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<style>
	#work_div1{
            width:300px;
            height:200px;
            background: #DE8336;
            position: fixed;
            z-index: 40;
            top:100px;
            left:300px;
            overflow: hidden;
            opacity: 0.5;
            display: none;
        }
	#work_div2{
            position: fixed;
            z-index: 50;
            width:300px;
            height:200px;
            top:200px;
            left:300px;
            background: #E8F2FE;
            display: none;
}
</style>
<base href="<%=basePath%>" />
	<div id="menu_table"
		style="text-align: center; border: 1px solid #F8D880; border-radius: 5px; background-color: #E8F2FE; padding-bottom: 10px; width: 90%; margin: 10px 5px 5px 30px; border: 1px solid #F8D880;">
		<table width="90%" border="0" cellpadding="0" cellspacing="0">
			<tr height="43">
				<td width="30%">学期编号：&nbsp;<input name='semester'
					id="work_semesterS1" type='radio' onClick="checkSemesters('S1')"
					value='S1' checked="checked" />S1 <input name='semester'
					id="work_semesterS2" type='radio' onClick="checkSemesters('S2')"
					value='S2' />S2 <input name='semester' id="work_semesterS3"
					type='radio' onClick="checkSemesters('S3')" value='S3' />S3
				</td>
				<td align="left" class="fontColor linefontsize"><input
					type="text" id="startDate" name="startDate" readonly="readonly"
					onclick="fPopCalendar(event,this,this)" onfocus="this.select()" />&nbsp;
					到 &nbsp;<input type="text" id="endDate" name="endDate"
					readonly="readonly" onclick="fPopCalendar(event,this,this)"
					onfocus="this.select()" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td align="left"><input onClick="student_workdetail_search()"
					type="button" value="查询" /></td>
			</tr>
		</table>
	</div>
	<br>
<div id="work_div2">
		<table  id="table_show_work_info" style="width: 80%; margin: 0 auto;margin-top:50px;"  border="0" cellspacing="0" cellpadding="0" >
		
		</table>
		<button style="margin: 0 auto; margin-left: 100px;margin-top: 20px;"  onclick="close_work_detail()">关闭</button>
</div>

	<div id="show_work_detail" style="text-align: center; background-color:#FCFCE0;width: 60%;margin: 0 auto;display: none;border: 1px solid #FCEEA0; border-radius: 5px;margin-bottom: 10px; ">
		<table style="font-size: 25px;font: bold; "  width="100%;">
			<tr >
				<td style="font-size: 20px;" width="25%" align="right" >作业总计:</td>
				<td style="font-size: 20px;" width="25%" align="left" id="show_workcount_detail"></td>
				<td style="font-size: 20px;" width="20%" align="right">完成次数:</td>
				<td style="font-size: 20px;" width="30%" align="left" id="show_checkcount_detail"></td>
			</tr>
		</table>
	</div>
	<table id="show_student_workdetail" width="90%" border="1"
		style="margin-left: 30px;" cellpadding="0" cellspacing="0">
		<tr bgcolor='#E8F2FE'>
			<td bordercolor='#C9F9FD' align="center">作业名称</td>
			<td bordercolor='#C9F9FD' align="center">作业检查时间</td>
			<td bordercolor='#C9F9FD' align="center">布置教师</td>
			<td bordercolor='#C9F9FD' align="center">作业描述</td>
			<td bordercolor='#C9F9FD' align="center">检查情况</td>
			<td bordercolor='#C9F9FD' align="center">备注</td>
			<td bordercolor='#C9F9FD' align="center">操作</td>
		</tr>
	</table>
	<script type="text/javascript">
function student_workdetail_search(){
			var semester = $('input[name="semester"]:checked').val();
			var startdate = $("#startDate").val(); // 查询开始时间
			var enddate = $("#endDate").val(); // 查询结束时间
			$
					.post( "work_findstudentWorkresult.action",
							{
								semester : semester,
								startdate : startdate,
								enddate : enddate
							},
							function(data) {
								
								var str = "";
								str += "<tr bgcolor='#E8F2FE' height='30'>";
								str += "<td align='center' bordercolor='#C9F9FD'>作业检查时间</td>";
								str += "<td align='center' bordercolor='#C9F9FD'>作业名称</td>";
								str += "<td align='center' bordercolor='#C9F9FD'>布置教师</td>";
								str += "<td align='center' bordercolor='#C9F9FD'>作业描述</td>";
								str += "<td align='center' bordercolor='#C9F9FD'>检查情况</td>";
								str+="<td align='center' bordercolor='#C9F9FD'>操作</td></tr>";
								if (data.responseCode == 1) {
									str+="<tr height='30'><td align='center' colspan='7'>暂无数据</td></tr>"
									$("#show_student_workdetail").html(str);
									$("#show_work_detail").css("display","none");
									return;
								}
								$("#show_work_detail").css("display","block");
								var obj = data.obj;
								var worknum=0
								var checknum=0
								for ( var i in obj) {
									if (obj[i].wname == undefined) {
										continue;
									}
									worknum++;
									if (obj[i].result == "false") {
										str += "<tr bgcolor='#FA433E' >"
									}else if(obj[i].result == "true"){
										checknum++
										str += "<tr   onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='white';  >"
									}else{
										str += "<tr bgcolor='#E3D1F5' >"
									}
									str += "<td align='center'>"
											+ change(obj[i].checkdate)
											+ "</td>";
									str += "<td align='center'>" + obj[i].wname
											+ "</td>";											
									str += "<td align='center'>"
											+ obj[i].checkcreator + "</td>";
									str += "<td align='center'>"
											+ changenull(obj[i].description)
											+ "</td>";
									str += "<td align='center'>"
											+ changecheck(obj[i].result)
											+ "</td>";
									str += "<td align='center'>"
										+"<a href='javascript:find_detail_work("+obj[i].wid+","+obj[i].result+")'>查看详情(暂未实现)</a>"
										+ "</td></tr>";
								}
								$("#show_checkcount_detail").html(checknum)
								$("#show_workcount_detail").html(worknum)
								$("#show_student_workdetail").html(str);

							}, 'json')
		}
		
		function changeresult(data){
			if (data == "null") {
				return "未检查"
			}
			if (data == "false") {
				return "未完成"
			}
			if (data == "true") {
				return "已完成"
			}
			return "发生错误";
			
		}
		
		function find_detail_work(wid,result){
			$.post("work_findWorkbyid.action",{wid:wid},function(data){
				if (data.responseCode == 1) {
					alert(data.errorMessage);
					return;
				}
				data = data.obj;
				
				var str="";
				str+='<tr height="23"><td align="right">作业描述:</td><td align="left">'+data.description+'</td></tr>';
				str+='<tr height="23"><td align="right">作业名称:</td><td align="left">'+data.wname+'</td></tr>';
				str+='<tr height="23"><td align="right">布置教员:</td><td align="left">'+data.checkcreator+'</td></tr>';
				str+='<tr ><td align="right">完成情况:</td><td align="left">'+changeresult(result)+'</td></tr>';
				$("#table_show_work_info").html(str)
				$("#work_div1").show(1000);
	            $("#work_div2").show(1000);
			},'json')
		}

		function changenull(data) {
			if (data == null || data == "") {
				return "无";
			}
			return data
		}
		
		function close_work_detail(){
			$("#work_div1").hide(1000);
            $("#work_div2").hide(1000);
		}	

		function changecheck(data) {
			if (data == "null") {
				return "<img src='student/img/check.jpg' alt='未检查' width='20px' height='20px'/>"
			}
			if (data == "false") {
				return "<img src='student/img/false.jpg' alt='未完成' width='20px' height='20px'/>"
			}
			if (data == "true") {
				return "<img src='student/img/true.jpg' alt='已完成'  width='20px' height='20px'/>"
			}
			return "发生错误";
		}

		function change(data) {
			if (data != null)
				time = data.replace(/\s(\w|:)*/, "");
			return time;
		}
	</script>
</body>

</html>