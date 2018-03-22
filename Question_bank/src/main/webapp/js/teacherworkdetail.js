var year;
var month
$(function(){
	finddata_tescherworkpool();
	
	
})



function find_teacherdetail(teachername){
	$.post("work_findteacherWorkdetail.action",{teachername:teachername,year:year,month:month},
			function(data){
		
		var obj=data.obj;
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>布置班级</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>作业名称</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>班级人数</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>完成人数</td></tr>";
		if(data.responseCode==1){
			str+="<tr height='30'><td align='center' colspan='4'> 暂无数据</td></tr>";
			$("#showteacher_workdetail").html(str);
			return 
		}
		for(var i in obj){
			if(obj[i].workname==undefined){
				continue;
			}
			var checkcount=obj[i].checkcount;
			if(obj[i].checkcount==undefined){
				checkcount=0;
			}
			str+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" height="30"><td align="center" >'+obj[i].className+'</td>';
			str+='<td align="center">'+obj[i].workname+'</td>';
			str+='<td align="center">'+obj[i].classcount+'</td>';
			str+='<td align="center">'+checkcount+'</td></tr>';
		}
		$("#showteacher_workdetail").html(str);
		$("#page_teacher_show").css("display","block");
	},'json')
}

function finddata_tescherworkpool(){
	year=$("#classwork_year").val();
	month=$("#classwork_month").val();
	$("#page_teacher_show").css("display","none");
	$.post("work_findteacherWorkpool.action",{year:year,month:month},
			function(data){
		$("#showteacher_pool").html("")
		var obj=data.obj;
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>教师姓名</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>布置作业次数</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>操作</td></tr>";
		if(data.responseCode==1){
			str+="<tr height='30'><td align='center' colspan='4'> 暂无数据</td></tr>";
			$("#showteacher_pool").html(str);
			return 
		}
		for(var i in obj){
			if(obj[i].checkcount==	undefined){
				continue;
			}
			str+='<tr height="30"><td align="center">'+obj[i].checkcreator+'</td>'
			str+='<td align="center">'+obj[i].checkcount+'</td>'
			str+='<td align="center"><input type="button" value="查看详情" onclick="find_teacherdetail(\''+obj[i].checkcreator+'\')"/></td></tr>'
		}
		$("#showteacher_pool").append(str);
	},'json')
	
}