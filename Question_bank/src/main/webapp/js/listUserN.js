//根据所有的角色种类	
 	$(function(){
 		$.ajax({
				url:"/Examination2.0/systemUser_findAuthorities.action",
				type:"post",
				datatype:"json",
				success:showAllAuthorities
			});
 	});
 	//显示角色下拉框
 	function showAllAuthorities(authoritiesInfos){
 		//var authoritiesInfos= eval("("+data+")");
		var optionstr="<option value='--请选择--'>--请选择--</option>";
		if(authoritiesInfos.responseCode==0){
			if(authoritiesInfos.obj!=null){
				$.each(authoritiesInfos.obj, function(i,authoritiesInfo){
					optionstr+="<option value='"+authoritiesInfo[1]+"' name='authorities'>"+authoritiesInfo[1]+"</option>";	  														
				});	
			}	
 		}
		$("#authorities").html(optionstr);
 	}
 	
 	//移到焦点除变颜色
 	function overChangeColor(myColor,myId){
 			$("#"+myId).mouseover(
 					function(){
 						$("#"+myId).css("color",myColor);
 					}
 				);
 	}
 	//离开焦点除变颜色
 	function outChangeColor(myColor,myId){
 			$("#"+myId).mouseout(
 					function(){
 						$("#"+myId).css("color",myColor);
 					}
 				);
 	}
 	//首页，上一页，下一页，尾页，点击时触发该函数
 	function skipToPageNum(status){
 		
 		//总页数
 		var totalPage=parseInt($.trim($("#tabTotalPage").text()));
 			//alert(totalPage);
 			if(totalPage==""){
 				displayRows=0;
 			}
 		//每页显示几条	
 		var displayRows=$.trim($("#displayRows").val());
 			if(displayRows==""){
 				displayRows=10;
 				$("#displayRows").val(displayRows);
 			}
 		//第几页		
 		var pageNume=$.trim($("#pageNume").val());
 			if(pageNume==""){
 				pageNume=1;
 				$("#pageNume").val(pageNume);
 			}else if(pageNume==0){
 				pageNume=1;
 			}
 		//当前第几页	
 		var currentNume=$.trim($("#tabCurrentPage").text());
 			if(currentNume==""){
 				currentNume=1;
 				$("#tabCurrentPage").val(currentNume);
 			}else if(currentNume==0){
 				currentNume=1;
 			}	
 		//权限编号
 		var remark=$.trim($("#authorities").val());
 			if(remark=="--请选择--"){
 				remark="";
 			}
 		if(status=="up"){
 			if(currentNume==1){
 				pageNume=currentNume;
 				return;
 			}else{
 				//$("#upPage").animate({color:'red'});
 				pageNume--;
 				$("#pageNume").val(pageNume);
 			}
 		}else if(status=="down"){
 			if(currentNume==totalPage||currentNume+""==totalPage+""){
 				pageNume=currentNume;
 				return;
 			}else{
 				//$("#downPage").animate({color:'red'});
 				pageNume++;
 				$("#pageNume").val(pageNume);
 			}
 		}else if(status=="first"){
 			if(currentNume==1){
 				pageNume=currentNume;
 				return;
 			}else{
 				//overChangeColor("firstPage","blue")
 				pageNume=1;
 				$("#pageNume").val(pageNume);
 			}
 		}else if(status=="last"){
 			if(currentNume==totalPage){
 				pageNume=currentNume;
 				return;
 			}else{
 				//overChangeColor("lastPage","blue")
 				pageNume=totalPage;
 				$("#pageNume").val(pageNume);
 			}
 		}	
 		if(pageNume>=1&&pageNume<=totalPage){
 			$.ajax({
 				url:"/Examination2.0/systemUser_showSystemUserPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"remark":remark},
 				success:showPageInfo
 			});
 		}
 				
 	}
 	//点击搜索和GO时触发此函数
 	$(function()
 	{
 		$("#searchGo").click(function()
 		{	
 			//总页数
 			var totalPage=parseInt($.trim($("#tabTotalPage").text()));
 			if(totalPage==""){
 				displayRows=0;
 			}
 			//每页显示几条
 			var displayRows=$.trim($("#displayRows").val());
 			if(displayRows==""){
 				displayRows=10;
 				$("#displayRows").val(displayRows);
 			}
 			//第几页   当$("#pageNume").val()>1时此处会导致点击#search时上传的页数不对
 			var pageNume=$.trim($("#pageNume").val());
 			if(pageNume==""){
 				pageNume=1;
 				$("#pageNume").val(pageNume);
 			}else if(pageNume==0){
 				pageNume=1;
 			}else if(pageNume>=totalPage){
 				pageNume=totalPage;
 				$("#pageNume").val(totalPage);
 			}
 			//权限编号
 	 		var remark=$.trim($("#authorities").val());
 	 			if(remark=="--请选择--"){
 	 				remark="";
 	 			}
 			$.ajax({
 				url:"/Examination2.0/systemUser_showSystemUserPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"remark":remark},
 				success:showPageInfo
 			});
 		});
 		
 		
 		$("#search").click(function()
 		 		{	
 		 			//总页数
 		 			var totalPage=parseInt($.trim($("#tabTotalPage").text()));
 		 			if(totalPage==""){
 		 				displayRows=0;
 		 			}
 		 			//每页显示几条
 		 			var displayRows=$.trim($("#displayRows").val());
 		 			if(displayRows==""){
 		 				displayRows=10;
 		 				$("#displayRows").val(displayRows);
 		 			}
 		 			//第几页   当$("#pageNume").val()>1时此处会导致点击#search时上传的页数不对
 		 			var pageNume=1;
 		 			$("#pageNume").val("1")
 		 			/*if(pageNume==""){
 		 				pageNume=1;
 		 				$("#pageNume").val(pageNume);
 		 			}else if(pageNume==0){
 		 				pageNume=1;
 		 			}else if(pageNume>=totalPage){
 		 				pageNume=totalPage;
 		 				$("#pageNume").val(totalPage);
 		 			}*/
 		 			//权限编号
 		 	 		var remark=$.trim($("#authorities").val());
 		 	 			if(remark=="--请选择--"){
 		 	 				remark="";
 		 	 			}
 		 			$.ajax({
 		 				url:"/Examination2.0/systemUser_showSystemUserPages.action",
 		 				type:"post",
 		 				datatype:"json",
 		 				data:{"displayRows":displayRows,"pageNume":pageNume,"remark":remark},
 		 				success:showPageInfo
 		 			});
 		 		});
 	});
 	//拼页面
 	function showPageInfo(pageinfos)
 	{		
 					//var pageinfos = eval("(" + data + ")");	
 					var pageStr="";
 					pageStr=pageStr+'<tr><th bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">用户名</th><th bordercolor="#7EA6DC">角色</th></tr>';
 					if(pageinfos.responseCode==0){
 						var pageSize=pageinfos.obj.pageSize;
 						var currentPage=pageinfos.obj.currentPage;
 						var totalsCount=pageinfos.obj.totalsCount;
 						var totalsPage=pageinfos.obj.totalsPage;
 						//alert("pagesize:"+pageSize+"\t"+"totalspage:"+totalsPage);				
 						$.each(pageinfos.obj.result, function(i,pageinfo){
 						//alert(pageinfo.examSubject);
 							pageStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" id="'+pageinfo.id+'">';
 							pageStr+='<td align="left">'+pageinfo.id+'</td>';
 							pageStr+='<td align="left">'+pageinfo.userName+'</td>';
 							pageStr+='<td align="center">'+pageinfo.remark+'</td>';
 							
 						});	
 					}	
 					if(totalsCount!=0){
 						$("#strPrompt").html("查出"+totalsCount+"记录");
 					}else{
 						$("#strPrompt").html("没此记录");
 					}
 					$("#tabCurrentPage").html(currentPage);
 					$("#tabTotalPage").html(totalsPage);
 					$("#tbPapershowInfo").html(pageStr);	
 					
 	}	
 		//把id存到localstorage或cookie中
 		function toShowWritingPaperPage(wpid){
 			if (window.localStorage) {
 	   			 localStorage.setItem("wpid", wpid);
 			} else {
 	    		 Cookie.write("wpid", wpid);	
 			}
 			window.location.href='/Examination2.0/Jsp/backoperation/showwritingpaper.html';
 		}
