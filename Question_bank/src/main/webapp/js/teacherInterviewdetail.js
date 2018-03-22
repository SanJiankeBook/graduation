var year;
var month
$(function(){
	finddata_tescherInterview();
})


function find_teacherInterviewdetail(teachername){
	//$("#page_teacherInterview_show").html("");
	$("#showInterviewRecord").hide();
	$("#showteacher_workdetail").html("");
	$.post("examinInterviewRecord_getTeacherInterviewByName.action",{teachername:teachername,year:year,month:month},
			function(data){
		if(data.responseCode==1){
			var str="";
			str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>学生名字</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>学生班级</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>地点</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>日期</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";
			str+='<tr><td colspan="5" align="center">暂无数据</td></tr>'
			$("#showteacher_workdetail").append(str);
			return 
		}
		var obj=data.obj;
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>学生名字</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>学生班级</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>地点</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>日期</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";;
		for(var i in obj){
			if(obj[i].className==undefined){
				continue;
			}
			str+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" height="30"><td align="center" >'+obj[i].studentName+'</td>';
			str+='<td align="center">'+obj[i].className+'</td>';
			str+='<td align="center">'+obj[i].interviewAddress+'</td>';
			str+='<td align="center">'+change(obj[i].pdate)+'</td>';
			str+='<td align="center"> <a  href="javascript:void(0)" onClick="gradeThisPaper(\''
				+ obj[i].id
				+ '\')" title="查看详情">详情</a>&nbsp; </td></tr>';
		}
		$("#showteacher_workdetail").append(str);
		$("#page_teacherInterview_show").css("display","block");
	},'json')
}

function change(data) {
	if(data!=null )
	time = data.replace(/\s(\w|:)*/, "");
	return time;
}
function finddata_tescherInterview(){
	$("#showteacherInterview_pool").html("");
	$("#showteacher_workdetail").html("");
	year=$("#Interview_year").val();
	month=$("#Interview_month").val();
	$.post("examinInterviewRecord_getTeacherInterview.action",{year:year,month:month},
			function(data){
		if(data.responseCode==1){
			str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>教师姓名</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>访谈人数</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";
			str+='<tr><td colspan="5" align="center">暂无数据</td></tr>'
			$("#showteacherInterview_pool").append(str);
			return 
		}
		var obj=data.obj;
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>教师姓名</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>访谈人数</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";
		for(var i in obj){
			if(obj[i].teacherName==	undefined){
				continue;
			}
			str+='<tr height="30"><td align="center">'+obj[i].teacherName+'</td>'
			str+='<td align="center">'+obj[i].num+'</td>'
			str+='<td align="center"><input type="button" value="查看详情" onclick="find_teacherInterviewdetail(\''+obj[i].teacherName+'\')"/></td></tr>'
		}
		$("#showteacherInterview_pool").append(str);
	},'json')
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