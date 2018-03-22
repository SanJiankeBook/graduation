	var answerIdeaArr;	
	$(function(){
			// 从浏览器中取前面存的值（从localstorage或cookie中取），然后显示到页面上
			var pid= window.localStorage? localStorage.getItem("pointPaper_pid"): Cookie.read("pointPaper_pid");
			var className= window.localStorage? localStorage.getItem("pointPaper_className"): Cookie.read("pointPaper_className");
			var pdate= window.localStorage? localStorage.getItem("pointPaper_pdate"): Cookie.read("pointPaper_pdate");
			var pstatus= window.localStorage? localStorage.getItem("pointPaper_pstatus"): Cookie.read("pointPaper_pstatus");
			var subjectName= window.localStorage? localStorage.getItem("pointPaper_subjectName"): Cookie.read("pointPaper_subjectName");
			var pname= window.localStorage? localStorage.getItem("pointPaper_pname"): Cookie.read("pointPaper_pname");
			var descript= window.localStorage? localStorage.getItem("pointPaper_descript"): Cookie.read("pointPaper_descript");
			var ptitle= window.localStorage? localStorage.getItem("pointPaper_ptitle"): Cookie.read("pointPaper_ptitle");
			var subjectId= window.localStorage? localStorage.getItem("pointPaper_subjectId"): Cookie.read("pointPaper_subjectId");
			$("#className").text(className);
			$("#pdate").text(pdate);
			$("#pstatus").text(0);
			$("#subjectName").text(subjectName);
			$("#pname").text(pname);
			if(descript==""){
				descript="暂无";
			}
			$("#descript").text(descript);
			if(className!=""&&subjectName!=""){
				$.ajax({
					url:"/Examination2.0/pointPaper_findPointPaperInfoPid.action",
					type:"post",
					datatype:"json",
					data:{"pid":pid},
					success:showAllPointInfo
					});	
			}
				
			
	});
	//显示测评知识点信息
	function showAllPointInfo(data){
		if(data.responseCode==0){
		var pointInfos = data ;
		var pointInfoStr='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" ><td height="60" colspan="3"><span class="fontColor">暂无测评知识点信息</span></td></tr>';
		
			if(pointInfos.obj!=null&&pointInfos.obj.length>0){
				$("#pstatus").text(pointInfos.obj[0].answerCount);
				pointInfoStr='';
				if(pointInfos.obj!=null){
					$.each(pointInfos.obj,function(i,pointInfo){
						var index=i+1;
						pointInfoStr+='<tr id="'+index+'" bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left">';
						pointInfoStr+='<td align="center" width="10%">'+index+'</td>';
						pointInfoStr+='<td width="70%">&nbsp;'+pointInfo.pcontent+'</td>';
						pointInfoStr+='<td align="center" width="10%">'+pointInfo.sumScore+'分</td>';
						pointInfoStr+='<td align="center" width="10%">'+pointInfo.avgScore+'分</td></tr>';
					});
				}else{
					pointInfoStr='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" ><td height="60" colspan="3"><span class="fontColor">暂无测评知识点信息</span></td></tr>';
				}
				$("#pointInfoMsg").html(pointInfoStr);
				
			}else{
				$("#pointInfoMsg").html(pointInfoStr);
			}	
		}else{
			$("#pointInfoMsg").html(pointInfoStr);
		}
	}	
	
	
	