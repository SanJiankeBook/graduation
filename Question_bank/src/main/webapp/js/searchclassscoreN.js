//根据id删除试卷
 	function deletePaper(pid){
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
			//取数据编号的值
			var paperId=$.trim($("#paperId").val());
			$.ajax({
				url:"/Examination2.0/dataarraylist_showPagesAfterDelete.action",
				type:"post",
				datatype:"json",
				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName,"paperId":paperId,"pid":pid,"examinationType":examinationType},
				success:showPageInfo
			});
 		
 	}
//根据学期显示班级 	
	function createSelectOption(s){
 		var selectedvalue=s;
 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":selectedvalue}, function(data){
				var examineeClassList=  eval("("+data+")");
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
 		if(pageNume>=1&&pageNume<=totalPage){
 			$.ajax({
 				url:"/Examination2.0/dataarraylist_showPaperPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName,"paperId":paperId,"examinationType":examinationType},
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
 				alert("请选择一个班级")
 				return;
 			}
 			var semester=$("input[name='semester']:checked").val();//选中的值
 			
 			//机试卷还是笔试卷
 			var examinationType=$.trim($("input[name='examinationType']:checked").val());
 			//取数据编号的值
 			var paperId=$.trim($("#paperId").val());
 			$.ajax({
 				url:"/Examination2.0/dataarraylist_showPaperPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName,"paperId":paperId,"examinationType":examinationType},
 				success:showPageInfo
 			});
 		});
 	});
 	//拼页面
 	function showPageInfo(data)
 	{		
 					var pageinfos =   data ;	
 					var pageStr="";
 					pageStr=pageStr+'<tr><th bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">考试日期</th><th bordercolor="#7EA6DC">班级</th><th bordercolor="#7EA6DC">试卷名称</th><th bordercolor="#7EA6DC">状态</th><th bordercolor="#7EA6DC">操作</th></tr>';
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
 							pageStr+='<td align="center">'+pageinfo.examDate+'</td>';
 							pageStr+='<td align="center">'+pageinfo.examineeClass+'</td>';
 							pageStr+='<td align="left">'+pageinfo.paperName+'</td>';
 							var statusStr="";
 							if(pageinfo.paperStatus==4){
 								statusStr='考试';
 							}else if(pageinfo.paperStatus==1){
 								statusStr='未考';
 							}else if(pageinfo.paperStatus==3){
 								statusStr='已评';
 							}else if(pageinfo.paperStatus==2){
 								statusStr='未评';
 							}
 							pageStr+='<td align="center">'+statusStr+'</td>';
 							if(pageinfo.paperStatus==3){
 								pageStr+='<td align="center"><a href="#" onclick="javascript:showwrittiongpapergrade(\''+pageinfo.id+'\',\''+pageinfo.paperName+'\',\''+pageinfo.examineeClass+'\',\''+pageinfo.examSubject+'\',\''+pageinfo.examDate+'\',\''+pageinfo.scorePercent+'\',\''+pageinfo.avgScore+'\',\''+pageinfo.maxScore+'\',\''+pageinfo.minScore+'\',\''+pageinfo.questionInfo+'\')" title="查看已评试卷的评卷结果">查看</a> &nbsp; / &nbsp;<a href="#" onClick="deletePaper(\''+pageinfo.id+'\')"title="删除此卷">删除</a></td>';
 							}else if(pageinfo.paperStatus==1){
 								pageStr+='<td align="center"><a href="#" onclick="updatePaperStatus('+pageinfo.id+',\'lbl_${rowNumber.index}\');return false";title="开考此卷，也可以在“考试安排”菜单中开考">开考</a><a href="#" onClick="javascript:deletePaper('+pageinfo.id+')" title="删除此卷">删除</a> </td>';													
 							}else if(pageinfo.paperStatus==4){
 								pageStr+='<td align="center"><a href="/Examination2.0/back/grade/showwritingpapergrade.html" title="查看正在考试的试卷">查看</a>&nbsp; <a href="#" onClick="javascript:deletePaper('+pageinfo.id+')" title="删除此卷">删除</a></td>';	
 							}else if(pageinfo.paperStatus==2){
 								pageStr+='<td align="center"> <a id="gradeA" href="#" onClick="gradePaper(this,'+pageinfo.id+',\'lbl_${rowNumber.index}\')" title="自动评卷">评卷</a>&nbsp; <a href="#" onClick="javascript:deletePaper('+pageinfo.id+')" title="删除此卷">删除</a></td>';	
 							}
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
 			window.location.href='/Examination2.0/back/grade/showwritingpaper.html';
 		}
 	//显示班级考试情况（带图表显示的）
 		function showwrittiongpapergrade(wpid,paperName,examineeClass,examSubject,examDate,scorePercent,avgScore,maxScore,minScore,questionInfo){
 			if (window.localStorage) {
	   			 localStorage.setItem("writtingPaper_wpid", wpid);
	   			 localStorage.setItem("writtingPaper_paperName", paperName);
	   			 localStorage.setItem("writtingPaper_examineeClass", examineeClass);
	   			 localStorage.setItem("writtingPaper_examSubject", examSubject);
	   			 localStorage.setItem("writtingPaper_examDate", examDate);
	   			 localStorage.setItem("writtingPaper_scorePercent", scorePercent);
	   			 localStorage.setItem("writtingPaper_avgScore", avgScore);
	   			 localStorage.setItem("writtingPaper_maxScore", maxScore);
	   			 localStorage.setItem("writtingPaper_minScore", minScore);
	   			 localStorage.setItem("writtingPaper_questionInfo", questionInfo);
			} else {
	    		 Cookie.write("writtingPaper_wpid", wpid);                   
	    		 Cookie.write("writtingPaper_paperName", paperName);         
	    		 Cookie.write("writtingPaper_examineeClass", examineeClass); 
	    		 Cookie.write("writtingPaper_examSubject", examSubject);     
	    		 Cookie.write("writtingPaper_examDate", examDate); 
	    		 Cookie.write("writtingPaper_scorePercent", scorePercent);  
	    		 Cookie.write("writtingPaper_avgScore", avgScore);     
	    		 Cookie.write("writtingPaper_maxScore", maxScore);
	    		 Cookie.write("writtingPaper_minScore", minScore);
	    		 Cookie.write("writtingPaper_questionInfo", questionInfo);
			}
 			var content = '<iframe src="/Examination2.0/back/grade/showwritingpapergrade.html" width="800px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
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
 		