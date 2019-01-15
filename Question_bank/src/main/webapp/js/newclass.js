var val;
$(function(){
	
	val=$("#semesterS1").attr('value');
	$("#semesterS1").prop('checked',true);
})

function reset(){
	$("#semesterS1").checked=true;
	$("#className").val("");
	$("#openTime").val("");
	$("#overTime").val("");
	$("#textarea").val("");
}
var check;
function checkClassName(){
	var className=$("#className").val();
	$.post("/Examination2.0/examineeclass_checkClassName.action",{className:className},function(obj){
		if(obj.responseCode==0){
			$("#classTip").html("该班级已经存在，请重新输入班级编号");
			check=null;
			return;
		}else{
			check="1";
			return;
		}
	});
}

function submitForm(){
	
	 $("#semesterS1").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	  
	  $("#semesterS2").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	  
	  $("#semesterS3").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	
	var semester=val;
	var className=$("#className").val();
	var createDate=$("#openTime").val();
	var overDate=$("#overTime").val();
	var remark=$("#textarea").val();
	
	if(className=="" || className==null){
		alert("班级不能为空");
		return;
	}
	
	checkClassName();
	if(check==null){
		alert("请重新输入班级编号");
		return;
	}
	
	if(createDate=="" || createDate==null){
		alert("开班时间不能为空");
		return;
	}
	
	
	$.post("/Examination2.0/examineeclass_addClass.action",{semester:semester,className:className,
		createDate:createDate,overDate:overDate,remark:remark},
			function(obj){
			if(obj.responseCode==0){
				$("#strPromp").text("班级添加成功");
				reset();
			}
			else if(obj.responseCode==1){
				$("#strPromp").val(obj.errorMessage);
			}
	});
}