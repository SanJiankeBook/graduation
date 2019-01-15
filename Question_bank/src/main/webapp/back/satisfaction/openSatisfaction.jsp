<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!--  <%@ taglib uri="http://www.hyycinfo.com" prefix="yc" %>	 --> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<title>开启满意度调查界面</title>
</head>
<body>

<label style="color:red;font-size:25px;">提示： 在每月的28号至30号必须开启.(关闭时间为开启时间后五天)</label><br><br>
<div>
	<div style="width: 200px; height: auto; border: 1px solid #ccc; float: left;">
		<ul class="easyui-tree" id="openSatisfaction_selectSubconditionSub"
			state="closed">
		</ul>
	</div>

	<div style=" font-size:13px; margin-left:30px;  width: 300px; height: auto; float: left;">
		<label style="font-size:14px;"> 班级名：</label><label style="font-size:14px;" id="openSatis_className"></label><label style="color:red;font-size:15px;">*</label><br><br>
		<label style="font-size:14px;">开启教员：</label><label id="satis_openTeacher"></label><br><br>
		<label style="font-size:14px;">待评测老师：</label><select id="openSatisfaction_selectSubcondition">
				<option value="-1" selected="selected">--请选择--</option>
			</select>
			<label style="color:red;font-size:15px;">*</label><br><br>
		<label style="font-size:14px;">评测时间：</label>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="startDate" name="startDate"
				readonly="readonly" onclick="fPopCalendar(event,this,this)"
				onfocus="this.select()" /><label style="color:red;font-size:15px;">*</label><br><br>
		<label style="font-size:14px;">教学所属年份：</label>&nbsp;&nbsp;&nbsp;&nbsp;<yc:yearSelect id="satis_year"></yc:yearSelect><br><br>
				
		<label style="font-size:14px;">教学所属月份：</label><yc:monthSelect id="satis_month"></yc:monthSelect>
		<label style="color:red;font-size:13px;">*请选择教评月份</label><br><br>
		<input name="start" style="margin-left:30px;" type="button" onClick="satis_openSatisfaction();" value="开启"> 
	</div>
</div>

</body>
<script type="text/javascript">
var id;
$(function() {

	var now = new Date();
	var startyear = getNum(now.getFullYear());
	var month=now.getMonth()+1;
	//获取当前月份和年份
	$("#satis_year").val(startyear);
	$("#satis_month").val(month);
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
	
	userName = localStorage.getItem("systemUser_userName");
	$("#satis_openTeacher").text(userName);
//班级树
	$('#openSatisfaction_selectSubconditionSub').tree('append', {
		data : [ {
			id : 'openSatisfaction_selectSubcondition',
			text : '班级'
		} ]
	});

	var nodeTemp = $('#openSatisfaction_selectSubconditionSub').tree('getRoot',
			"openSatisfaction_selectSubcondition");

	$.ajax({
		url : "findClass.action",
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			for (var i = 0; i < data.obj.length; i++) {
				$('#openSatisfaction_selectSubconditionSub').tree('append', {
					parent : nodeTemp.target,
					data : [ {
						id : data.obj[i].id,
						text : data.obj[i].className,

					} ]
				});
			}
			//折叠所有节点
			//$('#openSatisfaction_selectSubconditionSub').tree('collapseAll');
		}
	});

	$('#openSatisfaction_selectSubconditionSub').tree({
		onClick : function(node) {
			id = node.id;
			
			$("#openSatis_className").text(node.text);
		}
	});
	
	
	//获取老师信息
	$(function() {
		$.post("findAllTeacher.action", function(data) {
			if (data.responseCode == 1) {
				alert("老师查询错误,请与管理员联系");
				return;
			} else {
				for (var i = 0; i < data.obj.length; i++) {
					$("#openSatisfaction_selectSubcondition").append(
							"<option value="+data.obj[i].id+">"
									+ data.obj[i].userName + "</option>");
				}
			}
		});
	});
	
});	


//开启调查表
function satis_openSatisfaction(){
	if(id==undefined){
		alert("请选择班级");
		return;
	}
	var className=$("#openSatis_className").text();
	var teacherid=$("#openSatisfaction_selectSubcondition").find("option:selected").val();
	var teacherName=$("#openSatisfaction_selectSubcondition").find("option:selected").text();
	if(teacherid==-1){
		alert("请选择评测教师");
		return;
	}
	var openteacherName=$("#satis_openTeacher").text();
	var year=$("#satis_year").val();
	var month=$("#satis_month").find("option:selected").val();
	//处理结束时间
	var startDate = $("#startDate").val();
	 var  endDate= new Date(startDate);//这里日期是传递过来的，可以自己改
	    endDate.setDate(endDate.getDate()+5);
	    if(endDate.getDate()<10){
	    	if(endDate.getMonth()+1<10){
	    		endDate=endDate.getFullYear()+"-0"+(endDate.getMonth()+1)+"-0"+endDate.getDate();//新日期	
	    	}else{
	    		endDate=endDate.getFullYear()+"-"+(endDate.getMonth()+1)+"-0"+endDate.getDate();//新日期
	    	}
	    
	    }else{
	    	if(endDate.getMonth()+1<10){
	    		endDate=endDate.getFullYear()+"-0"+(endDate.getMonth()+1)+"-"+endDate.getDate();//新日期	
	    	}else{
	    		endDate=endDate.getFullYear()+"-"+(endDate.getMonth()+1)+"-"+endDate.getDate();//新日期
	    	}
	    }
	    
	   // alert(endDate+","+startDate+" ,"+classid+", "+teacherid+", "+year+", "+month);
		$.post("openSatisfactionAction.action", {
			className: className,
			teacherid : teacherid,
			teacherName:teacherName,
			year:year,
			month:month,
			startDate:startDate,
			endDate:endDate,
			openteacherName:openteacherName
		}, function(data) {
			if (data.responseCode == 1) {
				alert("开启调查表失败，请与管理员联系!");
				return;
			} else {
				 alert("开启调查表成功!");
			}
		});
	   
	   
}

</script>

</html>