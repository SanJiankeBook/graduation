	var selectedvalue="";
	function createSelectOption(s){
 		selectedvalue=s;
 		//根据学期显示课程名称
 		$.ajax({
			url:"/Examination2.0/subject_findSubjectBySemester.action",
			type:"post",
			datatype:"json",
			data:{"semester":selectedvalue},
			success:showSubjectName
			});
 		//根据学期显示班级 
 		$.ajax({
			url:"/Examination2.0/writingPaper_getExamineeClassNameAndClassId.action",
			type:"post",
			datatype:"json",
			data:{"semester":selectedvalue},
			success:showAllExamineeClass
			});
 		$("#examineeName").html("<option>--请选择--</option>");
	}

 	$(function(){
 		selectedvalue="S1";
 		//根据学期显示课程名称（默认的学期尾S1）
 		$.ajax({
			url:"/Examination2.0/subject_findSubjectBySemester.action",
			type:"post",
			datatype:"json",
			data:{"semester":selectedvalue},
			success:showSubjectName
			});	
 		//根据学期显示班级 （默认的学期尾S1）
 		$.ajax({
			url:"/Examination2.0/writingPaper_getExamineeClassNameAndClassId.action",
			type:"post",
			datatype:"json",
			data:{"semester":selectedvalue},
			success:showAllExamineeClass
			});	
 		$("#examineeName").html("<option>--请选择--</option>");
 	});
	//根据学期显示课程名称函数
 	function showSubjectName(data){
 		var subjects =  data ;
 		var optionstr='<option selected="selected" value="-1">--请选择--</option>';
		if(subjects.responseCode==0&&subjects.obj.length>0){
			$.each(subjects.obj, function(i,subject){
				optionstr+='<option value="'+subject[0]+'">'+subject[1]+'</option>';	  														
			});	
		}																
		$("#subjectOption").html(optionstr);
	}
 	

 	//把拼好的班级加到班级下拉框中
	function showAllExamineeClass(data){
		var examieeClassNames =  data ;
			var examineeClassStr="<option>--请选择--</option>";
			for(var i=0;i<examieeClassNames.obj.length;i++){
				examineeClassStr+="<option value='"+examieeClassNames.obj[i].className+"' name='className'>"+examieeClassNames.obj[i].className+"</option>";
					
			}
			$("#examclassName").html(examineeClassStr);	
		
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
		}else{
			var optionstr="<option>--请选择--</option>";
			$("#examineeName").html(optionstr);
		}
	}
	function showExamineeNameOption(data){
		var classNameInfos =  data ;
		var optionstr="<option>--请选择--</option>";
			for(var i=0;i<classNameInfos.length;i++){
				optionstr+="<option value='"+classNameInfos[i].name+"' name='stuName'>"+classNameInfos[i].name+"</option>";	  
			}
		$("#examineeName").html(optionstr);
	}
	//根据学生名称，课程id,班级班级名称来显示显示测评留言信息
	function showMessagesInfo(){
		var subjectid=$("#subjectOption").val();
		var className=$.trim($("#examclassName").val());
		var stuName=$.trim($("#examineeName").val());
		if(stuName=="--请选择--"){
			stuName="";
		}
		if(className=="--请选择--"){
			className="";
		}
		$.ajax({
			url:"/Examination2.0/pointAnswer_findPointAnswerInfo.action",
			type:"post",
			datatype:"json",
			data:{"examClassName":className,"subjectid":subjectid,"stuName":stuName},
			success:showPointAnswerInfo
		});	
		
	}
	function showPointAnswerInfo(data){
		var messageinfos =   data ;
		var str="<div class=\"showMessageInfoDiv\"><table width='680' border='1' align='center' cellpadding='1' bordercolor='#FFFFFF' cellspacing='0'>";
		if(messageinfos.responseCode==0){
			if(messageinfos.obj!=null){
				$.each(messageinfos.obj,function(index,item){ 
					str+="<tr><td><div class=\"messageInfoStyle\"><h4>"+item.name;
					str+="&nbsp;&nbsp;在&nbsp;&nbsp;"+item.pointPaper.pname+"&nbsp;&nbsp;课程中说：</h4><span>"+item.idea;
					str+="</span><br /></div></td></tr>";
				});
			}
		}	
		str+="</table></div>";
		$("#findMessagesInfo").html(str);
	}