//根据班级id来显示所有的学生姓名
function showExamineeNames(className){
		//alert("className:"+className);
		var className=$.trim(className);
			$.ajax({
				url:"/Examination2.0/examineeclass_showExamineeList.action",
				type:"post",
				datatype:"json",
				data:{"className":className},
				success:showExamineeNameOption
			});
		
		
};
function showExamineeNameOption(data){
		var classNameInfos =   data ;
		var optionstr="<option>--请选择--</option>";
			for(var i=0;i<classNameInfos.length;i++){
				optionstr+="<option value='"+classNameInfos[i].name+"' name='stuName'>"+classNameInfos[i].name+"</option>";	  
			}
		$("#examineeName").html(optionstr);
}
//根据id删除试卷
 	function deleteWritingAnswerById(paid){
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
			//第几页
			var pageNume=$.trim($("#pageNume").val());
			if(pageNume==""){
				pageNume=1;
				$("#pageNume").val(pageNume);
			}else if(pageNume==0){
				pageNume=1;
			}
			//班级名称	
			var examClassName=$.trim($("#examClassName").val());
			if(examClassName=="--请选择--"){
				examClassName="";
			}
			//机试卷还是笔试卷
			var examinationType=$.trim($("input[name='examinationType']:checked").val());
			//取试卷编号的值
			var paperId=$.trim($("#paperId").val());
			//取数据编号的值
			var paid=$.trim(paid);
			//取学生的姓名
			var stuName=$.trim($("#examineeName").val());
 			if(stuName=="--请选择--"){
 				stuName="";
 				$("#showMsgOfExamClassName").html("请选一项!");
 			}else{
 				$("#showMsgOfExamClassName").html("");
 			}
 			if(stuName!=""){
				$.ajax({
					url:"/Examination2.0/dataarraylist_showAnswerAfterDelete.action",
					type:"post",
					datatype:"json",
					data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName,"paid":paid,"paperId":paperId,"examinationType":examinationType,"stuName":stuName},
					success:showPageInfo
				});
 			}
 	}
//根据学期显示班级 	
	function createSelectOption(s){
		$("#examineeName").html("<option>--请选择--</option>");
 		var selectedvalue=s;
 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":selectedvalue}, function(data){
				var examineeClassList= eval("("+data+")");
				var optionstr="<option>--请选择--</option>";	
				if(examineeClassList!=null){
					$.each(examineeClassList, function(i,examineeClass){
  					optionstr+="<option value='"+examineeClass.className+"' name='className'>"+examineeClass.className+"</option>";	  														
					});	
				}																
				$("#examClassName").html(optionstr);
  				});  				  		
 	}
 	$(function(){
 		var selectedvalue="S1";
 		$("#examineeName").html("<option>--请选择--</option>");
 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":selectedvalue}, function(data){
				var examineeClassList= eval("("+data+")");
				var optionstr="<option>--请选择--</option>";																
				if(examineeClassList!=null){
					$.each(examineeClassList, function(i,examineeClass){
  					optionstr+="<option value='"+examineeClass.className+"' name='className'>"+examineeClass.className+"</option>";	  														
					});	
				}			
				$("#examClassName").html(optionstr);
  				});  			
 	});
 	
 	
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
 		//班级名称	
 		var examClassName=$.trim($("#examClassName").val());
 			if(examClassName=="--请选择--"){
 				examClassName="";
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
 			//机试卷还是笔试卷
			var examinationType=$.trim($("input[name='examinationType']:checked").val());
			//取数据编号的值
			var paperId=$.trim($("#paperId").val());
			//取学生姓名
			var stuName=$.trim($("#examineeName").val());
 			if(stuName=="--请选择--"){
 				stuName="";
 				$("#showMsgOfExamClassName").html("请选一项!");
 			}else{
 				$("#showMsgOfExamClassName").html("");
 			}
 		if(pageNume>=1&&pageNume<=totalPage&&stuName!=""){
 			$.ajax({
 				url:"/WebExamination/dataarraylist_showPaperPagesByName.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName,"paperId":paperId,"examinationType":examinationType,"stuName":stuName},
 				success:showPageInfo
 			});
 		}
 				/* $("#lastPage").css("color","black");	
 				$("#firstPage").css("color","black");
 				$("#upPage").css("color","black");
 				$("#downPage").css("color","black"); */
 	}
 	//点击搜索和GO时触发此函数
 	$(function()
 	{
 		$("#search,#searchGo").click(function()
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
 			//第几页
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
 			//班级名称	
 			var examClassName=$.trim($("#examClassName").val());
 			if(examClassName=="--请选择--"){
 				examClassName="";
 			}
 			//机试卷还是笔试卷
 			var examinationType=$.trim($("input[name='examinationType']:checked").val());
 			//取数据编号的值
 			var paperId=$.trim($("#paperId").val());
 			//取学生姓名
 			var stuName=$.trim($("#examineeName").val());
 			if(stuName=="--请选择--"){
 				stuName="";
 				$("#showMsgOfExamClassName").html("请选一项!");
 			}else{
 				$("#showMsgOfExamClassName").html("");
 			}
 			if(stuName!=""){
 			$.ajax({
 				url:"/Examination2.0/dataarraylist_showPaperPagesByName.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName,"paperId":paperId,"examinationType":examinationType,"stuName":stuName},
 				success:showPageInfo
 			});
 			}
 		});
 	});
 	//拼页面
 	function showPageInfo(data)
 	{		
 					var pageinfos =  data ;	
 					var pageStr="";
 					pageStr=pageStr+'<tr><th bordercolor="#7EA6DC">试卷编号</th><th bordercolor="#7EA6DC">试卷名称</th><th bordercolor="#7EA6DC">班级</th><th bordercolor="#7EA6DC">姓名</th><th bordercolor="#7EA6DC">成绩</th><th bordercolor="#7EA6DC">考试日期</th><th bordercolor="#7EA6DC">操作当前试卷</th></tr>';
 					if(pageinfos.responseCode==0){
 						var pageSize=pageinfos.obj.pageSize;
 						var currentPage=pageinfos.obj.currentPage;
 						var totalsCount=pageinfos.obj.totalsCount;
 						var totalsPage=pageinfos.obj.totalsPage;
 						//alert("pagesize:"+pageSize+"\t"+"totalspage:"+totalsPage);
 						$.each(pageinfos.obj.result, function(i,pageinfo){
 							if(pageinfo.score==null|| pageinfo.score==""){
 	 							pageinfo.score=0;
 	 						}
 						//alert(pageinfo.examSubject);
 							pageStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" id="'+pageinfo.id+'">';
 							pageStr+='<td align="left">'+pageinfo.writingPaper.id+'</td>';
 							pageStr+='<td align="left">'+pageinfo.writingPaper.paperName+'</td>';
 							pageStr+='<td align="center">'+pageinfo.writingPaper.examineeClass+'</td>';
 							pageStr+='<td align="center">'+pageinfo.examineeName+'</td>';
 							pageStr+='<td align="center">'+pageinfo.score+'</td>';
 							pageStr+='<td align="center">'+pageinfo.writingPaper.examDate+'</td>';
 							pageStr+='<td align="center"><a href="#"  onclick="javascript:toShowExamineeWritinggrade(\''+pageinfo.writingPaper.id+'\',\''+pageinfo.examineeName+'\',\''+pageinfo.writingPaper.examineeClass+'\',\''+pageinfo.score+'\',\''+pageinfo.id+'\')" title="查看评卷后的详细结果">考试信息</a> &nbsp;';
 							pageStr+='<a  href="#" onClick="toShowWritingAnswerPage(\''+pageinfo.writingPaper.id+'\','+pageinfo.id+')" title="查看考生的答卷">浏览答卷</a> &nbsp;';      
 							pageStr+=' <a href="#" title="删除答卷" onClick="deleteWritingAnswerById('+pageinfo.id+')">删除答卷</a> </td>';   
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
 		//把id存到localstorage或cookie中(跳到examineewritpaper.html页面中)
 		function toShowWritingAnswerPage(wpid,waid){
 			if (window.localStorage) {
 	   			 localStorage.setItem("wpid", wpid);
 	   			localStorage.setItem("waid", waid);
 			} else {
 	    		 Cookie.write("wpid", wpid);	
 	    		 Cookie.write("waid", waid);
 			}
 			window.location.href='/Examination2.0/back/backoperation/examineewritpaper.html';
 		}
 	//把id存到localstorage或cookie中（跳到ShowExamineeWritinggrade。html中）
 		
 		function toShowExamineeWritinggrade(wpid,examineeName,examineeClass,score,waid){
 			if (window.localStorage) {
	   			 localStorage.setItem("toExamineeWritinggrad_wpid", wpid);
	   			 localStorage.setItem("toExamineeWritinggrad_examineeName", examineeName);
	   			 localStorage.setItem("toExamineeWritinggrad_examineeClass", examineeClass);
	   			 localStorage.setItem("toExamineeWritinggrad_score", score);
	   			 localStorage.setItem("toExamineeWritinggrad_waid", waid);
			} else {
	    		 Cookie.write("toExamineeWritinggrad_wpid", wpid);                      
	    		 Cookie.write("toExamineeWritinggrad_examineeName", examineeName);      
	    		 Cookie.write("toExamineeWritinggrad_examineeClass", examineeClass);    
	    		 Cookie.write("toExamineeWritinggrad_score", score); 
	    		 Cookie.write("toExamineeWritinggrad_waid", waid);
			}
			var content = '<iframe src="/Examination2.0/back/grade/showexamineewritinggrade.html" width="800px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
 		    var boarddiv = '<div id="msgwindow" title="考试情况显示" width="1000px" height="100%"></div>'//style="overflow:hidden;"可以去掉滚动条  
 		    $(document.body).append(boarddiv);  
 		    var win = $('#msgwindow').dialog({  
 		        content: content,  
 		        width: "100%",  
 		        height: "100%",  
 		        modal: "true",  
 		        title: "考试情况显示",  
 		        onClose: function () {  
 		            $(this).dialog('destroy');//后面可以关闭后的事件  
 		        }  
 		    });  
 		    win.dialog('open'); 
 		}
 		
 		