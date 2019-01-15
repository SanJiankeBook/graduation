<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<style type="text/css">
#col {
	background: #FF99CC;
}
</style>
<script type="text/javascript" language="javascript"
	src="../../js/jquery-1.9.1.js"></script>
	
	<script type="text/javascript" language="javascript"
	src="../../js/curri.js"></script>

<script type="text/javascript" src="../../js/dateChoose.js"></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

  <style>
 #curriFind_form table{
    width: 90%;
    float: left;
    margin-left: 5%;
    margin-top:50px;
    height: 750px;
    border: 1px solid #000000;
    text-align: center;
    margin-bottom: 20px;
}
#curriFind_form table tr{
    border: 1px solid #000000;
    height: 8%;
}
#curriFind_form table td{
    border: 1px solid #000000;
    width: 12.5%;
    font-size: 13px;
}
#curriFind_form table td p{
    text-align: center;
    line-height: 50%;
}
    </style>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>老师课表查询</title>
</head>

<body>
	<div id="teacherCurriFind_conditionDiv">
		查询日期：<input type="text" id="startDate" name="startDate"
			readonly="readonly" onclick="fPopCalendar(event,this,this)"
			onfocus="this.select()" /> 

		老师姓名：<select id="teacherCurriFind_selectSubcondition">
			<option value="-1" selected="selected">--请选择--</option>
		</select> <input type="button" onClick="teacherCurriInfo()" value="查询" />

	</div>
	<div id="curriFind_showDiv">
		 <form id="curriFind_form" action="curriFind.action" method="post">
			
		</form> 
	</div>
<script>
var allTeacherName =new Array();
//获取老师信息
$(function() {
	$.post("findAllTeacher.action", function(data) {
		if (data.responseCode == 1) {
			alert("老师查询错误,请与管理员联系");
			return;
		} else {
			
			for (var i = 0; i < data.obj.length; i++) {
				$("#teacherCurriFind_selectSubcondition").append(
						"<option value="+data.obj[i].id+">"
								+ data.obj[i].userName + "</option>");
				
				allTeacherName[i]=data.obj[i].userName;
			}
		}
	});
});

//获取   老师  课表信息
function teacherCurriInfo() {
	var startDate = $("#startDate").val();
	//处理2015-02-06 去掉0
	/*  var sd= new Date(startDate);
	 sd.setDate(sd.getDate());
	 startDate=sd.getFullYear()+"-"+(sd.getMonth()+1)+"-"+sd.getDate();//新日期  */
	 
	if(startDate==""){
		alert("请选择日期");
		return;
	}
	var endDate ;
	//startdate向后取7天
    endDate= new Date(startDate);//这里日期是传递过来的，可以自己改
    
    endDate.setDate(endDate.getDate()+6);
    
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
   
    
    var teacherid = $("#teacherCurriFind_selectSubcondition").find(
	"option:selected").val();
    
	$.post("teacherCurriFind_getAllTeacherCurriInfo.action", {
		startDate : startDate,
		endDate : endDate,
		teacherid : teacherid,
	}, function(data) {
		console.info(data);
 		 if(data.obj.length<=0){
 			alert("暂无老师有课程安排");
 		} 
		
		if (data.responseCode == 1) {
			alert("该时间段该老师 没有课程 ");
			return;
		} else {
			
			$("#curriFind_form").html("");
			gettheday();
			var id=0;//课程表 的唯一的id号
			for(var i=0;i<allTeacherName.length;i++){
				var temp=new Array();//同一老师的数据
				var flag=0;
				for(var j=0;j<data.obj.length;j++){
				
					if(data.obj[j].username==allTeacherName[i]){
						temp[flag]=data.obj[j];
						flag++;
					}
				}
				
				if(temp.length>0){
					CreateTable(temp,allTeacherName[i],id);
					id++;
				}
			}
		}
	});
}

  
</script>
</body>
</html>