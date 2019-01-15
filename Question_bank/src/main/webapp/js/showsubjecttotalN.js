	//显示班级下拉框
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
				$("#examclassid").html(optionstr);
  				});  
	});
	
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
				$("#examclassid").html(optionstr);
  				});  				  		
 	}
	
	
	
	function changeSemsetere(){
		var examName=$.trim($("#examclassid").val());
		$.post("/Examination2.0/writingPaper_getId.action",{"examName":examName}, function(data){
			var classId=data;		
			var semseter=$.trim($("input[name='semseter']:checked").val());
			if(classId!="--请选择--"){
				$.ajax({
					url:"/Examination2.0/pointAnswer_findsubjectResult.action",
					type:"post",
					datatype:"json",
					data:{"semseter":semseter,"classId":classId},
					success:showAllPointAnswerInfo
				});	
			}
		});  
	}
	function showAllPointAnswerInfo(data){
		var returnInfos =  data ;
		if(returnInfos.responseCode==0){			
			var str=" <table width='680' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
			if(returnInfos.obj==null&&returnInfos.obj.length==0){
				str+="<tr><td colspan='6' style='color:red'>对不起,没有符合查询条件的测评试卷!</td></tr>";
			}else{
				var pcount=returnInfos.obj[returnInfos.obj.length-1];
				var str=" <table width='680' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
				for(var idx=0;idx<returnInfos.obj.length-1;idx++){
					var index=idx+1;
					
					str+="<tr id=\""+index+"\" bgcolor=\"#EDECEB\" onMouseOver=\"this.bgColor='#93BBDF';\" onMouseOut=\"this.bgColor='#EDECEB';\" align=\"center\">";
					str+="<td align=\"center\" width=\"15%\">"+index+"</td>";
					str+="<td align=\"center\" width=\"15%\">"+returnInfos.obj[idx][0]+"</td>";
					str+="<td align=\"center\" width=\"25%\">"+returnInfos.obj[idx][1]+"</td>";
					str+="<td align=\"center\" width=\"15%\">"+pcount+"</td>";
					str+="<td align=\"center\" width=\"15%\">"+returnInfos.obj[idx][3]+"</td>";
					if(returnInfos.obj[idx][4]>2){
						str+="<td width=\"15%\" align=\"center\" style=\"color:green\">"+returnInfos.obj[idx][4].toFixed(2)+"</td>";
					}else{
						str+="<td style=\"color:red\" width=\"15%\" align=\"center\">"+returnInfos.obj[idx][4].toFixed(2)+"</td>";
					}
					str+="</tr>"
				}
					
			
			}
			str+="</table>";
			$("#paperanswerinfo").html(str);
		}else{
			var str=" <table width='680' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
			str+="<tr><td colspan='6' style='color:red'>对不起,没有符合查询条件的测评试卷!</td></tr></table>";
			$("#paperanswerinfo").html(str);
			//alert("没有该条件对应的测评信息!");
		}
		
		
	}