var semester;
$(function() {
	createVersionOption();
	semester = $("#work_semesterS1").attr('value');
	var dateTime = $("#dateTime").find("option:selected").text().trim();

	$("#work_semesterS1").prop('checked', true);
	showClassName(semester);
	$("#checkDate").val(changeTime(new Date()));
})


function changeTime(time) {
	if (time == null) {
		var now = new Date();
	} else {
		var now = new Date(time);
	}

	var year = now.getFullYear(); //年
	var month = now.getMonth() + 1; //月
	var day = now.getDate(); //日

	//    var hh = now.getHours();            //时
	//    var mm = now.getMinutes();          //分

	var clock = year + "-";

	if (month < 10)
		clock += "0";

	clock += month + "-";

	if (day < 10)
		clock += "0";

	clock += day + " ";

	//    if(hh < 10)
	//        clock += "0";

	//    clock += hh + ":";
	//    if (mm < 10) clock += '0'; 
	//    clock += mm; 
	return (clock);
}

function checkSemesters(semester) {
	showClassName(semester);
}

function showClassName(semester) {
	$.post("examineeclass_showClassList.action", {
		semester : semester
	}, function(obj) {
		$("#work_examineeClass").html("<option value='0'>--请选择--</option>");
		if (obj == null) {
			return;
		}
		var len=obj.length;
		for (var i = 0; i < len; i++) {
			$("#work_examineeClass").append(
					"<option value='" + obj[i].id + "'>" + obj[i].className
					+ "</option>");
		}
	});
	changeVersion()
	changeSubject()
}

function changeVersion() {
	semester = $('input[name="semester"]:checked').val();
	version = $('#work_version').val();
	createSelectOption(version, semester);
}
//生成科目列表
function createSelectOption(version,semester){
	$.ajaxSettings.async = false; 
	$.ajax({
		url:"initData_subject.action",
		type:"post",
		data:{"editionId":version,"semester":semester},
		datatype:"json",
		success:function(examineeClassList){
			var optionstr="";
			if(examineeClassList.responseCode!=1){
				optionstr+="<option value='0'>请选择</option>";
				for(var i=0;i<examineeClassList.obj.length;i++){
					optionstr+="<option value='"+examineeClassList.obj[i].id+"'>"+examineeClassList.obj[i].subjectName+"</option>";
				}
			}else{
				optionstr+="<option value=0 >没有数据</option>"
			}

			$("#work_subject").html(optionstr);
		}
	});
}

function changeSubject() {
	subjectId = $('#work_subject').val();
	createChapterOption(subjectId);
}

//生成章节列表
function createChapterOption(subjectId){
	$.ajaxSettings.async = false; 
	$.ajax({
		url:"initData_chapter.action",
		type:"post",
		data:{"subjectId":subjectId},
		datatype:"json",
		success:function(examineeClassList){
			var optionstr="";
			if(examineeClassList.responseCode!=1){
				optionstr+="<option value='0'>请选择</option>";
				for(var i=0;i<examineeClassList.obj.length;i++){
					optionstr+="<option value='"+examineeClassList.obj[i].id+"'>"+examineeClassList.obj[i].chapterName+"</option>";
				}
			}else{
				optionstr+="<option value=0 >没有数据</option>"
			}

			$("#work_chapter").html(optionstr);
		}
	});
}

//生成版本列表
function createVersionOption(){
	var currentUse="";
	$.ajaxSettings.async = false; 
	$.ajax({
		url:"initData_version.action",
		type:"post",
		datatype:"json",
		success:function(examineeClassList){
			var optionstr="<option value=0 >--请选择--</option>";	
			if(examineeClassList.responseCode!=1){
				for(var i=0;i<examineeClassList.obj.length;i++){
					optionstr+="<option value='"+examineeClassList.obj[i].id+"' name='className'>"+examineeClassList.obj[i].editionName+"</option>";
					if(examineeClassList.obj[i].currentUse==1){
						currentUse=examineeClassList.obj[i].id;
					}
				}
			}else{
				optionstr+="<option value=-1 >没有数据</option>"
			}	
			$("#work_version").html(optionstr);
			$("#work_versionversion option[value="+currentUse+"]").attr("selected",true)

		}

	});

}


function submit_check(){

	var semester = $('input[name="semester"]:checked').val();
	var classname=$("#work_examineeClass").val();
	var checkDate=$("#checkDate").val();
	var editionid=$("#work_version").val();
	var subjectid=$("#work_subject").val();
	var chapterid=$("#work_chapter").val();
	var CheckingName=$("#CheckingName").val();
	var description=$("#Checkingdescription").val();
	var CheckingRemark=$("#CheckingRemark").val();

	if(classname==0){
		alert('请选择班级')
		return;
	}
	if(editionid==0){
		alert('请选择版本')
		return;
	}
	if(subjectid==0){
		alert('请选择科目')
		return;
	}
	if(chapterid==0){
		alert('请选择章节')
		return;
	}

	if(CheckingName==""){
		alert('请填写作业名')
		return;
	}
	$.post("/Examination2.0/work_addWork.action",{
		examineeclassid:classname,
		checkdate:checkDate,
		wname:CheckingName,
		description:description,
		semester:semester,
		editionid:editionid,
		subjectid:subjectid,
		chapterid:chapterid,
		remark:CheckingRemark
	},function(data){
		if(data.responseCode==0){
			alert("添加作业成功")
			$("#CheckingName").val("")
			$("#Checkingdescription").val("")
			$("#CheckingRemark").val("")
		}else{
			aler("添加作业失败")
		}
	},'json')


}