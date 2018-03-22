//显示班级下拉框
	$(function(){
		//显示班级下拉框
			$.ajax({
				url:"/Examination2.0/dataarraylist_findAllExamineeClass.action",
				type:"post",
				datatype:"json",
				success:showAllExamineeClass
			});	
	});
	//把拼好的班级加到班级下拉框中
	function showAllExamineeClass(data){
		var examieeClassNames =   data ;
		if(examieeClassNames.responseCode==0){			
			var examineeClassStr="<option selected='selected'>--请选择--</option>";
			for(var i=0;i<examieeClassNames.obj.length;i++){
 					examineeClassStr+="<option value='"+examieeClassNames.obj[i]+"'>"+examieeClassNames.obj[i]+"</option>"
			}
	$("#examclassid").html(examineeClassStr);	
		
		}
	}
	//根据班级名称来查学生的姓名
	function showStuNameByClassName(className){
		var className=$.trim(className);
		if(className!=""&&className!="--请选择--"){
			$.ajax({
				url:"/Examination2.0/examineeclass_showExamineeList.action",
				type:"post",
				datatype:"json",
				data:{"className":className},
				success:showExamineeNameOption
			});
		}
	}
	function showExamineeNameOption(data){
		var classNameInfos =   data ;
		var optionstr="<option>--请选择--</option>";
			for(var i=0;i<classNameInfos.length;i++){
				optionstr+="<option value='"+classNameInfos[i].name+"' name='stuName'>"+classNameInfos[i].name+"</option>";	  
			}
		$("#examineeName").html(optionstr);
	}
	//查询测评信息（查表pointAnswer中的信息）
	function findAnswerInfo(){
		//取选中班级的值并对其进行校验
		var className=$.trim($("#examclassid").val());
		if(className==""||className=="--请选择--"){
			className="";
			$("#examclassMsg").text("*请选一项！");
		}else{
			$("#examclassMsg").text("*");
		}
		//去选中学生姓名的值并对其进行校验
		var stuName=$.trim($("#examineeName").val());
		if(stuName==""||stuName=="--请选择--"){
			stuName="";
			$("#examineeNameMsg").text("*请选一项！");
		}else{
			$("#examineeNameMsg").text("*");
		}
		if(className!=""||stuName!=""){
		$.ajax({
			url:"/Examination2.0/pointAnswer_findPoinAnswerInfos.action",
			type:"post",
			datatype:"json",
			data:{"examClassName":className,"examineeName":stuName},
			success:showPointAnswerInfo
		});
		}
	}
	//根据班级和姓名来查找测评信息
	function showPointAnswerInfo(data){
		var paperinfos =  data ;
		var str="";
		if(paperinfos.responseCode==0&&paperinfos.obj!=null&&paperinfos.obj.length>0){
		    str=" <table width='680' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
				
		    $.each(paperinfos.obj,function(idx,paperinfo){
					var index=idx+1;
					str+="<tr id=\"tabletr_"+index+"\" bgcolor=\"#EDECEB\" onMouseOver=\"this.bgColor='#93BBDF';\" onMouseOut=\"this.bgColor='#EDECEB';\" align=\"center\">";
					str+="<td align=\"center\" width=\"12%\">"+paperinfo.pointPaper.pid+"</td>";
					str+="<td width=\"20%\">"+paperinfo.pointPaper.pname+"</td>";
					str+="<td align=\"center\" width=\"10%\">"+paperinfo.pointPaper.examineeClass.className+"</td>";
					str+="<td align=\"center\" width=\"15%\">"+paperinfo.name+"</td>";
					str+="<td align=\"center\" width=\"20%\">"+paperinfo.pointPaper.pdate+"</td>";
					str+="<td align=\"center\" width=\"23%\">";
					str+="<a href=\"javascript:showPointAnswerInfoByOpid("+paperinfo.opid+")\" title=\"查看答卷\">[查看答卷]</a>&nbsp;&nbsp;";
					str+="<a href=\"javascript:deletePointAnswer("+paperinfo.opid+","+index+")\" title=\"删除答卷\">[删除答卷]</a>";
					str+="</td></tr>"
				});
		str+="</table>";
		}
		$("#paperanswerinfos").html(str);
		
	}
	//根据答卷id来删除答卷
	var trIndex="";
	function deletePointAnswer(opid,index){
		trIndex=index;
		$.ajax({
			url:"/Examination2.0/pointAnswer_deletePoinAnswerInfoByOpid.action",
			type:"post",
			datatype:"json",
			data:{"opid":opid},
			success:showPointAnswerDelInfo
		});
	}
	function showPointAnswerDelInfo(data){
		var successInfo =   data ;
		if(successInfo.responseCode==0){
			$("#tabletr_"+trIndex).remove();
			alert("删除成功！");
		}else{
			alert("删除失败！");
		}
		
	}
	function showPointAnswerInfoByOpid(opid){
		if(opid!=""){
			if (window.localStorage) {
	   			 localStorage.setItem("opid", opid);
			} else {
	    		 Cookie.write("opid", opid);	
			}
			//location.href='/Examination2.0/back/admin/showpaperanswer.html';
			//window.open("/Examination2.0/back/admin/showpaperanswer.html");
			var content = '<iframe src="/Examination2.0/back/admin/showpaperanswer.html" width="800px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
 		    var boarddiv = '<div id="msgwindow" title="信息显示" width="1000px" height="100%"></div>'//style="overflow:hidden;"可以去掉滚动条  
 		    $(document.body).append(boarddiv);  
 		    var win = $('#msgwindow').dialog({  
 		        content: content,  
 		        width: "100%",  
 		        height: "100%",  
 		        modal: "true",  
 		        title: "信息显示",  
 		        onClose: function () {  
 		            $(this).dialog('destroy');//后面可以关闭后的事件  
 		        }  
 		    });  
 		    win.dialog('open'); 
		}
		
		
	}
	