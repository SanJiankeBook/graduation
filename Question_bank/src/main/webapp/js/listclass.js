$(function(){
	$('#changeClass').hide();
	$('#updateStudentInfo').hide();
	$('#showStudentInfo').hide();
	var semester=$("#semesterS1").attr('value');
	$("#semesterS1").prop('checked',true);
	changeSemester(semester);
});


function changeSemester(semester){ 

	$.post("examineeclass_showClassList.action",{semester:semester},function(obj){
		$("#message").text("查询到 "+obj.length+" 个班级");
		$("#showlist").html("");
		for( var i=0;i<obj.length;i++){
			var remark=obj[i].remark.replace(" ", "&nbsp;");
			//var remark=obj[i].remark.replace(",", "\,");
			//remark=remark.replace(".", "\.");
			//remark=remark.replace(" ", "&nbsp;");
			$("#showlist").append("<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'  onMouseOut=this.bgColor='#EDECEB';  align='center' > "+
					"<td align='center'>"+obj[i].className+"</td>"+
					" <td align='center' title=''> "+obj[i].remark+"  </td>"+
					"<td align='center'>"+obj[i].studentCount+"</td>"+
					"<td align='center'>"+(obj[i].createDate==undefined||obj[i].createDate=='undefined'?"":obj[i].createDate )+"</td>"+
					" <td align='center'>"+ (obj[i].overDate==undefined||obj[i].overDate=='undefined'?"":obj[i].overDate )+"</td>"+
					/*"  <a href='back/student/listexaminee.html?op=show&semester="+semester+"&className="+obj[i].className+"'   title='查看班级学生信息'>学生信息</a>"+
					" </td>"+*/

					"<td align='center'> <a  href='javascript:void(0)' onClick=serchExaminee('"
					+obj[i].className
					+ "') title='查看班级学生信息'>学生信息</a>&nbsp; </td>"+

					"<td align='center'> <a  href='javascript:void(0)' onClick=updateExaminessinfo('"
					+obj[i].id+"','"+obj[i].semester+"','"+obj[i].className+"','"+obj[i].createDate+"','"+obj[i].overDate+"','"+remark
					+ "') title='修改班级信息'>修改</a>&nbsp; </td>"+
					/*"	<td><a href='back/student/updateclass.html?id="+obj[i].id+",&remark="+obj[i].remark+",&semester="
					+obj[i].semester+",&studentCount="+obj[i].studentCount+",&createDate="
					+obj[i].createDate+",&overDate="+obj[i].overDate+",&className="+obj[i].className+"'>修改</a>&nbsp;</td>"+*/
			" </tr>");
		}
	});
}

function deleteClass(count,id){
	if(!confirm("确定要删除？")){
		return false;
	}
	if(count!=0 ||count!="0"){
		alert("该班级下还有考生，不能删除");
		return false;
	}
	$.post("examineeclass_deleteClass.action",{id:id},function(obj){
		if(obj.responseCode==0){
			alert("删除成功");
			//location.reload(); 
		}
	});
}
//显示学生个人信息
function serchExaminee(className) {
	$('#updateStudentInfo').hide();
	$('#showStudentInfo').show();
	if (className == null || className == "") {
		className = $("#className").find("option:selected").text().trim();
	}

	$.post(
			"examineeclass_showExamineeList.action",
			{
				className : className
			},

			function(obj) {
				$("#listexaminee").show();
				$("#message").text("查询到 " + obj.length + " 个学生");
				$("#examineerows").html("");
				
				if(obj!=null && obj!=""){
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

						$("#examineerows").append(
								"<tr bgcolor='#EDECEB' onMouseOver=this.bgColor='#93BBDF'; onMouseOut=this.bgColor='#EDECEB'; align='center' "
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
				}else{
					$("#examineerows").append('<tr><td colspan="5" style="text-align: center;">暂无数据</td></tr>');
				}
			});
}

function nextClassExaminee(stuName,className,id){
	$.post("examineeclass_showSameEditList.action", {
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
			/*parent.window.location.reload();*/
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}else{
			alert("转班失败！");
		}
	});


}

function updateExaminessinfo( id,semester,className,createDate,endDat,remark){
	$('#showStudentInfo').hide();
	$('#updateStudentInfo').show();

	//alert(semester);
	$("#idInfo").val(id);
	$("input[name=semester2][value=" +semester + "]").attr("checked", true); //设置当前性别选中项
	$("#className").val(className);
	$("#openTime").val(createDate);
	$("#overTime").val(endDat);
	$("#textarea").val(decodeURIComponent(remark));

	/* semester=semester.substring(1,2);
	 $("#semesterS"+semester).prop("checked",true);
	if(semester=="1"){
		$("#semesterS1").attr("checked",true);
		document.getElementById("semesterS1").checked = "checked";
	}else if(semester=="2"){
		$("#semesterS2").attr("checked",true);
		document.getElementById("semesterS2").checked = "checked";
	}else if(semester=="3"){
		$("#semesterS3").attr("checked",true);
		document.getElementById("semesterS3").checked = "checked";
	}
	$("#semesterS1").click(function(){
		val=$('input:radio[name="semester"]:checked').val();
	});
	$("#semesterS2").click(function(){
		val=$('input:radio[name="semester"]:checked').val();
	});
	$("#semesterS3").click(function(){
		val=$('input:radio[name="semester"]:checked').val();
	});*/
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

//班级信息修改
function submitForm(){
	var semester=$('input:radio[name="semester2"]:checked').val();
	var id=$("#idInfo").val();
	var className=$("#className").val();
	var createDate=$("#openTime").val();
	var overDate=$("#overTime").val();
	var remark=$("#textarea").val();
	$.post("examineeclass_updateClass.action",{id:id,semester:semester,className:className,
		createDate:createDate,overDate:overDate,remark:remark},
		function(obj){
			if(obj.responseCode==0){
				var semester=$("#semesterS1").attr('value');
				$("#semesterS1").prop('checked',true);
				changeSemester(semester);
				alert("班级信息更新成功");
			}
			else if(obj.responseCode==1){
				$("#strPromp").val(obj.errorMessage);
			}
		});

}


