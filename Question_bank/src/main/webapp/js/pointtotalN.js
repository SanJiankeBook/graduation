//根据学期显示班级 
	var selectedvalue="";
	var PID="";
	var lengthPoint=0;
	function createSelectOption(s){
 		selectedvalue=s;
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
	var selectedvalue1='';
	function createSelectOption1(s){
 		selectedvalue1=s;
 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":selectedvalue1}, function(data){
				var examineeClassList= eval("("+data+")");
				var optionstr="<option>--请选择--</option>";	
				if(examineeClassList!=null){
					
					$.each(examineeClassList, function(i,examineeClass){
  					optionstr+="<option id="+examineeClass.Id+" value='"+examineeClass.Id+"' name='className'>"+examineeClass.className+"</option>";	  														
					
					});	
				}																
				$("#show_anotherClass_class").html(optionstr);
  				});  				  		
 	}
	
	
	
 	$(function(){
 		selectedvalue="S1";
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
 	//整理时间在前台的显示
 	function change(data) {
		//2017-07-29 00:00:00
		time = data.replace(/\s(\w|:)*/, "");
		//time=time.replace(/\s$/,"");
		return time;
	}
 	//显示课程名字下拉框调用后台
 	function changeClassName(className){
 		var semester=selectedvalue;
 		//alert("className:"+className);
 		if(className!="--请选择--"&&className!=""){
 			$.ajax({
 				url:"/Examination2.0/pointPaper_findSubjectInfo.action",
 				type:"post",
 				datatype:"json",
 				data:{"className":className,"semester":semester},
 				success:showSubjectName
 				});
 		}else{
 			$("#subjectName").html("<option>--请选择--</option>");
 		}
 		
		
 	}
 	//显示课程名字下拉框的页面实现
 	function showSubjectName(data){
 		//alert("进入了showSubjectName");
 		var subjectNames =  data;
 		if(subjectNames.responseCode==0){
 			var optionstr="<option>--请选择--</option>";
 			if(subjectNames.obj!=null&&subjectNames.obj.length>0){
 				for(var i=0;i<subjectNames.obj.length;i++){
 					optionstr+="<option value='"+subjectNames.obj[i][0]+"' name='className'>"+subjectNames.obj[i][1]+"</option>";	
 				}
 				$("#subjectName").html(optionstr);
 			}else{
 				$("#subjectName").html("<option>--请选择--</option>");
 			}
 			
 		}else{
 			$("#subjectName").html("<option>--请选择--</option>");
 			
 		}
 	}
 	//根据课程id和班级名称来查找知识点统计的信息（向后台请求数据）
 	function findPointPaper(){
   		$("#showpointtotal").hide();
 		var className=$.trim($("#examClassName").val());
 		//var subjectId=$.trim($("#subjectName").val());
 		
 		if(className=="--请选择--"){
 			className=="";
 			alert("请选择一个班级");
 			var str=" <table width='100%' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
	 		str+="<td colspan='6' align='center' style='color:red'>对不起,没有符合查询条件的测评试卷!</td>";
 			$("#paperanswerinfo").html(str);
 			return;
 		}
 		if(className!=""){

	 		$.ajax({
				url:"/Examination2.0/pointPaper_findPointPaperInfo.action",
				type:"post",
				datatype:"json",
				data:{"className":className},
				success:showPointPaper
				});
	 	}else{
	 		$("#subjectNameMsg").text("*必选！");
	 		var str=" <table width='100%' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
	 		str+="<td colspan='6' align='center' style='color:red'>对不起,没有符合查询条件的测评试卷!</td>";
	 		$("#paperanswerinfo").html(str);
	 	}
 	}
 	//验证课程是否已选
 	$(document).ready(function(){
 		$('#subjectName').change(function(){
 			var subjectId=$.trim($("#subjectName").val());
 			if(subjectId!="--请选择--"){
 				$("#subjectNameMsg").text("*");
 			}else{
 				$("#subjectNameMsg").text("*必选！");
 			}
 		})
 		}) 
 	//根据课程id和班级名称来查找知识点统计的信息（向页面显示）
 	function showPointPaper(data){
 		//alert("11111");
 		var pointPapers =  data ;
   		$("#showpointtotal").hide();
   		$("#studentInfo").hide();
   		$("#pointAnotherClass").hide();
 		//alert(data[1]);
 		if(pointPapers.responseCode==0){
 			var str=" <table  width='100%' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
				
 				if(pointPapers.obj==null||pointPapers.obj==undefined ||pointPapers.obj.length<=0){
					str+="<td colspan='6' align='center' style='color:red'>对不起,没有符合查询条件的测评试卷!</td>";
				}else{
					var str=" <table width='100%' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
					$.each(pointPapers.obj,function(idx,pointPaper){
						var index=idx+1;
						if(pointPaper.className == undefined){
							return false;
						}
						str+="<tr id=\""+index+"\" bgcolor=\"#EDECEB\" onMouseOver=\"this.bgColor='#93BBDF';\" onMouseOut=\"this.bgColor='#EDECEB';\" align=\"center\">";
						str+="<td align=\"center\" width=\"8%\">"+index+"</td>";
						str+="<td width=\"25%\">"+pointPaper.pname+"</td>";
/*						str+="<td width=\"15%\">"+pointPaper.examineeClass.className+"</td>";
*/						str+="<td align=\"center\" width=\"17%\">"+pointPaper.subjectName+"</td>";
						str+="<td align=\"center\" width=\"15%\">"+change(pointPaper.examDate)+"</td>";
						str+="<td align=\"center\" width=\"25%\">";
						str+="<a href=\"javascript:showPointTotal('"+pointPaper.pid+"','"+pointPaper.className+"','"+pointPaper.examDate+"','"+pointPaper.pstatus+"','"+pointPaper.subjectName+"','"+pointPaper.pname+"','"+pointPaper.descript +"','"+pointPaper.ptitle+"')\" title=\"统计\">[统计]</a>";
						str+="<a href=\"javascript:showPointanswer('"+pointPaper.pid+"','"+pointPaper.className+"','"+pointPaper.examDate+"','"+pointPaper.pstatus+"','"+pointPaper.subjectName+"','"+pointPaper.pname+"','"+pointPaper.descript +"','"+pointPaper.ptitle+"')\" title=\"详情\">[详情]</a>";
						str+="<a href=\"javascript:getAnotherClass('"+pointPaper.pid+"')\" title=\"指派班级\">[指派班级]</a>";

						str+="</td></tr>"
					});
				}
 				var length=pointPapers.obj.length;
				str+="</table>";
				var strs='';
				for(var i=0;i<length;i++){
					strs+='<br/>'	
				}
				$("#split").html(strs);
				
				$("#paperanswerinfo").html(str);
 		}else{
 			alert("没有该条件对应的测评信息!");
 		}
 	}
 	//把该页的部分信息存到localStorage或cookie中下一个页面取
 	function showPointTotal(pid,className,pdate,pstatus,subjectName,pname,descript,ptitle){
 		$("#pointAnotherClass").hide();
 		$("#showpointtotal").toggle() ;
   		$("#studentInfo").hide();
   		$("#className").text(className);
		$("#pdate").text(change(pdate));
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
 		/*window.location.href='/Examination2.0/back/grade/showpointtotal.html';*/
 	/*	var content = '<iframe src="/Examination2.0/back/grade/showpointtotal.html" width="800px" height="95%" frameborder="0" scrolling="yes"></iframe>';  
		    var boarddiv = '<div id="msgwindow" title="知识点信息显示" width="1000px" height="100%"></div>'//style="overflow:hidden;"可以去掉滚动条  
		    $(document.body).append(boarddiv);  
		    var win = $('#msgwindow').dialog({  
		        content: content,  
		        width: "100%",  
		        height: "100%",  
		        modal: "true",  
		        title: "知识点信息显示",  
		        onClose: function () {  
		            $(this).dialog('destroy');//后面可以关闭后的事件  
		        }  
		    });  
		    win.dialog('open'); */
 	}
 	//显示测评知识点信息 统计
	function showAllPointInfo(data){
		if(data.responseCode==0){
		var pointInfos = data ;
		var pointInfoStr='<tr style="color:red" align="center"  bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';"  ><td height="60" colspan="3"><span >暂无测评知识点信息</span></td></tr>';
		
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
					pointInfoStr='<tr style="color:red" align="center" bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';"  ><td height="60" colspan="3"><span >暂无测评知识点信息</span></td></tr>';
				}
				$("#pointInfoMsg").html(pointInfoStr);
				
			}else{
				$("#pointInfoMsg").html(pointInfoStr);
			}	
		}else{
			$("#pointInfoMsg").html(pointInfoStr);
		}
	}	
	//指派班级
	function getAnotherClass(pid){
		pointid=pid;
		$("#showpointtotal").hide();
		$("#studentInfo").hide();
		$("#pointAnotherClass").toggle();
		$('#anotherClass_table').css("display","block")
		
		//selectedvalue1=S1;
 		$.post("/Examination2.0/writingPaper_getExamineeClassName.action",{"semester":"S1"}, function(data){
				var examineeClassList= eval("("+data+")");
				var optionstr="<option>--请选择--</option>";	
				if(examineeClassList!=null){
					
					$.each(examineeClassList, function(i,examineeClass){
  					optionstr+="<option id="+examineeClass.Id+" value='"+examineeClass.Id+"' name='className'>"+examineeClass.className+"</option>";	  														
					
					});	
				}																
				$("#show_anotherClass_class").html(optionstr);
  				});       
		/*$('#anotherClass_tree').html("");
		$('#anotherClass_tree').tree('append', {
			data : [ {
				id : 'point_selectSubcondition',
				text : '班级'
			} ]
		});

		var nodeTemp = $('#anotherClass_tree').tree('getRoot',
				"point_selectSubcondition");
		$.ajax({
			url : "/Examination2.0/findClass.action",
			type : "POST",
			dataType : "JSON",
			success : function(data) {
				
				for (var i = 0; i < data.obj.length; i++) {
					$('#anotherClass_tree').tree('append', {
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
		
		$('#anotherClass_tree').tree({
			onClick : function(node) {
				id = node.id;
				$("#show_anotherClass_class").html(node.text);
				$("#point_classid").html(id);
			}
		});*/
		
	}
	
	var pointid;//试卷id
	
	
	//重新指派班级
	function anotherClass(){
		
		var anotherTime=$("#checkDate").val();
		var anotherClassId=$("#show_anotherClass_class").val();
		
		//console.info("anotherClassPoint:"+anotherClassPoint);
		if(anotherTime==''){
			alert("请选择时间！");
			return;
		}
		
		if(anotherClassId==''){
			alert("请选择班级！");
			return;
		}
		 
		/*$.ajax({
			url:"/Examination2.0/pointPaper_AnotherPaper.action",
			type:"Post",
			datatype:"JSON",
			data:{"semester": anotherTime,"subjectId" : anotherClassId,"pid" : pointid},
			success:function(obj) {
				if (obj.responseCode == 1) {
					alert(obj.errorMessage);
				} else {
					alert("重新指派成功！");
					//$("#remarkInfo").val("");
				}
			}
			});*/
		
		$.post("/Examination2.0/pointPaper_AnotherPaper.action", {
			semester: anotherTime,
			subjectId : anotherClassId,
			pid : pointid
		}, function(obj) {
			
			if (obj.responseCode == 1) {
				alert(obj.errorMessage);
			} else {
				alert("重新指派成功！");
				//$("#remarkInfo").val("");
			}
		});
		
	}
	
	//查看详情
	function showPointanswer(pid,className,pdate,pstatus,subjectName,pname,descript,ptitle){
		$("#showpointtotal").hide();
		$("#pointAnotherClass").hide();
		$("#studentInfo").toggle();
		$("#className2").text(className);
		$("#subName2").text(subjectName);
		$("#pdate2").text(change(pdate));
		$("#pname2").text(pname);
		$("#descript2").text(descript);
		/*$.ajax({
			async: false,
			url:"/Examination2.0/pointAnswer_findPoinAnswerInfoByOpid.action",
			type:"post",
			datatype:"json",
			data:{"opid":pid},
			success:showPointAnswerInfo
			});*/
		//获取题目内容
		var answerArr=ptitle.split(',');
		answerIdeaArr=new Array();
		for(var j=0;j<answerArr.length;j++){
			answerIdeaArr[j]=answerArr[j].split('-')[1];
		}
		if( ptitle.charAt(ptitle.length-1)==","){
			ptitle=ptitle.substring(0,ptitle.length-1)
			}else{
				ptitle=ptitle;
			}
		if(ptitle!=""){
			ptitle="["+ptitle+"]";
			$.ajax({
				url:"/Examination2.0/pointAnswer_findAllPoinInfoByPids.action",
				type:"post",
				datatype:"json",
				data:{"ptitle":ptitle},
				success:showAllPointInfoAllStudent
				});	
		}
		PID=pid;
	}
	//显示所有学生的信息
	function showPointAnswerInfoAll(data){
		var pointInfos =   data ;
		//var pointInfoStr='<table id="showpointInfoAllstudent" border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1">'
		 pointInfoStr='<tr style="color:red" align="center" bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';"  ><td height="60" colspan="'+lengthPoint+'"><span >暂无学员进行知识评测</span></td></tr>';
		if(pointInfos.responseCode==0){
			if(pointInfos.obj!=null&&pointInfos.obj.length>0){
				//pointInfoStr='<table  border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1" style="word-break:break-all; word-wrap:break-all;">';
				var index=0;
				pointInfoStr="";
				$.each(pointInfos.obj,function(i,pointInfo){
					pointInfoStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left">';
					pointInfoStr+='<td bordercolor="#7EA6DC" align="center"  width="50px"  >'+pointInfo.name+'</td>'
					index=i+1;
					var scorce=pointInfo.answer.split(",");
					$.each(scorce,function(i,s){
						var dex=s.indexOf("-")+1;
						if(s!=""){
							pointInfoStr+='<td bordercolor="#7EA6DC" align="center"  width="50px"  >'+s.substr(dex)+'分</td>'
						}
						});
					if(pointInfo.idea!=undefined &&pointInfo.idea!=""&&pointInfo.idea!=null){
						pointInfoStr+='<td style="max-width:300px" bordercolor="#7EA6DC" align="center" >&nbsp;&nbsp;'+pointInfo.idea+'&nbsp;&nbsp;</td>';
					}else{
						pointInfoStr+='<td bordercolor="#7EA6DC" align="center" >&nbsp;&nbsp;无&nbsp;&nbsp;</td>';
					}
					pointInfoStr+='</tr>'
				});
				$("#showpointInfoAllstudent").append(pointInfoStr);
			}else{
				pointInfoStr+=""
				$("#showpointInfoAllstudent").append(pointInfoStr);
			}	
		}else{
			pointInfoStr+=""
			$("#showpointInfoAllstudent").append(pointInfoStr);
		}
	}
	//显示基本详情 div里面的东西
	function showPointAnswerInfo(data){
		$("#showpointtotal").hide();
		$("#studentInfo").toggle() ;
		var paperinfos =  data;
		if(paperinfos.responseCode==0){
			$.each(paperinfos.obj,function(idx,paperinfo){
				var className=$.trim(paperinfo.pointPaper.examineeClass.className);
				var subName=$.trim(paperinfo.pointPaper.subject.subjectName);
				var studentName=$.trim(paperinfo.name);
				var pdate=$.trim(paperinfo.pointPaper.pdate);
				var pname=$.trim(paperinfo.pointPaper.pname);
				var descript=$.trim(paperinfo.pointPaper.descript);
				var ptitle=$.trim(paperinfo.pointPaper.ptitle);
				var idea=$.trim(paperinfo.idea);
				var answer=$.trim(paperinfo.answer);
				if( answer.charAt(answer.length-1)==","){
					answer=answer.substring(0,answer.length-1)
					}else{
						answer=answer;
					}
				var answerArr=answer.split(',');
				answerIdeaArr=new Array();
				for(var j=0;j<answerArr.length;j++){
					answerIdeaArr[j]=answerArr[j].split('-')[1];
				}
				if( ptitle.charAt(ptitle.length-1)==","){
					ptitle=ptitle.substring(0,ptitle.length-1)
					}else{
						ptitle=ptitle;
					}
				if(ptitle!=""){
					ptitle="["+ptitle+"]";
					$.ajax({
						url:"/Examination2.0/pointAnswer_findAllPoinInfoByPids.action",
						type:"post",
						datatype:"json",
						data:{"ptitle":ptitle},
						success:showAllPointInfoAllStudent
						});	
				}
				
				if(descript==""){
					descript="无";
				}
				$("#className2").text(className);
				$("#subName2").text(subName);
				$("#studentName2").text(studentName);
				$("#pdate2").text(change(pdate));
				$("#pname2").text(pname);
				$("#descript2").text(descript);
			});
		}else{
			var pointInfoStr='<table   border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1">'
				 pointInfoStr+='<tr style="color: red;" bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" ><td height="40" colspan="9" align="center" style="color:red"><span >暂无学员进行知识测评</span></td></tr></table>';
			$("#showpointInfoAll").html(pointInfoStr);
		}
	}
	function showAllPointInfoAllStudent(data){
		var pointInfos =   data ;
		var pointInfoStr='<table   border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1">'
		 pointInfoStr+='<tr align="center" style="color:red" bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';"  ><td height="60" colspan="3"><span >暂无学员进行知识测评</span></td></tr>';
		lengthPoint=data.obj.length+2;
		if(pointInfos.responseCode==0){
			if(pointInfos.obj!=null&&pointInfos.obj.length>0){
				pointInfoStr='<table  id="showpointInfoAllstudent" border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1" style="word-break:break-all; word-wrap:break-all;">';
				pointInfoStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left">';
				var index=0;
				pointInfoStr+='<th bordercolor="#7EA6DC" align="center"  width="50px"  >&nbsp;&nbsp;姓名&nbsp;&nbsp;</th>'
				$.each(pointInfos.obj,function(i,pointInfo){
					 index=i+1;
					pointInfoStr+='<th bordercolor="#7EA6DC" align="center" >&nbsp;&nbsp;'+pointInfo.pcontent+'&nbsp;&nbsp;</th>';
				});
				pointInfoStr+='<th bordercolor="#7EA6DC"  align="center"  >&nbsp;&nbsp;意见或建议&nbsp;&nbsp;</th>'
				pointInfoStr+="</tr></table>"
				$("#showpointInfoAll").html(pointInfoStr);
			}else{
				pointInfoStr+="</table>"
				$("#showpointInfoAll").html(pointInfoStr);
			}
			$.ajax({
				url:"/Examination2.0/pointAnswer_findPoinAnswerInfoByOpidAll.action",
				type:"post",
				datatype:"json",
				data:{"opid":PID},
				success:showPointAnswerInfoAll
				});
		}else{
			pointInfoStr+="</table>"
			$("#showpointInfoAll").html(pointInfoStr);
		}
	}
	//查看
	function showTotalInfo(name) {
		var semester=$("input[name='semester1']:checked").val();
		var cid = $("select[name='examineeClass1']").val();
		var browser_width = $(document).width(); 
		$("#div1").css("width",browser_width*0.7); 
		var browser_height = $(document).height(); 
		$("#div1").css("height",browser_height); 
		/*$("#div1").show(1000);
	    $("#div2").show(1000);*/
		$("#div1").toggle();
		$("#div2").toggle();
		$.post("/Examination2.0/chapter_getTotal.action", {
			name : name,
			cid : cid,
			semester : semester
		}, function(data) {
			var data = JSON.parse(data); 
			if (data.responseCode == 0) {
				//var str="<br/><br/> <button type='button' onclick='closeInfo()' name=\"关闭\">关闭界面</button>";
				$("#div2").html(data.obj);
			} else {
				//
				alert(data.errorMessage);
			}
		});
	}