//根据学期显示班级 	
	function createSelectOption(s){
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
	var selectedvalue2;
	function createSelectOption2(s){
 		selectedvalue2=s;
 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":selectedvalue2}, function(data){
				var examineeClassList= eval("("+data+")");
				var optionstr="<option>--请选择--</option>";	
				if(examineeClassList!=null){
					
					$.each(examineeClassList, function(i,examineeClass){
  					optionstr+="<option id="+examineeClass.Id+" value='"+examineeClass.Id+"' name='className'>"+examineeClass.className+"</option>";	  														
					
					});	
				}																
				$("#exam_show_anotherClass_class").html(optionstr);
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
 				displayRows=50;
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
 		if(status=="up"){	//上一页
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
 				url:"/Examination2.0/dataarraylist_showWritingPaperPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName},
 				success:showPageInfo
 			});
 		}
 				/* $("#lastPage").css("color","black");	
 				$("#firstPage").css("color","black");
 				$("#upPage").css("color","black");
 				$("#downPage").css("color","black"); */
 	}
 	//点击搜索和GO时触发此函数
 	$(function(){
 		
 		$("#search,#searchGo").click(function()
 		{	
 			console.info("haaaaaaa");
 			//总页数
 			var totalPage=parseInt($.trim($("#tabTotalPage").text()));
 			if(totalPage==""){
 				displayRows=0;
 			}
 			//每页显示几条
 			var displayRows=$.trim($("#displayRows").val());
 			if(displayRows==""){
 				displayRows=50;
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
 			$.ajax({
 				url:"/Examination2.0/dataarraylist_showWritingPaperPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName},
 				success:showPageInfo
 			});
 		});
 	});
 	//拼页面
 	function showPageInfo(data)
 	{		
 					var pageinfos =   data ;	
 					var pageStr="";
 					pageStr=pageStr+'<tr><th bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">密码</th><th bordercolor="#7EA6DC">考试日期</th><th bordercolor="#7EA6DC">班级</th><th bordercolor="#7EA6DC">试卷名称</th><th bordercolor="#7EA6DC">状态</th><th bordercolor="#7EA6DC">操作</th></tr>';
 					if(pageinfos.responseCode==0){
 						var pageSize=pageinfos.obj.pageSize;
 						var currentPage=pageinfos.obj.currentPage;
 						var totalsCount=pageinfos.obj.totalsCount;
 						var totalsPage=pageinfos.obj.totalsPage;
 						//alert("pagesize:"+pageSize+"\t"+"totalspage:"+totalsPage);				
 						$.each(pageinfos.obj.result, function(i,pageinfo){
 						//alert(pageinfo.examSubject);
 							pageStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" id="tr_'+pageinfo.id+'">';
 							pageStr+='<td align="left">'+pageinfo.id+'</td>';
 							pageStr+='<td align="center">'+pageinfo.paperPwd+'</td>';
 							pageStr+='<td align="center">'+pageinfo.examDate+'</td>';
 							pageStr+='<td align="center">'+pageinfo.examineeClass+'</td>';
 							pageStr+='<td align="left"> <input type="text" style="color:#000099;cursor:pointer"onClick="toShowWritingPaperPage(\''+pageinfo.id+'\')" value="'+pageinfo.paperName+'" title="'+pageinfo.paperName+'" class="noneborder" size="23" readonly="readonly" /> </td>';
 							var statusStr="";
 							/*  关于评卷的业务约定: 只要开考的试卷，都显示  评卷，    老师点评卷后，将试卷状态变为已评， 则可以在班级成绩查询中查询学生成绩了. 同时学生也不可以再用这个试卷考试. 
         						另外，为了防止老师忘记评卷，则用定时器在后台扫描已开考，未评卷的试卷，自动评卷.   */
 							if(pageinfo.paperStatus==4){
 								statusStr='考试';
 							}else if(pageinfo.paperStatus==1){
 								statusStr='未考';
 							}else if(pageinfo.paperStatus==3){
 								statusStr='已评';
 							}else if(pageinfo.paperStatus==2){
 								statusStr='未评';
 							}
 							//alert("statusStr"+statusStr);
 							pageStr+='<td align="center" >'+statusStr+'</td>';
 							if(pageinfo.paperStatus==3){
 								pageStr+='<td align="center" id="tdOP_'+pageinfo.paperStatus+''+pageinfo.id+'"><a href="#" onclick="showwrittiongpapergrade(\''+pageinfo.id+'\',\''+pageinfo.paperName+'\',\''+pageinfo.examineeClass+'\',\''+pageinfo.examSubject+'\',\''+pageinfo.examDate+'\',\''+pageinfo.scorePercent+'\',\''+pageinfo.avgScore+'\',\''+pageinfo.maxScore+'\',\''+pageinfo.minScore+'\',\''+pageinfo.questionInfo+'\')" title="查看已评试卷的评卷结果">成绩汇总</a> &nbsp; <a href="#" id="reExam_${rowNumber.index}" onClick="reExamThisWp(\''+pageinfo.id+'\')" title="对本班重考此试卷，全班原有的考试成绩将会全部丢失">重考</a>&nbsp; <a href="#"  onClick="toShowWritingPaperPage(\''+pageinfo.id+'\')" title="考试详情">详情</a>&nbsp; <a href="#"  onClick="examToAnotherClass(\''+pageinfo.id+'\')" title="将试卷指派给其他班级考试">指派班级</a></td>';												
 							}else if(pageinfo.paperStatus==1){
 								pageStr+='<td align="center"><a href="#" onClick="updateThisPaperStatus(\''+pageinfo.id+'\',\''+4+'\')" title="开考此卷，也可以在“考试安排”菜单中开考">开考</a>&nbsp; <a href="#" onClick="javascript:deleteThisPaper(\''+pageinfo.id+'\')"title="删除此卷">删除</a> <a href="#"  onClick="toShowWritingPaperPage(\''+pageinfo.id+'\')" title="考试详情">详情</a></td>';													
 							}else if(pageinfo.paperStatus==4){
 								pageStr+='<td align="center"><a  href="#" onClick="gradeThisPaper(\''+pageinfo.id+'\')" title="自动评卷">评卷</a>&nbsp;<a href="#" onclick="javascript:toShowWritingPaperPage(\''+pageinfo.id+'\')" title="查看考试试卷">查看</a>&nbsp; <a href="#" onClick="javascript:deleteThisPaper(\''+pageinfo.id+'\')"title="删除此卷">删除</a></td>';	
 							}else if(pageinfo.paperStatus==2){
 								
 								pageStr+='<td align="center"> <a  href="#" onClick="gradeThisPaper(\''+pageinfo.id+'\')" title="自动评卷">评卷</a>&nbsp; <a href="#" onClick="javascript:deleteThisPaper(\''+pageinfo.id+'\')"title="删除此卷">删除</a</td>';	
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
 			
 			var content = '<iframe src="/Examination2.0/back/grade/showwritingpaper.html" width="800px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
 		    var boarddiv = '<div id="msgwindow" title="试卷详情显示" width="1000px" height="100%"></div>'//style="overflow:hidden;"可以去掉滚动条  
 		    $(document.body).append(boarddiv);  
 		    var win = $('#msgwindow').dialog({  
 		        content: content,  
 		        width: "100%",  
 		        height: "100%",  
 		        modal: "true",  
 		        title: "试卷详情显示",  
 		        onClose: function () {  
 		            $(this).dialog('destroy');//后面可以关闭后的事件  
 		        }  
 		    });  
 		    win.dialog('open'); 
 		}
 		var exampid;
 		//将试卷指派给其他的班级
 		function examToAnotherClass(pid){
 			exampid=pid;
 			$("#allGrade").hide();
 			$("#exam_pointAnotherClass").toggle();
 			$('#exam_anotherClass_table').css("display","block")
 			
 			//selectedvalue2=S1;
 	 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":"S1"}, function(data){
 					var examineeClassList= eval("("+data+")");
 					var optionstr="<option>--请选择--</option>";	
 					if(examineeClassList!=null){
 						
 						$.each(examineeClassList, function(i,examineeClass){
 	  					optionstr+="<option id="+examineeClass.Id+" value='"+examineeClass.Id+"' name='className'>"+examineeClass.className+"</option>";	  														
 						
 						});	
 					}																
 					$("#exam_show_anotherClass_class").html(optionstr);
 	  				}); 
 			/*$('#exam_anotherClass_tree').html("");
 			$('#exam_anotherClass_tree').tree('append', {
 				data : [ {
 					id : 'exam_point_selectSubcondition',
 					text : '班级'
 				} ]
 			});

 			var nodeTemp = $('#exam_anotherClass_tree').tree('getRoot',
 					"exam_point_selectSubcondition");
 			$.ajax({
 				url : "/Examination2.0/findClass.action",
 				type : "POST",
 				dataType : "JSON",
 				success : function(data) {
 					
 					for (var i = 0; i < data.obj.length; i++) {
 						$('#exam_anotherClass_tree').tree('append', {
 							parent : nodeTemp.target,
 							data : [ {
 								id : data.obj[i].id,
 								text : data.obj[i].className,

 							} ]
 						});
 					}
 					//折叠所有节点
 					//$('#openSatisfaction_selectSubconditionSub').tree('collapseAll');
 				}
 			});
 			
 			$('#exam_anotherClass_tree').tree({
 				onClick : function(node) {
 					id = node.id;
 					$("#exam_show_anotherClass_class").html(node.text);
 					$("#exam_point_classid").html(id);
 				}
 			});*/
 		}
 		
 		//确认指派
 		function exam_anotherClass(){
 			var anotherTime=$("#checkDate").val();
 			var anotherClassName=$("#exam_show_anotherClass_class").find("option:selected").text().trim();
 			var anotherClassId=$("#exam_show_anotherClass_class").val();
 			//console.info("anotherTime:"+anotherTime+"anotherClassId:"+anotherClassName);
 			if(anotherTime==''){
 				alert("请选择时间！");
 				return;
 			}
 			
 			if(anotherClassName==''){
 				alert("请选择班级！");
 				return;
 			}
 			
 			$.post("/Examination2.0/paper_examPaperToAnotherClass.action", {
 				examDate: anotherTime,
 				examineeClass : anotherClassName,
 				paperPwd : exampid,
 				paperStatus:anotherClassId
 			}, function(obj) {
 				
 				if (obj.responseCode == 1) {
 					alert(obj.errorMessage);
 				} else {
 					alert("重新指派成功！");
 					//$("#remarkInfo").val("");
 				}
 			});
 			
 		}
 		
 		//显示班级考试情况（带图表显示的）
 		function showwrittiongpapergrade(wpid,paperName,examineeClass,examSubject,examDate,scorePercent,avgScore,maxScore,minScore,questionInfo){
 			//$("#allGrade").css("display","block");
 			$("#exam_pointAnotherClass").hide();
 			$("#allGrade").toggle();
 			
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
 			
 			
 			
 			basicInfo();
 			column();
 			pie();
 			
 			/*window.location.href='/Examination2.0/back/grade/showwritingpapergrade.html';*/
 			/*$("#showPaperInfo").src="";
 			$("#datatest_show_carinfo_detail").show();
 			$("#datatest_show_carinfo_detail").window("open");
 			*/
 			
 			/*var content = '<iframe src="/Examination2.0/back/grade/showwritingpapergrade.html" width="800px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
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
 		    win.dialog('open'); */
 		}
 		function deleteThisPaper(wpid){
 			//alert("delId"+delId);
 			//delId=wpid;
 			$.ajax({
 				url:"/Examination2.0/writingPaper_delWritingPaperById.action",
 				type:"post",
 				datatype:"json",
 				data:{"wpid":wpid},
 				success:showDelInfo
 			});
 		};
 		//根据id来删除考卷
 		function showDelInfo(data){
 			var returninfos =   data ;
 			if(returninfos.responseCode==0){
 				//总页数
 	 			var totalPage=parseInt($.trim($("#tabTotalPage").text()));
 	 			if(totalPage==""){
 	 				displayRows=0;
 	 			}
 	 			//每页显示几条
 	 			var displayRows=$.trim($("#displayRows").val());
 	 			if(displayRows==""){
 	 				displayRows=50;
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
 	 			$.ajax({
 	 				url:"/Examination2.0/dataarraylist_showWritingPaperPages.action",
 	 				type:"post",
 	 				datatype:"json",
 	 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName},
 	 				success:showPageInfo
 	 			});
 			}else{
 				alert("删除失败！");
 			}
 		}
 		//更改评卷信息及其更改后的状态
 		function gradeThisPaper(wpid){
 			$.ajax({
 				url:"/Examination2.0/writingPaper_gradeThisWritingPaper.action",
 				type:"post",
 				datatype:"json",
 				data:{"wpid":wpid},
 				success:showUpdateExamWritingPaperInfo
 			});
 		}
 	
		//重考试卷
		function reExamThisWp(wpid){
			$.ajax({
				url:"/Examination2.0/writingPaper_reExamThisWritingPaper.action",
				type:"post",
				datatype:"json",
				data:{"wpid":wpid},
				success:showUpdateExamWritingPaperInfo
			});
		}
 		
 		
 		//根据id来更新试卷的状态
 		function updateThisPaperStatus(wpid,toPaperStatu){
 			$.ajax({
 				url:"/Examination2.0/writingPaper_updatePaperStatuById.action",
 				type:"post",
 				datatype:"json",
 				data:{"wpid":wpid,"paperStatus":toPaperStatu},
 				success:showUpdateExamWritingPaperInfo
 			});
 		}
 		function showUpdateExamWritingPaperInfo(data){
 			var returninfos =   data ;
 			if(returninfos.responseCode==0){
 				//总页数
 	 			var totalPage=parseInt($.trim($("#tabTotalPage").text()));
 	 			if(totalPage==""){
 	 				displayRows=0;
 	 			}
 	 			//每页显示几条
 	 			var displayRows=$.trim($("#displayRows").val());
 	 			if(displayRows==""){
 	 				displayRows=50;
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
 	 			$.ajax({
 	 				url:"/Examination2.0/dataarraylist_showWritingPaperPages.action",
 	 				type:"post",
 	 				datatype:"json",
 	 				data:{"displayRows":displayRows,"pageNume":pageNume,"examClassName":examClassName},
 	 				success:showPageInfo
 	 			});
 			}else{
 				alert("状态改变失败！");
 			}
 		}
 		
 		
 		//柱状图	
 		function column(){
 			var wpid= window.localStorage? localStorage.getItem("writtingPaper_wpid"): Cookie.read("writtingPaper_wpid");
 			console.info("wpid:"+wpid);
 			$.ajax({
 				url:"/Examination2.0/writingPaper_getCorrectRate.action",
 				type:"post",
 				datatype:"json",
 				data:{"wpid":wpid},
 				success:showCorrectRateOfChapterOrSubject
 			});
 		}
 		
 		//饼状图	
 		function  pie(){
 			var scorePercent= window.localStorage? localStorage.getItem("writtingPaper_scorePercent"): Cookie.read("writtingPaper_scorePercent");
 			if( scorePercent.charAt(scorePercent.length-1)==";"){
 				scorePercent=scorePercent.substring(0,scorePercent.length-1)
 				}else{
 					scorePercent=scorePercent;
 				}
 			var scorePercentArr=scorePercent.split(";");
 			var sumScorePercent=0;
 			for(var i=0;i<scorePercentArr.length;i++){
 				sumScorePercent=accAdd(scorePercentArr[i],sumScorePercent);
 			}
 			var examCount=sumScorePercent;
 			var passExamRate=fomatFloat(Subtr(1,accDiv(scorePercentArr[0],sumScorePercent)),3);
 			var  passExamCount=Subtr(sumScorePercent,scorePercentArr[0]);
 			//alert("passExamCount:"+passExamCount);
 			$("#examCount").val(examCount);
 			$("#passExamRate").text(passExamRate);
 			$("#passExamCount").text(passExamCount);
 			
 			var myData = new Array(['60分以下 '+scorePercentArr[0]+'人',accMul(fomatFloat(accDiv(scorePercentArr[0],sumScorePercent),3),100)], ['60-69分 '+scorePercentArr[1]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[1],sumScorePercent),3),100)], ['70-79分 '+scorePercentArr[2]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[2],sumScorePercent),3),100)], ['80-89分 '+scorePercentArr[3]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[3],sumScorePercent),3),100)], ['90-100分 '+scorePercentArr[4]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[4],sumScorePercent),3),100)]);
 			var colors = ['#C40000', '#750303', '#F9ECA2', '#FA9000', '#FA5400'];
 			var myChart = new JSChart('piegraph', 'pie');
 			myChart.setDataArray(myData);
 			myChart.colorizePie(colors);
 			myChart.setTitle('每分数段的考生比例 (%)');
 			myChart.setTitleColor('#8E8E8E');
 			myChart.setTitleFontSize(11);
 			myChart.setTextPaddingTop(30);
 			myChart.setSize(616, 321);
 			myChart.setPiePosition(308, 170);
 			myChart.setPieRadius(85);
 			myChart.setPieUnitsColor('#555');
 			myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
 			myChart.draw();	
 		};
 		

 		//
 		function showCorrectRateOfChapterOrSubject(data){
 			
 			console.info(data);
 			var returnInfos =  data ;
 			var examSubject="";
 			if(returnInfos.responseCode==0){
 				if(returnInfos.obj!=null){
 						var myDataArr=new Array();
 						var colorsArr=new Array();
 						var colors=new Array('#F13714','#C13714','#AF0202', '#EC7A00', '#FCD200', '#81C714', '#81a714', '#813714','#913714','#A13714','#B13714','#D13714','#F13714','#C13714','#AF0202', '#EC7A00', '#FCD200', '#81C714', '#81a714', '#813714','#913714','#A13714','#B13714','#D13714');
 						for(var i=0;i<parseInt(returnInfos.obj.length/3);i++){
 							myDataArr[i]=new Array();
 							colorsArr[i]=colors[i];
 								//alert(returnInfos.obj[j]);
 								for(var j=i*3;j<returnInfos.obj.length;j++){
 									if(j%3==1){
 										myDataArr[i][0]=returnInfos.obj[(i*3+1)];
 									}else if(j%3==2){
 										myDataArr[i][1]=parseFloat(returnInfos.obj[i*3+2]);
 									}else{
 										examSubject=returnInfos.obj[i*3];
 									}
 								}
 						}
 						
 						var myData=myDataArr;
 						var myChart = new JSChart('bargraph', 'bar');		
 						myChart.setDataArray(myData);
 						myChart.colorizeBars(colorsArr)
 						myChart.setTitle('科目《'+examSubject+'》及其章节的考生答对率');
 						myChart.setTitleColor('#8E8E8E');
 						myChart.setAxisNameX('所在章节');
 						myChart.setAxisNameY('答对人数比例');
 						myChart.setAxisColor('#C4C4C4');
 						myChart.setAxisNameFontSize(11);
 						myChart.setAxisNameColor('#999');
 						myChart.setAxisValuesColor('#7E7E7E');
 						myChart.setBarValuesColor('#7E7E7E');
 						myChart.setAxisPaddingTop(50);
 						myChart.setAxisPaddingRight(40);
 						myChart.setAxisPaddingLeft(50);
 						myChart.setAxisPaddingBottom(100);
 						myChart.setTextPaddingLeft(10);
 						myChart.setTextPaddingBottom(10);
 						myChart.setAxisValuesAngle(30);
 						myChart.setTitleFontSize(11);
 						myChart.setBarBorderWidth(1);
 						myChart.setBarBorderColor('#C4C4C4');
 						myChart.setBarSpacingRatio(50);
 						//myChart.setGrid(false);
 						myChart.setSize(616, 321);
 						myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
 						myChart.draw();		
 					}	
 				}else{
 				var examSubject= window.localStorage? localStorage.getItem("writtingPaper_examSubject"): Cookie.read("writtingPaper_examSubject");
 				//alert("获取数据失败！");
 				var colors=new Array('#F13714');
 				var myData=new Array(['章节不存在',0]);
 				var myChart = new JSChart('bargraph', 'bar');		
 				myChart.setDataArray(myData);
 				myChart.colorizeBars(colors)
 				myChart.setTitle('科目《'+examSubject+'》及其章节的考生答对率');
 				myChart.setTitleColor('#8E8E8E');
 				myChart.setAxisNameX('所在章节');
 				myChart.setAxisNameY('答对人数比例%');
 				myChart.setAxisColor('#C4C4C4');
 				myChart.setAxisNameFontSize(11);
 				myChart.setAxisNameColor('#999');
 				myChart.setAxisValuesColor('#7E7E7E');
 				myChart.setBarValuesColor('#7E7E7E');
 				myChart.setAxisPaddingTop(50);
 				myChart.setAxisPaddingRight(40);
 				myChart.setAxisPaddingLeft(50);
 				myChart.setAxisPaddingBottom(100);
 				myChart.setTextPaddingLeft(10);
 				myChart.setTextPaddingBottom(10);
 				myChart.setAxisValuesAngle(30);
 				myChart.setTitleFontSize(11);
 				myChart.setBarBorderWidth(1);
 				myChart.setBarBorderColor('#C4C4C4');
 				myChart.setBarSpacingRatio(50);
 				//myChart.setGrid(false);
 				myChart.setSize(616, 321);
 				myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
 				myChart.draw();		
 			}
 		}

 			


 		function basicInfo(){
 			var wpid= window.localStorage? localStorage.getItem("writtingPaper_wpid"): Cookie.read("writtingPaper_wpid");
 			var paperName= window.localStorage? localStorage.getItem("writtingPaper_paperName"): Cookie.read("writtingPaper_paperName");
 			var examineeClass= window.localStorage? localStorage.getItem("writtingPaper_examineeClass"): Cookie.read("writtingPaper_examineeClass");
 			var examSubject= window.localStorage? localStorage.getItem("writtingPaper_examSubject"): Cookie.read("writtingPaper_examSubject");
 			var examDate= window.localStorage? localStorage.getItem("writtingPaper_examDate"): Cookie.read("writtingPaper_examDate");
 			var scorePercent= window.localStorage? localStorage.getItem("writtingPaper_scorePercent"): Cookie.read("writtingPaper_scorePercent");
 			var avgScore= window.localStorage? localStorage.getItem("writtingPaper_avgScore"): Cookie.read("writtingPaper_avgScore");
 			var maxScore= window.localStorage? localStorage.getItem("writtingPaper_maxScore"): Cookie.read("writtingPaper_maxScore");
 			var minScore= window.localStorage? localStorage.getItem("writtingPaper_minScore"): Cookie.read("writtingPaper_minScore");
 			
 			$("#examClassId").val(wpid);
 			//alert(wpid);
 			$("#paperName").val(paperName);
 			$("#examineeClass").val(examineeClass);
 			$("#examSubject").val(examSubject);
 			$("#examDate").val(examDate);
 			$("#avgScore").text(avgScore);
 			$("#maxScore").text(maxScore);
 			$("#minScore").text(minScore);
 			$.ajax({
 				url:"/Examination2.0/writingAnswer_getCountOfExamPerson.action",
 				type:"post",
 				datatype:"json",
 				data:{"wpid":wpid},
 				success:showCountOfExamPerson
 			});
 			
 		};
 		function showCountOfExamPerson(data){
 			
 			//alert("sumScorePercent:"+sumScorePercent);
 			var writingAnswerInfos =   data ;
 			var questionStr='<tr><th bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">姓名</th><th bordercolor="#7EA6DC">分数</th> <th bordercolor="#7EA6DC" colspan="2">信息查看</th></tr>';
 			if(writingAnswerInfos.responseCode==0){
 				if(writingAnswerInfos.obj!=null){
 					$.each(writingAnswerInfos.obj, function(i,writingAnswerInfo){
 						questionStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="center" id="'+(i+1)+'">';
 						questionStr+='<td align="left">'+(i+1)+'';
 						questionStr+='<td align="left">'+writingAnswerInfo.examineeName+'';
 						questionStr+='<td align="left">'+writingAnswerInfo.score+'';
 						questionStr+='<td align="center"> <a href="#" title="查看评卷后的详细结果" onclick="javascript:toShowExamineeWritinggrade(\''+writingAnswerInfo.writingPaper.id+'\',\''+writingAnswerInfo.examineeName+'\',\''+writingAnswerInfo.writingPaper.examineeClass+'\',\''+writingAnswerInfo.score+'\',\''+writingAnswerInfo.id+'\')">考试信息</a> &nbsp; /<a href="#" onClick="javascript:toShowWritingAnswerPage(\''+writingAnswerInfo.writingPaper.id+'\','+writingAnswerInfo.id+')" title="查看考生的答卷">查看答卷</a> </td> </tr>';
 					});	
 				}else{
 					questionStr+='<tr><td colspan="4">暂无数据...</td></tr>';
 				}
 				
 			$("#showWritingAnswerInfo").html(questionStr);
 				
 				
 				
 			}
 			
 		}
 		//把id存到localstorage或cookie中
 			function toShowWritingAnswerPage(wpid,waid){
 				if (window.localStorage) {
 					 localStorage.setItem("wpid", wpid);
 					localStorage.setItem("waid", waid);
 				} else {
 		 		 Cookie.write("wpid", wpid);	
 		 		 Cookie.write("waid", waid);
 				}
 				window.open("/Examination2.0/back/backoperation/examineewritpaper.html");
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
			
		var content = '<iframe src="/Examination2.0/back/grade/showexamineewritinggrade.html" width="700px" height="95%" frameborder="0" scrolling="yes" ></iframe>';  
	    var boarddiv = '<div id="msgwindow2" title="浏览考生评卷结果"    width="1000px" height="100%"></div>'//style="overflow:hidden;"可以去掉滚动条  
	    $(document.body).append(boarddiv);  
	    var win = $('#msgwindow2').dialog({  
	        content: content,  
	        left: 100,
	        top: '20%',
	        width: "100%",  
	        height: "100%",  
	        modal: "true",  
	        title: "浏览考生评卷结果",  
	        onClose: function () {  
	            $(this).dialog('destroy');//后面可以关闭后的事件  
	        }  
	    });  
	    win.dialog('open'); 
			
		}
		