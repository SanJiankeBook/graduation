$(function() {
	$('#changeClass').hide();
	var result = null;
	result = getQueryString();
	console.info(result);
	if (result == null || result == "") {
		var semester = $("#semesterS1").attr('value');
		$("#semesterS1").prop('checked', true);
		showClassName(semester);
	} else {
		$.ajaxSettings.async = false;
		var arr = result.toString().split(",");
		var op = arr[0].split("=")[1];
		var semester = arr[1].split("=")[1];
		var className = arr[2].split("=")[1];
		$("#semester" + semester).prop('checked', true);
		showClassName(semester);
		$("#className option").each(function() {
			if ($(this).text() == className) {
				$(this).attr("selected", true);
				return;
			}
		});
		// window.history.back(-1);
		$("#back").show();
		serchExaminee(className);
	
	}
	

});

function backPage() {
	window.history.back(-1);
}
var len = 0;
function getQueryString() {

	var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+",
	"g"));

	if (result == null) {

		return "";

	}

	for (var i = 0; i < result.length; i++) {

		result[i] = result[i].substring(1);

	}

	return result;

}

function showClassName(semester) {
	$.post("/Examination2.0/examineeclass_showClassList.action", {
		semester : semester
	}, function(obj) {
		len = obj.length;
		$("#className").html("");
		if (obj == null) {
			$("#className").append("<option>--班级名称--</option>");
		}
		for (var i = 0; i < obj.length; i++) {
			$("#className").append(
					"<option value='" + i + "'>" + obj[i].className
					+ "</option>");
		}
	});
}

function changeSemester(semester) {
	showClassName(semester);
}

function serchExaminee(className) {
	if (className == null || className == "") {
		className = $("#className").find("option:selected").text().trim();
	}

	$.post(
			"/Examination2.0/examineeclass_showExamineeList.action",
			{
				className : className
			},
			function(obj) {
				$("#listexaminee").show();
				$("#message").text("查询到 " + obj.length + " 个学生");
				$("#examineerows").html("");
				for (var i = 0; i < obj.length; i++) {
					var status;
					//学生状态  0报名  1在读 （完成缴费） 2在读（未完成缴费）3毕业  4休学 
					if(obj[i].idCard==0){
						status="报名";
					}else if(obj[i].idCard==1){
						status="在读完成缴费";
						
					}else if(obj[i].idCard==1){
						status="在读未完成缴费";
						
					}else if(obj[i].idCard==1){
						status="毕业";
						
					}else if(obj[i].idCard==1){
						status="休学";
						
					}
					
					$("#examineerows")
					.append(
							"<tr bgcolor='#EDECEB' onMouseOver=this.bgColor='#93BBDF';"
							+ "onMouseOut=this.bgColor='#EDECEB'; align='center' "
							+ "id='"
							+ obj[i].name
							+ "'>"
							+ "<td align='left'>"
							+ (i + 1)
							+ "</td>"
							/*+ "<td align='left' id='className"
													+ (i + 1)
													+ "'>"
													+ obj[i].className
													+ "</td>"*/
							+ "<td align='left' id='examinee_"
							+ (i + 1)
							+"'>"
							+ obj[i].name
							+ "</td>"
							/*+ "<td align='left'><input type='text' "
													+ "value='"
													+ obj[i].idCard
													+ "' class='text4' size='18' maxlength='18' "
													+ "name='idCard' id='idCardExaminee_"
													+ (i + 1)
													+ "'  style='width:100%'></td>"*/
							+ "<td align='left' id='idCardExaminee_"
							+ (i + 1)
							+ "'>"
							+ status
							+ "</td>"
							+ "<td align='left' id='telExaminee_"
							+ (i + 1)
							+"'>"
							+ obj[i].tel
							+ "</td>"
							+ "</tr>");
				}
			});
}

function nextClassExaminee(stuName,className,id){
	$.post("/Examination2.0/examineeclass_showSameEditList.action", {
		className : className
	}, function(obj) {
		len = obj.length;
		$("#classNameChoose").html("");
		if (obj == null) {
			$("#classNameChoose").append("<option>--班级名称--</option>");
		}
		for (var i = 0; i < obj.length; i++) {
			$("#classNameChoose").append(
					"<option value='" + id + "'>" + obj[i].className
					+ "</option>");
		}
	});
		  layer.open({
		  type: 1,
		  area: ['400px', '260px'],
		  offset: ['100px', '300px'],

		  shadeClose: true, //点击遮罩关闭
		  content:  $('#changeClass')
		  });
	
	
}

function changeClass(){
	id=$("#classNameChoose").val();
	className=$("#classNameChoose").find("option:selected").text().trim();
	var userName = localStorage.getItem("systemUser_userName");
	
	$.post("/Examination2.0/examineeclass_changeClass.action", {
		
		examineeNames:userName,
		className : className,
		id:id
	}, function(obj) {
		//alert(obj.responseCode);
		if(obj.responseCode == 0){
			alert("转班成功！");
			parent.window.location.reload();
	        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	        parent.layer.close(index);
		}else{
			alert("转班失败！");
		}
	});


}

function updateExaminee(id, oldname, className, oldTel) {

	if (confirm("确定要修改？")) {
		var name = $("#examinee_" + id).val();
		//var idCard = $("#idCardExaminee_" + id).val();
		var tel = $("#telExaminee_" + id).val();
		//className = $("#className" + id).text();
		//alert(id+","+oldname+","+className+","+oldTel);
		if (name == oldname  && tel == oldTel) {
			alert("请确定您要修改的内容！！！");
			return;
		}

		/*if (idCard.length < 18) {
			alert("身份证必须为18位！请确定您要修改的内容！！！")
			return;
		}*/
		if (tel.length < 11) {
			alert("手机号码必须为11位！请确定您要修改的内容！！！")
			return;
		}

		if (name == null || name == "") {
			alert("考生姓名不能为空！请确定您要修改的内容！！！");
			return;
		}
		
		$.post("/Examination2.0/examineeclass_updateExaminee.action", {
			oldname : oldname,
			name : name,
			//idCard : idCard,
			tel : tel,
			className : className
		}, function(obj) {
			if (obj.responseCode == 0) {
				alert("修改成功");
				location.reload();
			} else {
				alert(obj.errorMessage);
			}
		});
	}
}



function deleteExaminee(name) {
	var className = $("#className").find("option:selected").text().trim();
	if (!confirm("确定要删除？")) {
		return false;
	}
	$.post("/Examination2.0/examineeclass_deleteExaminee.action", {
		name : name,
		className : className
	}, function(obj) {
		if (obj.responseCode == 0) {
			alert("删除成功");
			serchExaminee();
		}
	});
}
