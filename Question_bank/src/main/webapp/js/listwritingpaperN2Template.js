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
 		//班级名称	
		examSubject = $("#subject  option:selected").text();
 		if(pageNume>=1&&pageNume<=totalPage){
 			$.ajax({
 				url:"/Examination2.0/dataarraylist_showWritingPaperPages.action",
 				type:"post",
 				datatype:"json",
 				data:{"displayRows":displayRows,"pageNume":pageNume,"examSubject":examSubject},
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
 			serchAll();
 		});
 		$("#examClassName").change(function(){
 			serchAll();
 		})
 	});
 	function serchAll() {
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
			examSubject = $("#subject  option:selected").text();
			$.ajax({
				url:"/Examination2.0/dataarraylist_showWritingPaperPagesTemplate.action",
				type:"post",
				datatype:"json",
				data:{"displayRows":displayRows,"pageNume":pageNume,"examSubject":examSubject},
				success:showPageInfo
			});
	}
 	function change(data) {
		//2017-07-29 00:00:00
		time = data.replace(/\s(\w|:)*/, "");
		//time=time.replace(/\s$/,"");
		return time;
	}

 	//拼页面
 	function showPageInfo(data)
 	{		
 					var pageinfos =   data ;	
 					var pageStr="";
 					pageStr=pageStr+'<tr><th style="display:none" bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">试卷名称</th><th bordercolor="#7EA6DC">科目</th><th bordercolor="#7EA6DC">操作</th></tr>';
 					if(pageinfos.responseCode==0){
 						var pageSize=pageinfos.obj.pageSize;
 						var currentPage=pageinfos.obj.currentPage;
 						var totalsCount=pageinfos.obj.totalsCount;
 						var totalsPage=pageinfos.obj.totalsPage;
 						//alert("pagesize:"+pageSize+"\t"+"totalspage:"+totalsPage);				
 						$.each(pageinfos.obj.result, function(i,pageinfo){
 						//alert(pageinfo.examSubject);
 							pageStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" id="tr_'+pageinfo.id+'">';
 							pageStr+='<td style="display:none" align="left">'+pageinfo.id+'</td>';
 							pageStr+='<td width="15%" align="center"> <input type="text" style="color:#000099;cursor:pointer"onClick="toShowWritingPaperPage(\''+pageinfo.id+'\')" value="'+pageinfo.paperName+'" title="'+pageinfo.paperName+'" class="noneborder" size="10" readonly="readonly" /> </td>';
 							pageStr+='<td align="center">'+pageinfo.examSubject+'</td>';
 							
 							pageStr+='<td align="center" id="tdOP_'+pageinfo.paperStatus+''+pageinfo.id+'"> <a href="#"  onClick="examToAnotherClass(\''+pageinfo.id+'\')" title="将试卷指派给其他班级考试">指派班级</a></td>';												
 						});	
 					}	
 					if(totalsCount!=0){
 						$("#strPrompt").html("查出"+totalsCount+"记录");
 					}else{
 						pageStr+='<tr  bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="center"><td colspan="6">暂无数据</td></tr>'
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
 			
 			var content = '<iframe src="/Examination2.0/back/grade/showwritingpapertemplate.html" width="1000px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
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
 		//显示班级考试情况（带图表显示的）
 		function showwrittiongpapergrade(wpid,paperName,examineeClass,examSubject,examDate,scorePercent,avgScore,maxScore,minScore,questionInfo){
 			if (window.localStorage) {
 				//alert(wpid);
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
 			/*window.location.href='/Examination2.0/back/grade/showwritingpapergrade.html';*/
 			/*$("#showPaperInfo").src="";
 			$("#datatest_show_carinfo_detail").show();
 			$("#datatest_show_carinfo_detail").window("open");
 			*/
 			
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
 				alert(returninfos.errorMessage);
 			}
 		}
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
 			
 			if(anotherClassName=='--请选择--'){
 				alert("请选择班级！");
 				return;
 			}
 			
 			$.post("/Examination2.0/paper_examPaperToAnotherClassTemplate.action", {
 				examDate: anotherTime,
 				examineeClass : anotherClassName,
 				paperPwd : exampid,
 				paperStatus:anotherClassId
 			}, function(obj) {
 				
 				if (obj.responseCode == 1) {
 					alert(obj.errorMessage);
 				} else {
 					alert("指派成功！");
 					//$("#remarkInfo").val("");
 				}
 			});
 			
 		}
 		