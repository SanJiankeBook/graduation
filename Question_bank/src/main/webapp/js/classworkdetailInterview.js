var year;
var month
$(function(){
	year=$("#classwork_year").val();
	month=$("#classwork_month").val();
	$.post("examinInterviewRecord_findClassWorkpool.action",{year:year,month:month},
			function(data){
		if(data.responseCode==1){
			var str="";
			str+='<tr height="30"><td align="center">编号</td>'
			str+='<td align="center">班级名</td>'
			str+='<td align="center" >班级人数</td>'
			str+='<td align="center">访谈人次</td>'
			str+='<td align="center">访谈率</td>'
			str+='<td align="center">操作</td></tr>'
			str+='<tr><td colspan="6" align="center">暂无数据</td></tr>'
				$("#showclass_pool").html(str);
			return 
		}
		$("#showclass_pool").html("");
		var obj=data.obj;
		var str="";
		str+='<tr height="30"><td align="center">编号</td>'
		str+='<td align="center">班级名</td>'
		str+='<td align="center" >班级人数</td>'
		str+='<td align="center">访谈人次</td>'
		str+='<td align="center">访谈率</td>'
		str+='<td align="center">操作</td></tr>'
		for(var i in obj){
			if(obj[i].examineeclassid==	undefined){
				continue;
			}
			str+='<tr height="30"><td align="center">'+obj[i].examineeclassid+'</td>'
			str+='<td align="center">'+obj[i].className+'</td>'
			str+='<td align="center" >'+obj[i].classcount+'</td>'
			str+='<td align="center">'+obj[i].checkcount+'</td>'
			str+='<td align="center">'+obj[i].completionrate+'</td>'
			str+='<td align="center"><input type="button"  onclick="show_classworkdetail('+obj[i].examineeclassid+')" value="查看详情"/></td></tr>'
		}
		$("#showclass_pool").append(str)
	},'json')
})

function show_classworkdetail( classid){
	$("#showInterviewRecord").hide();
	$("#showclass_workdetail").html("");
	$.post("examinInterviewRecord_findClassWorkdetail.action",{classid:classid,year:year,month:month},
			function(data){
		if(data.responseCode==1){
			var str="";
			str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>访谈老师</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>访谈时间</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>访谈学生</td>";
			str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";
			str+='<tr><td colspan="4" align="center">暂无数据</td></tr>'
				$("#showclass_pool").append(str)
			return ;
		}
		var obj=data.obj;
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>访谈老师</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>访谈时间</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>访谈学生</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";
		for(var i in obj){
			if(obj[i].teacherName!=null){
			str+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" height="30"><td align="center" >'+obj[i].teacherName+'</td>';
			str+='<td align="center">'+change(obj[i].pdate)+'</td>';
			str+='<td align="center">'+obj[i].studentName+'</td>';
			str+='<td align="center"> <a  href="javascript:void(0)" onClick="gradeThisPaper(\''
				+ obj[i].id
				+ '\')" title="查看详情">详情</a>&nbsp; </td>';
			}
		}
		$("#showclass_workdetail").append(str);
		$("#page_show").html(""+$("#page_show").html());
		$("#page_show").css("display","block");
	},'json')
}

function change(data) {
	if(data!=null )
	time = data.replace(/\s(\w|:)*/, "");
	return time;
}


function finddata_workpool(){
	$("#page_show").css("display","none");
	year=$("#classwork_year").val();
	month=$("#classwork_month").val();
	$("#showInterviewRecord").hide();
	$.post("examinInterviewRecord_findClassWorkpool.action",{year:year,month:month},
			function(data){
		if(data.responseCode==1){
			$("#showclass_pool").html("");
			var str="";
			str+='<tr height="30"><td align="center">编号</td>'
			str+='<td align="center">班级名</td>'
			str+='<td align="center" >班级人数</td>'
			str+='<td align="center">访谈人次</td>'
			str+='<td align="center">操作</td></tr>'
			str+='<tr><td colspan="5" align="center">暂无数据</td></tr>'
				$("#showclass_pool").append(str)
				
			return ;
		}
		$("#showclass_pool").html("");
		var obj=data.obj;
		var str="";
		str+='<tr height="30"><td align="center">编号</td>'
		str+='<td align="center">班级名</td>'
		str+='<td align="center" >班级人数</td>'
		str+='<td align="center">访谈人次</td>'
		str+='<td align="center">操作</td></tr>'
		for(var i in obj){
			if(obj[i].examineeclassid==	undefined){
				continue;
			}
			str+='<tr height="30"><td align="center">'+obj[i].examineeclassid+'</td>'
			str+='<td align="center">'+obj[i].className+'</td>'
			str+='<td align="center" >'+obj[i].classcount+'</td>'
			str+='<td align="center">'+obj[i].checkcount+'</td>'
			str+='<td align="center"><input type="button"  onclick="show_classworkdetail('+obj[i].examineeclassid+')" value="查看详情"/></td></tr>'
		}
		$("#showclass_pool").append(str)
	},'json')
};
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
