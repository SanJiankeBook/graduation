var year;
var month
$(function(){
	finddata_workpool()
})

function show_classworkdetail(classid){
	$.post("work_findClassWorkdetail.action",{classid:classid,year:year,month:month},
			function(data){

		var obj=data.obj;
		var str="";
		str+="<tr bgcolor='#7EA6DC' height='30'><td align='center' bordercolor='#7EA6DC'>布置老师</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>布置时间</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>作业时间</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>班级人数</td>";
		str+="<td align='center' bordercolor='#7EA6DC'>完成人数</td></tr>";
		if(data.responseCode==1){
			str+="<tr ><td align='center' colspan='5'> 暂无数据</td></tr>";
			$("#showclass_workdetail").html(str);
			return 
		}
		for(var i in obj){
			if(obj[i].checkcreator==undefined){
				continue;
			}
			var checkcount=obj[i].checkcount;
			if(obj[i].checkcount==undefined){
				checkcount=0;
			}
			str+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" height="30"><td align="center" >'+obj[i].checkcreator+'</td>';
			str+='<td align="center">'+change(obj[i].checkdate)+'</td>';
			str+='<td align="center">'+obj[i].wname+'</td>';
			str+='<td align="center">'+obj[i].classcount+'</td>';
			str+='<td align="center">'+checkcount+'</td></tr>';
		}
		$("#showclass_workdetail").html(str);
		$("#page_show").css("display","block");
	},'json')
}

function change(data) {
	//2017-07-29 00:00:00
	time = data.replace(/\s(\w|:)*/, "");
	//time=time.replace(/\s$/,"");
	return time;
}


function finddata_workpool(){
	$("#page_show").css("display","none");
	year=$("#classwork_year").val();
	month=$("#classwork_month").val();
	$.post("work_findClassWorkpool.action",{year:year,month:month},
			function(data){
		$("#showclass_pool").html("")

		var obj=data.obj;
		var str="";
		str+='<tr height="30"><td align="center">编号</td>'
			str+='<td align="center">班级名</td>'
				str+='<td align="center">布置作业次数</td>'
					str+='<td align="center" >班级人数</td>'
						str+='<td align="center">检查人次</td>'
							str+='<td align="center">完成率</td>'
								str+='<td align="center">操作</td></tr>'
									if(data.responseCode==1){
										str+="<tr height='30'><td align='center' colspan='7'> 暂无数据</td></tr>";
										$("#showclass_pool").html(str)
										return 
									}														
		for(var i in obj){
			if(obj[i].examineeclassid==	undefined){
				continue;
			}
			str+='<tr height="30"><td align="center">'+obj[i].examineeclassid+'</td>'
			str+='<td align="center">'+obj[i].className+'</td>'
			str+='<td align="center">'+obj[i].workcount+'</td>'
			str+='<td align="center" >'+obj[i].classcount+'</td>'
			str+='<td align="center">'+obj[i].checkcount+'</td>'
			str+='<td align="center">'+obj[i].completionrate+' %</td>'
			str+='<td align="center"><input type="button"  onclick="show_classworkdetail('+obj[i].examineeclassid+')" value="查看详情"/></td></tr>'
		}
		$("#showclass_pool").append(str)
	},'json')
};
