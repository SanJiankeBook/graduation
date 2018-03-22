<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" language="javascript"
	src="../../js/jquery-1.9.1.js"></script>
<!-- 	<link rel="stylesheet" type="text/css" href="../../css/datedropper.css">
	<script type="text/javascript"  src="../../js/datedropper.min.js"></script> -->
	 
<script type="text/javascript" src="../../js/dateChoose.js"></script>
<style type="text/css">
#col {
	background: #FF99CC;
}
</style>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

 <style>
      #classroomCurriFind_form table{
    width: 90%;
    float: left;
    margin-left: 5%;
    margin-top:50px;
     height: 850px;
    border: 1px solid #000000;
    text-align: center;
    margin-bottom: 20px;
}
#classroomCurriFind_form table tr{
    border: 1px solid #000000;
    height: 8%;
}
#classroomCurriFind_form table td{
    border: 1px solid #000000;
    width: 12.5%;
    font-size: 13px;
}
#classroomCurriFind_form table td p{
    text-align: center;
    line-height: 50%;
}
    </style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室课表查询</title>
</head>
<body>
<div id="classroomCurriFind_conditionDiv" >
	日期：<input type="text" id="startDate" name="startDate1" readonly="readonly" onclick="fPopCalendar(event,this,this)"onfocus="this.select()" />
		<input style="visibility:hidden" type="text" id="endDate" name="endDate" readonly="readonly" disabled="disabled" onclick="fPopCalendar(event,this,this)" onfocus="this.select()" />
			
              <!--   <input type="text" class="input" id="pickdate"  placeholder="请输入日期"  style="padding-left:5px;color: #fff;"/> -->
	教室：<select id="classroomCurriFind_selectSubcondition" >
		<option value="-1"  selected="selected">--请选择--</option>
		</select> 
		
	<input type="button"  onClick="classroomCurriInfo()" value="查询" /> 

	
</div>
<div id="classroomCurriFind_showDiv" >
	<form id="classroomCurriFind_form"  action="classroomCurriFind.action" method="post">
		
	</form>
</div>

</body>
 
 
<script type="text/javascript">
 
var allClassRoomName =new Array();
$(function(){
	$.getJSON("findAllClassroom.action", function(data) {
		 if(data.responseCode==1){ 
			alert("教室查询错误,请与管理员联系");
			return;
		}else{ 
			 for(var i=0;i<data.obj.length;i++){
					$("#classroomCurriFind_selectSubcondition").append("<option value="+data.obj[i].classroomid+">"+data.obj[i].classroomname+"</option>");
					allClassRoomName[i]=data.obj[i].classroomname;
			 } 
			 
			}
	});
});

function classroomCurriInfo(){
	var startDate = $("#startDate").val();
	/* //处理2015-02-06 去掉0
	 var sd= new Date(startDate);
	 sd.setDate(sd.getDate());
	 startDate=sd.getFullYear()+"-"+(sd.getMonth()+1)+"-"+sd.getDate();//新日期  */
	if(startDate==""){
		alert("请选择日期");
		return;
	}
	var endDate ;
	var classroomid=$("#classroomCurriFind_selectSubcondition").find("option:selected").val();
	
	var date;
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
	
	 $.post("classroomCurriFind_getAllTeacherCurriInfo.action", {
		 startDate : startDate,
		 endDate:endDate,
		 classroomid:classroomid,
 	}, function(data) {
 		if(data.obj.length<=0){
 			alert("暂无教室被使用");
 		}
 		 if(data.responseCode==1){ 
				alert("查询有误，请与管理员联系");
				return;
			}else{				
				$("#classroomCurriFind_form").html("");
				
				gettheday();
				var id=0;//课程表 的id号
				for(var i=0;i<allClassRoomName.length;i++){
					var temp=new Array();//同一老师的数据
					var flag=0;
					for(var j=0;j<data.obj.length;j++){
						if(data.obj[j].classroomname==allClassRoomName[i]){
							temp[flag]=data.obj[j];
							flag++;
						}
					}
					if(temp.length>0){
						CreateTable(allClassRoomName[i],id);
						makea(temp,allClassRoomName[i],id);	
						id++;
					}
				}
				
			}
 	});

}


var a=[];//所有满足条件的数组
var obj=[];//满足坐标的数组
var d1,d2,d3,d4,d5,d6,d7,d;

function gettheday() {
    d1=$("#startDate").val();
    d= new Date(d1);//这里日期是传递过来的，可以自己改
    d.setDate(d.getDate());
    d1=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
    d.setDate(d.getDate() + 1);//天数+1
    d2=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
    d.setDate(d.getDate() + 1);//天数+1
    d3=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d4=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d5=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d6=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d7=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
}


//得到相对应的老师的纪录
function makea(arr,teachername,id) {
   	 for(var i=0;i<arr.length;i++){
   		if(arr[i].classroomname==teachername){
   			var ll=[];
   			for(var j in arr[i]){
   				ll.push(arr[i][j]);
   			}
               a.push(ll);
   		}
    }
   	// console.info(a);
    for(var i=0;i<a.length;i++){
        obj=maketable(a[i]);
        if(a[i][1]=='无'){
        	a[i][1]='';
        }
        if(a[i][3]=='无'){
        	a[i][3]='';
        }
        if(a[i][9]=='无'){
        	a[i][9]='';
        }
        
        $('#'+id+'').find("tr").eq(obj.a).find("td").eq(obj.b).append(a[i][1]+"<br/>"+a[i][3]+"<br/>"+a[i][9]).css({"background-color": a[i][0],"color":a[i][6]});
    }
    a=[];
}
//得到每个的坐标
function maketable(arr) {
    var a,b;
    for(var i=0;i<arr.length;i++){
        var time=arr[8];
        switch (time){
            case "8:30-10:30":
                a=1;
                break;
            case "10:30-12:00":
                a=2;
                break;
            case "14:00-15:30":
                a=3;
                break;
            case "15:30-17:30":
                a=4;
                break;
            case "19:00-20:30":
                a=5;
                break;
            case "20:30-22:00":
                a=6;
                break;

        }
        var date=arr[5];
       
        switch (date){
            case d1:
                b=1;
                break;
            case d2:
                b=2;
                break;
            case d3:
                b=3;
                break;
            case d4:
                b=4;
                break;
            case d5:
                b=5;
                break;
            case d6:
                b=6;
                break;
            case d7:
                b=7;
                break;
        }
    }
    return {a:a,b:b};
}


//动态生成表格
function CreateTable(teachername,id)
{
 var table=$("<table cellpadding='0' cellspacing='0'  id="+id+"><tbody>");
    table.appendTo($("#classroomCurriFind_form"));
    var tr1=$("<tr><td id='col'>"+teachername+"</td> <td id='col'>"+d1+"</td> <td id='col'>"+d2+"</td> <td id='col'>"+d3+"</td> <td id='col'>"+d4+"</td> <td id='col'>"+d5+"</td> <td id='col'>"+d6+"</td> <td id='col'>"+d7+"</td></tr>");
    var tr2=$("<tr><td id='col'>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr3=$("<tr><td id='col'>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr4=$("<tr><td id='col'>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr5=$("<tr><td id='col'>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr6=$("<tr><td id='col'>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr7=$("<tr><td id='col'>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    tr1.appendTo(table);tr2.appendTo(table);tr3.appendTo(table);tr4.appendTo(table);tr5.appendTo(table);tr6.appendTo(table);tr7.appendTo(table);
    $("#classroomCurriFind_form").append("</tbody></table>");
}


	
</script>
</html>