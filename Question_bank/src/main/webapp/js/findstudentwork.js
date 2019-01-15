/*function student_work_search(){
	var semester = $('input[name="semester"]:checked').val();
	var startdate = $("#startDate").val(); // 查询开始时间
	var enddate = $("#endDate").val(); // 查询结束时间
	$.post("work_findstudentWorkresult.action",{semester:semester,startdate:startdate,enddate:enddate},
			function(data){
		if(data.sss==0){
			alert('暂无数据')
			return ;
		}
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>学期</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>作业检查时间</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>作业名称</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>作业描述</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>检查情况</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>备注</td></tr>";
		var obj=data.obj;
		for(var i in obj){
			str+="<tr bgcolor='#7EA6DC' height='30'><td align='center'>"+semester+"<td>";
			str+="<td align='center'>"+semester+"<td>";
			str+="<td align='center'>"+semester+"<td>";
			str+="<td align='center'>"+semester+"<td>";
			str+="<td align='center'>"+semester+"<td>";
			str+="<td align='center'>"+semester+"<td>";
			str+="<td align='center'>"+semester+"<td></tr>";
		}
	},'json')
}*/