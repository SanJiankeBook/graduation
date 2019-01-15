
//function listClass(semester){
//	showClassName(semester);
//
//}
//function listExaminee(){
//	var className=$("#examClass").find("option:selected").text().trim();
//	showExamineeName(className);
//}

/**
 * 显示班级名称列表
 */
function listClass(semester){
	$.post("/Examination2.0/examineeclass_showClassList.action",{semester:semester},function(obj){
		$("#examClass").html("");
		if(obj==null){
			$("#examClass").append("<option>--班级名称--</option>");
		}
		for( var i=0;i<obj.length;i++){
			$("#examClass").append("<option value='"+i+"'>"+obj[i].className+"&nbsp;&nbsp;&nbsp;&nbsp;</option>");
		}
		
		listExaminee();
	});
}
/**
 * 显示考生姓名列表
 */

function listExaminee(){
	var className=$("#examClass").find("option:selected").text().trim();
	$.post("/Examination2.0/examineeclass_showExamineeList.action",{className:className},function(obj){
		$("#examineeName").html("");
		for( var i=0;i<obj.length;i++){
			$("#examineeName").append("<option value='"+i+"'>"+obj[i].name+"&nbsp;&nbsp;&nbsp;&nbsp;</option>");
		}
	});
}
/**
 *更新所有密码
 */
function updateAllPwd(){
	var className=$("#examClass").find("option:selected").text().trim();
	var pwd=$("#newPwd1").val();
	if(pwd==""){
		alert("密码不能为空");
	}
	$.post("/Examination2.0/examineeclass_updateAllExaminee.action",{className:className,pwd:pwd},function(obj){
		if(obj.responseCode==0){
			$("#strSpan").text("修改全班密码成功");
		}else{
			$("#strSpan").text(obj.errorMessage);
		}
	});
	
}

/**
 * 更新单个密码
 */

function updatePwd(){
	var className=$("#examClass").find("option:selected").text().trim();
	var name=$("#examineeName").find("option:selected").text().trim();
	var pwd=$("#newPwd1").val();
	if(pwd==""){
		alert("密码不能为空");
	}
	
	$.post("/Examination2.0/examineeclass_updatepassword.action",{className:className,name:name,pwd:pwd},function(obj){
		if(obj.responseCode==0){
			$("#strSpan").text("修改单个密码成功");
		}else{
			$("#strSpan").text(obj.errorMessage);
		}
	});
	
}
